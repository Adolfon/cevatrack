package com.ceva.cevatrack.vo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class JourneyVO {
	
	String routeID;
	Date seb_date;
	String loadID;
	String driver;
	String vehicule;
	List<Drop> drop;
	
	public String getRouteID() {
		return routeID;
	}
	public void setRouteID(String routeID) {
		this.routeID = routeID;
	}
	public Date getSeb_date() {
		return seb_date;
	}
	public String getSeb_dateString() {
			if (this.seb_date != null){
			String pattern = "HH:mm:ss";
			TimeZone zone = TimeZone.getTimeZone("Europe/London");			
			DateFormat df = new SimpleDateFormat(pattern);
			df.setTimeZone(zone);
			return df.format(this.seb_date);
		}else{
			return "";
		}
	}
	public void setSeb_date(Date seb_date) {
		this.seb_date = seb_date;
	}
	public String getLoadID() {
		return loadID;
	}
	public void setLoadID(String loadID) {
		this.loadID = loadID;
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public String getVehicule() {
		return vehicule;
	}
	public void setVehicule(String vehicule) {
		this.vehicule = vehicule;
	}
	public List<Drop> getDrop() {
		return drop;
	}
	public void setDrop(List<Drop> drop) {
		this.drop = drop;
	}
	@Override
	public String toString() {
		return "JourneyVO [routeID=" + routeID + ", seb_date=" + seb_date + ", loadID=" + loadID + ", driver=" + driver
				+ ", vehicule=" + vehicule + ", drop=" + drop + "]";
	}	

}


