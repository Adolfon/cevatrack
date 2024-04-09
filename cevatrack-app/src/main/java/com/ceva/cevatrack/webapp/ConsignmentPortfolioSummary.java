package com.ceva.cevatrack.webapp;

import java.util.HashMap;
import java.util.Map;
import com.ceva.cevatrack.vo.SmartNetClientVO;

/**
 * @author brian.goodall
 *
 */
public class ConsignmentPortfolioSummary {
  public Map<Short, Integer> statusCounts;
  public SmartNetClientVO clientConfig;

  public ConsignmentPortfolioSummary(SmartNetClientVO clientConfig) {
    statusCounts = new HashMap<Short, Integer>();
    this.clientConfig = clientConfig;
  }

  /**
   * @param pfxCounts is the prefix summary to add to our client summary
   */
  public void addPrefixSummary(ConsignmentPrefixCounts pfxCounts) {
    pfxCounts.getStatusCounts().forEach((k, v) -> {
      Integer sofar = statusCounts.get(k);
      if (sofar == null) {
        statusCounts.put(k, v);
      } else {
        statusCounts.put(k, sofar + v);
      }
    });
  }

  public SmartNetClientVO getClientConfig() {
    return clientConfig;
  }

  public void setClientConfig(SmartNetClientVO clientConfig) {
    this.clientConfig = clientConfig;
  }

  public Map<Short, Integer> getStatusCounts() {
    return statusCounts;
  }

  public void setStatusCounts(Map<Short, Integer> statusCounts) {
    this.statusCounts = statusCounts;
  }
}
