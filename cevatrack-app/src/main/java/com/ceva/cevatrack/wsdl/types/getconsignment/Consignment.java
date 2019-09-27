
package com.ceva.cevatrack.wsdl.types.getconsignment;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>
 * Java class for Consignment complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Consignment">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Label1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Label2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Label3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Label4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ConsignmentReference" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ConsignmentCustomerOrderRef" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ConsignmentStatus" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="ConsignmentDetails" type="{http://www.cevalogistics.com/XSD/MicroliseSmartNet/TMC/GetConsignment}ArrayOfConsignmentDetail" minOccurs="0"/>
 *         &lt;element name="ATA" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="ATD" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="Signatory" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Signature" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="JourneyDropID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="PTA" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="PTD" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="ETA" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="ETD" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="TimeWindowStart" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="TimeWindowEnd" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
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
 *         &lt;element name="SiteLat" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="SiteLong" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="RouteID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="JourneyAlias" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PlannedStartTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="ActualStartTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="PODStartTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="Vehicles" type="{http://www.cevalogistics.com/XSD/MicroliseSmartNet/TMC/GetConsignment}ArrayOfVehicle" minOccurs="0"/>
 *         &lt;element name="Drivers" type="{http://www.cevalogistics.com/XSD/MicroliseSmartNet/TMC/GetConsignment}ArrayOfDriver" minOccurs="0"/>
 *         &lt;element name="SiteGeoRadius" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="DurationOnSite" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="JourneyActualStartTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="IsAdHoc" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="IsTranshipment" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="ClauseCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ParentCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ConsignmentHeaderID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="JourneyID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ConsignmentHeaderInfo1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ConsignmentHeaderInfo2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ConsignmentHeaderInfo3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ConsignmentHeaderInfo4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ConsignmentHeaderInfo5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ConsignmentHeaderInfo6" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ConsignmentHeaderInfo7" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ConsignmentHeaderInfo8" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ConsignmentHeaderInfo9" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ConsignmentHeaderInfo10" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ConsignmentHeaderInfo11" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ConsignmentHeaderInfo12" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Consignment", propOrder = { "label1", "label2", "label3", "label4", "consignmentReference",
        "consignmentCustomerOrderRef", "consignmentStatus", "consignmentDetails", "ata", "atd", "signatory",
        "signature", "journeyDropID", "pta", "ptd", "eta", "etd", "timeWindowStart", "timeWindowEnd", "siteID",
        "siteName", "address1", "address2", "address3", "county", "postCode", "country", "contactName", "contactNumber",
        "siteLat", "siteLong", "routeID", "journeyAlias", "plannedStartTime", "actualStartTime", "podStartTime",
        "vehicles", "drivers", "siteGeoRadius", "durationOnSite", "journeyActualStartTime", "isAdHoc", "isTranshipment",
        "clauseCode", "parentCode", "consignmentHeaderID", "journeyID", "consignmentHeaderInfo1",
        "consignmentHeaderInfo2", "consignmentHeaderInfo3", "consignmentHeaderInfo4", "consignmentHeaderInfo5",
        "consignmentHeaderInfo6", "consignmentHeaderInfo7", "consignmentHeaderInfo8", "consignmentHeaderInfo9",
        "consignmentHeaderInfo10", "consignmentHeaderInfo11", "consignmentHeaderInfo12" })
public class Consignment {

