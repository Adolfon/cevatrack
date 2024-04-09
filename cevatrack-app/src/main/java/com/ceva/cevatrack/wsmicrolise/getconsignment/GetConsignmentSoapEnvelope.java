package com.ceva.cevatrack.wsmicrolise.getconsignment;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author brian.goodall
 *
 */
// @XmlRootElement(name = "Envelope", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
@XmlRootElement(name = "Envelope")
public class GetConsignmentSoapEnvelope {

  protected GetConsignmentSoapBody getConsignmentSoapBody;

  // @XmlElement(name = "Body", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
  @XmlElement(name = "Body")
  public GetConsignmentSoapBody getGetConsignmentSoapBody() {
    return getConsignmentSoapBody;
  }

  public void setGetConsignmentSoapBody(GetConsignmentSoapBody value) {
    this.getConsignmentSoapBody = value;
  }
}
