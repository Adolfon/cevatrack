
package com.ceva.cevatrack.wsdl.types.getjourneysummaryforaliasanddate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for JourneySummaryResponse complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="JourneySummaryResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="JourneySummary" type="{http://www.cevalogistics.com/XSD/MicroliseSmartNet/TMC/GetJourneySummaryForAliasAndDate}JourneySummary" minOccurs="0"/>
 *         &lt;element name="ErrorDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "JourneySummaryResponse", propOrder = { "journeySummary", "errorDescription" })
public class JourneySummaryResponse {

    @XmlElement(name = "JourneySummary")
    protected JourneySummary journeySummary;
    @XmlElement(name = "ErrorDescription")
    protected String errorDescription;

    /**
     * Gets the value of the journeySummary property.
     * 
     * @return possible object is {@link JourneySummary }
     * 
     */
    public JourneySummary getJourneySummary() {
        return journeySummary;
    }

    /**
     * Sets the value of the journeySummary property.
     * 
     * @param value
     *            allowed object is {@link JourneySummary }
     * 
     */
    public void setJourneySummary(JourneySummary value) {
        this.journeySummary = value;
    }

    /**
     * Gets the value of the errorDescription property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getErrorDescription() {
        return errorDescription;
    }

    /**
     * Sets the value of the errorDescription property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setErrorDescription(String value) {
        this.errorDescription = value;
    }

}
