package com.ceva.cevatrack.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ceva.cevatrack.domain.ConsignmentDetail44;
import com.ceva.cevatrack.domain.Driver44;
import com.ceva.cevatrack.domain.Vehicle44;


public class ConsignmentVO44 implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
     String consignmentreference;
	 String label1;
     String label2;
     String label3;
     String label4;
     String consignmentcustomerorderref;
     short consignmentstatus;
     List<ConsignmentDetailVO44> consignmentDetails;
     Date ata;
     Date atd;
     String signatory;
     String signature;
     Integer journeydropId;
     Date pta;
     Date ptd;
     Date eta;
     Date etd;
     Date timewindowstart;
     Date timewindowend;
     String siteid;
     String sitename;
     String address1;
     String address2;
     String address3;
     String county;
     String postcode;
     String country;
     String contactname;
     String contactnumber;
     BigDecimal sitelat;
     BigDecimal sitelong;
     String routeid;
     String journeyalias;
     Date plannedstarttime;
     Date actualstarttime;
     Date podstarttime;
     List<Vehicle44> vehicles;     
     List<Driver44> drivers;
     Integer sitegeoradius;
     double durationonsite;
     Date journeyactualstarttime;
     short isadhoc;
     short istranshipment;
     String clausecode;
     String parentcode;
     Integer consignmentheaderid;
     Integer journeyid;
     String consignmentheaderinfo1;
     String consignmentHeaderInfo2;
     String consignmentHeaderInfo3;
     String consignmentHeaderInfo4;
     String consignmentHeaderInfo5;
     String consignmentHeaderInfo6;
     String consignmentHeaderInfo7;
     String consignmentHeaderInfo8;
     String consignmentHeaderInfo9;
     String consignmentHeaderInfo10;
     String consignmentHeaderInfo11;
     String consignmentHeaderInfo12;
     
	
	public String getConsignmentreference() {
		return consignmentreference;
	}


	public void setConsignmentreference(String consignmentreference) {
		this.consignmentreference = consignmentreference;
	}


	public String getLabel1() {
		return label1;
	}


	public void setLabel1(String label1) {
		this.label1 = label1;
	}


	public String getLabel2() {
		return label2;
	}


	public void setLabel2(String label2) {
		this.label2 = label2;
	}


	public String getLabel3() {
		return label3;
	}


	public void setLabel3(String label3) {
		this.label3 = label3;
	}


	public String getLabel4() {
		return label4;
	}


	public void setLabel4(String label4) {
		this.label4 = label4;
	}


	public String getConsignmentcustomerorderref() {
		return consignmentcustomerorderref;
	}


	public void setConsignmentcustomerorderref(String consignmentcustomerorderref) {
		this.consignmentcustomerorderref = consignmentcustomerorderref;
	}


	public short getConsignmentstatus() {
		return consignmentstatus;
	}


	public void setConsignmentstatus(short consignmentstatus) {
		this.consignmentstatus = consignmentstatus;
	}


	public List<ConsignmentDetailVO44> getConsignmentDetails() {
		return consignmentDetails;
	}


	public void setConsignmentDetails(List<ConsignmentDetailVO44> consignmentDetails) {
		this.consignmentDetails = consignmentDetails;
	}


	public Date getAta() {
		return ata;
	}


	public void setAta(Date ata) {
		this.ata = ata;
	}


	public Date getAtd() {
		return atd;
	}


	public void setAtd(Date atd) {
		this.atd = atd;
	}


	public String getSignatory() {
		return signatory;
	}


	public void setSignatory(String signatory) {
		this.signatory = signatory;
	}


	public String getSignature() {
		return signature;
	}


	public void setSignature(String signature) {
		this.signature = signature;
	}


	public Integer getJourneydropId() {
		return journeydropId;
	}


	public void setJourneydropId(Integer journeydropId) {
		this.journeydropId = journeydropId;
	}


	public Date getPta() {
		return pta;
	}


	public void setPta(Date pta) {
		this.pta = pta;
	}


	public Date getPtd() {
		return ptd;
	}


	public void setPtd(Date ptd) {
		this.ptd = ptd;
	}


	public Date getEta() {
		return eta;
	}


	public void setEta(Date eta) {
		this.eta = eta;
	}


	public Date getEtd() {
		return etd;
	}


	public void setEtd(Date etd) {
		this.etd = etd;
	}


	public Date getTimewindowstart() {
		return timewindowstart;
	}


	public void setTimewindowstart(Date timewindowstart) {
		this.timewindowstart = timewindowstart;
	}


	public Date getTimewindowend() {
		return timewindowend;
	}


	public void setTimewindowend(Date timewindowend) {
		this.timewindowend = timewindowend;
	}


	public String getSiteid() {
		return siteid;
	}


	public void setSiteid(String siteid) {
		this.siteid = siteid;
	}


	public String getSitename() {
		return sitename;
	}


	public void setSitename(String sitename) {
		this.sitename = sitename;
	}


	public String getAddress1() {
		return address1;
	}


	public void setAddress1(String address1) {
		this.address1 = address1;
	}


	public String getAddress2() {
		return address2;
	}


	public void setAddress2(String address2) {
		this.address2 = address2;
	}


	public String getAddress3() {
		return address3;
	}


	public void setAddress3(String address3) {
		this.address3 = address3;
	}


	public String getCounty() {
		return county;
	}


	public void setCounty(String county) {
		this.county = county;
	}


	public String getPostcode() {
		return postcode;
	}


	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public String getContactname() {
		return contactname;
	}


	public void setContactname(String contactname) {
		this.contactname = contactname;
	}


	public String getContactnumber() {
		return contactnumber;
	}


	public void setContactnumber(String contactnumber) {
		this.contactnumber = contactnumber;
	}


	public BigDecimal getSitelat() {
		return sitelat;
	}


	public void setSitelat(BigDecimal sitelat) {
		this.sitelat = sitelat;
	}


	public BigDecimal getSitelong() {
		return sitelong;
	}


	public void setSitelong(BigDecimal sitelong) {
		this.sitelong = sitelong;
	}


	public String getRouteid() {
		return routeid;
	}


	public void setRouteid(String routeid) {
		this.routeid = routeid;
	}


	public String getJourneyalias() {
		return journeyalias;
	}


	public void setJourneyalias(String journeyalias) {
		this.journeyalias = journeyalias;
	}


	public Date getPlannedstarttime() {
		return plannedstarttime;
	}


	public void setPlannedstarttime(Date plannedstarttime) {
		this.plannedstarttime = plannedstarttime;
	}


	public Date getActualstarttime() {
		return actualstarttime;
	}


	public void setActualstarttime(Date actualstarttime) {
		this.actualstarttime = actualstarttime;
	}


	public Date getPodstarttime() {
		return podstarttime;
	}


	public void setPodstarttime(Date podstarttime) {
		this.podstarttime = podstarttime;
	}


	public List<Vehicle44> getVehicles() {
		return vehicles;
	}


	public void setVehicles(List<Vehicle44> vehicles) {
		this.vehicles = vehicles;
	}


	public List<Driver44> getDrivers() {
		return drivers;
	}


	public void setDrivers(List<Driver44> drivers) {
		this.drivers = drivers;
	}


	public Integer getSitegeoradius() {
		return sitegeoradius;
	}


	public void setSitegeoradius(Integer sitegeoradius) {
		this.sitegeoradius = sitegeoradius;
	}


	public double getDurationonsite() {
		return durationonsite;
	}


	public void setDurationonsite(double durationonsite) {
		this.durationonsite = durationonsite;
	}


	public Date getJourneyactualstarttime() {
		return journeyactualstarttime;
	}


	public void setJourneyactualstarttime(Date journeyactualstarttime) {
		this.journeyactualstarttime = journeyactualstarttime;
	}


	public short getIsadhoc() {
		return isadhoc;
	}


	public void setIsadhoc(short isadhoc) {
		this.isadhoc = isadhoc;
	}


	public short getIstranshipment() {
		return istranshipment;
	}


	public void setIstranshipment(short istranshipment) {
		this.istranshipment = istranshipment;
	}


	public String getClausecode() {
		return clausecode;
	}


	public void setClausecode(String clausecode) {
		this.clausecode = clausecode;
	}


	public String getParentcode() {
		return parentcode;
	}


	public void setParentcode(String parentcode) {
		this.parentcode = parentcode;
	}


	public Integer getConsignmentheaderid() {
		return consignmentheaderid;
	}


	public void setConsignmentheaderid(Integer consignmentheaderid) {
		this.consignmentheaderid = consignmentheaderid;
	}


	public Integer getJourneyid() {
		return journeyid;
	}


	public void setJourneyid(Integer journeyid) {
		this.journeyid = journeyid;
	}


	public String getConsignmentheaderinfo1() {
		return consignmentheaderinfo1;
	}


	public void setConsignmentheaderinfo1(String consignmentheaderinfo1) {
		this.consignmentheaderinfo1 = consignmentheaderinfo1;
	}


	public String getConsignmentHeaderInfo2() {
		return consignmentHeaderInfo2;
	}


	public void setConsignmentHeaderInfo2(String consignmentHeaderInfo2) {
		this.consignmentHeaderInfo2 = consignmentHeaderInfo2;
	}


	public String getConsignmentHeaderInfo3() {
		return consignmentHeaderInfo3;
	}


	public void setConsignmentHeaderInfo3(String consignmentHeaderInfo3) {
		this.consignmentHeaderInfo3 = consignmentHeaderInfo3;
	}


	public String getConsignmentHeaderInfo4() {
		return consignmentHeaderInfo4;
	}


	public void setConsignmentHeaderInfo4(String consignmentHeaderInfo4) {
		this.consignmentHeaderInfo4 = consignmentHeaderInfo4;
	}


	public String getConsignmentHeaderInfo5() {
		return consignmentHeaderInfo5;
	}


	public void setConsignmentHeaderInfo5(String consignmentHeaderInfo5) {
		this.consignmentHeaderInfo5 = consignmentHeaderInfo5;
	}


	public String getConsignmentHeaderInfo6() {
		return consignmentHeaderInfo6;
	}


	public void setConsignmentHeaderInfo6(String consignmentHeaderInfo6) {
		this.consignmentHeaderInfo6 = consignmentHeaderInfo6;
	}


	public String getConsignmentHeaderInfo7() {
		return consignmentHeaderInfo7;
	}


	public void setConsignmentHeaderInfo7(String consignmentHeaderInfo7) {
		this.consignmentHeaderInfo7 = consignmentHeaderInfo7;
	}


	public String getConsignmentHeaderInfo8() {
		return consignmentHeaderInfo8;
	}


	public void setConsignmentHeaderInfo8(String consignmentHeaderInfo8) {
		this.consignmentHeaderInfo8 = consignmentHeaderInfo8;
	}


	public String getConsignmentHeaderInfo9() {
		return consignmentHeaderInfo9;
	}


	public void setConsignmentHeaderInfo9(String consignmentHeaderInfo9) {
		this.consignmentHeaderInfo9 = consignmentHeaderInfo9;
	}


	public String getConsignmentHeaderInfo10() {
		return consignmentHeaderInfo10;
	}


	public void setConsignmentHeaderInfo10(String consignmentHeaderInfo10) {
		this.consignmentHeaderInfo10 = consignmentHeaderInfo10;
	}


	public String getConsignmentHeaderInfo11() {
		return consignmentHeaderInfo11;
	}


	public void setConsignmentHeaderInfo11(String consignmentHeaderInfo11) {
		this.consignmentHeaderInfo11 = consignmentHeaderInfo11;
	}


	public String getConsignmentHeaderInfo12() {
		return consignmentHeaderInfo12;
	}


	public void setConsignmentHeaderInfo12(String consignmentHeaderInfo12) {
		this.consignmentHeaderInfo12 = consignmentHeaderInfo12;
	}


	@Override
	public String toString() {
		return "ConsignmentVO44 [consignmentreference=" + consignmentreference + ", label1=" + label1 + ", label2="
				+ label2 + ", label3=" + label3 + ", label4=" + label4 + ", consignmentcustomerorderref="
				+ consignmentcustomerorderref + ", consignmentstatus=" + consignmentstatus + ", consignmentDetails="
				+ consignmentDetails + ", ata=" + ata + ", atd=" + atd + ", signatory=" + signatory + ", signature="
				+ signature + ", journeydropId=" + journeydropId + ", pta=" + pta + ", ptd=" + ptd + ", eta=" + eta
				+ ", etd=" + etd + ", timewindowstart=" + timewindowstart + ", timewindowend=" + timewindowend
				+ ", siteid=" + siteid + ", sitename=" + sitename + ", address1=" + address1 + ", address2=" + address2
				+ ", address3=" + address3 + ", county=" + county + ", postcode=" + postcode + ", country=" + country
				+ ", contactname=" + contactname + ", contactnumber=" + contactnumber + ", sitelat=" + sitelat
				+ ", sitelong=" + sitelong + ", routeid=" + routeid + ", journeyalias=" + journeyalias
				+ ", plannedstarttime=" + plannedstarttime + ", actualstarttime=" + actualstarttime + ", podstarttime="
				+ podstarttime + ", vehicles=" + vehicles + ", drivers=" + drivers + ", sitegeoradius=" + sitegeoradius
				+ ", durationonsite=" + durationonsite + ", journeyactualstarttime=" + journeyactualstarttime
				+ ", isadhoc=" + isadhoc + ", istranshipment=" + istranshipment + ", clausecode=" + clausecode
				+ ", parentcode=" + parentcode + ", consignmentheaderid=" + consignmentheaderid + ", journeyid="
				+ journeyid + ", consignmentheaderinfo1=" + consignmentheaderinfo1 + ", consignmentHeaderInfo2="
				+ consignmentHeaderInfo2 + ", consignmentHeaderInfo3=" + consignmentHeaderInfo3
				+ ", consignmentHeaderInfo4=" + consignmentHeaderInfo4 + ", consignmentHeaderInfo5="
				+ consignmentHeaderInfo5 + ", consignmentHeaderInfo6=" + consignmentHeaderInfo6
				+ ", consignmentHeaderInfo7=" + consignmentHeaderInfo7 + ", consignmentHeaderInfo8="
				+ consignmentHeaderInfo8 + ", consignmentHeaderInfo9=" + consignmentHeaderInfo9
				+ ", consignmentHeaderInfo10=" + consignmentHeaderInfo10 + ", consignmentHeaderInfo11="
				+ consignmentHeaderInfo11 + ", consignmentHeaderInfo12=" + consignmentHeaderInfo12 + "]";
	}
	    		
}
