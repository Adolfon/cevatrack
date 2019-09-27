
package com.ceva.cevatrack.wsmicrolise.getjourneysummaryforaliasanddate;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author brian.goodall
 *
 */
public class JourneySummaryResource {

    protected String resourceInfo;
    protected String resourceName;
    protected String resourceType;

    @XmlElement(name = "ResourceInfo")
    public String getResourceInfo() {
        return resourceInfo;
    }

    public void setResourceInfo(String value) {
        this.resourceInfo = value;
    }

    @XmlElement(name = "ResourceName")
    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String value) {
        this.resourceName = value;
    }

    @XmlElement(name = "ResourceType")
    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String value) {
        this.resourceType = value;
    }

}
