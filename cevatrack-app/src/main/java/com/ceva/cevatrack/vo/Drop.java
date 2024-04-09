package com.ceva.cevatrack.vo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class Drop {

  String site_name;
  String site_id;
  String address;
  Date ETA;
  Date PTA;
  Date ATA;
  Date time_window_start;
  Date time_window_end;
  String client;
  String colour;
  Date showedTime;
  int dropNumber;
  List<String> references;


  public Date getShowedTime() {
    return showedTime;
  }

  public String getShowedTimeString() {
    if (this.showedTime != null) {
      String pattern = "HH:mm:ss";
      TimeZone zone = TimeZone.getTimeZone("Europe/London");
      DateFormat df = new SimpleDateFormat(pattern);
      df.setTimeZone(zone);
      return df.format(this.showedTime);
    } else {
      return "ETA/ATA not available";
    }
  }

  public void setShowedTime(Date showedTime) {
    this.showedTime = showedTime;
  }

  public List<String> getReferences() {
    return references;
  }

  public void setReferences(List<String> references) {
    this.references = references;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public Date getETA() {
    return ETA;
  }

  public void setETA(Date eTA) {
    ETA = eTA;
  }

  public Date getPTA() {
    return PTA;
  }

  public void setPTA(Date pTA) {
    PTA = pTA;
  }

  public Date getATA() {
    return ATA;
  }

  public void setATA(Date aTA) {
    ATA = aTA;
  }

  public String getClient() {
    return client;
  }

  public void setClient(String client) {
    this.client = client;
  }

  public String getColour() {
    return colour;
  }

  public void setColour(String colour) {
    this.colour = colour;
  }

  public int getDropNumber() {
    return dropNumber;
  }

  public void setDropNumber(int dropNumber) {
    this.dropNumber = dropNumber;
  }

  public String getSite_name() {
    return site_name;
  }

  public void setSite_name(String site_name) {
    this.site_name = site_name;
  }

  public Date getTime_window_start() {
    return time_window_start;
  }

  public void setTime_window_start(Date time_window_start) {
    this.time_window_start = time_window_start;
  }

  public Date getTime_window_end() {
    return time_window_end;
  }

  public void setTime_window_end(Date time_window_end) {
    this.time_window_end = time_window_end;
  }

  public String getSite_id() {
    if (this.site_id == null) {
      return "";
    }
    return site_id;
  }

  public void setSite_id(String site_id) {
    this.site_id = site_id;
  }

  @Override
  public String toString() {
    return "Drop [site_name=" + site_name + ", site_id=" + site_id + ", address=" + address
        + ", ETA=" + ETA + ", PTA=" + PTA + ", ATA=" + ATA + ", time_window_start="
        + time_window_start + ", time_window_end=" + time_window_end + ", client=" + client
        + ", colour=" + colour + ", showedTime=" + showedTime + ", dropNumber=" + dropNumber
        + ", references=" + references + "]";
  }
}
