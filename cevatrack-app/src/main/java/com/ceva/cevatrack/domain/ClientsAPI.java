package com.ceva.cevatrack.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "clients_api")
@IdClass(NameId.class)
public class ClientsAPI implements Serializable{
  private static final long serialVersionUID = 1L;
  
  @Id
  @Column(name="client_id")
  private String client_id;
  
  @Id
  @Column(name="shipment_number")
  private String shipment_number;
  
  @Column(name="carrier_name")
  private String carrier_name;
  @Column(name="tracking_number")
  private String tracking_number;
  @Column(name="status")
  private String status;
  @Column(name="status_date")
  private Date status_date;
  @Column(name="revise_delivery_date")
  private Date revise_delivery_date;
  @Column(name="original_required_date")
  private Date original_required_date;
  @Column(name="reason_code")
  private String reason_code;
  @Column(name="customer_name")
  private String customer_name;
  @Column(name="customer_address")
  private String customer_address;
  @Column(name="actual_delivery_date")
  private Date actual_delivery_date;
  @Column(name="failure_reason_code")
  private String failure_reason_code;
  @Column(name="late_reason_code")
  private String late_reason_code;
  @Column(name="redelivery")
  private boolean redelivery;
  @Column(name="failure_reason")
  private String failure_reason;
  @Column(name="delivered_date")
  private Date delivered_date;
  @Column(name="claused_date")
  private Date claused_date;

public String getCarrier_name() {
		return carrier_name;
	}

	public void setCarrier_name(String carrier_name) {
		this.carrier_name = carrier_name;
	}

	public String getTracking_number() {
		return tracking_number;
	}

	public void setTracking_number(String tracking_number) {
		this.tracking_number = tracking_number;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getStatus_date() {
		return status_date;
	}

	public void setStatus_date(Date status_date) {
		this.status_date = status_date;
	}

	public Date getRevise_delivery_date() {
		return revise_delivery_date;
	}

	public void setRevise_delivery_date(Date revise_delivery_date) {
		this.revise_delivery_date = revise_delivery_date;
	}

	public Date getOriginal_required_date() {
		return original_required_date;
	}

	public void setOriginal_required_date(Date original_required_date) {
		this.original_required_date = original_required_date;
	}

	public String getReason_code() {
		return reason_code;
	}

	public void setReason_code(String reason_code) {
		this.reason_code = reason_code;
	}

	public String getCustomer_name() {
		return customer_name;
	}

	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}

	public String getCustomer_address() {
		return customer_address;
	}

	public void setCustomer_address(String customer_address) {
		this.customer_address = customer_address;
	}

	public Date getActual_delivery_date() {
		return actual_delivery_date;
	}

	public void setActual_delivery_date(Date actual_delivery_date) {
		this.actual_delivery_date = actual_delivery_date;
	}

	public String getFailure_reason_code() {
		return failure_reason_code;
	}

	public void setFailure_reason_code(String failure_reason_code) {
		this.failure_reason_code = failure_reason_code;
	}

	public String getLate_reason_code() {
		return late_reason_code;
	}

	public void setLate_reason_code(String late_reason_code) {
		this.late_reason_code = late_reason_code;
	}

	public boolean isRedelivery() {
		return redelivery;
	}

	public void setRedelivery(boolean redelivery) {
		this.redelivery = redelivery;
	}

	public String getFailure_reason() {
		return failure_reason;
	}

	public void setFailure_reason(String failure_reason) {
		this.failure_reason = failure_reason;
	}

	public String getClient_id() {
		return client_id;
	}
	
	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}
	
	public String getShipment_number() {
		return shipment_number;
	}
	
	public void setShipment_number(String shipment_number) {
		this.shipment_number = shipment_number;
	}

	public Date getDelivered_date() {
		return delivered_date;
	}

	public void setDelivered_date(Date delivered_date) {
		this.delivered_date = delivered_date;
	}

	public Date getClaused_date() {
		return claused_date;
	}

	public void setClaused_date(Date claused_date) {
		this.claused_date = claused_date;
	}

	@Override
	public String toString() {
		return "ClientsAPI [client_id=" + client_id + ", shipment_number=" + shipment_number + ", carrier_name="
				+ carrier_name + ", tracking_number=" + tracking_number + ", status=" + status + ", status_date="
				+ status_date + ", revise_delivery_date=" + revise_delivery_date + ", original_required_date="
				+ original_required_date + ", reason_code=" + reason_code + ", customer_name=" + customer_name
				+ ", customer_address=" + customer_address + ", actual_delivery_date=" + actual_delivery_date
				+ ", failure_reason_code=" + failure_reason_code + ", late_reason_code=" + late_reason_code
				+ ", redelivery=" + redelivery + ", failure_reason=" + failure_reason + ", delivered_date="
				+ delivered_date + ", claused_date=" + claused_date + "]";
	}
    
}