package com.ceva.cevatrack.wsmicrolise;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.faces.model.SelectItem;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
// import com.ceva.cevatrack.wsdl.types.findconsignmentsummaries.ConsignmentSummary;
import com.ceva.cevatrack.wsmicrolise.Common;
import com.ceva.cevatrack.wsmicrolise.WsMicroliseComms;
import com.ceva.cevatrack.wsmicrolise.findconsignmentsummaries.ConsignmentSummary;

/**
 * @author brian.goodall
 *
 */
public class DatedConsignmentList {
  private static final Logger LOGGER = LoggerFactory.getLogger(DatedConsignmentList.class);
  private LocalDateTime refreshDateTime;
  private String dateRange;
  private List<ConsignmentSummary> consignmentSummaries;
  private LocalDate toDate;
  private LocalDate fromDate;
  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.BASIC_ISO_DATE;

  public DatedConsignmentList() {
    // this is to create dummy objects in case of comms failures.
    consignmentSummaries = new ArrayList<ConsignmentSummary>();
  }

  /**
   * @param source for cloning prior to applying security
   */
  public DatedConsignmentList(DatedConsignmentList source) {
    // this will clone another DCL with an empty list
    this(source.refreshDateTime, source.dateRange, new ArrayList<ConsignmentSummary>(),
        source.toDate, source.fromDate);
  }

  /**
   * @param refreshDateTime extract time
   * @param dateRange yyyymmddyyyymmdd
   * @param consignmentSummaries list of cons
   * @param toDate taken from first part of range
   * @param fromDate taken from last part of range
   */
  public DatedConsignmentList(LocalDateTime refreshDateTime, String dateRange,
      List<ConsignmentSummary> consignmentSummaries, LocalDate toDate, LocalDate fromDate) {
    this(refreshDateTime, dateRange, toDate, fromDate);
    this.consignmentSummaries = consignmentSummaries;
  }

  /**
   * @param refreshDateTime extract time
   * @param dateRange yyyymmddyyyymmdd
   * @param toDate taken from first part of range
   * @param fromDate taken from last part of range
   */
  public DatedConsignmentList(LocalDateTime refreshDateTime, String dateRange, LocalDate toDate,
      LocalDate fromDate) {
    this.refreshDateTime = refreshDateTime;
    this.dateRange = dateRange;
    this.toDate = toDate;
    this.fromDate = fromDate;
  }

  /**
   * @param earliestDate is the from date in format yyyyMMdd
   * @param latestDate is the from date in format yyyyMMdd
   * @throws Exception if unable to access data
   */
  // public DatedConsignmentList(String dateRange) throws Exception {
  public DatedConsignmentList(String earliestDate, String latestDate, Boolean WS, String network)
      throws Exception {
    super();
    this.dateRange = dateRange;
    try {
      // fromDate = LocalDate.parse(dateRange.substring(0,8),FORMATTER);
      fromDate = LocalDate.parse(earliestDate, FORMATTER);
    } catch (DateTimeParseException e) {
      throw new Exception("From-Date is invalid", e);
    }
    try {
      // toDate = LocalDate.parse(dateRange.substring(8),FORMATTER);
      toDate = LocalDate.parse(latestDate, FORMATTER);
    } catch (DateTimeParseException e) {
      throw new Exception("To-Date is invalid", e);
    }
    try {
      consignmentSummaries = WsMicroliseComms.findConsignmentSummariesForDateRange(
          fromDate.format(FORMATTER), toDate.format(FORMATTER), WS, network);
    } catch (IOException e1) {
      throw new IOException("IOException error in remote server communication is escalating", e1);
    } catch (Exception e) { // Exception but cause IOException
      consignmentSummaries = null;
      throw new Exception("Error in remote server communication is escalating.", e);
    }
    refreshDateTime = LocalDateTime.now();
  }

  public String getDateRange() {
    return dateRange;
  }

  public void setDateRange(String dateRange) {
    this.dateRange = dateRange;
  }

  public LocalDateTime getRefreshDateTime() {
    return refreshDateTime;
  }

  public LocalDate getToDate() {
    return toDate;
  }

  public LocalDate getFromDate() {
    return fromDate;
  }

  public List<ConsignmentSummary> getConsignmentSummaries() {
    return consignmentSummaries;
  }

  /**
   * @return true if the data in the list is believed to be up to date
   */
  public Boolean isCurrent() {
    if (consignmentSummaries == null || consignmentSummaries.size() == 0) {
      return false;
    }
    if (getAgeInMinutes() > getValidMinutes()) {
      return false;
    }
    return true;

  }

  /**
   * @return a selection list to build a datatable filter, (view data)
   */
  public List<SelectItem> getStatusSelectItems() {
    Set<Short> deduper = new HashSet<Short>();
    List<SelectItem> resp = new ArrayList<SelectItem>();
    consignmentSummaries.forEach(c -> deduper.add(c.getConsignmentStatus()));
    deduper.forEach(s -> {
      String desc = Common.getStatusDescription(s);
      if (!desc.isEmpty()) {
        resp.add(new SelectItem(desc, desc));
      }
    });
    return resp;
  }

  /**
   * @return true if we are okay to delete this as it's getting old (2 days old)
   */
  public boolean isForDeletion() {
    LOGGER
        .info("isForDeletion method, refreshDateTime.until(LocalDateTime.now(), ChronoUnit.DAYS): "
            + refreshDateTime.until(LocalDateTime.now(), ChronoUnit.DAYS));
    if (refreshDateTime.until(LocalDateTime.now(), ChronoUnit.DAYS) > 2L) {
      return true;
    }
    return false;
  }

  /**
   * @return true if we are okay to delete this as it's getting old
   */
  public long getAgeInMinutes() {
    return refreshDateTime.until(LocalDateTime.now(), ChronoUnit.MINUTES);
  }

  /**
   * @return the number of minutes for which the list is considered current
   */
  public long getValidMinutes() {
    // rules are
    // current date or next date included = 15 minutes refresh
    // previous date included = 30 minute refresh
    // otherwise = 60 minute refresh
    // we will always return false if we come across any invalid dates
    LocalDate today = LocalDate.now();
    LocalDate tomorrow = today.minus(Period.ofDays(-1));

    if (!fromDate.isAfter(tomorrow) && !toDate.isBefore(today)) {
      return 5L;
    }

    LocalDate yesterday = today.minus(Period.ofDays(1));
    if (!fromDate.isAfter(yesterday) && !toDate.isBefore(yesterday)) {
      return 30L;
    }

    return 60L;
  }
}
