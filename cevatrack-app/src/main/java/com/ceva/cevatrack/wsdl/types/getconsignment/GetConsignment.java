
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
 *         &lt;element name="ConsignmentRef" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"consignmentRef"})
@XmlRootElement(name = "GetConsignment")
public class GetConsignment {

  @XmlElement(name = "ConsignmentRef")
  protected String consignmentRef;

  /**
   * Gets the value of the consignmentRef property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getConsignmentRef() {
    return consignmentRef;
  }

  /**
   * Sets the value of the consignmentRef property.
   * 
   * @param value allowed object is {@link String }
   * 
   */
  public void setConsignmentRef(String value) {
    this.consignmentRef = value;
  }

}
