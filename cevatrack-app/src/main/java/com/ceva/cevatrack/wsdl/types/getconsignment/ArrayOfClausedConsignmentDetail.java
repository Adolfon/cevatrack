
package com.ceva.cevatrack.wsdl.types.getconsignment;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for ArrayOfClausedConsignmentDetail complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfClausedConsignmentDetail">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ClausedConsignmentDetail" type="{http://www.cevalogistics.com/XSD/MicroliseSmartNet/TMC/GetConsignment}ClausedConsignmentDetail" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfClausedConsignmentDetail", propOrder = {"clausedConsignmentDetail"})
public class ArrayOfClausedConsignmentDetail {

  @XmlElement(name = "ClausedConsignmentDetail", nillable = true)
  protected List<ClausedConsignmentDetail> clausedConsignmentDetail;

  /**
   * Gets the value of the clausedConsignmentDetail property.
   * 
   * <p>
   * This accessor method returns a reference to the live list, not a snapshot. Therefore any
   * modification you make to the returned list will be present inside the JAXB object. This is why
   * there is not a <CODE>set</CODE> method for the clausedConsignmentDetail property.
   * 
   * <p>
   * For example, to add a new item, do as follows:
   * 
   * <pre>
   * getClausedConsignmentDetail().add(newItem);
   * </pre>
   * 
   * 
   * <p>
   * Objects of the following type(s) are allowed in the list {@link ClausedConsignmentDetail }
   * 
   * 
   */
  public List<ClausedConsignmentDetail> getClausedConsignmentDetail() {
    if (clausedConsignmentDetail == null) {
      clausedConsignmentDetail = new ArrayList<ClausedConsignmentDetail>();
    }
    return this.clausedConsignmentDetail;
  }

}
