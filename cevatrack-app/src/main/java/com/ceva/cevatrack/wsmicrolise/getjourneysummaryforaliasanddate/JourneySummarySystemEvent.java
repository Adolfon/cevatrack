
package com.ceva.cevatrack.wsmicrolise.getjourneysummaryforaliasanddate;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author brian.goodall
 *
 */
public class JourneySummarySystemEvent {

    protected String ruleId;
    protected String eventDateTime;
    protected String eventDescription;
    protected String displayName;
    protected Double eventLatitude;
    protected String eventLocation;
    protected Double eventLongitude;

    @XmlElement(name = "RuleID")
    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String value) {
        this.ruleId = value;
    }

    @XmlElement(name = "EventDateTime")
    public String getEventDateTime() {
        return eventDateTime;
    }

    public void setEventDateTime(String value) {
        this.eventDateTime = value;
    }

    @XmlElement(name = "EventDescription")
    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String value) {
        this.eventDescription = value;
    }

    @XmlElement(name = "DisplayName")
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String value) {
        this.displayName = value;
    }

    @XmlElement(name = "EventLatitude")
    public Double getEventLatitude() {
        return eventLatitude;
    }

    public void setEventLatitude(Double value) {
        this.eventLatitude = value;
    }

    @XmlElement(name = "EventLocation")
    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String value) {
        this.eventLocation = value;
    }

    @XmlElement(name = "EventLongitude")
    public Double getEventLongitude() {
        return eventLongitude;
    }

    public void setEventLongitude(Double value) {
        this.eventLongitude = value;
    }

}
