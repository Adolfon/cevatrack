
package com.ceva.cevatrack.wsdl.types.getconsignment;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for ClausedConsignmentDetail complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ClausedConsignmentDetail">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ParentCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ParentClauseDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ClauseCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ClauseDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ClausedQuantity" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ConsignmentDetailID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ClausedConsignmentDetail", propOrder = {"parentCode", "parentClauseDescription",
    "clauseCode", "clauseDescription", "clausedQuantity", "consignmentDetailID"})
public class ClausedConsignmentDetail {

  @XmlElement(name = "ParentCode")
  protected String parentCode;
  @XmlElement(name = "ParentClauseDescription")
  protected String parentClauseDescription;
  @XmlElement(name = "ClauseCode")
  protected String clauseCode;
  @XmlElement(name = "ClauseDescription")
  protected String clauseDescription;
  @XmlElement(name = "ClausedQuantity")
  protected int clausedQuantity;
  @XmlElement(name = "ConsignmentDetailID")
  protected int consignmentDetailID;

  /**
   * Gets the value of the parentCode property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getParentCode() {
    return parentCode;
  }

  /**
   * Sets the value of the parentCode property.
   * 
   * @param value allowed object is {@link String }
   * 
   */
  public void setParentCode(String value) {
    this.parentCode = value;
  }

  /**
   * Gets the value of the parentClauseDescription property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getParentClauseDescription() {
    return parentClauseDescription;
  }

  /**
   * Sets the value of the parentClauseDescription property.
   * 
   * @param value allowed object is {@link String }
   * 
   */
  public void setParentClauseDescription(String value) {
    this.parentClauseDescription = value;
  }

  /**
   * Gets the value of the clauseCode property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getClauseCode() {
    return clauseCode;
  }

  /**
   * Sets the value of the clauseCode property.
   * 
   * @param value allowed object is {@link String }
   * 
   */
  public void setClauseCode(String value) {
    this.clauseCode = value;
  }

  /**
   * Gets the value of the clauseDescription property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getClauseDescription() {
    return clauseDescription;
  }

  /**
   * Sets the value of the clauseDescription property.
   * 
   * @param value allowed object is {@link String }
   * 
   */
  public void setClauseDescription(String value) {
    this.clauseDescription = value;
  }

  /**
   * Gets the value of the clausedQuantity property.
   * 
   */
  public int getClausedQuantity() {
    return clausedQuantity;
  }

  /**
   * Sets the value of the clausedQuantity property.
   * 
   */
  public void setClausedQuantity(int value) {
    this.clausedQuantity = value;
  }

  /**
   * Gets the value of the consignmentDetailID property.
   * 
   */
  public int getConsignmentDetailID() {
    return consignmentDetailID;
  }

  /**
   * Sets the value of the consignmentDetailID property.
   * 
   */
  public void setConsignmentDetailID(int value) {
    this.consignmentDetailID = value;
  }

}
