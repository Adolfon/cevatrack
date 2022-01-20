package com.ceva.cevatrack.rest.v1.impl;

public class NikeResponseBean {

	private String date_shipped;
	private String estimated_arrival_date;
	private String actual_arrival_date;
	private String sent_to_ml;
	private String eta_no_ml;
	
	public String getSent_to_ml() {
		return sent_to_ml;
	}
	public void setSent_to_ml(String sent_to_ml) {
		this.sent_to_ml = sent_to_ml;
	}
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
				+ ", actual_arrival_date=" + actual_arrival_date + ", sent_to_ml=" + sent_to_ml + "]";
	}
	public String getEta_no_ml() {
		if (eta_no_ml == null){
			return "";
		}
		return eta_no_ml;
	}
	public void setEta_no_ml(String eta_no_ml) {
		this.eta_no_ml = eta_no_ml;
	}
		
}
