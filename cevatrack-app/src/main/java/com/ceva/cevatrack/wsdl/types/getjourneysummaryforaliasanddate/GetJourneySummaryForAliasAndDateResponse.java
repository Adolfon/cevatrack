
package com.ceva.cevatrack.wsdl.types.getjourneysummaryforaliasanddate;

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
 *         &lt;element name="GetJourneySummaryForAliasAndDateResult" type="{http://www.cevalogistics.com/XSD/MicroliseSmartNet/TMC/GetJourneySummaryForAliasAndDate}JourneySummaryResponse" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"getJourneySummaryForAliasAndDateResult"})
@XmlRootElement(name = "GetJourneySummaryForAliasAndDateResponse")
public class GetJourneySummaryForAliasAndDateResponse {

  @XmlElement(name = "GetJourneySummaryForAliasAndDateResult")
  protected JourneySummaryResponse getJourneySummaryForAliasAndDateResult;

  /**
   * Gets the value of the getJourneySummaryForAliasAndDateResult property.
   * 
   * @return possible object is {@link JourneySummaryResponse }
   * 
   */
  public JourneySummaryResponse getGetJourneySummaryForAliasAndDateResult() {
    return getJourneySummaryForAliasAndDateResult;
  }

  /**
   * Sets the value of the getJourneySummaryForAliasAndDateResult property.
   * 
   * @param value allowed object is {@link JourneySummaryResponse }
   * 
   */
  public void setGetJourneySummaryForAliasAndDateResult(JourneySummaryResponse value) {
    this.getJourneySummaryForAliasAndDateResult = value;
  }

}
