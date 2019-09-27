
package com.ceva.cevatrack.wsmicrolise.getjourneysummaryforaliasanddate;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author brian.goodall
 *
 */
public class PlannedDelay {

    protected int duration;
    protected String type;
    protected String position;

    @XmlElement(name = "Duration")
    public int getDuration() {
        return duration;
    }

    public void setDuration(int value) {
        this.duration = value;
    }

    @XmlElement(name = "Type")
    public String getType() {
        return type;
    }

    public void setType(String value) {
        this.type = value;
    }

    @XmlElement(name = "Position")
    public String getPosition() {
        return position;
    }

    public void setPosition(String value) {
        this.position = value;
    }

}
