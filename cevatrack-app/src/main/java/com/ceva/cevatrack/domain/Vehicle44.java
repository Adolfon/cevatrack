
package com.ceva.cevatrack.domain;

import java.io.Serializable;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Access(AccessType.FIELD)
@Table(name = "p44_consignment_vehicle")
public class Vehicle44 implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  protected String displayName;
  protected String lastLocation;
  protected float lastContactLat;
  protected float lastContactLong;
  protected int journeyId;
  String consignmentReference;

  public String getConsignmentReference() {
    return consignmentReference;
  }

  public void setConsignmentReference(String consignmentReference) {
    this.consignmentReference = consignmentReference;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String value) {
    this.displayName = value;
  }

  public String getLastLocation() {
    return lastLocation;
  }

  public void setLastLocation(String value) {
    this.lastLocation = value;
  }

  public float getLastContactLat() {
    return lastContactLat;
  }

  public void setLastContactLat(float value) {
    this.lastContactLat = value;
  }

  public float getLastContactLong() {
    return lastContactLong;
  }

  public void setLastContactLong(float value) {
    this.lastContactLong = value;
  }

  public int getJourneyId() {
    return journeyId;
  }

  public void setJourneyId(int value) {
    this.journeyId = value;
  }

}
