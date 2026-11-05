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
 * Application-scoped scheduler that keeps the CRM table up to date.
 * Only the newest REFRESH_INTERVAL_HOURS slice is fetched each cycle;
 * data older than RETENTION_DAYS is removed.
 *
 * Java 8 + existing JSF @ManagedBean style used in the project.
 */
@ManagedBean(name = "crmScheduler", eager = true)
@ApplicationScoped
public class CrmScheduler implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(CrmScheduler.class);

    public static final String DEFAULT_NETWORK = "smartnet";
    public static final int REFRESH_INTERVAL_HOURS = 4;
    public static final int RETENTION_DAYS = 2;
    public static final int STATUS_TO_DELIVER = 78;

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

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    @PostConstruct
    public void init() {
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                refreshInterval();
            }
        }, 0, REFRESH_INTERVAL_HOURS, TimeUnit.HOURS);
        LOG.info("CRM scheduler started (every {} h, network={})", REFRESH_INTERVAL_HOURS, DEFAULT_NETWORK);
    }

    /** Last day that was successfully loaded (used for resume). */
    private Date lastSuccessfulDay;

    /** Scheduled job: newest interval only, then purge older than retention. */
    public void refreshInterval() {
        Date to = new Date();
        Date from = addHours(to, -REFRESH_INTERVAL_HOURS);
        LOG.info("Scheduled refresh – only the last {} hours", REFRESH_INTERVAL_HOURS);
        refresh(from, to);
    }

    /**
     * Full retention load, day by day, each day committed independently.
     * Runs in a background thread so it does not depend on the user session.
     * If a day fails, the next call resumes from that day.
     */
    public void startFullRetentionAsync() {
        final Date end = new Date();
        Date start = addDays(end, -RETENTION_DAYS);

        if (lastSuccessfulDay != null && lastSuccessfulDay.after(start)) {
            start = addDays(lastSuccessfulDay, 1);
            LOG.info("Resuming full retention load from {}", start);
        } else {
            LOG.info("Starting full retention load from {} to {}", start, end);
            lastSuccessfulDay = null;
        }

        final Date finalStart = start;
        new Thread(new Runnable() {
            @Override
            public void run() {
                runDayByDay(finalStart, end);
            }
        }, "crm-populate").start();
    }

    private void runDayByDay(Date start, Date end) {
        Calendar cursor = Calendar.getInstance();
        cursor.setTime(truncateToDay(start));

        while (!cursor.getTime().after(end)) {
            Date dayStart = cursor.getTime();
            Date dayEnd = addDays(dayStart, 1);
            if (dayEnd.after(end)) {
                dayEnd = end;
            }

            try {
                LOG.info("Loading day {}", formatYyyymmdd(dayStart));
                boolean ok = refresh(dayStart, dayEnd);
                if (!ok) {
                    LOG.error("Stopped at day {} – will resume from here next time", formatYyyymmdd(dayStart));
                    return;
                }
                lastSuccessfulDay = dayStart;
            } catch (Exception e) {
                LOG.error("Stopped at day {} – will resume from here next time", formatYyyymmdd(dayStart), e);
                return;
            }

            cursor.add(Calendar.DAY_OF_YEAR, 1);
        }

        LOG.info("Full retention load completed");
        lastSuccessfulDay = null;
    }

    public boolean isLoadIncomplete() {
        return lastSuccessfulDay != null;
    }

    private Date truncateToDay(Date d) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * Fetches and saves one window. Returns false if the call failed
     * (so the caller can stop and resume later).
     */
    private boolean refresh(Date from, Date to) {
        try {
            LOG.info("CRM status-78 refresh for network {} from {} to {}", DEFAULT_NETWORK, from, to);

            String fromYyyymmdd = formatYyyymmdd(from);
            String toYyyymmdd = formatYyyymmdd(to);

            if (consignmentListManager == null) {
                LOG.warn("consignmentListManager is null – cannot refresh");
                return false;
            }

            DatedConsignmentList datedList = consignmentListManager.getConsignmentSummaries(
                    fromYyyymmdd, toYyyymmdd, true, false, DEFAULT_NETWORK, null);

            if (datedList == null || datedList.getConsignmentSummaries() == null) {
                LOG.info("No summaries returned for {} – {}", fromYyyymmdd, toYyyymmdd);
                return true;
            }

            int processed = 0;
            int upserted = 0;

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
                    Consignment detail = consignmentListManager.getConsignmentDetail(ref, DEFAULT_NETWORK);
                    if (detail == null) {
                        continue;
                    }
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
            return true;

        } catch (Exception e) {
            LOG.error("Error during CRM status-78 refresh", e);
            return false;
        }
    }

    private void purgeOld() {
        try {
            Date cutoff = addDays(new Date(), -RETENTION_DAYS);
            int deleted = repository.deleteOlderThan(cutoff);
            LOG.info("Purged {} old CRM records (older than {} days)", Integer.valueOf(deleted), RETENTION_DAYS);
        } catch (Exception e) {
            LOG.error("Error during purge", e);
        }
    }

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
                // try next
            }
        }
        LOG.debug("Could not parse date: {}", value);
        return null;
    }

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

    private Date addHours(Date d, int hours) {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.HOUR_OF_DAY, hours);
        return c.getTime();
    }

    public ConsignmentListManager getConsignmentListManager() {
        return consignmentListManager;
    }

    public void setConsignmentListManager(ConsignmentListManager consignmentListManager) {
        this.consignmentListManager = consignmentListManager;
    }
}
