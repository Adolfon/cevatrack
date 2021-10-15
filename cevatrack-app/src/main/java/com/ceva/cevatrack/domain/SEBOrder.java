package com.ceva.cevatrack.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the SEB view database table.
 * 
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = "seb_order")
public class SEBOrder implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    String ml_order_ref;
	String load_id; 
	Date scheduled_timeout;
	Date seb_date  ;
	String route_id;
	Integer drop_number;
	String client;
	String loc_name;
	String loc_addr1;
	String loc_addr2;
	String loc_addr3;
	String loc_addr4;
	String loc_postcode;
	String driver;
	String vehicle;
	Date last_updated;
	Date last_ws_call;
	Date pta;
	Date ptd;
	Date eta;
	Date etd;
	Date ata;
	Date atd;
	String site_id;
	String site_name;
	String site_postcode;
	String clause_code; 
	String clause_description;
	String driver_name;
	Date planned_start_time;
	Date actual_start_time;
	Date pod_start_time;
	Date journey_actual_start_time;
	Integer journey_id;
	Integer ml_route_id;
	Integer ml_journey_drop_id;
	String site_lat;
	String site_long;
	String ml_vehicle;
	Integer quantity_planned;
	Integer quantity_actual;
	Integer quantity_claused;
	String ml_status;
	Date time_window_start;
	Date time_window_end;
		
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
	public String getMl_order_ref() {
		return ml_order_ref;
	}
	public void setMl_order_ref(String ml_order_ref) {
		this.ml_order_ref = ml_order_ref;
	}
	public String getLoad_id() {
		return load_id;
	}
	public void setLoad_id(String load_id) {
		this.load_id = load_id;
	}
	public Date getScheduled_timeout() {
		return scheduled_timeout;
	}
	public void setScheduled_timeout(Date scheduled_timeout) {
		this.scheduled_timeout = scheduled_timeout;
	}
	public Date getSeb_date() {
		return seb_date;
	}
	public void setSeb_date(Date seb_date) {
		this.seb_date = seb_date;
	}
	public String getRoute_id() {
		return route_id;
	}
	public void setRoute_id(String route_id) {
		this.route_id = route_id;
	}
	public Integer getDrop_number() {
		return drop_number;
	}
	public void setDrop_number(Integer drop_number) {
		this.drop_number = drop_number;
	}
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
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
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public String getVehicle() {
		return vehicle;
	}
	public void setVehicle(String vehicle) {
		this.vehicle = vehicle;
	}
	public Date getLast_updated() {
		return last_updated;
	}
	public void setLast_updated(Date last_updated) {
		this.last_updated = last_updated;
	}
	public Date getLast_ws_call() {
		return last_ws_call;
	}
	public void setLast_ws_call(Date last_ws_call) {
		this.last_ws_call = last_ws_call;
	}
	public Date getPta() {
		return pta;
	}
	public void setPta(Date pta) {
		this.pta = pta;
	}
	public Date getPtd() {
		return ptd;
	}
	public void setPtd(Date ptd) {
		this.ptd = ptd;
	}
	public Date getEta() {
		return eta;
	}
	public void setEta(Date eta) {
		this.eta = eta;
	}
	public Date getEtd() {
		return etd;
	}
	public void setEtd(Date etd) {
		this.etd = etd;
	}
	public Date getAta() {
		return ata;
	}
	public void setAta(Date ata) {
		this.ata = ata;
	}
	public Date getAtd() {
		return atd;
	}
	public void setAtd(Date atd) {
		this.atd = atd;
	}
	public String getSite_id() {
		return site_id;
	}
	public void setSite_id(String site_id) {
		this.site_id = site_id;
	}
	public String getSite_name() {
		return site_name;
	}
	public void setSite_name(String site_name) {
		this.site_name = site_name;
	}
	public String getSite_postcode() {
		return site_postcode;
	}
	public void setSite_postcode(String site_postcode) {
		this.site_postcode = site_postcode;
	}
	public String getClause_code() {
		return clause_code;
	}
	public void setClause_code(String clause_code) {
		this.clause_code = clause_code;
	}
	public String getClause_description() {
		return clause_description;
	}
	public void setClause_description(String clause_description) {
		this.clause_description = clause_description;
	}
	public String getDriver_name() {
		return driver_name;
	}
	public void setDriver_name(String driver_name) {
		this.driver_name = driver_name;
	}
	public Date getPlanned_start_time() {
		return planned_start_time;
	}
	public void setPlanned_start_time(Date planned_start_time) {
		this.planned_start_time = planned_start_time;
	}
	public Date getActual_start_time() {
		return actual_start_time;
	}
	public void setActual_start_time(Date actual_start_time) {
		this.actual_start_time = actual_start_time;
	}
	public Date getPod_start_time() {
		return pod_start_time;
	}
	public void setPod_start_time(Date pod_start_time) {
		this.pod_start_time = pod_start_time;
	}
	public Date getJourney_actual_start_time() {
		return journey_actual_start_time;
	}
	public void setJourney_actual_start_time(Date journey_actual_start_time) {
		this.journey_actual_start_time = journey_actual_start_time;
	}
	public Integer getJourney_id() {
		return journey_id;
	}
	public void setJourney_id(Integer journey_id) {
		this.journey_id = journey_id;
	}
	public Integer getMl_route_id() {
		return ml_route_id;
	}
	public void setMl_route_id(Integer ml_route_id) {
		this.ml_route_id = ml_route_id;
	}
	public Integer getMl_journey_drop_id() {
		return ml_journey_drop_id;
	}
	public void setMl_journey_drop_id(Integer ml_journey_drop_id) {
		this.ml_journey_drop_id = ml_journey_drop_id;
	}
	public String getSite_lat() {
		return site_lat;
	}
	public void setSite_lat(String site_lat) {
		this.site_lat = site_lat;
	}
	public String getSite_long() {
		return site_long;
	}
	public void setSite_long(String site_long) {
		this.site_long = site_long;
	}
	public String getMl_vehicle() {
		return ml_vehicle;
	}
	public void setMl_vehicle(String ml_vehicle) {
		this.ml_vehicle = ml_vehicle;
	}
	public Integer getQuantity_planned() {
		return quantity_planned;
	}
	public void setQuantity_planned(Integer quantity_planned) {
		this.quantity_planned = quantity_planned;
	}
	public Integer getQuantity_actual() {
		return quantity_actual;
	}
	public void setQuantity_actual(Integer quantity_actual) {
		this.quantity_actual = quantity_actual;
	}
	public Integer getQuantity_claused() {
		return quantity_claused;
	}
	public void setQuantity_claused(Integer quantity_claused) {
		this.quantity_claused = quantity_claused;
	}
	public String getMl_status() {
		return ml_status;
	}
	public void setMl_status(String ml_status) {
		this.ml_status = ml_status;
	}
	
	@Override
	public String toString() {
		return "SEBOrder [ml_order_ref=" + ml_order_ref + ", load_id=" + load_id + ", scheduled_timeout="
				+ scheduled_timeout + ", seb_date=" + seb_date + ", route_id=" + route_id + ", drop_number="
				+ drop_number + ", client=" + client + ", loc_name=" + loc_name + ", loc_addr1=" + loc_addr1
				+ ", loc_addr2=" + loc_addr2 + ", loc_addr3=" + loc_addr3 + ", loc_addr4=" + loc_addr4
				+ ", loc_postcode=" + loc_postcode + ", driver=" + driver + ", vehicle=" + vehicle + ", last_updated="
				+ last_updated + ", last_ws_call=" + last_ws_call + ", pta=" + pta + ", ptd=" + ptd + ", eta=" + eta
				+ ", etd=" + etd + ", ata=" + ata + ", atd=" + atd + ", site_id=" + site_id + ", site_name=" + site_name
				+ ", site_postcode=" + site_postcode + ", clause_code=" + clause_code + ", clause_description="
				+ clause_description + ", driver_name=" + driver_name + ", planned_start_time=" + planned_start_time
				+ ", actual_start_time=" + actual_start_time + ", pod_start_time=" + pod_start_time
				+ ", journey_actual_start_time=" + journey_actual_start_time + ", journey_id=" + journey_id
				+ ", ml_route_id=" + ml_route_id + ", ml_journey_drop_id=" + ml_journey_drop_id + ", site_lat="
				+ site_lat + ", site_long=" + site_long + ", ml_vehicle=" + ml_vehicle + ", quantity_planned="
				+ quantity_planned + ", quantity_actual=" + quantity_actual + ", quantity_claused=" + quantity_claused
				+ ", ml_status=" + ml_status + "]";
	}	
	
}
