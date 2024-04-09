
package com.ceva.cevatrack.wsmicrolise.getconsignment;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author brian.goodall
 *
 */
public class GetConsignmentResponse {

  protected Consignment getConsignmentResult;

  // @XmlElement(name = "GetConsignmentResult", namespace = "http://microlise.com/TMC")
  @XmlElement(name = "GetConsignmentResult")
  public Consignment getGetConsignmentResult() {
    return getConsignmentResult;
  }

  public void setGetConsignmentResult(Consignment value) {
    this.getConsignmentResult = value;
  }

}
