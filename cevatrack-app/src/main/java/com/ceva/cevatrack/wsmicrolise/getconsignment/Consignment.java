
package com.ceva.cevatrack.wsmicrolise.getconsignment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.stream.Collectors;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import com.ceva.cevatrack.wsmicrolise.Common;

/**
 * @author brian.goodall
 *
 */
public class Consignment {

  protected String label1;
  protected String label2;
  protected String label3;
  protected String label4;
  protected String consignmentReference;
  protected String consignmentCustomerOrderRef;
  protected short consignmentStatus;
  protected ArrayList<ConsignmentDetail> consignmentDetails;
  protected String ata;
  protected String atd;
  protected String signatory;
  protected String signature;
  protected int journeyDropId;
  protected String pta;
  protected String ptd;
  protected String eta;
  protected String etd;
  protected String timeWindowStart;
  protected String timeWindowEnd;
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
  protected BigDecimal siteLat;
  protected BigDecimal siteLong;
  protected String routeId;
  protected String journeyAlias;
  protected String plannedStartTime;
  protected String actualStartTime;
  protected String podStartTime;
  protected ArrayList<Vehicle> vehicles;
  protected ArrayList<Driver> drivers;
  protected int siteGeoRadius;
  protected double durationOnSite;
  protected String journeyActualStartTime;
  protected short isAdHoc;
  protected short isTranshipment;
  protected String clauseCode;
  protected String parentCode;
  protected int consignmentHeaderId;
  protected int journeyId;
  protected String consignmentHeaderInfo1;
  protected String consignmentHeaderInfo2;
  protected String consignmentHeaderInfo3;
  protected String consignmentHeaderInfo4;
  protected String consignmentHeaderInfo5;
  protected String consignmentHeaderInfo6;
  protected String consignmentHeaderInfo7;
  protected String consignmentHeaderInfo8;
  protected String consignmentHeaderInfo9;
  protected String consignmentHeaderInfo10;
  protected String consignmentHeaderInfo11;
  protected String consignmentHeaderInfo12;

  @XmlElement(name = "Label1")
  public String getLabel1() {
    return label1;
  }

  public void setLabel1(String value) {
    this.label1 = value;
  }

  @XmlElement(name = "Label2")
  public String getLabel2() {
    return label2;
  }

  public void setLabel2(String value) {
    this.label2 = value;
  }

  @XmlElement(name = "Label3")
  public String getLabel3() {
    return label3;
  }

  public void setLabel3(String value) {
    this.label3 = value;
  }

  @XmlElement(name = "Label4")
  public String getLabel4() {
    return label4;
  }

  public void setLabel4(String value) {
    this.label4 = value;
  }

  @XmlElement(name = "ConsignmentReference")
  public String getConsignmentReference() {
    return consignmentReference;
  }

  public void setConsignmentReference(String value) {
    this.consignmentReference = value;
  }

  public String getPrefix() {
    return Common.getPrefix(consignmentReference);
  }

  @XmlElement(name = "ConsignmentCustomerOrderRef")
  public String getConsignmentCustomerOrderRef() {
    return consignmentCustomerOrderRef;
  }