    @XmlElement(name = "Label1")
    protected String label1;
    @XmlElement(name = "Label2")
    protected String label2;
    @XmlElement(name = "Label3")
    protected String label3;
    @XmlElement(name = "Label4")
    protected String label4;
    @XmlElement(name = "ConsignmentReference")
    protected String consignmentReference;
    @XmlElement(name = "ConsignmentCustomerOrderRef")
    protected String consignmentCustomerOrderRef;
    @XmlElement(name = "ConsignmentStatus")
    protected short consignmentStatus;
    @XmlElement(name = "ConsignmentDetails")
    protected ArrayOfConsignmentDetail consignmentDetails;
    @XmlElement(name = "ATA", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar ata;
    @XmlElement(name = "ATD", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar atd;
    @XmlElement(name = "Signatory")
    protected String signatory;
    @XmlElement(name = "Signature")
    protected byte[] signature;
    @XmlElement(name = "JourneyDropID")
    protected int journeyDropID;
    @XmlElement(name = "PTA", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar pta;
    @XmlElement(name = "PTD", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar ptd;
    @XmlElement(name = "ETA", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar eta;
    @XmlElement(name = "ETD", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar etd;
    @XmlElement(name = "TimeWindowStart", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar timeWindowStart;
    @XmlElement(name = "TimeWindowEnd", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar timeWindowEnd;
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
    @XmlElement(name = "SiteLat", required = true)
    protected BigDecimal siteLat;
    @XmlElement(name = "SiteLong", required = true)
    protected BigDecimal siteLong;
    @XmlElement(name = "RouteID")
    protected String routeID;
    @XmlElement(name = "JourneyAlias")
    protected String journeyAlias;
    @XmlElement(name = "PlannedStartTime", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar plannedStartTime;
    @XmlElement(name = "ActualStartTime", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar actualStartTime;
    @XmlElement(name = "PODStartTime", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar podStartTime;
    @XmlElement(name = "Vehicles")
    protected ArrayOfVehicle vehicles;
    @XmlElement(name = "Drivers")
    protected ArrayOfDriver drivers;
    @XmlElement(name = "SiteGeoRadius")
    protected int siteGeoRadius;
    @XmlElement(name = "DurationOnSite")
    protected double durationOnSite;
    @XmlElement(name = "JourneyActualStartTime", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar journeyActualStartTime;
    @XmlElement(name = "IsAdHoc")
    protected short isAdHoc;
    @XmlElement(name = "IsTranshipment")
    protected short isTranshipment;
    @XmlElement(name = "ClauseCode")
    protected String clauseCode;
    @XmlElement(name = "ParentCode")
    protected String parentCode;
    @XmlElement(name = "ConsignmentHeaderID")
    protected int consignmentHeaderID;
    @XmlElement(name = "JourneyID")
    protected int journeyID;
    @XmlElement(name = "ConsignmentHeaderInfo1")
    protected String consignmentHeaderInfo1;
    @XmlElement(name = "ConsignmentHeaderInfo2")
    protected String consignmentHeaderInfo2;
    @XmlElement(name = "ConsignmentHeaderInfo3")
    protected String consignmentHeaderInfo3;
    @XmlElement(name = "ConsignmentHeaderInfo4")
    protected String consignmentHeaderInfo4;
    @XmlElement(name = "ConsignmentHeaderInfo5")
    protected String consignmentHeaderInfo5;
    @XmlElement(name = "ConsignmentHeaderInfo6")
    protected String consignmentHeaderInfo6;
    @XmlElement(name = "ConsignmentHeaderInfo7")
    protected String consignmentHeaderInfo7;
    @XmlElement(name = "ConsignmentHeaderInfo8")
    protected String consignmentHeaderInfo8;
    @XmlElement(name = "ConsignmentHeaderInfo9")
    protected String consignmentHeaderInfo9;
    @XmlElement(name = "ConsignmentHeaderInfo10")
    protected String consignmentHeaderInfo10;
    @XmlElement(name = "ConsignmentHeaderInfo11")
    protected String consignmentHeaderInfo11;
    @XmlElement(name = "ConsignmentHeaderInfo12")
    protected String consignmentHeaderInfo12;

    /**
     * Gets the value of the label1 property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getLabel1() {
        return label1;
    }

    /**
     * Sets the value of the label1 property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setLabel1(String value) {
        this.label1 = value;
    }

    /**
     * Gets the value of the label2 property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getLabel2() {
        return label2;
    }

    /**
     * Sets the value of the label2 property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setLabel2(String value) {
        this.label2 = value;
    }

    /**
     * Gets the value of the label3 property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getLabel3() {
        return label3;
    }

    /**
     * Sets the value of the label3 property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setLabel3(String value) {
        this.label3 = value;
    }

    /**
     * Gets the value of the label4 property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getLabel4() {
        return label4;
    }

    /**
     * Sets the value of the label4 property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setLabel4(String value) {
        this.label4 = value;
    }

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
     * Gets the value of the consignmentCustomerOrderRef property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getConsignmentCustomerOrderRef() {
        return consignmentCustomerOrderRef;
    }

    /**
     * Sets the value of the consignmentCustomerOrderRef property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setConsignmentCustomerOrderRef(String value) {
        this.consignmentCustomerOrderRef = value;
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
     * Gets the value of the consignmentDetails property.
     * 
     * @return possible object is {@link ArrayOfConsignmentDetail }
     * 
     */
    public ArrayOfConsignmentDetail getConsignmentDetails() {
        return consignmentDetails;
    }

    /**
     * Sets the value of the consignmentDetails property.
     * 
     * @param value
     *            allowed object is {@link ArrayOfConsignmentDetail }
     * 
     */
    public void setConsignmentDetails(ArrayOfConsignmentDetail value) {
        this.consignmentDetails = value;
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
     * @param value
     *            allowed object is {@link XMLGregorianCalendar }
     * 
     */
    public void setATA(XMLGregorianCalendar value) {
        this.ata = value;
    }

    /**
     * Gets the value of the atd property.
     * 
     * @return possible object is {@link XMLGregorianCalendar }
     * 
     */
    public XMLGregorianCalendar getATD() {
        return atd;
    }

    /**
     * Sets the value of the atd property.
     * 
     * @param value
     *            allowed object is {@link XMLGregorianCalendar }
     * 
     */
    public void setATD(XMLGregorianCalendar value) {
        this.atd = value;
    }

    /**
     * Gets the value of the signatory property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getSignatory() {
        return signatory;
    }

    /**
     * Sets the value of the signatory property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setSignatory(String value) {
        this.signatory = value;
    }

    /**
     * Gets the value of the signature property.
     * 
     * @return possible object is byte[]
     */
    public byte[] getSignature() {
        return signature;
    }

    /**
     * Sets the value of the signature property.
     * 
     * @param value
     *            allowed object is byte[]
     */
    public void setSignature(byte[] value) {
        this.signature = value;
    }

    /**
     * Gets the value of the journeyDropID property.
     * 
     */
    public int getJourneyDropID() {
        return journeyDropID;
    }

    /**
     * Sets the value of the journeyDropID property.
     * 
     */
    public void setJourneyDropID(int value) {
        this.journeyDropID = value;
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
     * @param value
     *            allowed object is {@link XMLGregorianCalendar }
     * 
     */
    public void setPTA(XMLGregorianCalendar value) {
        this.pta = value;
    }

    /**
     * Gets the value of the ptd property.
     * 
     * @return possible object is {@link XMLGregorianCalendar }
     * 
     */
    public XMLGregorianCalendar getPTD() {
        return ptd;
    }

    /**
     * Sets the value of the ptd property.
     * 
     * @param value
     *            allowed object is {@link XMLGregorianCalendar }
     * 
     */
    public void setPTD(XMLGregorianCalendar value) {
        this.ptd = value;
    }

    /**
     * Gets the value of the eta property.
     * 
     * @return possible object is {@link XMLGregorianCalendar }
     * 
     */
    public XMLGregorianCalendar getETA() {
        return eta;
    }

    /**
     * Sets the value of the eta property.
     * 
     * @param value
     *            allowed object is {@link XMLGregorianCalendar }
     * 
     */
    public void setETA(XMLGregorianCalendar value) {
        this.eta = value;
    }

    /**
     * Gets the value of the etd property.
     * 
     * @return possible object is {@link XMLGregorianCalendar }
     * 
     */
    public XMLGregorianCalendar getETD() {
        return etd;
    }

    /**
     * Sets the value of the etd property.
     * 
     * @param value
     *            allowed object is {@link XMLGregorianCalendar }
     * 
     */
    public void setETD(XMLGregorianCalendar value) {
        this.etd = value;
    }

    /**
     * Gets the value of the timeWindowStart property.
     * 
     * @return possible object is {@link XMLGregorianCalendar }
     * 
     */
    public XMLGregorianCalendar getTimeWindowStart() {
        return timeWindowStart;
    }

    /**
     * Sets the value of the timeWindowStart property.
     * 
     * @param value
     *            allowed object is {@link XMLGregorianCalendar }
     * 
     */
    public void setTimeWindowStart(XMLGregorianCalendar value) {
        this.timeWindowStart = value;
    }

    /**
     * Gets the value of the timeWindowEnd property.
     * 
     * @return possible object is {@link XMLGregorianCalendar }
     * 
     */
    public XMLGregorianCalendar getTimeWindowEnd() {
        return timeWindowEnd;
    }

    /**
     * Sets the value of the timeWindowEnd property.
     * 
     * @param value
     *            allowed object is {@link XMLGregorianCalendar }
     * 
     */
    public void setTimeWindowEnd(XMLGregorianCalendar value) {
        this.timeWindowEnd = value;
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
     * @param value
     *            allowed object is {@link String }
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
     * @param value
     *            allowed object is {@link String }
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
     * @param value
     *            allowed object is {@link String }
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
     * @param value
     *            allowed object is {@link String }
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
     * @param value
     *            allowed object is {@link String }
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
     * @param value
     *            allowed object is {@link String }
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
     * @param value
     *            allowed object is {@link String }
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
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setContactNumber(String value) {
        this.contactNumber = value;
    }

    /**
     * Gets the value of the siteLat property.
     * 
     * @return possible object is {@link BigDecimal }
     * 
     */
    public BigDecimal getSiteLat() {
        return siteLat;
    }

    /**
     * Sets the value of the siteLat property.
     * 
     * @param value
     *            allowed object is {@link BigDecimal }
     * 
     */
    public void setSiteLat(BigDecimal value) {
        this.siteLat = value;
    }

    /**
     * Gets the value of the siteLong property.
     * 
     * @return possible object is {@link BigDecimal }
     * 
     */
    public BigDecimal getSiteLong() {
        return siteLong;
    }

    /**
     * Sets the value of the siteLong property.
     * 
     * @param value
     *            allowed object is {@link BigDecimal }
     * 
     */
    public void setSiteLong(BigDecimal value) {
        this.siteLong = value;
    }

    /**
     * Gets the value of the routeID property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getRouteID() {
        return routeID;
    }

    /**
     * Sets the value of the routeID property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setRouteID(String value) {
        this.routeID = value;
    }

    /**
     * Gets the value of the journeyAlias property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getJourneyAlias() {
        return journeyAlias;
    }

    /**
     * Sets the value of the journeyAlias property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setJourneyAlias(String value) {
        this.journeyAlias = value;
    }

    /**
     * Gets the value of the plannedStartTime property.
     * 
     * @return possible object is {@link XMLGregorianCalendar }
     * 
     */
    public XMLGregorianCalendar getPlannedStartTime() {
        return plannedStartTime;
    }

    /**
     * Sets the value of the plannedStartTime property.
     * 
     * @param value
     *            allowed object is {@link XMLGregorianCalendar }
     * 
     */
    public void setPlannedStartTime(XMLGregorianCalendar value) {
        this.plannedStartTime = value;
    }

    /**
     * Gets the value of the actualStartTime property.
     * 
     * @return possible object is {@link XMLGregorianCalendar }
     * 
     */
    public XMLGregorianCalendar getActualStartTime() {
        return actualStartTime;
    }

    /**
     * Sets the value of the actualStartTime property.
     * 
     * @param value
     *            allowed object is {@link XMLGregorianCalendar }
     * 
     */
    public void setActualStartTime(XMLGregorianCalendar value) {
        this.actualStartTime = value;
    }

    /**
     * Gets the value of the podStartTime property.
     * 
     * @return possible object is {@link XMLGregorianCalendar }
     * 
     */
    public XMLGregorianCalendar getPODStartTime() {
        return podStartTime;
    }

    /**
     * Sets the value of the podStartTime property.
     * 
     * @param value
     *            allowed object is {@link XMLGregorianCalendar }
     * 
     */
    public void setPODStartTime(XMLGregorianCalendar value) {
        this.podStartTime = value;
    }

    /**
     * Gets the value of the vehicles property.
     * 
     * @return possible object is {@link ArrayOfVehicle }
     * 
     */
    public ArrayOfVehicle getVehicles() {
        return vehicles;
    }

    /**
     * Sets the value of the vehicles property.
     * 
     * @param value
     *            allowed object is {@link ArrayOfVehicle }
     * 
     */
    public void setVehicles(ArrayOfVehicle value) {
        this.vehicles = value;
    }

    /**
     * Gets the value of the drivers property.
     * 
     * @return possible object is {@link ArrayOfDriver }
     * 
     */
    public ArrayOfDriver getDrivers() {
        return drivers;
    }

    /**
     * Sets the value of the drivers property.
     * 
     * @param value
     *            allowed object is {@link ArrayOfDriver }
     * 
     */
    public void setDrivers(ArrayOfDriver value) {
        this.drivers = value;
    }

    /**
     * Gets the value of the siteGeoRadius property.
     * 
     */
    public int getSiteGeoRadius() {
        return siteGeoRadius;
    }

    /**
     * Sets the value of the siteGeoRadius property.
     * 
     */
    public void setSiteGeoRadius(int value) {
        this.siteGeoRadius = value;
    }

    /**
     * Gets the value of the durationOnSite property.
     * 
     */
    public double getDurationOnSite() {
        return durationOnSite;
    }

    /**
     * Sets the value of the durationOnSite property.
     * 
     */
    public void setDurationOnSite(double value) {
        this.durationOnSite = value;
    }

    /**
     * Gets the value of the journeyActualStartTime property.
     * 
     * @return possible object is {@link XMLGregorianCalendar }
     * 
     */
    public XMLGregorianCalendar getJourneyActualStartTime() {
        return journeyActualStartTime;
    }

    /**
     * Sets the value of the journeyActualStartTime property.
     * 
     * @param value
     *            allowed object is {@link XMLGregorianCalendar }
     * 
     */
    public void setJourneyActualStartTime(XMLGregorianCalendar value) {
        this.journeyActualStartTime = value;
    }

    /**
     * Gets the value of the isAdHoc property.
     * 
     */
    public short getIsAdHoc() {
        return isAdHoc;
    }

    /**
     * Sets the value of the isAdHoc property.
     * 
     */
    public void setIsAdHoc(short value) {
        this.isAdHoc = value;
    }

    /**
     * Gets the value of the isTranshipment property.
     * 
     */
    public short getIsTranshipment() {
        return isTranshipment;
    }

    /**
     * Sets the value of the isTranshipment property.
     * 
     */
    public void setIsTranshipment(short value) {
        this.isTranshipment = value;
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
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setClauseCode(String value) {
        this.clauseCode = value;
    }

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
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setParentCode(String value) {
        this.parentCode = value;
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

    /**
     * Gets the value of the journeyID property.
     * 
     */
    public int getJourneyID() {
        return journeyID;
    }

    /**
     * Sets the value of the journeyID property.
     * 
     */
    public void setJourneyID(int value) {
        this.journeyID = value;
    }

    /**
     * Gets the value of the consignmentHeaderInfo1 property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getConsignmentHeaderInfo1() {
        return consignmentHeaderInfo1;
    }

    /**
     * Sets the value of the consignmentHeaderInfo1 property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setConsignmentHeaderInfo1(String value) {
        this.consignmentHeaderInfo1 = value;
    }

    /**
     * Gets the value of the consignmentHeaderInfo2 property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getConsignmentHeaderInfo2() {
        return consignmentHeaderInfo2;
    }

    /**
     * Sets the value of the consignmentHeaderInfo2 property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setConsignmentHeaderInfo2(String value) {
        this.consignmentHeaderInfo2 = value;
    }

    /**
     * Gets the value of the consignmentHeaderInfo3 property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getConsignmentHeaderInfo3() {
        return consignmentHeaderInfo3;
    }

    /**
     * Sets the value of the consignmentHeaderInfo3 property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setConsignmentHeaderInfo3(String value) {
        this.consignmentHeaderInfo3 = value;
    }

    /**
     * Gets the value of the consignmentHeaderInfo4 property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getConsignmentHeaderInfo4() {
        return consignmentHeaderInfo4;
    }

    /**
     * Sets the value of the consignmentHeaderInfo4 property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setConsignmentHeaderInfo4(String value) {
        this.consignmentHeaderInfo4 = value;
    }

    /**
     * Gets the value of the consignmentHeaderInfo5 property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getConsignmentHeaderInfo5() {
        return consignmentHeaderInfo5;
    }

    /**
     * Sets the value of the consignmentHeaderInfo5 property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setConsignmentHeaderInfo5(String value) {
        this.consignmentHeaderInfo5 = value;
    }

    /**
     * Gets the value of the consignmentHeaderInfo6 property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getConsignmentHeaderInfo6() {
        return consignmentHeaderInfo6;
    }

    /**
     * Sets the value of the consignmentHeaderInfo6 property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setConsignmentHeaderInfo6(String value) {
        this.consignmentHeaderInfo6 = value;
    }

    /**
     * Gets the value of the consignmentHeaderInfo7 property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getConsignmentHeaderInfo7() {
        return consignmentHeaderInfo7;
    }

    /**
     * Sets the value of the consignmentHeaderInfo7 property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setConsignmentHeaderInfo7(String value) {
        this.consignmentHeaderInfo7 = value;
    }

    /**
     * Gets the value of the consignmentHeaderInfo8 property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getConsignmentHeaderInfo8() {
        return consignmentHeaderInfo8;
    }

    /**
     * Sets the value of the consignmentHeaderInfo8 property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setConsignmentHeaderInfo8(String value) {
        this.consignmentHeaderInfo8 = value;
    }

    /**
     * Gets the value of the consignmentHeaderInfo9 property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getConsignmentHeaderInfo9() {
        return consignmentHeaderInfo9;
    }

    /**
     * Sets the value of the consignmentHeaderInfo9 property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setConsignmentHeaderInfo9(String value) {
        this.consignmentHeaderInfo9 = value;
    }

    /**
     * Gets the value of the consignmentHeaderInfo10 property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getConsignmentHeaderInfo10() {
        return consignmentHeaderInfo10;
    }

    /**
     * Sets the value of the consignmentHeaderInfo10 property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setConsignmentHeaderInfo10(String value) {
        this.consignmentHeaderInfo10 = value;
    }

    /**
     * Gets the value of the consignmentHeaderInfo11 property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getConsignmentHeaderInfo11() {
        return consignmentHeaderInfo11;
    }

    /**
     * Sets the value of the consignmentHeaderInfo11 property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setConsignmentHeaderInfo11(String value) {
        this.consignmentHeaderInfo11 = value;
    }

    /**
     * Gets the value of the consignmentHeaderInfo12 property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getConsignmentHeaderInfo12() {
        return consignmentHeaderInfo12;
    }

    /**
     * Sets the value of the consignmentHeaderInfo12 property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setConsignmentHeaderInfo12(String value) {
        this.consignmentHeaderInfo12 = value;
    }

}
