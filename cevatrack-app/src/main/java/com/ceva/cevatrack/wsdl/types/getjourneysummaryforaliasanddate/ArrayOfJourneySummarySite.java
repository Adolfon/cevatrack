
package com.ceva.cevatrack.wsdl.types.getjourneysummaryforaliasanddate;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for ArrayOfJourneySummarySite complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfJourneySummarySite">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="JourneySummarySite" type="{http://www.cevalogistics.com/XSD/MicroliseSmartNet/TMC/GetJourneySummaryForAliasAndDate}JourneySummarySite" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfJourneySummarySite", propOrder = { "journeySummarySite" })
public class ArrayOfJourneySummarySite {

    @XmlElement(name = "JourneySummarySite", nillable = true)
    protected List<JourneySummarySite> journeySummarySite;

    /**
     * Gets the value of the journeySummarySite property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to
     * the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for
     * the journeySummarySite property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getJourneySummarySite().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link JourneySummarySite }
     * 
     * 
     */
    public List<JourneySummarySite> getJourneySummarySite() {
        if (journeySummarySite == null) {
            journeySummarySite = new ArrayList<JourneySummarySite>();
        }
        return this.journeySummarySite;
    }

}
