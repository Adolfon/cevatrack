
package com.ceva.cevatrack.wsdl.types.getconsignment;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for ArrayOfConsignmentDetail complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfConsignmentDetail">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ConsignmentDetail" type="{http://www.cevalogistics.com/XSD/MicroliseSmartNet/TMC/GetConsignment}ConsignmentDetail" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfConsignmentDetail", propOrder = { "consignmentDetail" })
public class ArrayOfConsignmentDetail {

    @XmlElement(name = "ConsignmentDetail", nillable = true)
    protected List<ConsignmentDetail> consignmentDetail;

    /**
     * Gets the value of the consignmentDetail property.
     * 
     * <p>
     * This accessor method returns a reference to the live list, not a snapshot. Therefore any modification you make to
     * the returned list will be present inside the JAXB object. This is why there is not a <CODE>set</CODE> method for
     * the consignmentDetail property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getConsignmentDetail().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list {@link ConsignmentDetail }
     * 
     * 
     */
    public List<ConsignmentDetail> getConsignmentDetail() {
        if (consignmentDetail == null) {
            consignmentDetail = new ArrayList<ConsignmentDetail>();
        }
        return this.consignmentDetail;
    }

}
