package com.ceva.cevatrack.webapp;

import java.io.IOException;
import java.io.Serializable;
import java.net.SocketTimeoutException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.apache.commons.lang.exception.ExceptionUtils;
//import org.apache.commons.lang.exception.ExceptionUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ceva.cevatrack.service.AutomotiveClientService;
import com.ceva.cevatrack.service.HealthcareClientService;
import com.ceva.cevatrack.service.SmartNetClientService;
import com.ceva.cevatrack.service.TechNetClientService;
import com.ceva.cevatrack.util.CommonApp;
import com.ceva.cevatrack.vo.SmartNetClientVO;
import com.ceva.cevatrack.wsmicrolise.Common;
import com.ceva.cevatrack.wsmicrolise.findconsignmentsummaries.ConsignmentSummary;

/**
 * @author alexandru.castravet
 *
 */
@SuppressWarnings("serial")
@ManagedBean(name = "dtConsignmentView")
@ViewScoped
public class ConsignmentView implements Serializable, CommonApp {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConsignmentView.class);

    @ManagedProperty(value = "#{consignmentListManager}")
    public ConsignmentListManager consignmentListManager;

    private DatedConsignmentList consignments;
    private String message = "";
    private String client;
    private ConsignmentSummary selectedConsignment;
    private List<ConsignmentSummary> filteredSnConsignments;
    private LocalDate reportLocalDate;
    private LocalDate reportLocalDateTo;
    private final byte INTERVAL_DAYS = 7;
    private String network;
    private String initStatusSelect;

	public String getInitStatusSelect() {
		return initStatusSelect;
	}

	public void setInitStatusSelect(String initStatusSelect) {
		this.initStatusSelect = initStatusSelect;
	}

	public void setReportDateTo(Date reportLocalDateTo) {
        this.reportLocalDateTo = dateToLocal(reportLocalDateTo);
    }

    public Date getReportDateTo() {
        return localToDate(reportLocalDateTo);
    }

    private LocalDate minLocalDate;
    private LocalDate minLocalDateTo;

    public Date getMinLocalDate() {
        return localToDate(minLocalDate);
    }

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

    public void setMaxDateTo(Date maxLocalDate) {
        maxLocalDateTo = dateToLocal(maxLocalDate);
    }

    private Map<String, String> params;
    private SmartNetClientVO clientDetail;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.BASIC_ISO_DATE;
    private static final DateTimeFormatter DATEFORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");
    // Autorefresh
    private Boolean pollEnabled;
    private String date;
    private Boolean retry;
    private static LocalDate datePickerSuccess;
    private static LocalDate datePickerSuccessTo;

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
        try {
            if (!checkLogin()) {
                return;
            }
        } catch (IOException e1) {
            LOGGER.error(" -- There is not user in the session when calling ConsignmentView class ", e1);
            return;
        }
        date = LocalDateTime.now().format(DATEFORMAT);
        pollEnabled = new Boolean(false);
        retry = new Boolean(true);

        this.params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        this.network = params.get("network");        
        LOGGER.info("the network value in Consignment view is: " + network);
        client = params.get("client");                
        try{
        	boolean oneSelected = false;
        	if (client == null || network==null){
        		throw new Exception (" -- There is no a valid network or client parameter -- ");
        	}
	        if (network.equalsIgnoreCase("technet")){
	        	LOGGER.info("technet network detected");
	        	oneSelected = true;
	        	clientDetail = tnClientService.getTechNetClient(client);
	        } 
	        if (network.equalsIgnoreCase("smartnet")){
	        	LOGGER.info("smartnet network detected");
	        	oneSelected = true;
	        	clientDetail = snClientService.getSmartNetClient(client);
	        }
	        if (network.equalsIgnoreCase("Automotive")){
	        	LOGGER.info("Automotive network detected");
	        	oneSelected = true;
	        	clientDetail = amClientService.getAutomotiveClient(client);
	        }
	        if (network.equalsIgnoreCase("Healthcare")){
	        	LOGGER.info("Health network detected");
	        	oneSelected = true;
	        	clientDetail = hcClientService.getHealthcareClient(client);
	        }
	        if (!oneSelected){
	        	throw new Exception (" -- There was not a valid network selected -- ");
	        }
        } catch (Exception e){
        	LOGGER.error(" -- Error in DB service obtaining clients details -- " + ExceptionUtils.getFullStackTrace(e));
        }        
        reportLocalDate = LocalDate.now();
        reportLocalDateTo = reportLocalDate; // init the date to the 'from date'

        maxLocalDate = reportLocalDate.minusDays(0L);
        minLocalDate = reportLocalDate.minusDays(30L);
        maxLocalDateTo = LocalDate.now();
        try {
            // default to today's consignments for this client if not on parameter string
            if (params.containsKey("reportdate")) {
                String reportDate = params.get("reportdate");
                reportLocalDate = LocalDate.parse(reportDate, FORMATTER);
            }
            if (params.containsKey("reportdateTo")) { // Control the parameter day to a range of 5 days as a maximum
                String reportDateTo = params.get("reportdateTo");
                reportLocalDateTo = LocalDate.parse(reportDateTo, FORMATTER);
            }
            consignments = consignmentListManager.getFilteredConsignmentSummaries(reportLocalDate.format(FORMATTER),
                    null, clientDetail.getPfx(), true, false, this.network);
        } catch (Exception e) {
            LOGGER.error("Smartnet consignments error :" + client + "\n trace: \n" + ExceptionUtils.getFullStackTrace(e));
            message = e.getCause().getMessage() + "... Press F5 to retry.";
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Communication Error", getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            consignments = new DatedConsignmentList();
        }
        RequestContext rc = RequestContext.getCurrentInstance();
        if (params.containsKey("initstatus")) {        	           
            String paramStatus = params.get("initstatus");
            this.initStatusSelect = paramStatus;
            LOGGER.info(" -- param value: " + paramStatus);
        }else{
        	 this.initStatusSelect = "";
        }
        LOGGER.info("Init filter value: " + this.initStatusSelect);
        String at = "jQuery('div.ui-helper-hidden-accessible select').val('" + initStatusSelect +"');";
        rc.execute(at);
        rc.execute("PF('consignmentsTable').filter();");
    }

    /**
     * @param event
     *            for selection of date
     */
    public void onDateSelect(SelectEvent event) {
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("PF('consignmentsTable').clearFilters()");
        setMinDateTo(getReportDate());
        setReportDateTo(localToDate(reportLocalDate));
        try {
            consignments = consignmentListManager.getFilteredConsignmentSummaries(reportLocalDate.format(FORMATTER),
                    null, clientDetail.getPfx(), true, false, this.network);
        } catch (Exception e) {
            setReportDate(Date.from(datePickerSuccess.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            LOGGER.error("Request consignments summaries for the new dates (calendar event) failed", e);
        }
        LOGGER.info(" -- Data successfully retrieved for the new selected date: " + reportLocalDate);
        datePickerSuccess = reportLocalDate;
        datePickerSuccessTo = reportLocalDate;
    }

    public void onDateSelectTo(SelectEvent event) {
        LOGGER.info("-- Datepicker OnDateSelectTo event, date selected: " + reportLocalDateTo.format(FORMATTER));
        if (this.reportLocalDate.isEqual(this.reportLocalDateTo) || this.reportLocalDate.isAfter(this.reportLocalDateTo)
                || this.reportLocalDateTo.isAfter(this.reportLocalDate.plusDays(INTERVAL_DAYS + 1))) {
            return;
        }
        RequestContext requestContext = RequestContext.getCurrentInstance();
        // requestContext.execute("PF('consignmentsTable').clearFilters()");
        setMinDateTo(getReportDate());

        try {
            consignments = consignmentListManager.getFilteredConsignmentSummaries(reportLocalDate.format(FORMATTER),
                    reportLocalDateTo.format(FORMATTER), clientDetail.getPfx(), true, false, this.network);
            requestContext.execute("PF('consignmentsTable').clearFilters()");
        } catch (IOException st) {
            LOGGER.error("Socket time out exception, there were too many consingments probably..: " + st);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Communication Error", st.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            RequestContext.getCurrentInstance()
                    .execute("alert('Too many consignments, please try to use a shorter range..')");
            saveDateTo();
        } catch (Exception e) {
            this.reportLocalDateTo = datePickerSuccessTo;
            LOGGER.error(
                    "Request consignments summaries for the new dates (calendar event) failed to method onDateSelectTo",
                    e);
            saveDateTo();
        }
        LOGGER.info(" -- Data successfully retrieved for the new selected dates: (from) " + reportLocalDate + ", to: "
                + reportLocalDateTo);
        datePickerSuccessTo = reportLocalDateTo;
    }

    private void saveDateTo() {
        if (datePickerSuccessTo != null) {
            this.reportLocalDateTo = datePickerSuccessTo;
        } else {
            this.reportLocalDateTo = this.reportLocalDate;
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
        LOGGER.info("I am in the consignement refresh method");
        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("PF('consignmentsTable').clearFilters()"); // For now so I can see when refresh its
                                                                          // applied..
        LOGGER.info("Call parameters, dateFrom: " + reportLocalDate.format(FORMATTER) + ", client prefix: "
                + clientDetail.getPfx());
        try {
            consignments = consignmentListManager.getFilteredConsignmentSummaries(reportLocalDate.format(FORMATTER),
                    reportLocalDateTo.format(FORMATTER), clientDetail.getPfx(), false, false, this.network);
            retry = true;
            this.setDate(LocalDateTime.now().format(DATEFORMAT));
        } catch (Exception e) {
            if (retry) {
                LOGGER.info("Retrying call to the consignments list..");
                retry = false;
                this.refresh();
            }
            LOGGER.error(" -- Error calling getFilteredConsignmentSummaries in auto refresh ", e);
            retry = true;
        }

    }

    public Boolean getPollEnabled() {
        return pollEnabled;
    }

    public void setPollEnabled(Boolean pollEnabled) {
        this.pollEnabled = pollEnabled;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<SelectItem> getStatusSelectItems() {
        LOGGER.info(" --Inside getStatusSelectItems -- ");
        if (this.params.containsKey("initstatus")) {
            List<SelectItem> selItems = new ArrayList<SelectItem>();
            consignments.getStatusSelectItems().stream().forEach(c -> {
                if (!c.getLabel().equalsIgnoreCase(params.get("initstatus"))) {
                    selItems.add(c);
                }
            });
            return selItems;
        }
        return consignments.getStatusSelectItems();
    }

    public Date getReportDate() {
        return localToDate(reportLocalDate);
    }

    public String getReportDateString() {
        return reportLocalDate.format(FORMATTER);
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

    /**
     * @return info for user
     */
    public String getMessage() {
        if (!message.isEmpty()) {
            return message;
        } else {
            return "Info: Consignment data was " + String.valueOf(getConsignmentsAgeInMinutes())
                    + " minute(s) old when interrogated.";
        }
    }

    public List<ConsignmentSummary> getConsignments() {
        return consignments.getConsignmentSummaries();
    }

    public long getConsignmentsAgeInMinutes() {
        return consignments.getAgeInMinutes();
    }

    public ConsignmentListManager getConsignmentListManager() {
        return consignmentListManager;
    }

    public void setConsignmentListManager(ConsignmentListManager consignmentListManager) {
        this.consignmentListManager = consignmentListManager;
    }

    public void setSelectedConsignment(ConsignmentSummary selectedConsignment) {
        this.selectedConsignment = selectedConsignment;
    }

    public ConsignmentSummary getSelectedConsignment() {
        return selectedConsignment;
    }

    public List<ConsignmentSummary> getFilteredSnConsignments() {
        return filteredSnConsignments;
    }

    public void setFilteredSnConsignments(List<ConsignmentSummary> filteredSnConsignments) {
        this.filteredSnConsignments = filteredSnConsignments;
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
     * @param event
     *            called from screen when user clicks (picks up from selected consignment)
     */
    public void onRowSelect(SelectEvent event) {
        try {
            FacesContext.getCurrentInstance().getExternalContext()
                    .redirect("consignmentSnDetail.xhtml?network=" + this.network 
                    		+ "&con=" + selectedConsignment.getConsignmentReference()
                            + "&daterange=" + consignments.getDateRange() + "&client=" + client);
        } catch (IOException e) {
            LOGGER.error("An IO Exception happened when the row was selected", e);
        }
    }

}
