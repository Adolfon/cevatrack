
package com.ceva.cevatrack.wsdl.types.findconsignmentsummaries;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for anonymous complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="consignmentFilter" type="{http://www.cevalogistics.com/XSD/MicroliseSmartNet/TMC/FindConsignmentSummaries}ConsignmentFilter" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "consignmentFilter" })
@XmlRootElement(name = "FindConsignmentSummaries")
public class FindConsignmentSummaries {

    protected ConsignmentFilter consignmentFilter;

    /**
     * Gets the value of the consignmentFilter property.
     * 
     * @return possible object is {@link ConsignmentFilter }
     * 
     */
    public ConsignmentFilter getConsignmentFilter() {
        return consignmentFilter;
    }

    /**
     * Sets the value of the consignmentFilter property.
     * 
     * @param value
     *            allowed object is {@link ConsignmentFilter }
     * 
     */
    public void setConsignmentFilter(ConsignmentFilter value) {
        this.consignmentFilter = value;
    }

}
