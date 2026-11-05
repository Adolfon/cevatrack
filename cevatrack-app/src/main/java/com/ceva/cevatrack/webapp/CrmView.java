package com.ceva.cevatrack.webapp;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ceva.cevatrack.domain.CrmStatus78;
import com.ceva.cevatrack.repository.CrmStatus78Repository;
import com.ceva.cevatrack.webapp.DatedConsignmentList;
import com.ceva.cevatrack.wsmicrolise.findconsignmentsummaries.ConsignmentSummary;
import com.ceva.cevatrack.wsmicrolise.getconsignment.ClausedConsignmentDetail;
import com.ceva.cevatrack.wsmicrolise.getconsignment.Consignment;
import com.ceva.cevatrack.wsmicrolise.getconsignment.ConsignmentDetail;

/**
 * Application-scoped bean that:
 *  - Serves the CRM view (date window, ordered by ATA descending)
 *  - Runs a background refresh every REFRESH_INTERVAL_HOURS
 *  - Calls ConsignmentListManager.getConsignmentSummaries / getConsignmentDetail
 *  - Filters status 78 ("to be delivered")
 *  - Maps line-level details into crm_status78
 *  - Purges data older than RETENTION_DAYS
 *
 * Java 8 + existing JSF @ManagedBean style used in the project.
 */
