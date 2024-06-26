package com.ceva.cevatrack.webapp;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.exception.ExceptionUtils;
//import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ceva.cevatrack.service.AutomotiveClientService;
import com.ceva.cevatrack.service.HealthcareClientService;
import com.ceva.cevatrack.service.MicrolisePhotoService;
import com.ceva.cevatrack.service.SmartNetClientService;
import com.ceva.cevatrack.service.TechNetClientService;
import com.ceva.cevatrack.util.CommonApp;
import com.ceva.cevatrack.vo.MicrolisePhotoVO;
import com.ceva.cevatrack.vo.SmartNetClientVO;
import com.ceva.cevatrack.wsmicrolise.Common;
import com.ceva.cevatrack.wsmicrolise.WSMicroliseImage;
import com.ceva.cevatrack.wsmicrolise.getconsignment.Consignment;

/**
 * @author alexandru.castravet
 *
 */
@SuppressWarnings("serial")
@ManagedBean(name = "dtDetailView")
@ViewScoped
public class DetailView implements Serializable, CommonApp {

    private static final Logger LOGGER = LoggerFactory.getLogger(DetailView.class);
    private static final DateTimeFormatter DATEFORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");
    private Consignment consignment;
    private String client;
    private String dateRange;
    private String consignmentReference,prefix;
    private String message;
    // AutoRefresh
    private String date;
    private Boolean pollEnabled;
    private Boolean retry;
    private String network;
    private ArrayList<MicrolisePhotoVO> photos = null;
    

    public ArrayList<MicrolisePhotoVO> getPhotos() {
		return photos;
	}

	public void setPhotos(ArrayList<MicrolisePhotoVO> photos) {
		this.photos = photos;
	}

	@ManagedProperty(value = "#{consignmentListManager}")
    public ConsignmentListManager consignmentListManager;
    
    @EJB
    MicrolisePhotoService mpsService;

    @EJB
    SmartNetClientService snClientService;

    @EJB
    TechNetClientService tnClientService;
    
    @EJB
    AutomotiveClientService amClientService;
    
    @EJB
    HealthcareClientService hcClientService; 
    
