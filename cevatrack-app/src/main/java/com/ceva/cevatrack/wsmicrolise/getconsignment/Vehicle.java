
package com.ceva.cevatrack.wsmicrolise.getconsignment;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author brian.goodall
 *
 */
public class Vehicle {

    protected String displayName;
    protected String lastLocation;
    protected float lastContactLat;
    protected float lastContactLong;
    protected int journeyId;

    @XmlElement(name = "DisplayName")
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String value) {
        this.displayName = value;
    }

    @XmlElement(name = "LastLocation")
    public String getLastLocation() {
        return lastLocation;
    }

    public void setLastLocation(String value) {
        this.lastLocation = value;
    }

    @XmlElement(name = "LastContactLat")
    public float getLastContactLat() {
        return lastContactLat;
    }

    public void setLastContactLat(float value) {
        this.lastContactLat = value;
    }

    @XmlElement(name = "LastContactLong")
    public float getLastContactLong() {
        return lastContactLong;
    }

    public void setLastContactLong(float value) {
        this.lastContactLong = value;
    }

    @XmlElement(name = "JourneyID")
    public int getJourneyId() {
        return journeyId;
    }

    public void setJourneyId(int value) {
        this.journeyId = value;
    }

}
