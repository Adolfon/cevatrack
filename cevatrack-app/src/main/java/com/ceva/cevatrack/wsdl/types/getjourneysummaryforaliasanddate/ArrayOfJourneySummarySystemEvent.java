
package com.ceva.cevatrack.wsdl.types.getjourneysummaryforaliasanddate;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for ArrayOfJourneySummarySystemEvent complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfJourneySummarySystemEvent">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="JourneySummarySystemEvent" type="{http://www.cevalogistics.com/XSD/MicroliseSmartNet/TMC/GetJourneySummaryForAliasAndDate}JourneySummarySystemEvent" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfJourneySummarySystemEvent", propOrder = { "journeySummarySystemEvent" })
public class ArrayOfJourneySummarySystemEvent {

    @XmlElement(name = "JourneySummarySystemEvent", nillable = true)
    protected List<JourneySummarySystemEvent> journeySummarySystemEvent;

    /**
     * Gets the value of the journeySummarySystemEvent property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to
     * the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for
     * the journeySummarySystemEvent property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getJourneySummarySystemEvent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link JourneySummarySystemEvent }
     * 
     * 
     */
    public List<JourneySummarySystemEvent> getJourneySummarySystemEvent() {
        if (journeySummarySystemEvent == null) {
            journeySummarySystemEvent = new ArrayList<JourneySummarySystemEvent>();
        }
        return this.journeySummarySystemEvent;
    }

}
