
package com.ceva.cevatrack.wsdl.types.getjourneysummaryforaliasanddate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>
 * Java class for JourneySummary complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="JourneySummary">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ActualEndTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="ActualMileage" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ActualStartTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="Events" type="{http://www.cevalogistics.com/XSD/MicroliseSmartNet/TMC/GetJourneySummaryForAliasAndDate}ArrayOfJourneySummarySystemEvent" minOccurs="0"/>
 *         &lt;element name="FuelConsumption" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="PlannedEndTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="PlannedMileage" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="PlannedStartTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="Resources" type="{http://www.cevalogistics.com/XSD/MicroliseSmartNet/TMC/GetJourneySummaryForAliasAndDate}ArrayOfJourneySummaryResource" minOccurs="0"/>
 *         &lt;element name="RouteID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="JourneyStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PODStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TrackingStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Sites" type="{http://www.cevalogistics.com/XSD/MicroliseSmartNet/TMC/GetJourneySummaryForAliasAndDate}ArrayOfJourneySummarySite" minOccurs="0"/>
 *         &lt;element name="Vehicles" type="{http://www.cevalogistics.com/XSD/MicroliseSmartNet/TMC/GetJourneySummaryForAliasAndDate}ArrayOfJourneySummaryTrackedVehicle" minOccurs="0"/>
 *         &lt;element name="JourneyID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="JourneyAlias" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "JourneySummary", propOrder = { "actualEndTime", "actualMileage", "actualStartTime", "events",
        "fuelConsumption", "plannedEndTime", "plannedMileage", "plannedStartTime", "resources", "routeID",
        "journeyStatus", "podStatus", "trackingStatus", "sites", "vehicles", "journeyID", "journeyAlias" })
public class JourneySummary {

    @XmlElement(name = "ActualEndTime")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar actualEndTime;
    @XmlElement(name = "ActualMileage")
    protected int actualMileage;
    @XmlElement(name = "ActualStartTime")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar actualStartTime;
    @XmlElement(name = "Events")
    protected ArrayOfJourneySummarySystemEvent events;
    @XmlElement(name = "FuelConsumption")
    protected double fuelConsumption;
    @XmlElement(name = "PlannedEndTime")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar plannedEndTime;
    @XmlElement(name = "PlannedMileage")
    protected int plannedMileage;
    @XmlElement(name = "PlannedStartTime")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar plannedStartTime;
    @XmlElement(name = "Resources")
    protected ArrayOfJourneySummaryResource resources;
    @XmlElement(name = "RouteID")
    protected String routeID;
    @XmlElement(name = "JourneyStatus")
    protected String journeyStatus;
    @XmlElement(name = "PODStatus")
    protected String podStatus;
    @XmlElement(name = "TrackingStatus")
    protected String trackingStatus;
    @XmlElement(name = "Sites")
    protected ArrayOfJourneySummarySite sites;
    @XmlElement(name = "Vehicles")
    protected ArrayOfJourneySummaryTrackedVehicle vehicles;
    @XmlElement(name = "JourneyID")
    protected int journeyID;
    @XmlElement(name = "JourneyAlias")
    protected String journeyAlias;

    /**
     * Gets the value of the actualEndTime property.
     * 
     * @return possible object is {@link XMLGregorianCalendar }
     * 
     */
    public XMLGregorianCalendar getActualEndTime() {
        return actualEndTime;
    }

    /**
     * Sets the value of the actualEndTime property.
     * 
     * @param value
     *            allowed object is {@link XMLGregorianCalendar }
     * 
     */
    public void setActualEndTime(XMLGregorianCalendar value) {
        this.actualEndTime = value;
    }

    /**
     * Gets the value of the actualMileage property.
     * 
     */
    public int getActualMileage() {
        return actualMileage;
    }

    /**
     * Sets the value of the actualMileage property.
     * 
     */
    public void setActualMileage(int value) {
        this.actualMileage = value;
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
     * Gets the value of the events property.
     * 
     * @return possible object is {@link ArrayOfJourneySummarySystemEvent }
     * 
     */
    public ArrayOfJourneySummarySystemEvent getEvents() {
        return events;
    }

    /**
     * Sets the value of the events property.
     * 
     * @param value
     *            allowed object is {@link ArrayOfJourneySummarySystemEvent }
     * 
     */
    public void setEvents(ArrayOfJourneySummarySystemEvent value) {
        this.events = value;
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
     * Gets the value of the plannedEndTime property.
     * 
     * @return possible object is {@link XMLGregorianCalendar }
     * 
     */
    public XMLGregorianCalendar getPlannedEndTime() {
        return plannedEndTime;
    }

    /**
     * Sets the value of the plannedEndTime property.
     * 
     * @param value
     *            allowed object is {@link XMLGregorianCalendar }
     * 
     */
    public void setPlannedEndTime(XMLGregorianCalendar value) {
        this.plannedEndTime = value;
    }

    /**
     * Gets the value of the plannedMileage property.
     * 
     */
    public int getPlannedMileage() {
        return plannedMileage;
    }

    /**
     * Sets the value of the plannedMileage property.
     * 
     */
    public void setPlannedMileage(int value) {
        this.plannedMileage = value;
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
     * Gets the value of the resources property.
     * 
     * @return possible object is {@link ArrayOfJourneySummaryResource }
     * 
     */
    public ArrayOfJourneySummaryResource getResources() {
        return resources;
    }

    /**
     * Sets the value of the resources property.
     * 
     * @param value
     *            allowed object is {@link ArrayOfJourneySummaryResource }
     * 
     */
    public void setResources(ArrayOfJourneySummaryResource value) {
        this.resources = value;
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
     * Gets the value of the journeyStatus property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getJourneyStatus() {
        return journeyStatus;
    }

    /**
     * Sets the value of the journeyStatus property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setJourneyStatus(String value) {
        this.journeyStatus = value;
    }

    /**
     * Gets the value of the podStatus property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getPODStatus() {
        return podStatus;
    }

    /**
     * Sets the value of the podStatus property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setPODStatus(String value) {
        this.podStatus = value;
    }

    /**
     * Gets the value of the trackingStatus property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getTrackingStatus() {
        return trackingStatus;
    }

    /**
     * Sets the value of the trackingStatus property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setTrackingStatus(String value) {
        this.trackingStatus = value;
    }

    /**
     * Gets the value of the sites property.
     * 
     * @return possible object is {@link ArrayOfJourneySummarySite }
     * 
     */
    public ArrayOfJourneySummarySite getSites() {
        return sites;
    }

    /**
     * Sets the value of the sites property.
     * 
     * @param value
     *            allowed object is {@link ArrayOfJourneySummarySite }
     * 
     */
    public void setSites(ArrayOfJourneySummarySite value) {
        this.sites = value;
    }

    /**
     * Gets the value of the vehicles property.
     * 
     * @return possible object is {@link ArrayOfJourneySummaryTrackedVehicle }
     * 
     */
    public ArrayOfJourneySummaryTrackedVehicle getVehicles() {
        return vehicles;
    }

    /**
     * Sets the value of the vehicles property.
     * 
     * @param value
     *            allowed object is {@link ArrayOfJourneySummaryTrackedVehicle }
     * 
     */
    public void setVehicles(ArrayOfJourneySummaryTrackedVehicle value) {
        this.vehicles = value;
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

}
