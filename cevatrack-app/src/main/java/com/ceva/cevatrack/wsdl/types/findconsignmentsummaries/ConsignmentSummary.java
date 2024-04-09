
package com.ceva.cevatrack.wsdl.types.findconsignmentsummaries;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>
 * Java class for ConsignmentSummary complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ConsignmentSummary">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ConsignmentReference" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ConsignmentStatus" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="OrderReferences" type="{http://www.cevalogistics.com/XSD/MicroliseSmartNet/TMC/FindConsignmentSummaries}ArrayOfString" minOccurs="0"/>
 *         &lt;element name="PTA" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="ATA" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="PTA2" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="SiteID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SiteName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Address1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Address2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Address3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="County" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PostCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Country" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ContactName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ContactNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "ConsignmentSummary",
    propOrder = {"consignmentReference", "consignmentStatus", "orderReferences", "pta", "ata",
        "pta2", "siteID", "siteName", "address1", "address2", "address3", "county", "postCode",
        "country", "contactName", "contactNumber", "consignmentHeaderID"})
public class ConsignmentSummary {

  @XmlElement(name = "ConsignmentReference")
  protected String consignmentReference;
  @XmlElement(name = "ConsignmentStatus")
  protected short consignmentStatus;
  @XmlElement(name = "OrderReferences")
  protected ArrayOfString orderReferences;
  @XmlElement(name = "PTA", required = true)
  @XmlSchemaType(name = "dateTime")
  protected XMLGregorianCalendar pta;
  @XmlElement(name = "ATA", required = true)
  @XmlSchemaType(name = "dateTime")
  protected XMLGregorianCalendar ata;
  @XmlElement(name = "PTA2", required = true)
  @XmlSchemaType(name = "dateTime")
  protected XMLGregorianCalendar pta2;
  @XmlElement(name = "SiteID")
  protected String siteID;
  @XmlElement(name = "SiteName")
  protected String siteName;
  @XmlElement(name = "Address1")
  protected String address1;
  @XmlElement(name = "Address2")
  protected String address2;
  @XmlElement(name = "Address3")
  protected String address3;
  @XmlElement(name = "County")
  protected String county;
  @XmlElement(name = "PostCode")
  protected String postCode;
  @XmlElement(name = "Country")
  protected String country;
  @XmlElement(name = "ContactName")
  protected String contactName;
  @XmlElement(name = "ContactNumber")
  protected String contactNumber;
  @XmlElement(name = "ConsignmentHeaderID")
  protected int consignmentHeaderID;

  /**
   * Gets the value of the consignmentReference property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getConsignmentReference() {
    return consignmentReference;
  }

  /**
   * Sets the value of the consignmentReference property.
   * 
   * @param value allowed object is {@link String }
   * 
   */
  public void setConsignmentReference(String value) {
    this.consignmentReference = value;
  }

  /**
   * Gets the value of the consignmentStatus property.
   * 
   */
  public short getConsignmentStatus() {
    return consignmentStatus;
  }

  /**
   * Sets the value of the consignmentStatus property.
   * 
   */
  public void setConsignmentStatus(short value) {
    this.consignmentStatus = value;
  }

  /**
   * Gets the value of the orderReferences property.
   * 
   * @return possible object is {@link ArrayOfString }
   * 
   */
  public ArrayOfString getOrderReferences() {
    return orderReferences;
  }

  /**
   * Sets the value of the orderReferences property.
   * 
   * @param value allowed object is {@link ArrayOfString }
   * 
   */
  public void setOrderReferences(ArrayOfString value) {
    this.orderReferences = value;
  }

  /**
   * Gets the value of the pta property.
   * 
   * @return possible object is {@link XMLGregorianCalendar }
   * 
   */
  public XMLGregorianCalendar getPTA() {
    return pta;
  }

  /**
   * Sets the value of the pta property.
   * 
   * @param value allowed object is {@link XMLGregorianCalendar }
   * 
   */
  public void setPTA(XMLGregorianCalendar value) {
    this.pta = value;
  }

  /**
   * Gets the value of the ata property.
   * 
   * @return possible object is {@link XMLGregorianCalendar }
   * 
   */
  public XMLGregorianCalendar getATA() {
    return ata;
  }

  /**
   * Sets the value of the ata property.
   * 
   * @param value allowed object is {@link XMLGregorianCalendar }
   * 
   */
  public void setATA(XMLGregorianCalendar value) {
    this.ata = value;
  }

  /**
   * Gets the value of the pta2 property.
   * 
   * @return possible object is {@link XMLGregorianCalendar }
   * 
   */
  public XMLGregorianCalendar getPTA2() {
    return pta2;
  }

  /**
   * Sets the value of the pta2 property.
   * 
   * @param value allowed object is {@link XMLGregorianCalendar }
   * 
   */
  public void setPTA2(XMLGregorianCalendar value) {
    this.pta2 = value;
  }

  /**
   * Gets the value of the siteID property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getSiteID() {
    return siteID;
  }

  /**
   * Sets the value of the siteID property.
   * 
   * @param value allowed object is {@link String }
   * 
   */
  public void setSiteID(String value) {
    this.siteID = value;
  }

  /**
   * Gets the value of the siteName property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getSiteName() {
    return siteName;
  }

  /**
   * Sets the value of the siteName property.
   * 
   * @param value allowed object is {@link String }
   * 
   */
  public void setSiteName(String value) {
    this.siteName = value;
  }

  /**
   * Gets the value of the address1 property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getAddress1() {
    return address1;
  }

  /**
   * Sets the value of the address1 property.
   * 
   * @param value allowed object is {@link String }
   * 
   */
  public void setAddress1(String value) {
    this.address1 = value;
  }

  /**
   * Gets the value of the address2 property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getAddress2() {
    return address2;
  }

  /**
   * Sets the value of the address2 property.
   * 
   * @param value allowed object is {@link String }
   * 
   */
  public void setAddress2(String value) {
    this.address2 = value;
  }

  /**
   * Gets the value of the address3 property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getAddress3() {
    return address3;
  }

  /**
   * Sets the value of the address3 property.
   * 
   * @param value allowed object is {@link String }
   * 
   */
  public void setAddress3(String value) {
    this.address3 = value;
  }

  /**
   * Gets the value of the county property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getCounty() {
    return county;
  }

  /**
   * Sets the value of the county property.
   * 
   * @param value allowed object is {@link String }
   * 
   */
  public void setCounty(String value) {
    this.county = value;
  }

  /**
   * Gets the value of the postCode property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getPostCode() {
    return postCode;
  }

  /**
   * Sets the value of the postCode property.
   * 
   * @param value allowed object is {@link String }
   * 
   */
  public void setPostCode(String value) {
    this.postCode = value;
  }

  /**
   * Gets the value of the country property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getCountry() {
    return country;
  }

  /**
   * Sets the value of the country property.
   * 
   * @param value allowed object is {@link String }
   * 
   */
  public void setCountry(String value) {
    this.country = value;
  }

  /**
   * Gets the value of the contactName property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getContactName() {
    return contactName;
  }

  /**
   * Sets the value of the contactName property.
   * 
   * @param value allowed object is {@link String }
   * 
   */
  public void setContactName(String value) {
    this.contactName = value;
  }

  /**
   * Gets the value of the contactNumber property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getContactNumber() {
    return contactNumber;
  }

  /**
   * Sets the value of the contactNumber property.
   * 
   * @param value allowed object is {@link String }
   * 
   */
  public void setContactNumber(String value) {
    this.contactNumber = value;
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
