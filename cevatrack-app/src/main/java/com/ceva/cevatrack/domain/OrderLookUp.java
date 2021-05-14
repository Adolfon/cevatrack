package com.ceva.cevatrack.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "order_lookup")
public class OrderLookUp implements Serializable{

	private static final long serialVersionUID = 1L;
	
	String client;
    @Id
	String client_ref;
	String load_id;
	Date load_date;
	String order_number;
	String notify_method;
	Date shipped_date;
	String ml_order_ref;
	Date last_updated;
	Date sent_to_ml;
	Date eta;
	
	public Date getEta_no_ml() {
		return eta;
	}
	public void setEta_no_ml(Date eta_no_ml) {
		this.eta = eta_no_ml;
	}
	public Date getSent_to_ml() {
		return sent_to_ml;
	}
	public void setSent_to_ml(Date sent_to_ml) {
		this.sent_to_ml = sent_to_ml;
	}
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public String getClient_ref() {
		return client_ref;
	}
	public void setClient_ref(String client_ref) {
		this.client_ref = client_ref;
	}
	public String getLoad_id() {
		return load_id;
	}
	public void setLoad_id(String load_id) {
		this.load_id = load_id;
	}
	public Date getLoad_date() {
		return load_date;
	}
	public void setLoad_date(Date load_date) {
		this.load_date = load_date;
	}
	public String getOrder_number() {
		return order_number;
	}
	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}
	public String getNotify_method() {
		return notify_method;
	}
	public void setNotify_method(String notify_method) {
		this.notify_method = notify_method;
	}
	public Date getShipped_date() {
		return shipped_date;
	}
	public void setShipped_date(Date shipped_date) {
		this.shipped_date = shipped_date;
	}
	public String getMl_order_ref() {
		return ml_order_ref;
	}
	public void setMl_order_ref(String ml_order_ref) {
		this.ml_order_ref = ml_order_ref;
	}
	public Date getLast_updated() {
		return last_updated;
	}
	public void setLast_updated(Date last_updated) {
		this.last_updated = last_updated;
	}
	
	public OrderLookUp(){
		
	}
	
	@Override
	public String toString() {
		return "OrderLookUp [client=" + client + ", client_ref=" + client_ref + ", load_id=" + load_id + ", load_date="
				+ load_date + ", order_number=" + order_number + ", notify_method=" + notify_method + ", shipped_date="
				+ shipped_date + ", ml_order_ref=" + ml_order_ref + ", last_updated=" + last_updated + ", sent_to_ml="
				+ sent_to_ml + ", eta_no_ml=" + eta + "]";
	}
	
	public OrderLookUp(String client, String client_ref, String load_id, Date load_date, String order_number,
			String notify_method, Date shipped_date, String ml_order_ref, Date last_updated, Date sent_to_ml,
			Date eta_no_ml) {
		super();
		this.client = client;
		this.client_ref = client_ref;
		this.load_id = load_id;
		this.load_date = load_date;
		this.order_number = order_number;
		this.notify_method = notify_method;
		this.shipped_date = shipped_date;
		this.ml_order_ref = ml_order_ref;
		this.last_updated = last_updated;
		this.sent_to_ml = sent_to_ml;
		this.eta = eta_no_ml;
	}
		
}
