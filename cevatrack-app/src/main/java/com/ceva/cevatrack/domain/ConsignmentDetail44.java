
package com.ceva.cevatrack.domain;

import java.io.Serializable;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.ceva.cevatrack.wsmicrolise.Common;

@Entity
@Access(AccessType.FIELD)
@Table(name = "p44_consignment_detail")
public class ConsignmentDetail44 implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  String detailRef;
  String customerDetailRef;
  String parentRef;
  String orderRef;
  String description;
  int plannedQuantity;
  int actualQuantity;
  short status;
  // ArrayList<ClausedConsignmentDetail44> clauseDetails;
  String info1;
  String info2;
  String info3;
  String info4;
  int consignmentHeaderId;
  String consignmentReference;

  public String getConsignmentReference() {
    return consignmentReference;
  }

  public void setConsignmentReference(String consignmentReference) {
    this.consignmentReference = consignmentReference;
  }

  public String getDetailRef() {
    return detailRef;
  }

  public void setDetailRef(String value) {
    this.detailRef = value;
  }

  public String getCustomerDetailRef() {
    return customerDetailRef;
  }

  public void setCustomerDetailRef(String value) {
    this.customerDetailRef = value;
  }

  public String getParentRef() {
    return parentRef;
  }

  public void setParentRef(String value) {
    this.parentRef = value;
  }

  public String getOrderRef() {
    return orderRef;
  }

  public void setOrderRef(String value) {
    this.orderRef = value;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String value) {
    this.description = value;
  }

  public int getPlannedQuantity() {
    return plannedQuantity;
  }

  public void setPlannedQuantity(int value) {
    this.plannedQuantity = value;
  }

  public int getActualQuantity() {
    return actualQuantity;
  }

  public void setActualQuantity(int value) {
    this.actualQuantity = value;
  }

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

  public String getInfo1() {
    return info1;
  }

  public void setInfo1(String value) {
    this.info1 = value;
  }

  public String getInfo2() {
    return info2;
  }

  public void setInfo2(String value) {
    this.info2 = value;
  }

  public String getInfo3() {
    return info3;
  }

  public void setInfo3(String value) {
    this.info3 = value;
  }

  public String getInfo4() {
    return info4;
  }

  public void setInfo4(String value) {
    this.info4 = value;
  }

  public int getConsignmentHeaderId() {
    return consignmentHeaderId;
  }

  public void setConsignmentHeaderId(int value) {
    this.consignmentHeaderId = value;
  }

  @Override
  public String toString() {
    return "ConsignmentDetail [ detailRef=" + detailRef + ", customerDetailRef=" + customerDetailRef
        + ", parentRef=" + parentRef + ", orderRef=" + orderRef + ", description=" + description
        + ", plannedQuantity=" + plannedQuantity + ", actualQuantity=" + actualQuantity
        + ", status=" + status + ", info1=" + info1 + ", info2=" + info2 + ", info3=" + info3
        + ", info4=" + info4 + ", consignmentHeaderId=" + consignmentHeaderId
        + ", ConsingmentReference= " + consignmentReference + "]";
  }

}
