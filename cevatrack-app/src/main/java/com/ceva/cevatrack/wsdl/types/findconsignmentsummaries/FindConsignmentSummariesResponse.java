
package com.ceva.cevatrack.wsdl.types.findconsignmentsummaries;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element name="FindConsignmentSummariesResult" type="{http://www.cevalogistics.com/XSD/MicroliseSmartNet/TMC/FindConsignmentSummaries}ArrayOfConsignmentSummary" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "findConsignmentSummariesResult" })
@XmlRootElement(name = "FindConsignmentSummariesResponse")
public class FindConsignmentSummariesResponse {

    @XmlElement(name = "FindConsignmentSummariesResult")
    protected ArrayOfConsignmentSummary findConsignmentSummariesResult;

    /**
     * Gets the value of the findConsignmentSummariesResult property.
     * 
     * @return possible object is {@link ArrayOfConsignmentSummary }
     * 
     */
    public ArrayOfConsignmentSummary getFindConsignmentSummariesResult() {
        return findConsignmentSummariesResult;
    }

    /**
     * Sets the value of the findConsignmentSummariesResult property.
     * 
     * @param value
     *            allowed object is {@link ArrayOfConsignmentSummary }
     * 
     */
    public void setFindConsignmentSummariesResult(ArrayOfConsignmentSummary value) {
        this.findConsignmentSummariesResult = value;
    }

}
