
package com.ceva.cevatrack.wsdl.types.getjourneysummaryforaliasanddate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for JourneySummaryResource complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="JourneySummaryResource">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ResourceInfo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ResourceName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ResourceType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "JourneySummaryResource", propOrder = { "resourceInfo", "resourceName", "resourceType" })
public class JourneySummaryResource {

    @XmlElement(name = "ResourceInfo")
    protected String resourceInfo;
    @XmlElement(name = "ResourceName")
    protected String resourceName;
    @XmlElement(name = "ResourceType")
    protected String resourceType;

    /**
     * Gets the value of the resourceInfo property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getResourceInfo() {
        return resourceInfo;
    }

    /**
     * Sets the value of the resourceInfo property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setResourceInfo(String value) {
        this.resourceInfo = value;
    }

    /**
     * Gets the value of the resourceName property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getResourceName() {
        return resourceName;
    }

    /**
     * Sets the value of the resourceName property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setResourceName(String value) {
        this.resourceName = value;
    }

    /**
     * Gets the value of the resourceType property.
     * 
     * @return possible object is {@link String }
     * 
     */
    public String getResourceType() {
        return resourceType;
    }

    /**
     * Sets the value of the resourceType property.
     * 
     * @param value
     *            allowed object is {@link String }
     * 
     */
    public void setResourceType(String value) {
        this.resourceType = value;
    }

}
