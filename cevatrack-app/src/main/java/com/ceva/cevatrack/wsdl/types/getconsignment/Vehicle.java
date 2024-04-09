
package com.ceva.cevatrack.wsdl.types.getconsignment;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for Vehicle complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Vehicle">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DisplayName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LastLocation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="LastContactLat" type="{http://www.w3.org/2001/XMLSchema}float"/>
 *         &lt;element name="LastContactLong" type="{http://www.w3.org/2001/XMLSchema}float"/>
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
@XmlType(name = "Vehicle",
    propOrder = {"displayName", "lastLocation", "lastContactLat", "lastContactLong", "journeyID"})
public class Vehicle {

  @XmlElement(name = "DisplayName")
  protected String displayName;
  @XmlElement(name = "LastLocation")
  protected String lastLocation;
  @XmlElement(name = "LastContactLat")
  protected float lastContactLat;
  @XmlElement(name = "LastContactLong")
  protected float lastContactLong;
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
   * @param value allowed object is {@link String }
   * 
   */
  public void setDisplayName(String value) {
    this.displayName = value;
  }

  /**
   * Gets the value of the lastLocation property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getLastLocation() {
    return lastLocation;
  }

  /**
   * Sets the value of the lastLocation property.
   * 
   * @param value allowed object is {@link String }
   * 
   */
  public void setLastLocation(String value) {
    this.lastLocation = value;
  }

  /**
   * Gets the value of the lastContactLat property.
   * 
   */
  public float getLastContactLat() {
    return lastContactLat;
  }

  /**
   * Sets the value of the lastContactLat property.
   * 
   */
  public void setLastContactLat(float value) {
    this.lastContactLat = value;
  }

  /**
   * Gets the value of the lastContactLong property.
   * 
   */
  public float getLastContactLong() {
    return lastContactLong;
  }

  /**
   * Sets the value of the lastContactLong property.
   * 
   */
  public void setLastContactLong(float value) {
    this.lastContactLong = value;
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
