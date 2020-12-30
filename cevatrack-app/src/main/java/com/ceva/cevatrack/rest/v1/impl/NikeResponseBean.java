package com.ceva.cevatrack.rest.v1.impl;

public class NikeResponseBean {

	private String date_shipped;
	private String estimated_arrival_date;
	private String actual_arrival_date;
	
	public String getDate_shipped() {
		return date_shipped;
	}
	public void setDate_shipped(String date_shipped) {
		this.date_shipped = date_shipped;
	}
	public String getEstimated_arrival_date() {
		return estimated_arrival_date;
	}
	public void setEstimated_arrival_date(String estimated_arrival_date) {
		this.estimated_arrival_date = estimated_arrival_date;
	}
	public String getActual_arrival_date() {
		return actual_arrival_date;
	}
	public void setActual_arrival_date(String actual_arrival_date) {
		this.actual_arrival_date = actual_arrival_date;
	}
	@Override
	public String toString() {
		return "NikeResponseBean [date_shipped=" + date_shipped + ", estimated_arrival_date=" + estimated_arrival_date
				+ ", actual_arrival_date=" + actual_arrival_date + "]";
	}

	
	
}
