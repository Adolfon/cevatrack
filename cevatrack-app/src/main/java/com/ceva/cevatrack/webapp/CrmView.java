package com.ceva.cevatrack.webapp;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ceva.cevatrack.domain.CrmStatus78;
import com.ceva.cevatrack.repository.CrmStatus78Repository;

/**
 * View-scoped bean for the CRM page.
 * Date selectors only filter existing database data (ATA window).
 * Background refresh lives in CrmScheduler.
 *
 * Java 8 + existing JSF @ManagedBean style used in the project.
 */
@ManagedBean(name = "crmView")
@ViewScoped
public class CrmView implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger(CrmView.class);

    public static final int REFRESH_INTERVAL_HOURS = CrmScheduler.REFRESH_INTERVAL_HOURS;
    public static final int RETENTION_DAYS = CrmScheduler.RETENTION_DAYS;
    public static final int DEFAULT_WINDOW_HOURS = 48;

    @Inject
    private CrmStatus78Repository repository;

    @ManagedProperty(value = "#{crmScheduler}")
    private CrmScheduler crmScheduler;

    private Date fromDate;
    private Date toDate;
    private List<CrmStatus78> rows;

    /**
     * Timezone used for display and date filtering.
     * Defaults to the server JVM timezone.
     * Later you can set this from the user profile or from the browser.
     */
    private TimeZone userTimeZone = TimeZone.getDefault();

    @PostConstruct
    public void init() {
        initDefaultWindow();
        loadData();
    }

    private void initDefaultWindow() {
        Date now = new Date();
        toDate = snapToInterval(now);
        Calendar cal = Calendar.getInstance();
        cal.setTime(toDate);
        cal.add(Calendar.HOUR_OF_DAY, -DEFAULT_WINDOW_HOURS);
        fromDate = cal.getTime();

        Date min = getMinSelectableDate();
        if (fromDate.before(min)) {
            fromDate = min;
        }
    }

    private Date snapToInterval(Date d) {
        if (d == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int snappedHour = (hour / REFRESH_INTERVAL_HOURS) * REFRESH_INTERVAL_HOURS;
        cal.set(Calendar.HOUR_OF_DAY, snappedHour);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /** Earliest selectable From = now - RETENTION_DAYS, snapped to the interval. */
    public Date getMinSelectableDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -RETENTION_DAYS);
        return snapToInterval(cal.getTime());
    }

    /** Latest selectable To = current time snapped to the interval. */
    public Date getMaxSelectableDate() {
        return snapToInterval(new Date());
    }

    /**
     * Earliest selectable To = From + 1 minute.
     * Falls back to minSelectableDate if From is not set.
     */
    public Date getMinToDate() {
        if (fromDate == null) {
            return getMinSelectableDate();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(fromDate);
        cal.add(Calendar.MINUTE, 1);
        return cal.getTime();
    }

    /**
     * Filters the database by Actual Arrival Time (ATA) using the submitted From/To.
     * No web-service call.
     */
    public void loadData() {
        if (fromDate == null || toDate == null) {
            initDefaultWindow();
        }

        Date min = getMinSelectableDate();
        Date max = getMaxSelectableDate();
        if (fromDate.before(min)) {
            fromDate = min;
        }
        if (toDate.after(max)) {
            toDate = max;
        }
        if (fromDate.after(toDate)) {
            fromDate = toDate;
        }

        LOG.info("CRM view filter – ATA from {} to {}", fromDate, toDate);
        rows = repository.findByDateWindow(fromDate, toDate);
        LOG.info("CRM view filter – returned {} rows", rows != null ? rows.size() : 0);
    }

    public boolean isTableEmpty() {
        try {
            return repository.countAll() == 0L;
        } catch (Exception e) {
            LOG.warn("Could not check if CRM table is empty", e);
            return true;
        }
    }

    /** Populate / Re-try button – starts day-by-day load in background. */
    public void populateIfEmpty() {
        if (crmScheduler != null) {
            crmScheduler.startFullRetentionAsync();
            LOG.info("Populate started in background");
        }
        loadData();
    }

    /**
     * True when the table is empty OR a previous load was interrupted.
     * Used to show the Populate / Re-try button.
     */
    public boolean isShowPopulateButton() {
        return isTableEmpty() || (crmScheduler != null && crmScheduler.isLoadIncomplete());
    }

    /** Button label: "Re-try" if a previous load was interrupted, otherwise "Populate". */
    public String getPopulateButtonLabel() {
        if (crmScheduler != null && crmScheduler.isLoadIncomplete()) {
            return "Re-try";
        }
        return "Populate";
    }

    // ----- getters / setters -----

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

    public CrmScheduler getCrmScheduler() {
        return crmScheduler;
    }

    public void setCrmScheduler(CrmScheduler crmScheduler) {
        this.crmScheduler = crmScheduler;
    }

    public TimeZone getUserTimeZone() {
        return userTimeZone;
    }

    public void setUserTimeZone(TimeZone userTimeZone) {
        if (userTimeZone != null) {
            this.userTimeZone = userTimeZone;
        }
    }

    public String getUserTimeZoneId() {
        return userTimeZone != null ? userTimeZone.getID() : TimeZone.getDefault().getID();
    }
}
