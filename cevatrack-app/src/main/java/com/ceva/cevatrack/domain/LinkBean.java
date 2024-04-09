package com.ceva.cevatrack.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the generated link database table.
 * 
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = "mailer_order")
public class LinkBean implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  private String ml_order_ref;
  private String client;
  private String access_token;
  private String loc_name;
  private String loc_addr1;
  private String loc_addr2;
  private String loc_addr3;
  private String loc_addr4;
  private String loc_postcode;
  private String ml_site_lat;
  private String ml_site_long;

  public String getMl_order_ref() {
    return ml_order_ref;
  }

  public void setMl_order_ref(String ml_order_ref) {
    this.ml_order_ref = ml_order_ref;
  }

  public String getClient() {
    return client;
  }

  public void setClient(String client) {
    this.client = client;
  }

  public String getAccess_token() {
    return access_token;
  }

  public void setAccess_token(String access_token) {
    this.access_token = access_token;
  }

  public String getLoc_name() {
    return loc_name;
  }

  public void setLoc_name(String loc_name) {
    this.loc_name = loc_name;
  }

  public String getLoc_addr1() {
    return loc_addr1;
  }

  public void setLoc_addr1(String loc_addr1) {
    this.loc_addr1 = loc_addr1;
  }

  public String getLoc_addr2() {
    return loc_addr2;
  }

  public void setLoc_addr2(String loc_addr2) {
    this.loc_addr2 = loc_addr2;
  }

  public String getLoc_addr3() {
    return loc_addr3;
  }

  public void setLoc_addr3(String loc_addr3) {
    this.loc_addr3 = loc_addr3;
  }

  public String getLoc_addr4() {
    return loc_addr4;
  }

  public void setLoc_addr4(String loc_addr4) {
    this.loc_addr4 = loc_addr4;
  }

  public String getLoc_postcode() {
    return loc_postcode;
  }

  public void setLoc_postcode(String loc_postcode) {
    this.loc_postcode = loc_postcode;
  }

  public String getMl_site_lat() {
    return ml_site_lat;
  }

  public void setMl_site_lat(String ml_site_lat) {
    this.ml_site_lat = ml_site_lat;
  }

  public String getMl_site_long() {
    return ml_site_long;
  }

  public void setMl_site_long(String ml_site_long) {
    this.ml_site_long = ml_site_long;
  }

  @Override
  public String toString() {
    return "LinkBean [ml_order_ref=" + ml_order_ref + ", client=" + client + ", access_token="
        + access_token + ", loc_name=" + loc_name + ", loc_addr1=" + loc_addr1 + ", loc_addr2="
        + loc_addr2 + ", loc_addr3=" + loc_addr3 + ", loc_addr4=" + loc_addr4 + ", loc_postcode="
        + loc_postcode + ", ml_site_lat=" + ml_site_lat + ", ml_site_long=" + ml_site_long + "]";
  }
}
