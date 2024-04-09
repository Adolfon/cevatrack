
package com.ceva.cevatrack.wsmicrolise.getconsignment;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import com.ceva.cevatrack.wsmicrolise.Common;

/**
 * @author brian.goodall
 *
 */
public class ConsignmentDetail {

  protected int detailId;
  protected String detailRef;
  protected String customerDetailRef;
  protected String parentRef;
  protected String orderRef;
  protected String description;
  protected int plannedQuantity;
  protected int actualQuantity;
  protected short status;
  protected ArrayList<ClausedConsignmentDetail> clauseDetails;
  protected String info1;
  protected String info2;
  protected String info3;
  protected String info4;
  protected int consignmentHeaderId;

  @XmlElement(name = "DetailID")
  public int getDetailId() {
    return detailId;
  }

  public void setDetailId(int value) {
    this.detailId = value;
  }

  @XmlElement(name = "DetailRef")
  public String getDetailRef() {
    return detailRef;
  }

  public void setDetailRef(String value) {
    this.detailRef = value;
  }

  @XmlElement(name = "CustomerDetailRef")
  public String getCustomerDetailRef() {
    return customerDetailRef;
  }

  public void setCustomerDetailRef(String value) {
    this.customerDetailRef = value;
  }

  @XmlElement(name = "ParentRef")
  public String getParentRef() {
    return parentRef;
  }

  public void setParentRef(String value) {
    this.parentRef = value;
  }

  @XmlElement(name = "OrderRef")
  public String getOrderRef() {
    return orderRef;
  }

  public void setOrderRef(String value) {
    this.orderRef = value;
  }

  @XmlElement(name = "Description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String value) {
    this.description = value;
  }

  @XmlElement(name = "PlannedQuantity")
  public int getPlannedQuantity() {
    return plannedQuantity;
  }

  public void setPlannedQuantity(int value) {
    this.plannedQuantity = value;
  }

  @XmlElement(name = "ActualQuantity")
  public int getActualQuantity() {
    return actualQuantity;
  }

  public void setActualQuantity(int value) {
    this.actualQuantity = value;
  }

  @XmlElement(name = "Status")
  public short getStatus() {
    return status;
  }

  public void setStatus(short value) {
    this.status = value;
  }

  public String getStatusString() {
    return Common.getStatusDescription(status);
  }

  public String getStatusCssClass() {
    return Common.getStatusCssClass(status);
  }

  @XmlElementWrapper(name = "ClauseDetails")
  @XmlElement(name = "ClausedConsignmentDetail")
  public ArrayList<ClausedConsignmentDetail> getClauseDetails() {
    return clauseDetails;
  }

  public void setClauseDetails(ArrayList<ClausedConsignmentDetail> value) {
    this.clauseDetails = value;
  }

  @XmlElement(name = "Info1")
  public String getInfo1() {
    return info1;
  }

  public void setInfo1(String value) {
    this.info1 = value;
  }

  @XmlElement(name = "Info2")
  public String getInfo2() {
    return info2;
  }

  public void setInfo2(String value) {
    this.info2 = value;
  }

  @XmlElement(name = "Info3")
  public String getInfo3() {
    return info3;
  }

  public void setInfo3(String value) {
    this.info3 = value;
  }

  @XmlElement(name = "Info4")
  public String getInfo4() {
    return info4;
  }

  public void setInfo4(String value) {
    this.info4 = value;
  }

  @XmlElement(name = "ConsignmentHeaderID")
  public int getConsignmentHeaderId() {
    return consignmentHeaderId;
  }

  public void setConsignmentHeaderId(int value) {
    this.consignmentHeaderId = value;
  }

  @Override
  public String toString() {
    return "ConsignmentDetail [detailId=" + detailId + ", detailRef=" + detailRef
        + ", customerDetailRef=" + customerDetailRef + ", parentRef=" + parentRef + ", orderRef="
        + orderRef + ", description=" + description + ", plannedQuantity=" + plannedQuantity
        + ", actualQuantity=" + actualQuantity + ", status=" + status + ", clauseDetails="
        + clauseDetails + ", info1=" + info1 + ", info2=" + info2 + ", info3=" + info3 + ", info4="
        + info4 + ", consignmentHeaderId=" + consignmentHeaderId + "]";
  }

}
