package com.ceva.cevatrack.wsmicrolise.getconsignment;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author brian.goodall
 *
 */
public class GetConsignmentSoapBody {
  protected GetConsignmentResponse getConsignmentResponse;

  // @XmlElement(name = "GetConsignmentResponse", namespace = "http://microlise.com/TMC")
  @XmlElement(name = "GetConsignmentResponse")
  public GetConsignmentResponse getGetConsignmentResponse() {
    return getConsignmentResponse;
  }

  public void setGetConsignmentResponse(GetConsignmentResponse value) {
    this.getConsignmentResponse = value;
  }
}
