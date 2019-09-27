
package com.ceva.cevatrack.wsdl.types.getjourneysummaryforaliasanddate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for JourneySummaryTrackedVehicle complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="JourneySummaryTrackedVehicle">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DisplayName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EndOdometer" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="StartOdometer" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "JourneySummaryTrackedVehicle", propOrder = { "displayName", "endOdometer", "startOdometer" })
public class JourneySummaryTrackedVehicle {

    @XmlElement(name = "DisplayName")
    protected String displayName;
    @XmlElement(name = "EndOdometer")
    protected double endOdometer;
    @XmlElement(name = "StartOdometer")
    protected double startOdometer;

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
     * Gets the value of the endOdometer property.
     * 
     */
    public double getEndOdometer() {
        return endOdometer;
    }

    /**
     * Sets the value of the endOdometer property.
     * 
     */
    public void setEndOdometer(double value) {
        this.endOdometer = value;
    }

    /**
     * Gets the value of the startOdometer property.
     * 
     */
    public double getStartOdometer() {
        return startOdometer;
    }

    /**
     * Sets the value of the startOdometer property.
     * 
     */
    public void setStartOdometer(double value) {
        this.startOdometer = value;
    }

}
