
package com.ceva.cevatrack.wsmicrolise.findconsignmentsummaries;

import java.time.format.DateTimeFormatter;
import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ceva.cevatrack.technet.ws.WebServiceTechNetImpl;
import com.ceva.cevatrack.wsmicrolise.Common;

/**
 * @author brian.goodall
 *
 */
public class ConsignmentSummary {
	
    protected String consignmentReference;
    protected short consignmentStatus;
    protected ArrayOfString orderReferences;
    protected String pta;
    protected String ata;
    protected String pta2;
    protected String siteId;
    protected String siteName;
    protected String address1;
    protected String address2;
    protected String address3;
    protected String county;
    protected String postCode;
    protected String country;
    protected String contactName;
    protected String contactNumber;
    protected int consignmentHeaderId;
    // DD/MM/YYY HH:MM
    private static final DateTimeFormatter FORMATTERDEFAULT = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm ");
    private static final Logger LOG = LoggerFactory.getLogger(ConsignmentSummary.class);

    /**
     * @return is complex due to bug in web service - erroneous blank at char 16
     */
    @XmlElement(name = "ConsignmentReference")
    public String getConsignmentReference() {
    	if ("LOTPRO".equals(consignmentReference.substring(0,6))){
    		if ("L02".equals(consignmentReference.substring(7,10)) || "RED".equals(consignmentReference.substring(7,10)) 
    				&& !"REDARCH".equals(consignmentReference.substring(7,14))){
    			LOG.warn(" ------ Special conversion 1 for the XML has been done for the consignment reference: " + this.consignmentReference);
    			return consignmentReference.replaceAll("\\s", "");
    		}    		
    		return consignmentReference;
    	}    	
    	if (consignmentReference.length() == 17){
    		LOG.warn(" ---- The consignment has exactly 17 characteres so it is longer than 17, no special conversion at this point..");
    		return consignmentReference;
    	}
        if (consignmentReference.length() > 16) {
        	LOG.warn(" ------ Special conversion 2 for the XML has been done for the consignment reference: " + this.consignmentReference);
            return consignmentReference.substring(0, 16) + consignmentReference.substring(17);
        }
        return consignmentReference;
    }

    public void setConsignmentReference(String value) {
        this.consignmentReference = value;
    }

    public String getPrefix() {
        return Common.getPrefix(getConsignmentReference());
    }

    @XmlElement(name = "ConsignmentStatus")
    public short getConsignmentStatus() {
        return consignmentStatus;
    }

    public void setConsignmentStatus(short value) {
        this.consignmentStatus = value;
    }

    public String getStatusString() {
        return Common.getStatusDescription(consignmentStatus);
    }

    @XmlElement(name = "OrderReferences")
    public ArrayOfString getOrderReferences() {
        return orderReferences;
    }

    public void setOrderReferences(ArrayOfString value) {
        this.orderReferences = value;
    }    
    
    @XmlElement(name = "PTA")
    public String getPta() {
        return pta;
    }

    public void setPta(String value) {
        this.pta = value;
    }

    public String getPtaString() {
    	LOG.info("-- pta: " + pta);
        return Common.formatDate(pta, FORMATTERDEFAULT, null, pta);
    }

    @XmlElement(name = "ATA")
    public String getAta() {
        return ata;
    }

    public void setAta(String value) {
        this.ata = value;
    }

    public String getAtaString() {
    	if (!StringUtils.isBlank(ata)){
    		return Common.formatDate(ata, FORMATTERDEFAULT, null, ata);
    	}else{
    		return "";
    	}
    }

    @XmlElement(name = "PTA2")
    public String getPta2() {
        return pta2;
    }

    public void setPta2(String value) {
        this.pta2 = value;
    }

    public String getPta2String() {
        return Common.formatDate(pta2, null, null, pta2);
    }

    @XmlElement(name = "SiteID")
    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String value) {
        this.siteId = value;
    }

    @XmlElement(name = "SiteName")
    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String value) {
        this.siteName = value;
    }

    @XmlElement(name = "Address1")
    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String value) {
        this.address1 = value;
    }

    @XmlElement(name = "Address2")
    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String value) {
        this.address2 = value;
    }

    @XmlElement(name = "Address3")
    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String value) {
        this.address3 = value;
    }

    @XmlElement(name = "County")
    public String getCounty() {
        return county;
    }

    public void setCounty(String value) {
        this.county = value;
    }

    @XmlElement(name = "PostCode")
    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String value) {
        this.postCode = value;
    }

    @XmlElement(name = "Country")
    public String getCountry() {
        return country;
    }

    public void setCountry(String value) {
        this.country = value;
    }

    @XmlElement(name = "ContactName")
    public String getContactName() {
        return contactName;
    }

    public void setContactName(String value) {
        this.contactName = value;
    }

    @XmlElement(name = "ContactNumber")
    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String value) {
        this.contactNumber = value;
    }

    @XmlElement(name = "ConsignmentHeaderID")
    public int getConsignmentHeaderId() {
        return consignmentHeaderId;
    }

    public void setConsignmentHeaderId(int value) {
        this.consignmentHeaderId = value;
    }

}