@ManagedBean(name = "crmView")
@ApplicationScoped
public class CrmView implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(CrmView.class);

    // -------------------------------------------------------------------------
    // Global configuration (change here or later move to application.properties)
    // -------------------------------------------------------------------------
    public static final String DEFAULT_NETWORK = "technet";
    public static final int REFRESH_INTERVAL_HOURS = 4;
    public static final int RETENTION_DAYS = 10;
    public static final int STATUS_TO_DELIVER = 78;
    public static final int DEFAULT_WINDOW_HOURS = 48;

    private static final String[] DATE_PATTERNS = {
            "yyyy-MM-dd HH:mm:ss",
            "yyyy-MM-dd'T'HH:mm:ss",
            "dd/MM/yy HH:mm",
            "dd/MM/yyyy HH:mm",
            "yyyy-MM-dd HH:mm"
    };

    @Inject
    private CrmStatus78Repository repository;

    @ManagedProperty(value = "#{consignmentListManager}")
    private ConsignmentListManager consignmentListManager;

    private Date fromDate;
    private Date toDate;
    private List<CrmStatus78> rows;

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    // -------------------------------------------------------------------------
    // Lifecycle
    // -------------------------------------------------------------------------

    @PostConstruct
    public void init() {
        initDefaultWindow();
        loadData();
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                refreshFromWebService();
            }
        }, 0, REFRESH_INTERVAL_HOURS, TimeUnit.HOURS);
        LOG.info("CRM status-78 scheduler started (every {} h, network={})", REFRESH_INTERVAL_HOURS, DEFAULT_NETWORK);
    }

    private void initDefaultWindow() {
        Calendar cal = Calendar.getInstance();
        toDate = cal.getTime();
        cal.add(Calendar.HOUR_OF_DAY, -DEFAULT_WINDOW_HOURS);
        fromDate = cal.getTime();
    }

    // -------------------------------------------------------------------------
    // View actions
    // -------------------------------------------------------------------------

    public void loadData() {
        if (fromDate == null || toDate == null) {
            initDefaultWindow();
        }
        rows = repository.findByDateWindow(fromDate, toDate);
    }

    public void onDateSelect() {
        if (fromDate != null && toDate != null) {
            long maxMillis = 10L * 24 * 60 * 60 * 1000;
            if (toDate.getTime() - fromDate.getTime() > maxMillis) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(toDate);
                cal.add(Calendar.DAY_OF_YEAR, -10);
                fromDate = cal.getTime();
            }
        }
        loadData();
    }

    // -------------------------------------------------------------------------
    // Background refresh – real integration with ConsignmentListManager
    // -------------------------------------------------------------------------

    private void refreshFromWebService() {
        try {
            LOG.info("Starting CRM status-78 refresh for network {}", DEFAULT_NETWORK);

            String fromYyyymmdd = formatYyyymmdd(addDays(new Date(), -RETENTION_DAYS));
            String toYyyymmdd = formatYyyymmdd(new Date());

            if (consignmentListManager == null) {
                LOG.warn("consignmentListManager is null – cannot refresh");
                return;
            }

            // 1. Call existing getConsignmentSummaries
            DatedConsignmentList datedList = consignmentListManager.getConsignmentSummaries(
                    fromYyyymmdd, toYyyymmdd, true, false, DEFAULT_NETWORK, null);

            if (datedList == null || datedList.getConsignmentSummaries() == null) {
                LOG.info("No summaries returned for {} – {}", fromYyyymmdd, toYyyymmdd);
                purgeOld();
                return;
            }

            int processed = 0;
            int upserted = 0;

            // 2. Filter status 78 and process each
            for (ConsignmentSummary summary : datedList.getConsignmentSummaries()) {
                if (summary == null) {
                    continue;
                }
                if (summary.getConsignmentStatus() != STATUS_TO_DELIVER) {
                    continue;
                }
                processed++;

                String ref = summary.getConsignmentReference();
                if (StringUtils.isBlank(ref)) {
                    continue;
                }

                try {
                    // 3. Get full detail
                    Consignment detail = consignmentListManager.getConsignmentDetail(ref, DEFAULT_NETWORK);
                    if (detail == null) {
                        continue;
                    }

                    // 4. Map line-level details
                    List<CrmStatus78> entities = mapToEntities(detail, summary);
                    for (CrmStatus78 entity : entities) {
                        repository.saveOrUpdate(entity);
                        upserted++;
                    }
                } catch (Exception ex) {
                    LOG.warn("Failed to process consignment {}: {}", ref, ex.getMessage());
                }
            }

            LOG.info("CRM refresh finished – summaries status 78 processed: {}, rows upserted: {}", processed, upserted);

            // 5. Purge older than retention
            purgeOld();

        } catch (Exception e) {
            LOG.error("Error during CRM status-78 refresh", e);
        }
    }

    private void purgeOld() {
        try {
            Date cutoff = addDays(new Date(), -RETENTION_DAYS);
            int deleted = repository.deleteOlderThan(cutoff);
            LOG.info("Purged {} old CRM records", Integer.valueOf(deleted));
        } catch (Exception e) {
            LOG.error("Error during purge", e);
        }
    }

    /**
     * Maps a Consignment (header + lines) into one or more CrmStatus78 rows.
     * Prefers line-level fields; falls back to header when needed.
     */
    private List<CrmStatus78> mapToEntities(Consignment detail, ConsignmentSummary summary) {
        List<CrmStatus78> result = new ArrayList<CrmStatus78>();
        Date now = new Date();

        String siteId = firstNonBlank(detail.getSiteId(), summary != null ? summary.getSiteId() : null);
        String siteName = firstNonBlank(detail.getSiteName(), summary != null ? summary.getSiteName() : null);
        String routeCode = detail.getJourneyAlias();
        Date ata = parseDate(firstNonBlank(detail.getAta(), summary != null ? summary.getAta() : null));
        Date atd = parseDate(detail.getAtd());
        String orderReference = detail.getConsignmentCustomerOrderRef();
        String scannedHeader = detail.getConsignmentHeaderInfo1();
        String clauseHeader = detail.getClauseCode();

        List<ConsignmentDetail> lines = detail.getConsignmentDetails();
        if (lines == null || lines.isEmpty()) {
            // header-only fallback
            CrmStatus78 e = baseEntity(siteId, siteName, routeCode, ata, atd, orderReference, now);
            e.setOrderLineRef(detail.getConsignmentReference());
            e.setStatus(detail.getConsignmentStatus() != 0 ? Integer.valueOf(detail.getConsignmentStatus()) : null);
            e.setScanned(scannedHeader);
            e.setClauseReason(clauseHeader);
            result.add(e);
            return result;
        }

        for (ConsignmentDetail line : lines) {
            if (line == null) {
                continue;
            }
            CrmStatus78 e = baseEntity(siteId, siteName, routeCode, ata, atd, orderReference, now);
            e.setOrderLineRef(line.getDetailRef());
            e.setPlannedQty(Integer.valueOf(line.getPlannedQuantity()));
            e.setActualQty(Integer.valueOf(line.getActualQuantity()));
            e.setStatus(line.getStatus() != 0 ? Integer.valueOf(line.getStatus()) : Integer.valueOf(detail.getConsignmentStatus()));
            e.setScanned(firstNonBlank(line.getInfo1(), scannedHeader));
            e.setClauseReason(extractClauseReason(line, clauseHeader));
            result.add(e);
        }
        return result;
    }

    private CrmStatus78 baseEntity(String siteId, String siteName, String routeCode,
                                   Date ata, Date atd, String orderReference, Date now) {
        CrmStatus78 e = new CrmStatus78();
        e.setSiteId(siteId);
        e.setSiteName(siteName);
        e.setRouteCode(routeCode);
        e.setAta(ata);
        e.setAtd(atd);
        e.setOrderReference(orderReference);
        e.setNetwork(DEFAULT_NETWORK);
        e.setLastUpdated(now);
        e.setLastWsCall(now);
        e.setCreatedAt(now);
        return e;
    }

    private String extractClauseReason(ConsignmentDetail line, String fallback) {
        if (line.getClauseDetails() != null && !line.getClauseDetails().isEmpty()) {
            ClausedConsignmentDetail cd = line.getClauseDetails().get(0);
            if (cd != null && StringUtils.isNotBlank(cd.getClauseDescription())) {
                return cd.getClauseDescription();
            }
        }
        return fallback;
    }

    private String firstNonBlank(String a, String b) {
        if (StringUtils.isNotBlank(a)) {
            return a;
        }
        return b;
    }

    private Date parseDate(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        for (int i = 0; i < DATE_PATTERNS.length; i++) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERNS[i]);
                sdf.setLenient(false);
                return sdf.parse(value.trim());
            } catch (ParseException ignored) {
                // try next pattern
            }
        }
        LOG.debug("Could not parse date: {}", value);
        return null;
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    private String formatYyyymmdd(Date d) {
        if (d == null) {
            return "";
        }
        return new SimpleDateFormat("yyyyMMdd").format(d);
    }

    private Date addDays(Date d, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.DAY_OF_YEAR, days);
        return c.getTime();
    }

    // -------------------------------------------------------------------------
    // Getters / Setters for the XHTML
    // -------------------------------------------------------------------------

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public List<CrmStatus78> getRows() {
        return rows;
    }

    public void setRows(List<CrmStatus78> rows) {
        this.rows = rows;
    }

    public ConsignmentListManager getConsignmentListManager() {
        return consignmentListManager;
    }

    public void setConsignmentListManager(ConsignmentListManager consignmentListManager) {
        this.consignmentListManager = consignmentListManager;
    }
}
