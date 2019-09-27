
package com.ceva.cevatrack.wsmicrolise.getjourneysummaryforaliasanddate;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author brian.goodall
 *
 */
public class JourneySummarySite {

    protected double actualOdometerLegMileage;
    protected int callSequence;
    protected double fuelConsumption;
    protected int journeyDropId;
    protected String journeyDropRef;
    protected double latitude;
    protected double longitude;
    protected double plannedLegMileage;
    protected int podActualCallSequence;
    protected double podActualLegMileage;
    protected String podata;
    protected String podatd;
    protected String pta;
    protected String ptd;
    protected String siteAddress1;
    protected String siteAddress2;
    protected String siteCity;
    protected String siteCountry;
    protected String siteCounty;
    protected String siteId;
    protected String siteName;
    protected String sitePostCode;
    protected double trackingActualLegMileage;
    protected String trackingAta;
    protected String trackingAtd;
    protected JourneySummarySiteDeplay journeySummarySiteDelays;

    @XmlElement(name = "ActualOdometerLegMileage")
    public double getActualOdometerLegMileage() {
        return actualOdometerLegMileage;
    }

    public void setActualOdometerLegMileage(double value) {
        this.actualOdometerLegMileage = value;
    }

    @XmlElement(name = "CallSequence")
    public int getCallSequence() {
        return callSequence;
    }

    public void setCallSequence(int value) {
        this.callSequence = value;
    }

    @XmlElement(name = "FuelConsumption")
    public double getFuelConsumption() {
        return fuelConsumption;
    }

    public void setFuelConsumption(double value) {
        this.fuelConsumption = value;
    }

    @XmlElement(name = "JourneyDropID")
    public int getJourneyDropId() {
        return journeyDropId;
    }

    public void setJourneyDropId(int value) {
        this.journeyDropId = value;
    }

    @XmlElement(name = "JourneyDropRef")
    public String getJourneyDropRef() {
        return journeyDropRef;
    }

    public void setJourneyDropRef(String value) {
        this.journeyDropRef = value;
    }

    @XmlElement(name = "Latitude")
    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double value) {
        this.latitude = value;
    }

    @XmlElement(name = "Longitude")
    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double value) {
        this.longitude = value;
    }

    @XmlElement(name = "PlannedLegMileage")
    public double getPlannedLegMileage() {
        return plannedLegMileage;
    }

    public void setPlannedLegMileage(double value) {
        this.plannedLegMileage = value;
    }

    @XmlElement(name = "PodActualCallSequence")
    public int getPodActualCallSequence() {
        return podActualCallSequence;
    }

    public void setPodActualCallSequence(int value) {
        this.podActualCallSequence = value;
    }

    @XmlElement(name = "PodActualLegMileage")
    public double getPodActualLegMileage() {
        return podActualLegMileage;
    }

    public void setPodActualLegMileage(double value) {
        this.podActualLegMileage = value;
    }

    @XmlElement(name = "PODATA")
    public String getPodAta() {
        return podata;
    }

    public void setPodAta(String value) {
        this.podata = value;
    }

    @XmlElement(name = "PODATD")
    public String getPodAtd() {
        return podatd;
    }

    public void setPodAtd(String value) {
        this.podatd = value;
    }

    @XmlElement(name = "PTA")
    public String getPta() {
        return pta;
    }

    public void setPta(String value) {
        this.pta = value;
    }

    @XmlElement(name = "PTD")
    public String getPtd() {
        return ptd;
    }

    public void setPtd(String value) {
        this.ptd = value;
    }

    @XmlElement(name = "SiteAddress1")
    public String getSiteAddress1() {
        return siteAddress1;
    }

    public void setSiteAddress1(String value) {
        this.siteAddress1 = value;
    }

    @XmlElement(name = "SiteAddress2")
    public String getSiteAddress2() {
        return siteAddress2;
    }

    public void setSiteAddress2(String value) {
        this.siteAddress2 = value;
    }

    @XmlElement(name = "SiteCity")
    public String getSiteCity() {
        return siteCity;
    }

    public void setSiteCity(String value) {
        this.siteCity = value;
    }

    @XmlElement(name = "SiteCountry")
    public String getSiteCountry() {
        return siteCountry;
    }

    public void setSiteCountry(String value) {
        this.siteCountry = value;
    }

    @XmlElement(name = "SiteCounty")
    public String getSiteCounty() {
        return siteCounty;
    }

    public void setSiteCounty(String value) {
        this.siteCounty = value;
    }

    @XmlElement(name = "SiteID")
    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String value) {
        this.siteId = value;
    }

    @XmlElement(name = "SiteName")
    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String value) {
        this.siteName = value;
    }

    @XmlElement(name = "SitePostCode")
    public String getSitePostCode() {
        return sitePostCode;
    }

    public void setSitePostCode(String value) {
        this.sitePostCode = value;
    }

    @XmlElement(name = "TrackingActualLegMileage")
    public double getTrackingActualLegMileage() {
        return trackingActualLegMileage;
    }

    public void setTrackingActualLegMileage(double value) {
        this.trackingActualLegMileage = value;
    }

    @XmlElement(name = "TrackingATA")
    public String getTrackingAta() {
        return trackingAta;
    }

    public void setTrackingAta(String value) {
        this.trackingAta = value;
    }

    @XmlElement(name = "TrackingATD")
    public String getTrackingAtd() {
        return trackingAtd;
    }

    public void setTrackingAtd(String value) {
        this.trackingAtd = value;
    }

    @XmlElement(name = "JourneySummarySiteDelays")
    public JourneySummarySiteDeplay getJourneySummarySiteDelays() {
        return journeySummarySiteDelays;
    }

    public void setJourneySummarySiteDelays(JourneySummarySiteDeplay value) {
        this.journeySummarySiteDelays = value;
    }

}
