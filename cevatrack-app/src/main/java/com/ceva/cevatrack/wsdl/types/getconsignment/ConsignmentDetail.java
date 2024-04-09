
package com.ceva.cevatrack.wsdl.types.getconsignment;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for ConsignmentDetail complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ConsignmentDetail">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DetailID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="DetailRef" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CustomerDetailRef" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ParentRef" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderRef" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PlannedQuantity" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ActualQuantity" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="ClauseDetails" type="{http://www.cevalogistics.com/XSD/MicroliseSmartNet/TMC/GetConsignment}ArrayOfClausedConsignmentDetail" minOccurs="0"/>
 *         &lt;element name="Info1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Info2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Info3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Info4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ConsignmentHeaderID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConsignmentDetail",
    propOrder = {"detailID", "detailRef", "customerDetailRef", "parentRef", "orderRef",
        "description", "plannedQuantity", "actualQuantity", "status", "clauseDetails", "info1",
        "info2", "info3", "info4", "consignmentHeaderID"})
public class ConsignmentDetail {

  @XmlElement(name = "DetailID")
  protected int detailID;
  @XmlElement(name = "DetailRef")
  protected String detailRef;
  @XmlElement(name = "CustomerDetailRef")
  protected String customerDetailRef;
  @XmlElement(name = "ParentRef")
  protected String parentRef;
  @XmlElement(name = "OrderRef")
  protected String orderRef;
  @XmlElement(name = "Description")
  protected String description;
  @XmlElement(name = "PlannedQuantity")
  protected int plannedQuantity;
  @XmlElement(name = "ActualQuantity")
  protected int actualQuantity;
  @XmlElement(name = "Status")
  protected short status;
  @XmlElement(name = "ClauseDetails")
  protected ArrayOfClausedConsignmentDetail clauseDetails;
  @XmlElement(name = "Info1")
  protected String info1;
  @XmlElement(name = "Info2")
  protected String info2;
  @XmlElement(name = "Info3")
  protected String info3;
  @XmlElement(name = "Info4")
  protected String info4;
  @XmlElement(name = "ConsignmentHeaderID")
  protected int consignmentHeaderID;

  /**
   * Gets the value of the detailID property.
   * 
   */
  public int getDetailID() {
    return detailID;
  }

  /**
   * Sets the value of the detailID property.
   * 
   */
  public void setDetailID(int value) {
    this.detailID = value;
  }

  /**
   * Gets the value of the detailRef property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getDetailRef() {
    return detailRef;
  }

  /**
   * Sets the value of the detailRef property.
   * 
   * @param value allowed object is {@link String }
   * 
   */
  public void setDetailRef(String value) {
    this.detailRef = value;
  }

  /**
   * Gets the value of the customerDetailRef property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getCustomerDetailRef() {
    return customerDetailRef;
  }

  /**
   * Sets the value of the customerDetailRef property.
   * 
   * @param value allowed object is {@link String }
   * 
   */
  public void setCustomerDetailRef(String value) {
    this.customerDetailRef = value;
  }

  /**
   * Gets the value of the parentRef property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getParentRef() {
    return parentRef;
  }

  /**
   * Sets the value of the parentRef property.
   * 
   * @param value allowed object is {@link String }
   * 
   */
  public void setParentRef(String value) {
    this.parentRef = value;
  }

  /**
   * Gets the value of the orderRef property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getOrderRef() {
    return orderRef;
  }

  /**
   * Sets the value of the orderRef property.
   * 
   * @param value allowed object is {@link String }
   * 
   */
  public void setOrderRef(String value) {
    this.orderRef = value;
  }

  /**
   * Gets the value of the description property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets the value of the description property.
   * 
   * @param value allowed object is {@link String }
   * 
   */
  public void setDescription(String value) {
    this.description = value;
  }

  /**
   * Gets the value of the plannedQuantity property.
   * 
   */
  public int getPlannedQuantity() {
    return plannedQuantity;
  }

  /**
   * Sets the value of the plannedQuantity property.
   * 
   */
  public void setPlannedQuantity(int value) {
    this.plannedQuantity = value;
  }

  /**
   * Gets the value of the actualQuantity property.
   * 
   */
  public int getActualQuantity() {
    return actualQuantity;
  }

  /**
   * Sets the value of the actualQuantity property.
   * 
   */
  public void setActualQuantity(int value) {
    this.actualQuantity = value;
  }

  /**
   * Gets the value of the status property.
   * 
   */
  public short getStatus() {
    return status;
  }

  /**
   * Sets the value of the status property.
   * 
   */
  public void setStatus(short value) {
    this.status = value;
  }

  /**
   * Gets the value of the clauseDetails property.
   * 
   * @return possible object is {@link ArrayOfClausedConsignmentDetail }
   * 
   */
  public ArrayOfClausedConsignmentDetail getClauseDetails() {
    return clauseDetails;
  }

  /**
   * Sets the value of the clauseDetails property.
   * 
   * @param value allowed object is {@link ArrayOfClausedConsignmentDetail }
   * 
   */
  public void setClauseDetails(ArrayOfClausedConsignmentDetail value) {
    this.clauseDetails = value;
  }

  /**
   * Gets the value of the info1 property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getInfo1() {
    return info1;
  }

  /**
   * Sets the value of the info1 property.
   * 
   * @param value allowed object is {@link String }
   * 
   */
  public void setInfo1(String value) {
    this.info1 = value;
  }

  /**
   * Gets the value of the info2 property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getInfo2() {
    return info2;
  }

  /**
   * Sets the value of the info2 property.
   * 
   * @param value allowed object is {@link String }
   * 
   */
  public void setInfo2(String value) {
    this.info2 = value;
  }

  /**
   * Gets the value of the info3 property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getInfo3() {
    return info3;
  }

  /**
   * Sets the value of the info3 property.
   * 
   * @param value allowed object is {@link String }
   * 
   */
  public void setInfo3(String value) {
    this.info3 = value;
  }

  /**
   * Gets the value of the info4 property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getInfo4() {
    return info4;
  }

  /**
   * Sets the value of the info4 property.
   * 
   * @param value allowed object is {@link String }
   * 
   */
  public void setInfo4(String value) {
    this.info4 = value;
  }

  /**
   * Gets the value of the consignmentHeaderID property.
   * 
   */
  public int getConsignmentHeaderID() {
    return consignmentHeaderID;
  }

  /**
   * Sets the value of the consignmentHeaderID property.
   * 
   */
  public void setConsignmentHeaderID(int value) {
    this.consignmentHeaderID = value;
  }

}
