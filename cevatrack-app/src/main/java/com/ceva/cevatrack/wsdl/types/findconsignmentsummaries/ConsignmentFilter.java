
package com.ceva.cevatrack.wsdl.types.findconsignmentsummaries;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>
 * Java class for ConsignmentFilter complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ConsignmentFilter">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ConsignmentReference" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OrderReference" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EarliestDeliveryDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="LatestDeliveryDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="SiteID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SiteName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SiteAddress" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SitePostCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConsignmentFilter", propOrder = { "consignmentReference", "orderReference", "earliestDeliveryDate",
        "latestDeliveryDate", "siteID", "siteName", "siteAddress", "sitePostCode" })
public class ConsignmentFilter {

    @XmlElement(name = "ConsignmentReference")
    protected String consignmentReference;
    @XmlElement(name = "OrderReference")
    protected String orderReference;
    @XmlElement(name = "EarliestDeliveryDate", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar earliestDeliveryDate;
    @XmlElement(name = "LatestDeliveryDate", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar latestDeliveryDate;
    @XmlElement(name = "SiteID")
    protected String siteID;
    @XmlElement(name = "SiteName")
    protected String siteName;
    @XmlElement(name = "SiteAddress")
    protected String siteAddress;
    @XmlElement(name = "SitePostCode")
    protected String sitePostCode;

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
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setConsignmentReference(String value) {
        this.consignmentReference = value;
    }

    /**
     * Gets the value of the orderReference property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getOrderReference() {
        return orderReference;
    }

    /**
     * Sets the value of the orderReference property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setOrderReference(String value) {
        this.orderReference = value;
    }

    /**
     * Gets the value of the earliestDeliveryDate property.
     * 
     * @return possible object is {@link XMLGregorianCalendar }
     * 
     */
    public XMLGregorianCalendar getEarliestDeliveryDate() {
        return earliestDeliveryDate;
    }

    /**
     * Sets the value of the earliestDeliveryDate property.
     * 
     * @param value
     *            allowed object is {@link XMLGregorianCalendar }
     * 
     */
    public void setEarliestDeliveryDate(XMLGregorianCalendar value) {
        this.earliestDeliveryDate = value;
    }

    /**
     * Gets the value of the latestDeliveryDate property.
     * 
     * @return possible object is {@link XMLGregorianCalendar }
     * 
     */
    public XMLGregorianCalendar getLatestDeliveryDate() {
        return latestDeliveryDate;
    }

    /**
     * Sets the value of the latestDeliveryDate property.
     * 
     * @param value
     *            allowed object is {@link XMLGregorianCalendar }
     * 
     */
    public void setLatestDeliveryDate(XMLGregorianCalendar value) {
        this.latestDeliveryDate = value;
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
     * @param value
     *            allowed object is {@link String }
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
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setSiteName(String value) {
        this.siteName = value;
    }

    /**
     * Gets the value of the siteAddress property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getSiteAddress() {
        return siteAddress;
    }

    /**
     * Sets the value of the siteAddress property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setSiteAddress(String value) {
        this.siteAddress = value;
    }

    /**
     * Gets the value of the sitePostCode property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getSitePostCode() {
        return sitePostCode;
    }

    /**
     * Sets the value of the sitePostCode property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setSitePostCode(String value) {
        this.sitePostCode = value;
    }

}
