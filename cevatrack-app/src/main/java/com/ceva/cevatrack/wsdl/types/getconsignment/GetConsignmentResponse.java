
package com.ceva.cevatrack.wsdl.types.getconsignment;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for anonymous complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GetConsignmentResult" type="{http://www.cevalogistics.com/XSD/MicroliseSmartNet/TMC/GetConsignment}Consignment" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"getConsignmentResult"})
@XmlRootElement(name = "GetConsignmentResponse")
public class GetConsignmentResponse {

  @XmlElement(name = "GetConsignmentResult")
  protected Consignment getConsignmentResult;

  /**
   * Gets the value of the getConsignmentResult property.
   * 
   * @return possible object is {@link Consignment }
   * 
   */
  public Consignment getGetConsignmentResult() {
    return getConsignmentResult;
  }

  /**
   * Sets the value of the getConsignmentResult property.
   * 
   * @param value allowed object is {@link Consignment }
   * 
   */
  public void setGetConsignmentResult(Consignment value) {
    this.getConsignmentResult = value;
  }

}
