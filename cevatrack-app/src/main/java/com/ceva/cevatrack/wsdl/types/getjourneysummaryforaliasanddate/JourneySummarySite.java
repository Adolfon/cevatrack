
package com.ceva.cevatrack.wsdl.types.getjourneysummaryforaliasanddate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>
 * Java class for JourneySummarySite complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="JourneySummarySite">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ActualOdometerLegMileage" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="CallSequence" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="FuelConsumption" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="JourneyDropID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="JourneyDropRef" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Latitude" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="Longitude" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="PlannedLegMileage" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="PodActualCallSequence" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="PodActualLegMileage" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="PODATA" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="PODATD" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="PTA" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="PTD" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="SiteAddress1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SiteAddress2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SiteCity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SiteCountry" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SiteCounty" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SiteID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SiteName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SitePostCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TrackingActualLegMileage" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="TrackingATA" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="TrackingATD" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="JourneySummarySiteDelays" type="{http://www.cevalogistics.com/XSD/MicroliseSmartNet/TMC/GetJourneySummaryForAliasAndDate}JourneySummarySiteDeplay" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "JourneySummarySite",
    propOrder = {"actualOdometerLegMileage", "callSequence", "fuelConsumption", "journeyDropID",
        "journeyDropRef", "latitude", "longitude", "plannedLegMileage", "podActualCallSequence",
        "podActualLegMileage", "podata", "podatd", "pta", "ptd", "siteAddress1", "siteAddress2",
        "siteCity", "siteCountry", "siteCounty", "siteID", "siteName", "sitePostCode",
        "trackingActualLegMileage", "trackingATA", "trackingATD", "journeySummarySiteDelays"})
public class JourneySummarySite {

  @XmlElement(name = "ActualOdometerLegMileage")
  protected double actualOdometerLegMileage;
  @XmlElement(name = "CallSequence")
  protected int callSequence;
  @XmlElement(name = "FuelConsumption")
  protected double fuelConsumption;
  @XmlElement(name = "JourneyDropID")
  protected int journeyDropID;
  @XmlElement(name = "JourneyDropRef")
  protected String journeyDropRef;
  @XmlElement(name = "Latitude")
  protected double latitude;
  @XmlElement(name = "Longitude")
  protected double longitude;
  @XmlElement(name = "PlannedLegMileage")
  protected double plannedLegMileage;
  @XmlElement(name = "PodActualCallSequence")
  protected int podActualCallSequence;
  @XmlElement(name = "PodActualLegMileage")
  protected double podActualLegMileage;
  @XmlElement(name = "PODATA")
  @XmlSchemaType(name = "dateTime")
  protected XMLGregorianCalendar podata;
  @XmlElement(name = "PODATD")
  @XmlSchemaType(name = "dateTime")
  protected XMLGregorianCalendar podatd;
  @XmlElement(name = "PTA")
  @XmlSchemaType(name = "dateTime")
  protected XMLGregorianCalendar pta;
  @XmlElement(name = "PTD")
  @XmlSchemaType(name = "dateTime")
  protected XMLGregorianCalendar ptd;
  @XmlElement(name = "SiteAddress1")
  protected String siteAddress1;
  @XmlElement(name = "SiteAddress2")
  protected String siteAddress2;
  @XmlElement(name = "SiteCity")
  protected String siteCity;
  @XmlElement(name = "SiteCountry")
  protected String siteCountry;
  @XmlElement(name = "SiteCounty")
  protected String siteCounty;
  @XmlElement(name = "SiteID")
  protected String siteID;
  @XmlElement(name = "SiteName")
  protected String siteName;
  @XmlElement(name = "SitePostCode")
  protected String sitePostCode;
  @XmlElement(name = "TrackingActualLegMileage")
  protected double trackingActualLegMileage;
  @XmlElement(name = "TrackingATA")
  @XmlSchemaType(name = "dateTime")
  protected XMLGregorianCalendar trackingATA;
  @XmlElement(name = "TrackingATD")
  @XmlSchemaType(name = "dateTime")
  protected XMLGregorianCalendar trackingATD;
  @XmlElement(name = "JourneySummarySiteDelays")
  protected JourneySummarySiteDeplay journeySummarySiteDelays;

