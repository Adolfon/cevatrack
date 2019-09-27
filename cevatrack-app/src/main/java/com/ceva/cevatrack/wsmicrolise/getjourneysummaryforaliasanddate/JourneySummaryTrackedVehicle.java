
package com.ceva.cevatrack.wsmicrolise.getjourneysummaryforaliasanddate;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author brian.goodall
 *
 */
public class JourneySummaryTrackedVehicle {

    protected String displayName;
    protected double endOdometer;
    protected double startOdometer;

    @XmlElement(name = "DisplayName")
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String value) {
        this.displayName = value;
    }

    @XmlElement(name = "EndOdometer")
    public double getEndOdometer() {
        return endOdometer;
    }

    public void setEndOdometer(double value) {
        this.endOdometer = value;
    }

    @XmlElement(name = "StartOdometer")
    public double getStartOdometer() {
        return startOdometer;
    }

    public void setStartOdometer(double value) {
        this.startOdometer = value;
    }

}
