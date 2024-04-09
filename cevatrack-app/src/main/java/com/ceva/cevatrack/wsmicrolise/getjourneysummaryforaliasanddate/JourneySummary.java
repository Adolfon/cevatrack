
package com.ceva.cevatrack.wsmicrolise.getjourneysummaryforaliasanddate;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author brian.goodall
 *
 */
public class JourneySummary {

  protected String actualEndTime;
  protected int actualMileage;
  protected String actualStartTime;
  protected ArrayList<JourneySummarySystemEvent> events;
  protected double fuelConsumption;
  protected String plannedEndTime;
  protected int plannedMileage;
  protected String plannedStartTime;
  protected ArrayList<JourneySummaryResource> resources;
  protected String routeId;
  protected String journeyStatus;
  protected String podStatus;
  protected String trackingStatus;
  protected ArrayList<JourneySummarySite> sites;
  protected ArrayList<JourneySummaryTrackedVehicle> vehicles;
  protected int journeyId;
  protected String journeyAlias;

  @XmlElement(name = "ActualEndTime")
  public String getActualEndTime() {
    return actualEndTime;
  }

  public void setActualEndTime(String value) {
    this.actualEndTime = value;
  }

  @XmlElement(name = "ActualMileage")
  public int getActualMileage() {
    return actualMileage;
  }

  public void setActualMileage(int value) {
    this.actualMileage = value;
  }

  @XmlElement(name = "ActualStartTime")
  public String getActualStartTime() {
    return actualStartTime;
  }

  public void setActualStartTime(String value) {
    this.actualStartTime = value;
  }

  @XmlElement(name = "Events")
  public ArrayList<JourneySummarySystemEvent> getEvents() {
    return events;
  }

  public void setEvents(ArrayList<JourneySummarySystemEvent> value) {
    this.events = value;
  }

  @XmlElement(name = "FuelConsumption")
  public double getFuelConsumption() {
    return fuelConsumption;
  }

  public void setFuelConsumption(double value) {
    this.fuelConsumption = value;
  }

  @XmlElement(name = "PlannedEndTime")
  public String getPlannedEndTime() {
    return plannedEndTime;
  }

  public void setPlannedEndTime(String value) {
    this.plannedEndTime = value;
  }

  @XmlElement(name = "PlannedMileage")
  public int getPlannedMileage() {
    return plannedMileage;
  }

  public void setPlannedMileage(int value) {
    this.plannedMileage = value;
  }

  @XmlElement(name = "PlannedStartTime")
  public String getPlannedStartTime() {
    return plannedStartTime;
  }

  public void setPlannedStartTime(String value) {
    this.plannedStartTime = value;
  }

  @XmlElement(name = "Resources")
  public ArrayList<JourneySummaryResource> getResources() {
    return resources;
  }

  public void setResources(ArrayList<JourneySummaryResource> value) {
    this.resources = value;
  }

  @XmlElement(name = "RouteID")
  public String getRouteId() {
    return routeId;
  }

  public void setRouteId(String value) {
    this.routeId = value;
  }

  @XmlElement(name = "JourneyStatus")
  public String getJourneyStatus() {
    return journeyStatus;
  }

  public void setJourneyStatus(String value) {
    this.journeyStatus = value;
  }

  @XmlElement(name = "PODStatus")
  public String getPodStatus() {
    return podStatus;
  }

  public void setPodStatus(String value) {
    this.podStatus = value;
  }

  @XmlElement(name = "TrackingStatus")
  public String getTrackingStatus() {
    return trackingStatus;
  }

  public void setTrackingStatus(String value) {
    this.trackingStatus = value;
  }

  @XmlElement(name = "Sites")
  public ArrayList<JourneySummarySite> getSites() {
    return sites;
  }

  public void setSites(ArrayList<JourneySummarySite> value) {
    this.sites = value;
  }

  @XmlElement(name = "Vehicles")
  public ArrayList<JourneySummaryTrackedVehicle> getVehicles() {
    return vehicles;
  }

  public void setVehicles(ArrayList<JourneySummaryTrackedVehicle> value) {
    this.vehicles = value;
  }

  @XmlElement(name = "JourneyID")
  public int getJourneyId() {
    return journeyId;
  }

  public void setJourneyId(int value) {
    this.journeyId = value;
  }

  @XmlElement(name = "JourneyAlias")
  public String getJourneyAlias() {
    return journeyAlias;
  }

  public void setJourneyAlias(String value) {
    this.journeyAlias = value;
  }

}
