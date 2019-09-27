
package com.ceva.cevatrack.wsmicrolise.getconsignment;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author brian.goodall
 *
 */
public class Driver {

    protected String displayName;
    protected String fullName;
    protected String contactNumber;
    protected int journeyId;

    @XmlElement(name = "DisplayName")
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String value) {
        this.displayName = value;
    }

    @XmlElement(name = "FullName")
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String value) {
        this.fullName = value;
    }

    @XmlElement(name = "ContactNumber")
    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String value) {
        this.contactNumber = value;
    }

    @XmlElement(name = "JourneyID")
    public int getJourneyId() {
        return journeyId;
    }

    public void setJourneyId(int value) {
        this.journeyId = value;
    }

}
