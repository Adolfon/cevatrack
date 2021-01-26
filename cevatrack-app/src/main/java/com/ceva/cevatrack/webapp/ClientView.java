package com.ceva.cevatrack.webapp;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ceva.cevatrack.service.AutomotiveClientService;
import com.ceva.cevatrack.service.SmartNetClientService;
import com.ceva.cevatrack.service.TechNetClientService;
import com.ceva.cevatrack.util.CommonApp;
import com.ceva.cevatrack.vo.SmartNetClientVO;
import com.ceva.cevatrack.wsmicrolise.Common;

/**
 * @author alexandru.castravet
 *
 */
@SuppressWarnings("serial")
@ManagedBean(name = "dtClientView")
@ViewScoped
public class ClientView implements Serializable, CommonApp {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientView.class);
    private final byte INTERVAL_DAYS = 7;
    public Map<String, ConsignmentPortfolioSummary> sclients = new HashMap<String, ConsignmentPortfolioSummary>();
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.BASIC_ISO_DATE;
    private LocalDate reportLocalDate;
    private LocalDate reportLocalDateTo;
    private static LocalDate datePickerSuccessTo;
    
    private String network;    

	public void setReportDateTo(Date reportLocalDateTo) {
        this.reportLocalDateTo = dateToLocal(reportLocalDateTo);
    }

    public Date getReportDateTo() {
        return localToDate(reportLocalDateTo);
    }

    private LocalDate minLocalDate;
    private LocalDate minLocalDateTo;

    public Date getMinDateTo() {
        return localToDate(minLocalDateTo);
    }

    public void setMinDateTo(Date minLocalDate) {
        this.minLocalDateTo = dateToLocal(minLocalDate);
    }

    private LocalDate maxLocalDate;
    private LocalDate maxLocalDateTo;

    public Date getMaxDateTo() {
        if (this.reportLocalDate.plusDays(INTERVAL_DAYS).isAfter(LocalDate.now())) {
            this.maxLocalDateTo = LocalDate.now();
        } else {
            this.maxLocalDateTo = this.reportLocalDate.plusDays(INTERVAL_DAYS);
        }
        return localToDate(maxLocalDateTo);
    }

    public void setMaxDateTo(Date maxLocalDateTo) {
        this.maxLocalDateTo = dateToLocal(maxLocalDateTo);
    }
    //SmartNetClientVO is the same for all the clients
    private List<SmartNetClientVO> configs;
    private List<SmartNetClientVO> configsTechNet;
    private static LocalDate datePickerSuccess;

    @ManagedProperty(value = "#{consignmentListManager}")
    public ConsignmentListManager consignmentListManager;

    @EJB
    SmartNetClientService snClientService;
    
    @EJB
    TechNetClientService tnClientService;
    
    @EJB
    AutomotiveClientService amClientService;

    /**
     * initialise
     */
    @PostConstruct
    public void init() {
        LOGGER.info(" -- starting method init on ClientView class -- ");
        try {//TODO Better an interceptor
            if (!checkLogin()) {
                return;
            }
        } catch (IOException e1) {
            LOGGER.error(" -- There is not user in the session when calling ClientView class ", e1);
            return;
        }

		FacesContext context = FacesContext.getCurrentInstance();
		Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
		this.network = paramMap.get("network");
        LOGGER.info("Network arg: " + network);
        if (network == null){
        	LOGGER.error("No network parameter was passed..");
        	return;
        }
        try{
        	 switch (network){
        	 case "technet": 
        		configs = tnClientService.searchTechNetClients();
        	 	this.network="TechNet";
        	 	break;
        	 case "smartnet": 
        		configs = snClientService.searchSmartNetClient();
        	 	this.network="SmartNet";
        	 	break;
        	 case "automotive": 
        		 configs = amClientService.searchAutomotiveClient();
        		 this.network="Automotive";        		         	
        	 }        	 
             LOGGER.info(" -- the number of clients recovered from the data base are: " + configs.size());
        }catch (Exception e){
        	LOGGER.error(" error trying recovering database table for clients: " + e.getStackTrace());
        }
                
        reportLocalDate = LocalDate.now();
        reportLocalDateTo = LocalDate.now();
        this.maxLocalDateTo = LocalDate.now().plusDays(INTERVAL_DAYS);
        // setMaxDateTo(localToDate(LocalDate.now().plusDays(INTERVAL_DAYS)));
        maxLocalDate = reportLocalDate.minusDays(0L);
        minLocalDate = reportLocalDate.minusDays(30L);
        try {        	
            LOGGER.info(" -- Updating the Datepicker dates.. ");
            sclients = consignmentListManager.getPortfolioStats(reportLocalDate.format(FORMATTER), null, configs, network);
        } catch (Exception e) {            
            LOGGER.error(" -- getPortfolioStats web service call failed .. (init method). \n trace: \n " + ExceptionUtils.getFullStackTrace(e));
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Communication Error",
                    e.getCause().getMessage() + "... Press F5 to retry.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    
    /**
     * @param event
     *            for selection of date
     */
    public void onDateSelect(SelectEvent event) {
        LOGGER.info("-- Datepicker OnDateSelect event, date selected: " + reportLocalDate.format(FORMATTER));
        try {
            sclients = consignmentListManager.getPortfolioStats(reportLocalDate.format(FORMATTER), null, configs, network);
        } catch (Exception e) {
            setReportDate(Date.from(datePickerSuccess.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            LOGGER.error(" -- On error I synchronize the datePicker to the last success value:  "
                    + reportLocalDate.format(FORMATTER));
            LOGGER.error(" -- ClientView.onDateSelect getPortfolioStats web service call failed .. (ajax method) ", e);
        } finally {
            reportLocalDateTo = reportLocalDate;
        }
        LOGGER.info("-- Consignments suceesfully retrieved for date: " + reportLocalDate.format(FORMATTER));
        datePickerSuccess = reportLocalDate;
        datePickerSuccessTo = reportLocalDate;
    }

    public void onDateSelectTo(SelectEvent event) {
        LOGGER.info("-- Datepicker OnDateSelectTo event, date selected: " + reportLocalDateTo.format(FORMATTER));
        if (this.reportLocalDate.isAfter(this.reportLocalDateTo)
                || this.reportLocalDateTo.isAfter(this.reportLocalDate.plusDays(INTERVAL_DAYS + 1))) {
            return;
        }
        this.setMinDateTo(getReportDate());
        try {
            sclients = consignmentListManager.getPortfolioStats(reportLocalDate.format(FORMATTER),
                    reportLocalDateTo.format(FORMATTER), configs, network);
        } catch (IOException st) {
            LOGGER.error("Socket time out exception, there were probably too many consingments..: " + st.getMessage());
            saveDateTo();
            RequestContext.getCurrentInstance()
                    .execute("alert('Too many consignments, please try to use a shorter range..')");
        } catch (Exception e) {
            LOGGER.error(
                    "Request consignments summaries for the new dates (calendar event) failed to method onDateSelectTo",
                    e);
            saveDateTo();
        }
        LOGGER.info(" -- Data successfully retrieved for the new selected dates: (from) " + reportLocalDate + ", to: "
                + reportLocalDateTo);
        datePickerSuccessTo = this.reportLocalDateTo;
    }

    private void saveDateTo() {
        if (datePickerSuccessTo != null) {
            this.reportLocalDateTo = datePickerSuccessTo;
        } else {
            this.reportLocalDateTo = this.reportLocalDate;
        }
    }

    public Date getReportDate() {
        return localToDate(reportLocalDate);
    }

    public String getReportDateString() {
        return reportLocalDate.format(FORMATTER);
    }

    public String getReportDateStringTo() {
        return reportLocalDateTo.format(FORMATTER);
    }

    public void setReportDate(Date reportDate) {
        reportLocalDate = dateToLocal(reportDate);
    }

    public Date getMinDate() {
        return localToDate(minLocalDate);
    }

    public void setMinDate(Date minDate) {
        minLocalDate = dateToLocal(minDate);
    }

    public Date getMaxDate() {
        return localToDate(maxLocalDate);
    }

    public void setMaxDate(Date maxDate) {
        maxLocalDate = dateToLocal(maxDate);
    }

    public Date localToDate(LocalDate ld) {
        return Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public LocalDate dateToLocal(Date d) {
        return d.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public ConsignmentListManager getConsignmentListManager() {
        return consignmentListManager;
    }

    public void setConsignmentListManager(ConsignmentListManager consignmentListManager) {
        this.consignmentListManager = consignmentListManager;
    }

    public Map<String, ConsignmentPortfolioSummary> getSclients() {
    	if (sclients == null){
    		sclients = new HashMap<String, ConsignmentPortfolioSummary>();
    	}
        return sclients;
    }

    public void setSclients(Map<String, ConsignmentPortfolioSummary> sclients) {
        this.sclients = sclients;
    }

    public String getStatusDescription(String code) {
        short thiscode = Short.parseShort(code);
        return Common.getStatusDescription(thiscode);
    }

    public String getStatusCssClass(String code) {
        short thiscode = Short.parseShort(code);
        return Common.getStatusCssClass(thiscode);
    }
    
    public String getNetwork() {
		return network;
	}

	public void setNetwork(String network) {
		this.network = network;
	}

    /**
     * @param client
     *            name for lookup
     * @return the bootstrap class indicating client status
     */
    public String getClientCssClass(String client) {
        ConsignmentPortfolioSummary thissummary = sclients.get(client);
        if (thissummary == null) {
            return "default";
        }

        Integer claused = thissummary.getStatusCounts().get((short) 88);
        if (claused == null) {
            claused = 0;
        }
        if (claused > 0) {
            return "danger";
        }

        Integer refused = thissummary.getStatusCounts().get((short) 82);
        if (refused == null) {
            refused = 0;
        }
        if (refused > 0) {
            return "danger";
        }

        Integer pending = thissummary.getStatusCounts().get((short) 78);
        if (pending == null) {
            pending = 0;
        }
        if (pending > 0) {
            return "warning";
        }

        Integer delivered = thissummary.getStatusCounts().get((short) 67);
        if (delivered == null) {
            delivered = 0;
        }
        if (delivered > 0) {
            return "success";
        }

        return "default";
    }
}