    /**
     * called once
     */
    @PostConstruct
    public void init() {
        LOGGER.info("-- I am in the init function of DetailView");
        try {
            if (!checkLogin()) {
                return;
            }
        } catch (IOException e1) {
            LOGGER.error(" -- There is not User in the session when calling DetailView class.. ", e1);
            return;
        }
        retry = true;
        date = LocalDateTime.now().format(DATEFORMAT);
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        pollEnabled = new Boolean(false);
        client = params.get("client");
        dateRange = params.get("daterange");//null
        try{
	        consignmentReference = params.get("con");
	        if (consignmentReference == null){
	        	LOGGER.error(" -- No consignment reference has been passed as a parameter --");
	        	throw new Exception("-- No consignment reference passed as a parameter..");	        	  	
	        }else{
	        	LOG.info(" -- consignment is not null --");
	        	prefix = new String(consignmentReference.substring(0, consignmentReference.indexOf("/")));
	        	LOG.info(" -- prefix is: " + prefix);
	        }
        }catch (Exception e){
        	LOGGER.error("-- Error retrieving the consignment param.. ", e);
        	return;
        }
        LOGGER.info("(init) Parameters: client: " + client + ",dateRange: " + dateRange + ",cons Ref: "
                + consignmentReference);
        // ClientConfig cc;
        SmartNetClientVO clientDetail = null;

        this.network = params.get("network");        
        LOGGER.info("the network value in Consignment view is: " + network);
        client = params.get("client");                
        try{
        	boolean match = false;
        	if (prefix == null || network==null){
        		throw new Exception (" -- There is no a valid network or client parameter -- ");
        	}
	        if (network.equalsIgnoreCase("technet")){
	        	match = true;
	        	LOGGER.info("technet network detected");
	        	clientDetail = tnClientService.getTechNetClient(prefix);
	        } 
	        if (network.equalsIgnoreCase("smartnet")){
	        	match = true;
	        	LOGGER.info("smartnet network detected");
	        	clientDetail = snClientService.getSmartNetClient(prefix);
	        }
	        if (network.equalsIgnoreCase("Automotive")){
	        	match = true;
	        	LOGGER.info("Automotive network detected");
	        	clientDetail = amClientService.getAutomotiveClient(prefix);
	        }
	        if (network.equalsIgnoreCase("Healthcare")){
	        	match = true;
	        	LOGGER.info("Healthcare network detected");
	        	clientDetail = hcClientService.getHealthcareClient(prefix);
	        }
	        if (!match || clientDetail == null){
	        	throw new Exception (" -- There is not a valid matching network or client does not exist -- ");
	        }
        } catch (Exception e){
        	LOGGER.error(" -- Error in DB service obtaining clients details -- ", e);
        	return;
        }        
        // check that the client has access to this consignment
        if (!clientDetail.getPfx().contains(Common.getPrefix(consignmentReference))) {
            message = "Client access violation for: " + client + " con prefix" + Common.getPrefix(consignmentReference);
            return;
        }
        // fetch the consignment
        try {
            consignment = consignmentListManager.getConsignmentDetail(consignmentReference,network);
            if (!StringUtils.isBlank(consignmentReference)) {
                this.consignment.setConsignmentReference(consignmentReference);
                LOGGER.info("--Setted consignmentReference value to: " + this.consignmentReference);
            }
            if (consignment.getVehicles()!= null){
            	LOGGER.info(" -- Final content, vehicles: " + consignment.getVehicles().size());
            }
            if (consignment.getDrivers() != null){
            	LOGGER.info("-- Drivers size: " + consignment.getDrivers().size());
            }
            if ( consignment.getConsignmentDetails() != null){
            	LOGGER.info("-- Cons details size: " + consignment.getConsignmentDetails().size());
            }
            if (network.equalsIgnoreCase("technet")){
            	LOGGER.info("-- Looking on Technet network for associated consignment reference photos");
            	retrieve_microlise_photos(consignmentReference);
            }
        } catch (Exception e) {
            LOGGER.error("-- Problem retrieving consignments detail.. " + " \n trace: \n" + ExceptionUtils.getFullStackTrace(e), e.getStackTrace());
            // this message works fine
            // message = ExceptionUtils.getRootCauseMessage(e) + "... Press F5 to retry.";
            message = e.getCause().getMessage() + "... Press F5 to retry.";
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Communication Error", getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    private void retrieve_microlise_photos(String consignmentReference) {
    	this.photos = new ArrayList<>();
    	//ArrayList<MicrolisePhotoVO> beansP = (ArrayList<MicrolisePhotoVO>) mpsService.getPhotoRef("RICOHT/230629/11");
    	ArrayList<MicrolisePhotoVO> beansP = (ArrayList<MicrolisePhotoVO>) mpsService.getPhotoRef(consignmentReference);
    	if (beansP != null){
    		LOG.info("-- Recovered photos: " + beansP.size());
    	}else{
    		LOG.warn("-- There are no photos for reference: " + consignmentReference);
    	}
    	MicrolisePhotoVO beanp = null;
    	LOG.info(" -- Orders beans recovered: " + beansP.size());  
    	WSMicroliseImage ws = new WSMicroliseImage();
    	for (MicrolisePhotoVO photo:beansP){
    		LOG.info("photo bean recovered: " + photo.toString());
    		byte[] ressPhoto = WSMicroliseImage.callWS(photo.getActivity_image_no());
    		beanp = new MicrolisePhotoVO();
    		beanp.setActivity_image_no(photo.getActivity_image_no());
    		beanp.setActivity_name(photo.getActivity_name());
    		beanp.setPhoto(ressPhoto);
    		if (ressPhoto != null && ressPhoto.length != 0){
    			photos.add(beanp);
    		}else{
    			LOG.error("-- Photo was not retreived, null..");
    		}
    	}    	
		
	}

	public void changePoll() {
        if (this.pollEnabled) {
            this.pollEnabled = false;
            LOGGER.info("pollEnabled = " + getPollEnabled());
        } else {
            this.pollEnabled = true;
            LOGGER.info("pollEnabled = " + getPollEnabled());
        }
    }

    public void refresh() {
        LOGGER.info("I am in the refresh method in the Detailed view");
        consignmentReference = this.consignment.getConsignmentReference();
        LOGGER.info(" consignmentReference from the form: " + consignmentReference);
        try {
            consignment = consignmentListManager.getConsignmentDetail(consignmentReference,network);
            if (!StringUtils.isBlank(consignmentReference)) {
                this.consignment.setConsignmentReference(consignmentReference);
                LOGGER.info("--Setted consignmentReference value to: " + this.consignmentReference);
            }
            this.setDate(LocalDateTime.now().format(DATEFORMAT));
            retry = true;
        } catch (Exception e) {
            if (retry) {
                retry = false;
                LOGGER.info("-- Retrying to call WS getDetailConsignment..");
                this.refresh();
            } else {
                // message = ExceptionUtils.getRootCauseMessage(e) + "... Press F5 to retry."; e.getCause().getMessage()
                message = e.getCause().getMessage() + "... Press F5 to retry.";
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Communication Error", getMessage());
                FacesContext.getCurrentInstance().addMessage(null, msg);
                retry = true;
            }
            LOGGER.error("-- Error in refresh method in the DetailView: " + e.getStackTrace());
        }
    }

    public Boolean getPollEnabled() {
        return pollEnabled;
    }

    public void setPollEnabled(Boolean pollEnabled) {
        this.pollEnabled = pollEnabled;
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

    /**
     * @return info for user
     */
    public String getMessage() {
        return message;
    }

}