package com.ceva.cevatrack.technet.ws;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jws.WebService;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ceva.cevatrack.service.WebServiceTechNet;
//import com.ceva.cevatrack.util.SystemSessionConfig;
import com.ceva.cevatrack.webapp.ConsignmentListManager;
import com.ceva.cevatrack.webapp.DatedConsignmentList;
//import com.ceva.cevatrack.vo.TechNetClientVO;
import com.ceva.cevatrack.wsdl.types.findconsignmentsummaries.ConsignmentFilter;
import com.ceva.security.aaf.vo.AAFUserVO;

@WebService(endpointInterface = "com.ceva.cevatrack.service.WebServiceTechNet")
public class WebServiceTechNetImpl implements WebServiceTechNet {

    private static final long serialVersionUID = 2L;
    private static final Logger LOGGER = LoggerFactory.getLogger(WebServiceTechNetImpl.class);
    private static ConsignmentListManager clm = new ConsignmentListManager();
    // private List<TechNetClientVO> clientsDB;
    protected AAFUserVO aafUser;
    private static final DateTimeFormatter FORMATTERYYYYMMDD = DateTimeFormatter.BASIC_ISO_DATE;
    private static final DateTimeFormatter FORMATTERISO = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // ofPattern("yyyy-MM-dd
                                                                                                     // HH:mm:ss.SSS")
    private static final DateTimeFormatter FORMATTERINPUT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // It does not work
    // @Inject
    // private AAFUserService userService;
    // @Inject
    // private SystemSessionConfig sysConf;

    @Resource
    WebServiceContext wsctx;

    @Override
    public String healthTest() {
        return "The WS is healthy..";
    }

    @Override
    public com.ceva.cevatrack.wsmicrolise.findconsignmentsummaries.FindConsignmentSummariesResponse findConsignmentSummaries(
            ConsignmentFilter cf) throws Exception {
        LOGGER.info(" -- In the new method -- ");

        if (cf == null || cf.getEarliestDeliveryDate() == null || cf.getLatestDeliveryDate() == null) {
            throw new Exception(
                    "-- Parameters from the WS call are incorrect, please set Earliest and LatestDelivery date, example: 2019-05-15T00:00:00 and 2019-05-15T23:59:59");
        }

        // if (sysConf!=null){
        // LOGGER.info("sysconf: " + sysConf.toString());
        // }

        MessageContext mctx = wsctx.getMessageContext();
        Map http_headers = null;
        List userList = null, passList = null;
        if (mctx != null) {
            LOGGER.info("-- There are messages in the headers --");
            http_headers = (Map) mctx.get(MessageContext.HTTP_REQUEST_HEADERS);
            userList = (List) http_headers.get("Username");
            passList = (List) http_headers.get("Password");
        }

        if (userList != null || passList != null) {
            LOGGER.info(" WS headers parameters: " + userList.toString() + ", pass: " + passList.toString());
        } else {
            LOGGER.info(" -- Null parameters for user/pass on the headers ");
        }

        LocalDate fromlocaldate = null;
        LocalDate tolocaldate = null;
        // Trying to get an user from DB(LoginController put this user into session)
        // LoginBean lb = new LoginBean();
        // lb.setLoginId("techNetWS");
        // lb.setPassword("techNetWS");
        // lb.login();
        // try {
        // this.userService.login("techNetWS", "techNetWS");
        // SessionManager.setSession(new AAFUserSession());
        // LOGGER.debug("Login Successfull -> {}", "techNetWS");
        // this.aafUser = this.userService.getUser("techNetWS");
        // SessionManager.getSession().setAttribute("AAF_USER", this.aafUser);
        // } catch (AAFException var2) {
        // LOGGER.error(" -- Error doing Login " + var2);
        // }
        // I need an environment ---> it comes from the user configuration..

        LOGGER.info("-- Input dates, from: " + cf.getEarliestDeliveryDate() + ", To: " + cf.getLatestDeliveryDate());

        try {
            fromlocaldate = checkyyyymmdd(cf.getEarliestDeliveryDate().toString().replace("T", " ").trim());
            tolocaldate = checkyyyymmdd(cf.getLatestDeliveryDate().toString().replace("T", " ").trim());
        } catch (Exception e) {
            LOGGER.error("Abend occurred - invalid date: ,from: " + cf.getEarliestDeliveryDate() + ", to: "
                    + cf.getLatestDeliveryDate().toString(), e);
        }

        // ConsignmentFilter cfAux = null;
        // cfAux = convertDates(cf);
        DatedConsignmentList wsConsignments = null;
        com.ceva.cevatrack.wsmicrolise.findconsignmentsummaries.FindConsignmentSummariesResponse response = new com.ceva.cevatrack.wsmicrolise.findconsignmentsummaries.FindConsignmentSummariesResponse();
        try {
            wsConsignments = clm.getConsignmentSummaries(fromlocaldate.format(FORMATTERYYYYMMDD),
                    tolocaldate.format(FORMATTERYYYYMMDD), true, true, "", null);
        } catch (Exception e) {
            LOGGER.error("-- There was an Exception retrieving consignment summaries --", e);
        }
        response.setFindConsignmentSummariesResult(
                (ArrayList<com.ceva.cevatrack.wsmicrolise.findconsignmentsummaries.ConsignmentSummary>) wsConsignments
                        .getConsignmentSummaries());
        return response;
    }

    /**
     * @param input
     *            is the date to check
     * @return is the date it means
     * @throws Exception
     *             if the date is not right
     */
    public static LocalDate checkyyyymmdd(String input) throws Exception {
        if (input != null) {
            try {
                LOGGER.debug("-- The input string is: " + input);
                LocalDate ret = LocalDate.parse(input, FORMATTERINPUT);
                return ret;
            } catch (Exception e) {
                LOGGER.error("-- The input string: " + input + " ,and the formatter: " + FORMATTERINPUT);
                throw new Exception("Invalid Date check exception", e);
            }
        }
        throw new Exception("Date check exception");
    }

    private ConsignmentFilter convertDates(ConsignmentFilter cf) {
        XMLGregorianCalendar startDate = null, endDate = null;
        LOGGER.info("-- startDeliveryDate: " + cf.getEarliestDeliveryDate());
        LOGGER.info("-- endDeliveryDate: " + cf.getLatestDeliveryDate());
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            LOGGER.info(" -- String to parse: " + cf.getEarliestDeliveryDate().toString().replace("T", " ").trim());
            Date locDate = format.parse(cf.getEarliestDeliveryDate().toString().replace("T", " ").trim());
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(locDate);
            startDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
            LOGGER.info("-- startDate: " + startDate);

            locDate = (Date) format.parse(cf.getLatestDeliveryDate().toString().replace("T", " ").trim());
            calendar.setTime(locDate);
            endDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
            LOGGER.info("-- endDate: " + endDate);
        } catch (DatatypeConfigurationException e) {
            LOGGER.error("Problem setting up the consingments date: " + e);
        } catch (ParseException e) {
            LOGGER.error(" -- Parse Exception: " + e);
        }
        cf.setEarliestDeliveryDate(startDate);
        cf.setLatestDeliveryDate(endDate);
        return cf;
    }

}
