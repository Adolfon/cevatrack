package com.ceva.cevatrack.webapp;

import java.util.HashMap;
import java.util.Map;
import com.ceva.cevatrack.wsmicrolise.findconsignmentsummaries.ConsignmentSummary;

/**
 * @author brian.goodall
 *
 */
public class ConsignmentPrefixCounts {
  // this class is used to build up the consignment portfolio summaries getting data at a prefix
  // level
  public String prefix;
  public Map<Short, Integer> statusCounts;

  public ConsignmentPrefixCounts(String prefix) {
    statusCounts = new HashMap<Short, Integer>();
    this.prefix = prefix;
  }

  /**
   * @param consignment is the consignment to add to the stats
   */
  public void addConsignment(ConsignmentSummary consignment) {
    Integer statusCounter = statusCounts.get(consignment.getConsignmentStatus());
    if (statusCounter == null) {
      statusCounts.put(consignment.getConsignmentStatus(), 1);
    } else {
      statusCounts.put(consignment.getConsignmentStatus(), statusCounter + 1);
    }
  }

  public String getPrefix() {
    return prefix;
  }

  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }

  public Map<Short, Integer> getStatusCounts() {
    return statusCounts;
  }

  public void setStatusCounts(Map<Short, Integer> statusCounts) {
    this.statusCounts = statusCounts;
  }

}
