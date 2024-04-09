
package com.ceva.cevatrack.wsmicrolise.getjourneysummaryforaliasanddate;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author brian.goodall
 *
 */
public class JourneySummaryResponse {

  protected JourneySummary journeySummary;
  protected String errorDescription;

  @XmlElement(name = "JourneySummary")
  public JourneySummary getJourneySummary() {
    return journeySummary;
  }

  public void setJourneySummary(JourneySummary value) {
    this.journeySummary = value;
  }

  @XmlElement(name = "ErrorDescription")
  public String getErrorDescription() {
    return errorDescription;
  }

  public void setErrorDescription(String value) {
    this.errorDescription = value;
  }

}
