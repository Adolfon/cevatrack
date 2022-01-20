package com.ceva.cevatrack.webapp;

import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ceva.cevatrack.service.SEBService;
import com.ceva.cevatrack.util.CommonApp;
import com.ceva.cevatrack.vo.JourneyVO;
import com.ceva.security.aaf.session.SessionManager;
/*
 * SMS sent to the clients with URL token validation
 * 
 */

@SuppressWarnings("serial")
@ManagedBean(name = "sebView")
@SessionScoped
public class SEBview implements CommonApp, Serializable {

    private static final Logger log = LoggerFactory.getLogger(SEBview.class);
	
    private Date tripDate;
    private List<JourneyVO> trips;
    Subject currentUser;
    boolean change = false;

    
    @EJB
    SEBService seb;


	@PostConstruct
    public void init() {
        log.info("-- I am in the init function of SEBView");
        try {
			checkLogin();
		} catch (IOException e) {
			log.error(" --Error checking if logged in --");
		}
        if (tripDate == null){
        	log.info(" -- Trip date was null..");
        	tripDate = new Date();
        }
        log.info("-- Date for trips: " + tripDate);
        if (currentUser == null){      	
        	currentUser = SecurityUtils.getSubject();
        	log.info(" -- current user is: " + currentUser.getPrincipal());
        }
			String pattern = "yyyy-MM-dd";		
			DateFormat df = new SimpleDateFormat(pattern);
			log.info(" -- String init: " + df.format(this.tripDate));
			this.setTrips(seb.getJourneysByDateTimeUserFiltered(df.format(this.tripDate), currentUser));
               
    }
   

	private void filterTripsByUserRole() {
		log.info("-- Filtering trips by user permissions --");
		Subject currentUser = SecurityUtils.getSubject();
		 if (currentUser.isPermitted("")){}
//	            envDescription = "direct.prod";
//	        }
//		    private  final String SN_SITE = "sn_site:view:";
//		    private  final String SN_GLOBAL = "sn_site:view";
//		    //for ()
//         if (currentUser.isPermitted(SN_GLOBAL) || currentUser.isPermitted(SN_SITE + consignment.getSiteId())) {
//             return consignment;
//         }	
	}


	private void goToError(String error) {
    	try {
    		SessionManager.getSession().setAttribute("error-message-nutricia", error);
			FacesContext.getCurrentInstance().getExternalContext().redirect("error.xhtml");
		} catch (IOException e) {
			log.error("-- Error redirecting: ", e);
		}
		
	}


	public Date getTripDate() {
		return tripDate;
	}
	
	public String getTripDateString() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		return format.format(this.tripDate);
	}


	public void setTripDate(Date tripDate) {
		this.tripDate = tripDate;
	}
	
	public void onDateSelect(SelectEvent event){
		log.info(" -- Date selected for the trip has changed -- :" + getTripDate());
	    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	    log.info("Date from the calendar object: " + format.format(event.getObject()));
	    this.setTripDate((Date) event.getObject());
	    this.setTrips(seb.getJourneysByDateTimeUserFiltered(format.format(this.tripDate), currentUser));
	    this.change = true;
	    //facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));	
		//Refresh trips
	}


	public List<JourneyVO> getTrips() {
		return trips;
	}


	public void setTrips(List<JourneyVO> trips) {
		this.trips = trips;
	}

}