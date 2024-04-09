package com.ceva.cevatrack.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tracking_reference_suspense")
public class Tracking_reference_suspense implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  private String order_number;

  private String tracking_reference;
  private Date last_updated;
  private Date applied;

  public String getOrder_number() {
    return order_number;
  }

  public void setOrder_number(String order_number) {
    this.order_number = order_number;
  }

  public String getTracking_reference() {
    return tracking_reference;
  }

  public void setTracking_reference(String tracking_reference) {
    this.tracking_reference = tracking_reference;
  }

  public Date getLast_updated() {
    return last_updated;
  }

  public void setLast_updated(Date last_updated) {
    this.last_updated = last_updated;
  }

  public Date getApplied() {
    return applied;
  }

  public void setApplied(Date applied) {
    this.applied = applied;
  }

  @Override
  public String toString() {
    return "TRS [order_number=" + order_number + ", tracking_reference=" + tracking_reference
        + ", last_updated=" + last_updated + ", applied=" + applied + "]";
  }


}
