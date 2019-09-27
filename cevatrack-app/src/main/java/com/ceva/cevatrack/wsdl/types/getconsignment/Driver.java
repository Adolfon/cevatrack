
package com.ceva.cevatrack.wsdl.types.getconsignment;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for Driver complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Driver">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DisplayName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FullName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ContactNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="JourneyID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Driver", propOrder = { "displayName", "fullName", "contactNumber", "journeyID" })
public class Driver {

    @XmlElement(name = "DisplayName")
    protected String displayName;
    @XmlElement(name = "FullName")
    protected String fullName;
    @XmlElement(name = "ContactNumber")
    protected String contactNumber;
    @XmlElement(name = "JourneyID")
    protected int journeyID;

    /**
     * Gets the value of the displayName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Sets the value of the displayName property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setDisplayName(String value) {
        this.displayName = value;
    }

    /**
     * Gets the value of the fullName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Sets the value of the fullName property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setFullName(String value) {
        this.fullName = value;
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

}