  /**
   * Gets the value of the actualOdometerLegMileage property.
   * 
   */
  public double getActualOdometerLegMileage() {
    return actualOdometerLegMileage;
  }

  /**
   * Sets the value of the actualOdometerLegMileage property.
   * 
   */
  public void setActualOdometerLegMileage(double value) {
    this.actualOdometerLegMileage = value;
  }

  /**
   * Gets the value of the callSequence property.
   * 
   */
  public int getCallSequence() {
    return callSequence;
  }

  /**
   * Sets the value of the callSequence property.
   * 
   */
  public void setCallSequence(int value) {
    this.callSequence = value;
  }

  /**
   * Gets the value of the fuelConsumption property.
   * 
   */
  public double getFuelConsumption() {
    return fuelConsumption;
  }

  /**
   * Sets the value of the fuelConsumption property.
   * 
   */
  public void setFuelConsumption(double value) {
    this.fuelConsumption = value;
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
   * Gets the value of the journeyDropRef property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getJourneyDropRef() {
    return journeyDropRef;
  }

  /**
   * Sets the value of the journeyDropRef property.
   * 
   * @param value allowed object is {@link String }
   * 
   */
  public void setJourneyDropRef(String value) {
    this.journeyDropRef = value;
  }

  /**
   * Gets the value of the latitude property.
   * 
   */
  public double getLatitude() {
    return latitude;
  }

  /**
   * Sets the value of the latitude property.
   * 
   */
  public void setLatitude(double value) {
    this.latitude = value;
  }

  /**
   * Gets the value of the longitude property.
   * 
   */
  public double getLongitude() {
    return longitude;
  }

  /**
   * Sets the value of the longitude property.
   * 
   */
  public void setLongitude(double value) {
    this.longitude = value;
  }

  /**
   * Gets the value of the plannedLegMileage property.
   * 
   */
  public double getPlannedLegMileage() {
    return plannedLegMileage;
  }

  /**
   * Sets the value of the plannedLegMileage property.
   * 
   */
  public void setPlannedLegMileage(double value) {
    this.plannedLegMileage = value;
  }

  /**
   * Gets the value of the podActualCallSequence property.
   * 
   */
  public int getPodActualCallSequence() {
    return podActualCallSequence;
  }

  /**
   * Sets the value of the podActualCallSequence property.
   * 
   */
  public void setPodActualCallSequence(int value) {
    this.podActualCallSequence = value;
  }

  /**
   * Gets the value of the podActualLegMileage property.
   * 
   */
  public double getPodActualLegMileage() {
    return podActualLegMileage;
  }

  /**
   * Sets the value of the podActualLegMileage property.
   * 
   */
  public void setPodActualLegMileage(double value) {
    this.podActualLegMileage = value;
  }

  /**
   * Gets the value of the podata property.
   * 
   * @return possible object is {@link XMLGregorianCalendar }
   * 
   */
  public XMLGregorianCalendar getPODATA() {
    return podata;
  }

  /**
   * Sets the value of the podata property.
   * 
   * @param value allowed object is {@link XMLGregorianCalendar }
   * 
   */
  public void setPODATA(XMLGregorianCalendar value) {
    this.podata = value;
  }

  /**
   * Gets the value of the podatd property.
   * 
   * @return possible object is {@link XMLGregorianCalendar }
   * 
   */
  public XMLGregorianCalendar getPODATD() {
    return podatd;
  }

  /**
   * Sets the value of the podatd property.
   * 
   * @param value allowed object is {@link XMLGregorianCalendar }
   * 
   */
  public void setPODATD(XMLGregorianCalendar value) {
    this.podatd = value;
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
   * @param value allowed object is {@link XMLGregorianCalendar }
   * 
   */
  public void setPTD(XMLGregorianCalendar value) {
    this.ptd = value;
  }

  /**
   * Gets the value of the siteAddress1 property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getSiteAddress1() {
    return siteAddress1;
  }

  /**
   * Sets the value of the siteAddress1 property.
   * 
   * @param value allowed object is {@link String }
   * 
   */
  public void setSiteAddress1(String value) {
    this.siteAddress1 = value;
  }

  /**
   * Gets the value of the siteAddress2 property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getSiteAddress2() {
    return siteAddress2;
  }

  /**
   * Sets the value of the siteAddress2 property.
   * 
   * @param value allowed object is {@link String }
   * 
   */
  public void setSiteAddress2(String value) {
    this.siteAddress2 = value;
  }

  /**
   * Gets the value of the siteCity property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getSiteCity() {
    return siteCity;
  }

  /**
   * Sets the value of the siteCity property.
   * 
   * @param value allowed object is {@link String }
   * 
   */
  public void setSiteCity(String value) {
    this.siteCity = value;
  }

  /**
   * Gets the value of the siteCountry property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getSiteCountry() {
    return siteCountry;
  }

  /**
   * Sets the value of the siteCountry property.
   * 
   * @param value allowed object is {@link String }
   * 
   */
  public void setSiteCountry(String value) {
    this.siteCountry = value;
  }

  /**
   * Gets the value of the siteCounty property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getSiteCounty() {
    return siteCounty;
  }

  /**
   * Sets the value of the siteCounty property.
   * 
   * @param value allowed object is {@link String }
   * 
   */
  public void setSiteCounty(String value) {
    this.siteCounty = value;
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
   * @param value allowed object is {@link String }
   * 
   */
  public void setSitePostCode(String value) {
    this.sitePostCode = value;
  }

  /**
   * Gets the value of the trackingActualLegMileage property.
   * 
   */
  public double getTrackingActualLegMileage() {
    return trackingActualLegMileage;
  }

  /**
   * Sets the value of the trackingActualLegMileage property.
   * 
   */
  public void setTrackingActualLegMileage(double value) {
    this.trackingActualLegMileage = value;
  }

  /**
   * Gets the value of the trackingATA property.
   * 
   * @return possible object is {@link XMLGregorianCalendar }
   * 
   */
  public XMLGregorianCalendar getTrackingATA() {
    return trackingATA;
  }

  /**
   * Sets the value of the trackingATA property.
   * 
   * @param value allowed object is {@link XMLGregorianCalendar }
   * 
   */
  public void setTrackingATA(XMLGregorianCalendar value) {
    this.trackingATA = value;
  }

  /**
   * Gets the value of the trackingATD property.
   * 
   * @return possible object is {@link XMLGregorianCalendar }
   * 
   */
  public XMLGregorianCalendar getTrackingATD() {
    return trackingATD;
  }

  /**
   * Sets the value of the trackingATD property.
   * 
   * @param value allowed object is {@link XMLGregorianCalendar }
   * 
   */
  public void setTrackingATD(XMLGregorianCalendar value) {
    this.trackingATD = value;
  }

  /**
   * Gets the value of the journeySummarySiteDelays property.
   * 
   * @return possible object is {@link JourneySummarySiteDeplay }
   * 
   */
  public JourneySummarySiteDeplay getJourneySummarySiteDelays() {
    return journeySummarySiteDelays;
  }

  /**
   * Sets the value of the journeySummarySiteDelays property.
   * 
   * @param value allowed object is {@link JourneySummarySiteDeplay }
   * 
   */
  public void setJourneySummarySiteDelays(JourneySummarySiteDeplay value) {
    this.journeySummarySiteDelays = value;
  }

}
