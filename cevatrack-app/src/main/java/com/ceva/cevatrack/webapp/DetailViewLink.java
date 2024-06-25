package com.ceva.cevatrack.webapp;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ceva.cevatrack.domain.LinkBean;
import com.ceva.cevatrack.service.LinkService;
import com.ceva.cevatrack.util.ApplicationGlobalConfig;
import com.ceva.cevatrack.wsmicrolise.getconsignment.Consignment;
import com.ceva.cevatrack.wsmicrolise.getconsignment.ConsignmentDetail;
import com.ceva.cevatrack.wsmicrolise.getconsignment.Vehicle;
import com.ceva.security.aaf.session.SessionManager;
/*
 * SMS sent to the clients with URL token validation
 * 
 */

@SuppressWarnings("serial")
@ManagedBean(name = "dtDetailViewLink")
@RequestScoped
public class DetailViewLink implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(DetailViewLink.class);
    private Consignment consignment;
    private String client;
    private String message;
    private String date;
    private String network;   
    private String windowStart;
    private String windowEnd;	
	private String vehicule_loc;	
	private String place_loc;	
	private String apiKey = ApplicationGlobalConfig.GoogleMapsAPIKey;
	
	public String getApiKey() {
		return apiKey;
	}


	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}


	public String getVehicule_loc() {
		return vehicule_loc;
	}


	public void setVehicule_loc(String vehicule_loc) {
		this.vehicule_loc = vehicule_loc;
	}


	public String getPlace_loc() {
		return place_loc;
	}


	public void setPlace_loc(String place_loc) {
		this.place_loc = place_loc;
	}

	@ManagedProperty(value = "#{consignmentListManager}")
    public ConsignmentListManager consignmentListManager;

    @EJB
    LinkService linkService;
	private boolean showMap;
    public boolean isShowMap() {
		return showMap;
	}

	public void setShowMap(boolean showMap) {
		this.showMap = showMap;
	}
	String feedBack_URL;
	
	public String getFeedBack_URL() {
		return feedBack_URL;
	}


	public void setFeedBack_URL(String feedBack_URL) {
		this.feedBack_URL = feedBack_URL;
	}


	@PostConstruct
    public void init() {
        LOGGER.info("-- I am in the init function of DetailViewLink");
        Object obj = FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        LOGGER.info(" -- Session obj: " + obj);
        place_loc = "";
        vehicule_loc = "";
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap(); 
        String linkParameter = params.get("t");
        LOGGER.info("-- Link parameter retrieved: " + linkParameter );
        if (linkParameter == null){
        	goToError("Parameter missing");
        	return;
        }           
        client = "Nutricia"; //Nutricia from "Health care medicine" network  
        if (linkService == null){
        	LOGGER.error(" -- linkService EJB not available (null) --");
        	goToError("There was a technical problem. \n Please contact support. \nSorry for the inconvenience");
        	return;
        }
        LinkBean lbean = linkService.getLinkBeanById(linkParameter);        
        LOGGER.info("lbean recovered: " + lbean);
        if (lbean == null){
        	LOGGER.warn(" -- Not found on database --");
        	goToError("Authentication error");
        	return;
        }        
        String consingmentString = lbean.getMl_order_ref();
        client = lbean.getClient();
        place_loc = lbean.getMl_site_lat() + ", " + lbean.getMl_site_long();
        LOGGER.info(" -- Data retrieved from data base, client: " + client + " ,consignment ref: " + consingmentString + " ,location: " + place_loc);
        if (!client.equalsIgnoreCase("NUTRIC")){
        	LOGGER.warn(" --  client error --, linkParameter: " + linkParameter + ",linkBean: " + lbean);
        	goToError("Not enough information error");
        	return;
        }
        //Dedicated view for the link and special permission for retrieving consignment.
        this.network = "HealthLink";     
        LOGGER.info("the network value is: " + network);                         
        // fetch the consignment
        try {
            consignment = consignmentListManager.getConsignmentDetail(consingmentString.trim(),network);
            LOGGER.info(" --Consignment retrieved: " + consignment);
        } catch (Exception e) {
            LOGGER.error("-- Problem retrieving consignments detail.. " + " \n trace: \n", e);
            goToError(" - Sorry, there was a network problem, please try again later -");
        }
        if (consignment == null){
        	LOGGER.warn(" -- Consignment not available ");
        	goToError("Sorry, the information is not available righ now, please try again later");
        	return;
        }    
        LOGGER.info(" -- Consingment recovered details: " + consignment);
        this.feedBack_URL = linkService.getURL_rate_link("NUTRIC");    
        LOGGER.info(" -- Feedback link recovered: " + this.feedBack_URL);
        modifyConsignmentVisitWindow(consignment);
        
        LOGGER.info("Check");
        try{
        	if (consignment != null && consignment.getConsignmentDetails() != null){
		        for(ConsignmentDetail con: consignment.getConsignmentDetails()){
		        	LOGGER.info("\n --Details: " + con);
		        	LOGGER.info(" \n============================================== ");
		        	LOGGER.info(" Detail ref: " + con.getDetailRef() + "Customer detail ref: " + con.getCustomerDetailRef());
		        }   
        	}else{
        		LOGGER.warn("--Consignment was null..");
        	}
        }catch (Exception e){
        	LOGGER.error("--Exception..", e);
        }
        LOGGER.info("Check");                              
        String vlong = null, vlat = null;        
        if (consignment!= null && consignment.getVehicles() != null){
        	LOGGER.info("-- Number of vehicles: " + consignment.getVehicles().size());
	        for (Vehicle v: consignment.getVehicles()){
	        	if (v.getLastContactLong() != 0.0f){
	        		vlong = String.valueOf(v.getLastContactLong());
	        	}
	        	if (v.getLastContactLat() != 0.0f){
	        		vlat = String.valueOf(v.getLastContactLat());
	        	}
	        }
        }else{
        	LOGGER.warn("-- Vehicles was null");
        }       
        if (consignment.getStatusString() != null && !consignment.getStatusString().equalsIgnoreCase("To be delivered") ||  
        		lbean.getMl_site_lat() == null || lbean.getMl_site_long() == null
        		|| "".equals(lbean.getMl_site_lat()) || "".equals(lbean.getMl_site_long())
        				|| vlong == null || vlat == null || vlong.equals("") || vlat.equals("")){
        	LOGGER.info("Map is false, values: (site lat) " + lbean.getMl_site_lat() + ", (site long) " + lbean.getMl_site_long() 
        		+ " ,(vlong) " + vlong + " ,(vlat) " + vlat);
        	this.showMap = false;
        }else{
        	this.showMap = true;
        }
        vehicule_loc = vlat + " ," + vlong;
        LOGGER.info(" -- Vehicle location: " + vehicule_loc);
    }

    private void modifyConsignmentVisitWindow(Consignment consignment2) {
    	if (StringUtils.isBlank(consignment2.getEtaString()) || !consignment2.getStatusString().equalsIgnoreCase("To be delivered")){
    		LOGGER.info(" -- Estimated time is null, empty or Status is not 'To be delivered' so no window conversion possible or needed..");
    		this.windowStart = "";
    		this.windowEnd = "";
    		return;
    	}
    	LOGGER.info(" -- Window modified from (stimated time): " + consignment2.getEtaString());
    	try{    		    			    	
			 //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM hh:mm a", Locale.ENGLISH);   
		     LocalDateTime ldt_start = LocalDateTime.parse(consignment2.getEta(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		     LocalDateTime ldt_end = LocalDateTime.parse(consignment2.getEta(),  DateTimeFormatter.ISO_LOCAL_DATE_TIME);
		     //ZoneId defaultZone = TimeZone.getTimeZone("Europe/London").toZoneId();
	    	 ldt_start = ldt_start.minusMinutes(30);
	    	 ldt_end = ldt_end.plusMinutes(30);
		     this.windowStart = ldt_start.format(DateTimeFormatter.ofPattern("dd MMM hh:mm a"));
		     this.windowEnd = ldt_end.format(DateTimeFormatter.ofPattern("dd MMM hh:mm a"));
    	}catch(Exception e){
    		this.windowStart = "";
    		this.windowEnd = "";
    		LOGGER.error(" -- Date modification error -- ", e);
    	}
    	 LOGGER.info("-- Window modified to (start, end): " + getWindowStart() + " ,end: " + getWindowEnd());
	}

	private void goToError(String error) {
    	try {
    		SessionManager.getSession().setAttribute("error_msg", error);
			FacesContext.getCurrentInstance().getExternalContext().redirect("error.xhtml");
		} catch (IOException e) {
			LOGGER.error("-- Error redirecting: ", e);
		}
		
	}

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ConsignmentListManager getConsignmentListManager() {
        return consignmentListManager;
    }

    public void setConsignmentListManager(ConsignmentListManager consignmentListManager) {
        this.consignmentListManager = consignmentListManager;
    }

    public Consignment getConsignment() {
        return consignment;
    }

    public void setConsignment(Consignment consignment) {
        this.consignment = consignment;
    }

    public String getMessage() {
        return message;
    }

	public String getWindowStart() {
		return windowStart;
	}

	public void setWindowStart(String windowStart) {
		this.windowStart = windowStart;
	}

	public String getWindowEnd() {
		return windowEnd;
	}

	public void setWindowEnd(String windowEnd) {
		this.windowEnd = windowEnd;
	}

}