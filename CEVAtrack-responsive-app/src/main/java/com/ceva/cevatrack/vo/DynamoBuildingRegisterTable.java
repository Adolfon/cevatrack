package com.ceva.cevatrack.vo;

public class DynamoBuildingRegisterTable {
  
  private String Id, CevaLocation, EntryDate, EntryTime,ExitDate, ExitTime, EmpFirstName, EmpLastTime, CevaDeviceId, FireGroup, FireAssemblyPoint; 
  private Boolean IsAppointment;

  public String getId() {
    return Id;
  }

  public void setId(String id) {
    Id = id;
  }

  public String getCevaLocation() {
    return CevaLocation;
  }

  public void setCevaLocation(String cevaLocation) {
    CevaLocation = cevaLocation;
  }

  public String getEntryDate() {
    return EntryDate;
  }

  public void setEntryDate(String entryDate) {
    EntryDate = entryDate;
  }

  public String getEntryTime() {
    return EntryTime;
  }

  public void setEntryTime(String entryTime) {
    EntryTime = entryTime;
  }

  public String getExitDate() {
    return ExitDate;
  }

  public void setExitDate(String exitDate) {
    ExitDate = exitDate;
  }

  public String getExitTime() {
    return ExitTime;
  }

  public void setExitTime(String exitTime) {
    ExitTime = exitTime;
  }

  public String getEmpFirstName() {
    return EmpFirstName;
  }

  public void setEmpFirstName(String empFirstName) {
    EmpFirstName = empFirstName;
  }

  public String getEmpLastTime() {
    return EmpLastTime;
  }

  public void setEmpLastTime(String empLastTime) {
    EmpLastTime = empLastTime;
  }

  public String getCevaDeviceId() {
    return CevaDeviceId;
  }

  public void setCevaDeviceId(String cevaDeviceId) {
    CevaDeviceId = cevaDeviceId;
  }

  public String getFireGroup() {
    return FireGroup;
  }

  public void setFireGroup(String fireGroup) {
    FireGroup = fireGroup;
  }

  public String getFireAssemblyPoint() {
      return FireAssemblyPoint;    
  }

  public void setFireAssemblyPoint(String fireAssemblyPoint) {
    FireAssemblyPoint = fireAssemblyPoint;
  }

  public Boolean getIsAppointment() {
    return IsAppointment;
  }

  public void setIsAppointment(Boolean isAppointment) {
    IsAppointment = isAppointment;
  }

  @Override
  public String toString(){
    return " Id: " + getId() + ", Ceva Location: " + getCevaLocation() + "\n Entry date: " + getEntryDate() + ", Entry time: " 
        + getEntryTime() + ", Exit date: " + getExitDate() + ", Exit time: " + getExitTime() + " \n First Name: " + getEmpFirstName() +
        ", Last Time: " + getEmpLastTime() + ", Ceva device Id: " + getCevaDeviceId() + ", Fire group: " + getFireGroup() + 
        " \n Fire Assembly point: " + getFireAssemblyPoint() + ", is an appointment?: " + getIsAppointment();
  }
  
  
}
