
package com.ceva.cevatrack.wsdl.types.getjourneysummaryforaliasanddate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for JourneySummarySiteDeplay complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="JourneySummarySiteDeplay">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PlannedDelays" type="{http://www.cevalogistics.com/XSD/MicroliseSmartNet/TMC/GetJourneySummaryForAliasAndDate}ArrayOfPlannedDelay" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "JourneySummarySiteDeplay", propOrder = {"plannedDelays"})
public class JourneySummarySiteDeplay {

  @XmlElement(name = "PlannedDelays")
  protected ArrayOfPlannedDelay plannedDelays;

  /**
   * Gets the value of the plannedDelays property.
   * 
   * @return possible object is {@link ArrayOfPlannedDelay }
   * 
   */
  public ArrayOfPlannedDelay getPlannedDelays() {
    return plannedDelays;
  }

  /**
   * Sets the value of the plannedDelays property.
   * 
   * @param value allowed object is {@link ArrayOfPlannedDelay }
   * 
   */
  public void setPlannedDelays(ArrayOfPlannedDelay value) {
    this.plannedDelays = value;
  }

}
