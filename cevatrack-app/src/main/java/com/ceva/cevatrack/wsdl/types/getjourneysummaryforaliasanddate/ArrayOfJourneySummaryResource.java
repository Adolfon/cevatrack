
package com.ceva.cevatrack.wsdl.types.getjourneysummaryforaliasanddate;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for ArrayOfJourneySummaryResource complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfJourneySummaryResource">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="JourneySummaryResource" type="{http://www.cevalogistics.com/XSD/MicroliseSmartNet/TMC/GetJourneySummaryForAliasAndDate}JourneySummaryResource" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfJourneySummaryResource", propOrder = { "journeySummaryResource" })
public class ArrayOfJourneySummaryResource {

    @XmlElement(name = "JourneySummaryResource", nillable = true)
    protected List<JourneySummaryResource> journeySummaryResource;

    /**
     * Gets the value of the journeySummaryResource property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to
     * the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for
     * the journeySummaryResource property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getJourneySummaryResource().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link JourneySummaryResource }
     * 
     * 
     */
    public List<JourneySummaryResource> getJourneySummaryResource() {
        if (journeySummaryResource == null) {
            journeySummaryResource = new ArrayList<JourneySummaryResource>();
        }
        return this.journeySummaryResource;
    }

}
