
package com.ceva.cevatrack.wsdl.types.getjourneysummaryforaliasanddate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>
 * Java class for JourneySummarySystemEvent complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="JourneySummarySystemEvent">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RuleID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EventDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="EventDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DisplayName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EventLatitude" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="EventLocation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EventLongitude" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "JourneySummarySystemEvent", propOrder = { "ruleID", "eventDateTime", "eventDescription", "displayName",
        "eventLatitude", "eventLocation", "eventLongitude" })
public class JourneySummarySystemEvent {

    @XmlElement(name = "RuleID")
    protected String ruleID;
    @XmlElement(name = "EventDateTime", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar eventDateTime;
    @XmlElement(name = "EventDescription")
    protected String eventDescription;
    @XmlElement(name = "DisplayName")
    protected String displayName;
    @XmlElement(name = "EventLatitude")
    protected Double eventLatitude;
    @XmlElement(name = "EventLocation")
    protected String eventLocation;
    @XmlElement(name = "EventLongitude")
    protected Double eventLongitude;

    /**
     * Gets the value of the ruleID property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getRuleID() {
        return ruleID;
    }

    /**
     * Sets the value of the ruleID property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setRuleID(String value) {
        this.ruleID = value;
    }

    /**
     * Gets the value of the eventDateTime property.
     * 
     * @return possible object is {@link XMLGregorianCalendar }
     * 
     */
    public XMLGregorianCalendar getEventDateTime() {
        return eventDateTime;
    }

    /**
     * Sets the value of the eventDateTime property.
     * 
     * @param value
     *            allowed object is {@link XMLGregorianCalendar }
     * 
     */
    public void setEventDateTime(XMLGregorianCalendar value) {
        this.eventDateTime = value;
    }

    /**
     * Gets the value of the eventDescription property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getEventDescription() {
        return eventDescription;
    }

    /**
     * Sets the value of the eventDescription property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setEventDescription(String value) {
        this.eventDescription = value;
    }

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
     * Gets the value of the eventLatitude property.
     * 
     * @return possible object is {@link Double }
     * 
     */
    public Double getEventLatitude() {
        return eventLatitude;
    }

    /**
     * Sets the value of the eventLatitude property.
     * 
     * @param value
     *            allowed object is {@link Double }
     * 
     */
    public void setEventLatitude(Double value) {
        this.eventLatitude = value;
    }

    /**
     * Gets the value of the eventLocation property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getEventLocation() {
        return eventLocation;
    }

    /**
     * Sets the value of the eventLocation property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setEventLocation(String value) {
        this.eventLocation = value;
    }

    /**
     * Gets the value of the eventLongitude property.
     * 
     * @return possible object is {@link Double }
     * 
     */
    public Double getEventLongitude() {
        return eventLongitude;
    }

    /**
     * Sets the value of the eventLongitude property.
     * 
     * @param value
     *            allowed object is {@link Double }
     * 
     */
    public void setEventLongitude(Double value) {
        this.eventLongitude = value;
    }

}
