package com.ceva.cevatrack.wsmicrolise.getjourneysummaryforaliasanddate;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author brian.goodall
 *
 */
@XmlRootElement(name = "Envelope", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
public class GetJourneySummaryForAliasAndDateSoapEnvelope {

  protected GetJourneySummaryForAliasAndDateSoapBody getJourneySummaryForAliasAndDateSoapBody;

  @XmlElement(name = "Body", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
  public GetJourneySummaryForAliasAndDateSoapBody getGetJourneySummaryForAliasAndDateSoapBody() {
    return getJourneySummaryForAliasAndDateSoapBody;
  }

  public void setGetJourneySummaryForAliasAndDateSoapBody(
      GetJourneySummaryForAliasAndDateSoapBody value) {
    this.getJourneySummaryForAliasAndDateSoapBody = value;
  }
}