  public void setConsignmentCustomerOrderRef(String value) {
    this.consignmentCustomerOrderRef = value;
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

  public String getStatusCssClass() {
    return Common.getStatusCssClass(consignmentStatus);
  }

  @XmlElementWrapper(name = "ConsignmentDetails")
  @XmlElement(name = "ConsignmentDetail")
  public ArrayList<ConsignmentDetail> getConsignmentDetails() {
    return consignmentDetails;
  }

  public void setConsignmentDetails(ArrayList<ConsignmentDetail> value) {
    this.consignmentDetails = value;
  }

  @XmlElement(name = "ATA")
  public String getAta() {
    return ata;
  }

  public void setAta(String value) {
    this.ata = value;
  }

  public String getAtaString() {
    return Common.formatDate(ata, null, null, ata);
  }

  @XmlElement(name = "ATD")
  public String getAtd() {
    return atd;
  }

  public void setAtd(String value) {
    this.atd = value;
  }

  public String getAtdString() {
    return Common.formatDate(atd, null, null, atd);
  }

  @XmlElement(name = "Signatory")
  public String getSignatory() {
    return signatory;
  }

  public void setSignatory(String value) {
    this.signatory = value;
  }

  @XmlElement(name = "Signature")
  public String getSignature() {
    return signature;
  }

  public void setSignature(String value) {
    this.signature = value;
  }

  @XmlElement(name = "JourneyDropID")
  public int getJourneyDropId() {
    return journeyDropId;
  }

  public void setJourneyDropId(int value) {
    this.journeyDropId = value;
  }

  @XmlElement(name = "PTA")
  public String getPta() {
    return pta;
  }

  public void setPta(String value) {
    this.pta = value;
  }

  public String getPtaString() {
    return Common.formatDate(pta, null, null, pta);
  }

  @XmlElement(name = "PTD")
  public String getPtd() {
    return ptd;
  }

  public void setPtd(String value) {
    this.ptd = value;
  }

  public String getPtdString() {
    return Common.formatDate(ptd, null, null, ptd);
  }

  @XmlElement(name = "ETA")
  public String getEta() {
    return eta;
  }

  public void setEta(String value) {
    this.eta = value;
  }

  public String getEtaString() {
    return Common.formatDate(eta, null, null, eta);
  }

  @XmlElement(name = "ETD")
  public String getEtd() {
    return etd;
  }

  public void setEtd(String value) {
    this.etd = value;
  }

  public String getEtdString() {
    return Common.formatDate(etd, null, null, etd);
  }

  @XmlElement(name = "TimeWindowStart")
  public String getTimeWindowStart() {
    return timeWindowStart;
  }

  public void setTimeWindowStart(String value) {
    this.timeWindowStart = value;
  }

  public String getTimeWindowStartString() {
    return Common.formatDate(timeWindowStart, null, null, timeWindowStart);
  }

  @XmlElement(name = "TimeWindowEnd")
  public String getTimeWindowEnd() {
    return timeWindowEnd;
  }

  public void setTimeWindowEnd(String value) {
    this.timeWindowEnd = value;
  }

  public String getTimeWindowEndString() {
    return Common.formatDate(timeWindowEnd, null, null, timeWindowEnd);
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

  @XmlElement(name = "SiteLat")
  public BigDecimal getSiteLat() {
    return siteLat;
  }

  public void setSiteLat(BigDecimal value) {
    this.siteLat = value;
  }

  @XmlElement(name = "SiteLong")
  public BigDecimal getSiteLong() {
    return siteLong;
  }

  public void setSiteLong(BigDecimal value) {
    this.siteLong = value;
  }

  @XmlElement(name = "RouteID")
  public String getRouteId() {
    return routeId;
  }

  public void setRouteId(String value) {
    this.routeId = value;
  }

  @XmlElement(name = "JourneyAlias")
  public String getJourneyAlias() {
    return journeyAlias;
  }

  public void setJourneyAlias(String value) {
    this.journeyAlias = value;
  }

  @XmlElement(name = "PlannedStartTime")
  public String getPlannedStartTime() {
    return plannedStartTime;
  }

  public void setPlannedStartTime(String value) {
    this.plannedStartTime = value;
  }

  public String getPlannedStartTimeString() {
    return Common.formatDate(plannedStartTime, null, null, plannedStartTime);
  }

  @XmlElement(name = "ActualStartTime")
  public String getActualStartTime() {
    return actualStartTime;
  }

  public void setActualStartTime(String value) {
    this.actualStartTime = value;
  }

  public String getActualStartTimeString() {
    return Common.formatDate(actualStartTime, null, null, actualStartTime);
  }

  @XmlElement(name = "PODStartTime")
  public String getPodStartTime() {
    return podStartTime;
  }

  public void setPodStartTime(String value) {
    this.podStartTime = value;
  }

  public String getPodStartTimeString() {
    return Common.formatDate(podStartTime, null, null, podStartTime);
  }

  @XmlElementWrapper(name = "Vehicles")
  @XmlElement(name = "Vehicle")
  public ArrayList<Vehicle> getVehicles() {
    return vehicles;
  }

  public void setVehicles(ArrayList<Vehicle> value) {
    this.vehicles = value;
  }

  @XmlElementWrapper(name = "Drivers")
  @XmlElement(name = "Driver")
  public ArrayList<Driver> getDrivers() {
    return drivers;
  }

  public void setDrivers(ArrayList<Driver> value) {
    this.drivers = value;
  }

  @XmlElement(name = "SiteGeoRadius")
  public int getSiteGeoRadius() {
    return siteGeoRadius;
  }

  public void setSiteGeoRadius(int value) {
    this.siteGeoRadius = value;
  }

  @XmlElement(name = "DurationOnSite")
  public double getDurationOnSite() {
    return durationOnSite;
  }

  public void setDurationOnSite(double value) {
    this.durationOnSite = value;
  }

  public int getDurationOnSiteMins() {
    return (int) durationOnSite / 60;
  }

  @XmlElement(name = "JourneyActualStartTime", required = true)
  public String getJourneyActualStartTime() {
    return journeyActualStartTime;
  }

  public void setJourneyActualStartTime(String value) {
    this.journeyActualStartTime = value;
  }

  /**
   * @return only if different to actual arrival. I think this is geo-fence arrival.
   */
  public String getJourneyActualStartTimeString() {
    if (journeyActualStartTime == null || journeyActualStartTime.equals(ata)) {
      return null;
    }
    return Common.formatDate(journeyActualStartTime, null, null, journeyActualStartTime);
  }

  @XmlElement(name = "IsAdHoc")
  public short getIsAdHoc() {
    return isAdHoc;
  }

  public void setIsAdHoc(short value) {
    this.isAdHoc = value;
  }

  @XmlElement(name = "IsTranshipment")
  public short getIsTranshipment() {
    return isTranshipment;
  }

  public void setIsTranshipment(short value) {
    this.isTranshipment = value;
  }

  @XmlElement(name = "ClauseCode")
  public String getClauseCode() {
    return clauseCode;
  }

  public void setClauseCode(String value) {
    this.clauseCode = value;
  }

  @XmlElement(name = "ParentCode")
  public String getParentCode() {
    return parentCode;
  }

  public void setParentCode(String value) {
    this.parentCode = value;
  }

  @XmlElement(name = "ConsignmentHeaderID")
  public int getConsignmentHeaderId() {
    return consignmentHeaderId;
  }

  public void setConsignmentHeaderId(int value) {
    this.consignmentHeaderId = value;
  }

  @XmlElement(name = "JourneyID")
  public int getJourneyId() {
    return journeyId;
  }

  public void setJourneyId(int value) {
    this.journeyId = value;
  }

  @XmlElement(name = "ConsignmentHeaderInfo1")
  public String getConsignmentHeaderInfo1() {
    return consignmentHeaderInfo1;
  }

  public void setConsignmentHeaderInfo1(String value) {
    this.consignmentHeaderInfo1 = value;
  }

  @XmlElement(name = "ConsignmentHeaderInfo2")
  public String getConsignmentHeaderInfo2() {
    return consignmentHeaderInfo2;
  }

  public void setConsignmentHeaderInfo2(String value) {
    this.consignmentHeaderInfo2 = value;
  }

  @XmlElement(name = "ConsignmentHeaderInfo3")
  public String getConsignmentHeaderInfo3() {
    return consignmentHeaderInfo3;
  }

  public void setConsignmentHeaderInfo3(String value) {
    this.consignmentHeaderInfo3 = value;
  }

  @XmlElement(name = "ConsignmentHeaderInfo4")
  public String getConsignmentHeaderInfo4() {
    return consignmentHeaderInfo4;
  }

  public void setConsignmentHeaderInfo4(String value) {
    this.consignmentHeaderInfo4 = value;
  }

  @XmlElement(name = "ConsignmentHeaderInfo5")
  public String getConsignmentHeaderInfo5() {
    return consignmentHeaderInfo5;
  }

  public void setConsignmentHeaderInfo5(String value) {
    this.consignmentHeaderInfo5 = value;
  }

  @XmlElement(name = "ConsignmentHeaderInfo6")
  public String getConsignmentHeaderInfo6() {
    return consignmentHeaderInfo6;
  }

  public void setConsignmentHeaderInfo6(String value) {
    this.consignmentHeaderInfo6 = value;
  }

  @XmlElement(name = "ConsignmentHeaderInfo7")
  public String getConsignmentHeaderInfo7() {
    return consignmentHeaderInfo7;
  }

  public void setConsignmentHeaderInfo7(String value) {
    this.consignmentHeaderInfo7 = value;
  }

  @XmlElement(name = "ConsignmentHeaderInfo8")
  public String getConsignmentHeaderInfo8() {
    return consignmentHeaderInfo8;
  }

  public void setConsignmentHeaderInfo8(String value) {
    this.consignmentHeaderInfo8 = value;
  }

  @XmlElement(name = "ConsignmentHeaderInfo9")
  public String getConsignmentHeaderInfo9() {
    return consignmentHeaderInfo9;
  }

  public void setConsignmentHeaderInfo9(String value) {
    this.consignmentHeaderInfo9 = value;
  }

  @XmlElement(name = "ConsignmentHeaderInfo10")
  public String getConsignmentHeaderInfo10() {
    return consignmentHeaderInfo10;
  }

  public void setConsignmentHeaderInfo10(String value) {
    this.consignmentHeaderInfo10 = value;
  }

  @XmlElement(name = "ConsignmentHeaderInfo11")
  public String getConsignmentHeaderInfo11() {
    return consignmentHeaderInfo11;
  }

  public void setConsignmentHeaderInfo11(String value) {
    this.consignmentHeaderInfo11 = value;
  }

  @XmlElement(name = "ConsignmentHeaderInfo12")
  public String getConsignmentHeaderInfo12() {
    return consignmentHeaderInfo12;
  }

  public void setConsignmentHeaderInfo12(String value) {
    this.consignmentHeaderInfo12 = value;
  }

  @Override
  public String toString() {
    String v1 = "Consignment [label1=" + label1 + ", label2=" + label2 + ", label3=" + label3
        + ", label4=" + label4 + ", consignmentReference=" + consignmentReference
        + ", consignmentCustomerOrderRef=" + consignmentCustomerOrderRef + ", consignmentStatus="
        + consignmentStatus + ", consignmentDetails=" + consignmentDetails + ", ata=" + ata
        + ", atd=" + atd + ", signatory=" + signatory + ", signature=" + signature
        + ", journeyDropId=" + journeyDropId + ", pta=" + pta + ", ptd=" + ptd + ", eta=" + eta
        + ", etd=" + etd + ", timeWindowStart=" + timeWindowStart + ", timeWindowEnd="
        + timeWindowEnd + ", siteId=" + siteId + ", siteName=" + siteName + ", address1=" + address1
        + ", address2=" + address2 + ", address3=" + address3 + ", county=" + county + ", postCode="
        + postCode + ", country=" + country + ", contactName=" + contactName + ", contactNumber="
        + contactNumber + ", siteLat=" + siteLat + ", siteLong=" + siteLong + ", routeId=" + routeId
        + ", journeyAlias=" + journeyAlias + ", plannedStartTime=" + plannedStartTime
        + ", actualStartTime=" + actualStartTime + ", podStartTime=" + podStartTime + ", vehicles="
        + vehicles + ", drivers=" + drivers + ", siteGeoRadius=" + siteGeoRadius
        + ", durationOnSite=" + durationOnSite + ", journeyActualStartTime="
        + journeyActualStartTime + ", isAdHoc=" + isAdHoc + ", isTranshipment=" + isTranshipment
        + ", clauseCode=" + clauseCode + ", parentCode=" + parentCode + ", consignmentHeaderId="
        + consignmentHeaderId + ", journeyId=" + journeyId + ", consignmentHeaderInfo1="
        + consignmentHeaderInfo1 + ", consignmentHeaderInfo2=" + consignmentHeaderInfo2
        + ", consignmentHeaderInfo3=" + consignmentHeaderInfo3 + ", consignmentHeaderInfo4="
        + consignmentHeaderInfo4 + ", consignmentHeaderInfo5=" + consignmentHeaderInfo5
        + ", consignmentHeaderInfo6=" + consignmentHeaderInfo6 + ", consignmentHeaderInfo7="
        + consignmentHeaderInfo7 + ", consignmentHeaderInfo8=" + consignmentHeaderInfo8
        + ", consignmentHeaderInfo9=" + consignmentHeaderInfo9 + ", consignmentHeaderInfo10="
        + consignmentHeaderInfo10 + ", consignmentHeaderInfo11=" + consignmentHeaderInfo11
        + ", consignmentHeaderInfo12=" + consignmentHeaderInfo12 + "]";
    String v2 =
        "\nVehicles: " + vehicles.stream().map(Object::toString).collect(Collectors.joining(","));
    String v3 =
        "\nDrivers: " + drivers.stream().map(Object::toString).collect(Collectors.joining(","));
    return v1 + v2 + v3;
  }

}
