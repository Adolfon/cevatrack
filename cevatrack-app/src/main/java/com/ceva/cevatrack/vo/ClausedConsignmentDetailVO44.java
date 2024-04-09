
package com.ceva.cevatrack.vo;

import java.io.Serializable;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;

public class ClausedConsignmentDetailVO44 implements Serializable {

  private static final long serialVersionUID = 1L;
  protected String parentCode;
  protected String parentClauseDescription;
  protected String clauseCode;
  protected String clauseDescription;
  protected int clausedQuantity;
  protected int consignmentDetailId;

  public String getParentCode() {
    return parentCode;
  }

  public void setParentCode(String value) {
    this.parentCode = value;
  }

  public String getParentClauseDescription() {
    return parentClauseDescription;
  }

  public void setParentClauseDescription(String value) {
    this.parentClauseDescription = value;
  }

  public String getClauseCode() {
    return clauseCode;
  }

  public void setClauseCode(String value) {
    this.clauseCode = value;
  }

  public String getClauseDescription() {
    return clauseDescription;
  }

  public void setClauseDescription(String value) {
    this.clauseDescription = value;
  }

  public int getClausedQuantity() {
    return clausedQuantity;
  }

  public void setClausedQuantity(int value) {
    this.clausedQuantity = value;
  }

  public int getConsignmentDetailId() {
    return consignmentDetailId;
  }

  public void setConsignmentDetailId(int value) {
    this.consignmentDetailId = value;
  }

}
