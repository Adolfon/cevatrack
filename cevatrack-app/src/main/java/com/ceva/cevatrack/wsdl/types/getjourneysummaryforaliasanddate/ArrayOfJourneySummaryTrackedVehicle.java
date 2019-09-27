
package com.ceva.cevatrack.wsdl.types.getjourneysummaryforaliasanddate;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for ArrayOfJourneySummaryTrackedVehicle complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfJourneySummaryTrackedVehicle">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="JourneySummaryTrackedVehicle" type="{http://www.cevalogistics.com/XSD/MicroliseSmartNet/TMC/GetJourneySummaryForAliasAndDate}JourneySummaryTrackedVehicle" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfJourneySummaryTrackedVehicle", propOrder = { "journeySummaryTrackedVehicle" })
public class ArrayOfJourneySummaryTrackedVehicle {

    @XmlElement(name = "JourneySummaryTrackedVehicle", nillable = true)
    protected List<JourneySummaryTrackedVehicle> journeySummaryTrackedVehicle;

    /**
     * Gets the value of the journeySummaryTrackedVehicle property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to
     * the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for
     * the journeySummaryTrackedVehicle property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getJourneySummaryTrackedVehicle().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link JourneySummaryTrackedVehicle }
     * 
     * 
     */
    public List<JourneySummaryTrackedVehicle> getJourneySummaryTrackedVehicle() {
        if (journeySummaryTrackedVehicle == null) {
            journeySummaryTrackedVehicle = new ArrayList<JourneySummaryTrackedVehicle>();
        }
        return this.journeySummaryTrackedVehicle;
    }

}
