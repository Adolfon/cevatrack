
package com.ceva.cevatrack.wsdl.types.findconsignmentsummaries;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for ArrayOfConsignmentSummary complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfConsignmentSummary">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ConsignmentSummary" type="{http://www.cevalogistics.com/XSD/MicroliseSmartNet/TMC/FindConsignmentSummaries}ConsignmentSummary" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfConsignmentSummary", propOrder = { "consignmentSummary" })
public class ArrayOfConsignmentSummary {

    @XmlElement(name = "ConsignmentSummary", nillable = true)
    protected List<ConsignmentSummary> consignmentSummary;

    /**
     * Gets the value of the consignmentSummary property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to
     * the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for
     * the consignmentSummary property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getConsignmentSummary().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link ConsignmentSummary }
     * 
     * 
     */
    public List<ConsignmentSummary> getConsignmentSummary() {
        if (consignmentSummary == null) {
            consignmentSummary = new ArrayList<ConsignmentSummary>();
        }
        return this.consignmentSummary;
    }

}
