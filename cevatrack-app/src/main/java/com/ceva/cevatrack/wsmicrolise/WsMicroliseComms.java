package com.ceva.cevatrack.wsmicrolise;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.sax.SAXSource;

import javax.xml.bind.JAXBElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.ceva.cevatrack.wsdl.service.MicroliseSmartNetSoap;
import com.ceva.cevatrack.wsdl.service.MicroliseSmartNetV1;
//import com.ceva.cevatrack.wsdl.service.MicroliseSmartNet_V1;
import com.ceva.cevatrack.wsdl.types.findconsignmentsummaries.ConsignmentFilter;
//import com.ceva.cevatrack.wsdl.types.findconsignmentsummaries.ConsignmentSummary;
import com.ceva.cevatrack.wsdl.types.findconsignmentsummaries.FindConsignmentSummaries;
import com.ceva.cevatrack.wsdl.types.findconsignmentsummaries.FindConsignmentSummariesResponse;
import com.ceva.cevatrack.wsdl.types.getconsignment.Vehicle;
import com.ceva.cevatrack.wsmicrolise.findconsignmentsummaries.ConsignmentSummary;
import com.ceva.cevatrack.wsmicrolise.findconsignmentsummaries.FindConsignmentSummariesSoapEnvelope;
import com.ceva.cevatrack.wsmicrolise.getconsignment.Consignment;
import com.ceva.cevatrack.wsmicrolise.getconsignment.GetConsignmentSoapEnvelope;

import javax.xml.bind.JAXBElement;


/**
 * @author brian.goodall
 *
 */
public class WsMicroliseComms {

    private static final Logger LOGGER = LoggerFactory.getLogger(WsMicroliseComms.class);
    // private static final java.text.SimpleDateFormat SDF = new java.text.SimpleDateFormat("yyyyMMdd");
    private static final DateTimeFormatter FORMATTERISO = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter FORMATTERALL = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");// 2018-10-31
                                                                                                             // 23:59:59
    private static final DateTimeFormatter FORMATTERYYYYMMDD = DateTimeFormatter.BASIC_ISO_DATE;
    private static final String WSURL = "https://ws6.microlise.com/CEVA_CRM_WEBSERVICE/TMCWebService.asmx";
    private static final String WSURLUAT = "https://ws6.microlise.com/CEVA_CRM_UAT_WEBSERVICE/TMCWebService.asmx";
    private static final String SOAPSTART = "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
            + "xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">"
            + "<soap:Body>";
    private static final String SOAPEND = "</soap:Body></soap:Envelope>";
    private static final String HEADERSOAPACTIONSTART = "http://microlise.com/TMC/";
    private static final String BASIC = "Basic ";
    private static final String GETCONSIGNMENT = "<GetConsignment xmlns=\"http://microlise.com/TMC\"><ConsignmentRef>"
            + "<![CDATA[???????]]></ConsignmentRef></GetConsignment>";
    private static final String FINDCONSIGNMENTSUMMARIESSTART = "<FindConsignmentSummaries xmlns=\"http://microlise.com/TMC\">"
            + "<consignmentFilter xmlns=\"http://microlise.com/TMC\">";
    private static final String FILTEREARLIEST = "<EarliestDeliveryDate xmlns=\"http://microlise.com/webservices/TMC/\">"
            + "<![CDATA[???????]]></EarliestDeliveryDate>";
    private static final String FILTERLATEST = "<LatestDeliveryDate xmlns=\"http://microlise.com/webservices/TMC/\">"
            + "<![CDATA[???????]]></LatestDeliveryDate>";
    private static final String FINDCONSIGNMENTSUMMARIESEND = "</consignmentFilter></FindConsignmentSummaries>";

    /**
     * @param consignmentRef
     *            is the look-up reference
     * @return is the Consignment object derived from the xml response
     * @throws Exception
     *             for any errors found
     */
    public static Consignment getConsignment(String consignmentRef, String network) throws Exception {
        LOGGER.info("WsMicroliseComms - getConsignment-starting");
        WsMicroliseEnvironment env = new WsMicroliseEnvironment(network);
        // String requestBody = GETCONSIGNMENT.replace("???????", consignmentRef);
        String requestBody = env.getGetConsignmentBody(consignmentRef);
        String soapAction = env.getGetConsignmentSoapAction();
        LOGGER.info(" -- soapAction: " + soapAction + ", requestBody: " + requestBody);
        GetConsignmentSoapEnvelope wsresp = null;
        try {
            wsresp = getWsObject(soapAction, requestBody, env, GetConsignmentSoapEnvelope.class);
        } catch (IOException el) {
            // el.printStackTrace();
            if ("5".equals(el.getMessage().substring(0, 1))) {
                LOGGER.info("WsMicroliseComms - getConsignment-retrying on " + el.getMessage() + " response");
                try {
                    wsresp = getWsObject(soapAction, requestBody, env, GetConsignmentSoapEnvelope.class);
                } catch (Exception e2) {
                    // e2.printStackTrace();
                    throw new Exception("abend escalated on retry of getConsignment", e2);
                }
                return wsresp.getGetConsignmentSoapBody().getGetConsignmentResponse().getGetConsignmentResult();
            }
        } catch (Exception e) {
            // e.printStackTrace();
            throw new Exception("abend escalated on getConsignment", e);
        }
        LOGGER.info("wsAutomotive - Received:" + wsresp.toString());
        return wsresp.getGetConsignmentSoapBody().getGetConsignmentResponse().getGetConsignmentResult();
    }

    /**
     * @param fromdate
     *            is the from date for extract yyyymmdd
     * @param todate
     *            is the to date for extract yyyymmdd
     * @return is the list of consignments
     * @throws Exception
     *             if unable to access for any reason or duff date
     */
    public static List<ConsignmentSummary> findConsignmentSummariesForDateRange(String fromdate, String todate,
            Boolean WS, String network) throws Exception {
        LocalDate fromlocaldate;
        LocalDate tolocaldate;
        try {
            fromlocaldate = checkyyyymmdd(fromdate);
        } catch (Exception e) {
            throw new Exception("Abend occurred - invalid date:" + fromdate, e);
        }

        if (fromlocaldate == null) {
            throw new DataFormatException("Abend occurred - invalid 'from date' specified as filter: " + fromdate);
        }

        if (!fromdate.equals(todate)) {
            try {
                tolocaldate = checkyyyymmdd(todate);
            } catch (Exception e) {
                throw new Exception("Abend occurred - invalid date:" + todate, e);
            }
            if (tolocaldate == null) {
                throw new DataFormatException("Abend occurred - invalid 'to date' specified as filter");
            }
        } else {
            tolocaldate = fromlocaldate;
        }

        // String[] filters = new String[2];
        // filters[0] = FILTEREARLIEST.replace("???????", fromlocaldate.format(FORMATTERISO) + "T00:00:00");
        // filters[1] = FILTERLATEST.replace("???????", tolocaldate.format(FORMATTERISO) + "T23:59:59");
        List<ConsignmentSummary> resp;
        try {
            resp = findConsignmentSummaries(fromlocaldate.format(FORMATTERISO) + "T00:00:00",
                    tolocaldate.format(FORMATTERISO) + "T23:59:59", WS, network);
        } catch (IOException e1) {
            throw new IOException("IOException abend escalated in FindConsignmentSummariesForDateRange", e1);
        } catch (Exception e) {// Aqui IO
            throw new Exception("Abend escalated in FindConsignmentSummariesForDateRange", e);
        }
        return resp;
    }

    public static Consignment getConsignment_wsdl(String consignmentRef) throws Exception {
        LOGGER.info("method getConsignment, consignmentRef: " + consignmentRef);
        MicroliseSmartNetSoap webservice = null;
        com.ceva.cevatrack.wsdl.types.getconsignment.Consignment resp = null;
        Consignment data = null;
        try {
            webservice = new MicroliseSmartNetV1().getMicroliseSmartNet();
            resp = webservice.getConsignment(consignmentRef);
        } catch (Exception e) {
            LOGGER.error(" -- Thre are problems with the WS response --", e);
            throw new Exception(e);
        }
        if (resp != null) {
            try {
                data = new Consignment();
                data.setActualStartTime(
                        (resp.getActualStartTime() == null) ? "" : resp.getActualStartTime().toString());
                data.setAddress1(resp.getAddress1());
                data.setAddress2(resp.getAddress2());
                data.setAddress3(resp.getAddress3());
                data.setAta((resp.getATA() == null) ? "" : resp.getATA().toString());
                data.setAtd((resp.getATD() == null) ? "" : resp.getATD().toString());
                data.setClauseCode(resp.getClauseCode());
                data.setConsignmentCustomerOrderRef(resp.getConsignmentCustomerOrderRef());
                // data.setConsignmentDetails(resp.getConsignmentDetails()); // traverse the array and set the values
                data.setConsignmentHeaderId(resp.getConsignmentHeaderID());
                data.setConsignmentHeaderInfo1(resp.getConsignmentHeaderInfo1());
                data.setConsignmentHeaderInfo2(resp.getConsignmentHeaderInfo2());
                data.setConsignmentHeaderInfo3(resp.getConsignmentHeaderInfo3());
                data.setConsignmentHeaderInfo4(resp.getConsignmentHeaderInfo4());
                data.setConsignmentHeaderInfo5(resp.getConsignmentHeaderInfo5());
                data.setConsignmentHeaderInfo6(resp.getConsignmentHeaderInfo6());
                data.setConsignmentHeaderInfo7(resp.getConsignmentHeaderInfo7());
                data.setConsignmentHeaderInfo8(resp.getConsignmentHeaderInfo8());
                data.setConsignmentHeaderInfo9(resp.getConsignmentHeaderInfo9());
                data.setConsignmentHeaderInfo10(resp.getConsignmentHeaderInfo10());
                data.setConsignmentHeaderInfo11(resp.getConsignmentHeaderInfo11());
                data.setConsignmentHeaderInfo12(resp.getConsignmentHeaderInfo12());
                data.setConsignmentStatus(resp.getConsignmentStatus());
                data.setContactName(resp.getContactName());
                data.setContactNumber(resp.getContactNumber());
                data.setCountry(resp.getCountry());
                data.setCounty(resp.getCounty());
                // data.setDrivers(value);
                data.setDurationOnSite(resp.getDurationOnSite());
                data.setEta((resp.getETA() == null) ? "" : resp.getETA().toString());
                data.setEtd((resp.getETD() == null) ? "" : resp.getETD().toString());
                data.setIsAdHoc(resp.getIsAdHoc());
                data.setIsTranshipment(resp.getIsTranshipment());
                data.setJourneyActualStartTime(
                        (resp.getJourneyActualStartTime() == null) ? "" : resp.getJourneyActualStartTime().toString());
                data.setJourneyAlias(resp.getJourneyAlias());
                data.setJourneyDropId(resp.getJourneyDropID());
                data.setJourneyId(resp.getJourneyID());
                data.setLabel1(resp.getLabel1());
                data.setLabel2(resp.getLabel2());
                data.setLabel3(resp.getLabel3());
                data.setLabel4(resp.getLabel4());
                data.setParentCode(resp.getParentCode());
                data.setPlannedStartTime(
                        (resp.getPlannedStartTime() == null) ? "" : resp.getPlannedStartTime().toString());
                data.setPodStartTime((resp.getPODStartTime() == null) ? "" : resp.getPODStartTime().toString());
                data.setPostCode(resp.getPostCode());
                data.setPtd((resp.getPTD() == null) ? "" : resp.getPTD().toString());
                data.setRouteId(resp.getRouteID());
                data.setSignatory(resp.getSignatory());
                data.setSignature((resp.getSignature() == null) ? "" : resp.getSignature().toString());
                data.setSiteGeoRadius(resp.getSiteGeoRadius());
                data.setSiteId(resp.getSiteID());
                data.setSiteLat(resp.getSiteLat());
                data.setSiteLong(resp.getSiteLong());
                data.setSiteName(resp.getSiteName());
                data.setTimeWindowEnd((resp.getTimeWindowEnd() == null) ? "" : resp.getTimeWindowEnd().toString());
                data.setTimeWindowStart(
                        (resp.getTimeWindowStart() == null) ? "" : resp.getTimeWindowStart().toString());
                // data.setVehicles(resp.getVehicles());
                if (resp.getVehicles() != null && resp.getVehicles().getVehicle() != null) {
                    ArrayList<com.ceva.cevatrack.wsmicrolise.getconsignment.Vehicle> vehiclesWsdl = transformVehicule(
                            resp.getVehicles().getVehicle());
                    data.setVehicles(vehiclesWsdl);
                }
                data.setPta(resp.getPTA().toString());
            } catch (Exception e) {
                LOGGER.error(" -- Problems with data parsign in getConsignment --", e);
                throw new Exception(e);
            }
        }
        return data;
    }

    private static ArrayList<com.ceva.cevatrack.wsmicrolise.getconsignment.Vehicle> transformVehicule(
            List<Vehicle> vehicle) {
        ArrayList<com.ceva.cevatrack.wsmicrolise.getconsignment.Vehicle> vehicles = new ArrayList<>();
        for (Vehicle aux : vehicle) {
            if (aux != null) {
                com.ceva.cevatrack.wsmicrolise.getconsignment.Vehicle vehicleNew = new com.ceva.cevatrack.wsmicrolise.getconsignment.Vehicle();
                vehicleNew.setDisplayName(aux.getDisplayName());
                vehicleNew.setJourneyId(aux.getJourneyID());
                vehicleNew.setLastContactLat(aux.getLastContactLat());
                vehicleNew.setLastContactLong(aux.getLastContactLong());
                vehicleNew.setLastLocation(aux.getLastLocation());
                vehicles.add(vehicleNew);
            }
        }
        return vehicles;
    }

    private static List<ConsignmentSummary> findConsignmentSummaries_withWSDLs(String fromDate, String toDate)
            throws Exception {
        List<com.ceva.cevatrack.wsdl.types.findconsignmentsummaries.ConsignmentSummary> resp = wsClientWsdl(fromDate,
                toDate).getFindConsignmentSummariesResult().getConsignmentSummary();
        LOGGER.info("---------- Information retrieved: " + resp.size());
        List<ConsignmentSummary> transformResp = new ArrayList<>();
        Iterator<com.ceva.cevatrack.wsdl.types.findconsignmentsummaries.ConsignmentSummary> it = resp.iterator();
        while (it.hasNext()) {
            com.ceva.cevatrack.wsdl.types.findconsignmentsummaries.ConsignmentSummary data = it.next();
            if (data != null) {
                ConsignmentSummary consSum = new ConsignmentSummary();
                consSum.setAddress1(data.getAddress1());
                consSum.setAddress2(data.getAddress2());
                consSum.setAddress3(data.getAddress3());
                consSum.setAta((data.getATA() == null) ? "" : data.getATA().toString());
                consSum.setConsignmentHeaderId(data.getConsignmentHeaderID());
                consSum.setConsignmentReference(data.getConsignmentReference());
                consSum.setConsignmentStatus(data.getConsignmentStatus());
                consSum.setContactName(data.getContactName());
                consSum.setContactNumber(data.getContactName());
                consSum.setCountry(data.getCountry());
                consSum.setCounty(data.getCounty());
//                consSum.setOrderReferences((data.getOrderReferences() == null) ? null
//                        : (ArrayList<String>) data.getOrderReferences().getString());
                consSum.setPostCode(data.getPostCode());
                consSum.setPta((data.getPTA() == null) ? "" : data.getPTA().toString());
                consSum.setPta2((data.getPTA2() == null) ? "" : data.getPTA2().toString());
                consSum.setSiteId(data.getSiteID());
                consSum.setSiteName(data.getSiteName());
                transformResp.add(consSum);
            }
        }
        return transformResp;
    }

    private static List<ConsignmentSummary> findConsignmentSummaries(String fromDate, String toDate, Boolean WS, String network)
            throws Exception {
        LOGGER.info("WsMicroliseComms - findConsignentSummaries-starting");

        WsMicroliseEnvironment env;
        if (!WS) {
            env = new WsMicroliseEnvironment(network);
        } else {
            env = new WsMicroliseEnvironment(true);
        }
        // String requestBody = GETCONSIGNMENT.replace("???????", consignmentRef);
        String requestBody = env.getFindConsignmentSummariesBody(fromDate, toDate);
        String soapAction = env.getFindConsignmentSummariesSoapAction();

        // StringBuilder requestBody = new StringBuilder(FINDCONSIGNMENTSUMMARIESSTART);
        // for (String f : filters) {
        // requestBody.append(f);
        // }
        // requestBody.append(FINDCONSIGNMENTSUMMARIESEND);
        FindConsignmentSummariesSoapEnvelope wsresp = null;
        try {
            wsresp = getWsObject(soapAction, requestBody, env, FindConsignmentSummariesSoapEnvelope.class);
        } catch (IOException el) {
            if ("5".equals(el.getMessage().substring(0, 1))) {
                LOGGER.warn("WsMicroliseComms - FindConsignmentSummaries-retrying on " + el.getMessage() + " response");
                try {
                    wsresp = getWsObject(soapAction, requestBody, env, FindConsignmentSummariesSoapEnvelope.class);
                } catch (Exception e2) {
                    // e2.printStackTrace();
                    if (IOException.class == e2.getClass()) {
                        throw new IOException("IOException escalation from FindConsignmentSummaries ", e2);
                    }
                    throw new Exception("abend escalated on retry of FindConsignmentSummaries", e2);
                }
                return wsresp.getFindConsignmentSummariesSoapBody().getFindConsignmentSummariesResponse()
                        .getFindConsignmentSummariesResult();
            }
            throw new Exception("Abend escalated in FindConsignmentSummaries", el);
        } catch (Exception e) {
            // e.printStackTrace();
            throw new Exception("Abend escalated in FindConsignmentSummaries", e);
        }
        return wsresp.getFindConsignmentSummariesSoapBody().getFindConsignmentSummariesResponse()
                .getFindConsignmentSummariesResult();
    }

    // ===============================================================================

    private static <T> T getWsObject(String soapAction, String requestBody, WsMicroliseEnvironment env,
            Class<T> jaxbClass) throws Exception {
        LOGGER.info("WsMicroliseComms - getWsObject-starting");
        // byte[] soapBody = (SOAPSTART + requestBody + SOAPEND).getBytes();
        byte[] soapBody = requestBody.getBytes();

        // String soapAction = HEADERSOAPACTIONSTART + call;
        HttpURLConnection httpsUrlConnection = null;
        OutputStream out = null; // This is for the request body
       

        try {
            httpsUrlConnection = setUpHttpsConnection(httpsUrlConnection, env, soapAction);
            // post the XML message
            out = httpsUrlConnection.getOutputStream();
            LOGGER.info("WsMicroliseComms - sending request");
            out.write(soapBody);
            out.flush();

            LOGGER.info("WsMicroliseComms - httpsUrlConnection.getContentEncoding(): "
                    + httpsUrlConnection.getContentEncoding());
            // read the response
            int respcode = httpsUrlConnection.getResponseCode();
            LOGGER.info("Response code for the WS request: " + respcode);
            switch (respcode) {
            case HttpURLConnection.HTTP_OK:
                break; // fine, go on
            /*
             * case httpsUrlConnection.HTTP_GATEWAY_TIMEOUT: throw new Exception(httpsUrlConnection.getResponseCode() +
             * " Remote server communication is timing out. Please try again later."); case
             * httpsUrlConnection.HTTP_UNAVAILABLE: throw new Exception(httpsUrlConnection.getResponseCode() +
             * " Remote server communication is unavailable. Please try again later.");
             */
            default:
                LOGGER.error("WsMicroliseComms - Http unexpected response: " + respcode + ", launching IOException..");
                throw new IOException(String.valueOf(respcode));
            }

            return fromXMLtoObject(httpsUrlConnection, jaxbClass);
            //return xmlToObject(httpsUrlConnection, jaxbClass, soapBody);

        } catch (SocketTimeoutException e1) {
            LOGGER.info("WsMicroliseComms - Socket Timeout! - throwing dummy 504 exception, " +e1.getMessage() );
            throw new IOException("504", e1);
        } catch (Exception e2) {
            LOGGER.error("WsMicroliseComms - Exception: " + e2.getMessage());
            throw new Exception("General exeception, caught during comms with Microlise WebServices", e2);
        } finally {
            LOGGER.info(" -- Finally block reached, cleaning resources..");
            if (out != null) {
                out = null;
            }            
            httpsUrlConnection = cleanConnection(httpsUrlConnection);
        }
    }
    
     private static <T> T fromXMLtoObject(HttpURLConnection httpsUrlConnection, Class<T> jaxbClass) throws JAXBException, Exception{    	 
    	 Unmarshaller u = null;   	 
         BufferedReader in = null;
         OutputStream out = null; // This is for the request body
         JAXBContext context;
    	 XMLReader reader = XMLReaderFactory.createXMLReader();
        InputSource is;
        try {
            context = JAXBContext.newInstance(jaxbClass);
        } catch (JAXBException e1) {
            throw new Exception("JAXBException abend occurred while creating JAXB Context", e1);
        }
        NamespaceFilter nsf = new NamespaceFilter(null, false); // this will remove all namespaces
        try {
            u = context.createUnmarshaller();
        } catch (JAXBException e1) {
            throw new Exception("JAXBException abend occurred while creating JAXB Unmarshaller", e1);
        }
        try {
            u.setEventHandler(new javax.xml.bind.helpers.DefaultValidationEventHandler());
        } catch (JAXBException e1) {
            throw new Exception("JAXBException abend occurred while creating JAXB Event Handler", e1);
        }
        if ("gzip".equals(httpsUrlConnection.getContentEncoding())) {
            is = new InputSource(new GZIPInputStream(httpsUrlConnection.getInputStream()));
        } else {
            is = new InputSource(httpsUrlConnection.getInputStream());
        }
        
//        BufferedReader br = new BufferedReader(new InputStreamReader(readerhttp.getInputStream()));
//        String s;
//        LOGGER.info(" ================= Response from WS ===================");
//        while ((s=br.readLine())!=null)
//        {
//               //LOGGER.info(s + "\n");
//        	LOGGER.info(s);
//        }
        
        
        nsf.setParent(reader); // load a reader to the filter

        // Create a SAXSource specifying the filter
        SAXSource source = new SAXSource(nsf, is);
        
        Object o = u.unmarshal(source); // Exception here: unexpected element (uri:"", local:"string"). Expected
                                        // elements are (none)
        LOGGER.info("WsMicroliseComms - object has been unmarshalled");
        return jaxbClass.cast(o);
    }
     
     public static <T> T xmlToObject(HttpURLConnection httpsUrlConnection, Class<T> jaxbClass, byte[] soapBody) throws XMLStreamException, JAXBException, IOException{
    	 LOGGER.info("WsMicroliseComms - xmlToObject method httpsUrlConnection.getContentEncoding(): "
                 + httpsUrlConnection.getContentEncoding());
    	 String string = new String(soapBody, StandardCharsets.UTF_8);
    	 LOGGER.info(" --response string from the outputstream: " + string);
         InputStream stream = new ByteArrayInputStream(string.getBytes("UTF-8"));
         JAXBContext jaxbContext2 = JAXBContext.newInstance(jaxbClass);
         Unmarshaller jaxbUnmarshaller2 = jaxbContext2.createUnmarshaller();
         XMLInputFactory factory = XMLInputFactory.newInstance();
         XMLEventReader someSource = factory.createXMLEventReader(stream);
         JAXBElement<T> userElement =  jaxbUnmarshaller2.unmarshal(someSource, jaxbClass);
         T responseObject = userElement.getValue();
         return responseObject;
     }
     
    private static HttpURLConnection cleanConnection(HttpURLConnection httpsUrlConnection) {
        if (httpsUrlConnection != null) {
            try {
                LOGGER.info("httpUrlConneciton is not null, closing it..");
                httpsUrlConnection.getInputStream().close();
            } catch (Exception e) {
                LOGGER.error(
                        " -- Unable to close httpsUrlConnection object.. " + httpsUrlConnection.getRequestMethod());
            }
            httpsUrlConnection.disconnect();
            httpsUrlConnection = null;
            LOGGER.info(" -- Finally block ended, resources successfully cleared");
        }
        return httpsUrlConnection;
    }

    private static HttpURLConnection setUpHttpsConnection(HttpURLConnection httpsUrlConnection,
            WsMicroliseEnvironment env, String soapAction) throws IOException, Exception {

        URL url;
        Boolean https = false;
        try {
            LOGGER.info("-- I am opening the URL: " + env.getUrl().trim());
            url = new URL(env.getUrl().trim());
        } catch (MalformedURLException e1) {
            throw new Exception("MalformedURLException abend occurred in getWsObject", e1);
        }

        // Set up the connection object (casting exception http)
        if (env.hasProxy()) {
            httpsUrlConnection = (HttpURLConnection) url.openConnection(env.getProxy());
            LOGGER.info("I am using a proxy for the connection: " + env.getProxy());
        } else {
            httpsUrlConnection = (HttpURLConnection) url.openConnection();
            LOGGER.info("I am NOT using a proxy for the connection: " + env.getProxy());
        }
        // Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.fifthlayer.net", 3128));
        // httpsUrlConnection = (HttpsURLConnection) url.openConnection(proxy);
        httpsUrlConnection.setRequestMethod("POST");
        httpsUrlConnection.setUseCaches(false);

        httpsUrlConnection.setReadTimeout(17 * 1000);
        httpsUrlConnection.setConnectTimeout(10 * 1000);

        httpsUrlConnection.setRequestProperty("Accept-Encoding", "gzip,deflate");
        httpsUrlConnection.setRequestProperty("Content-Type", "text/xml");
        httpsUrlConnection.setRequestProperty("SOAPAction", soapAction);
        if (env.authorized) {
            httpsUrlConnection.setRequestProperty("Authorization", getAuthorization(env));
        }
        // httpsUrlConnection.setRequestProperty("Connection", "Keep-Alive"); Reset connections

        // httpsUrlConnection.setDoInput(true);
        httpsUrlConnection.setDoOutput(true);

        // httpsUrlConnection.connect();
        return httpsUrlConnection;

    }
    // =============================================================================

    private static <T> T getWsObject_(String soapAction, String requestBody, WsMicroliseEnvironment env,
            Class<T> jaxbClass) throws Exception {
        LOGGER.info("WsMicroliseComms - getWsObject-starting");
        // byte[] soapBody = (SOAPSTART + requestBody + SOAPEND).getBytes();
        byte[] soapBody = requestBody.getBytes();

        URL url;
        try {
            // url = new URL(WSURL);
            // url = new URL(env.getUrl());
            // Pay atention to https vs http same for HttpURLConnection
            // url = new URL("http://gdcb2bprd02:8080/MicroliseSmartNet"); //http://gdcesbqa:8080/MicroliseSmartNet
            // url = new URL("http://gdcesbqa.logistics.corp:8080/MicroliseSmartNet");
            // //http://gdcesbqa:8080/MicroliseSmartNet
            url = new URL("http://gdcesbqa:8080/MicroliseSmartNet");
        } catch (MalformedURLException e1) {
            try {
                url = new URL(
                        "file:/C:/Users/canoad/workspace/ClientWSDL/src/com/ceva/cevatrack/wsdl/microlisesmartnet_v1.wsdl");
            } catch (MalformedURLException e2) {
                throw new Exception("MalformedURLException abend occurred in FindConsignmentSummaries", e2);
            }
        }

        // String soapAction = HEADERSOAPACTIONSTART + call;
        HttpURLConnection httpUrlConnection = null;
        BufferedReader in = null;
        OutputStream out = null; // This is for the request body
        JAXBContext context;
        try {
            context = JAXBContext.newInstance(jaxbClass);
        } catch (JAXBException e1) {
            throw new Exception("JAXBException abend occurred while creating JAXB Context", e1);
        }
        NamespaceFilter nsf = new NamespaceFilter(null, false); // this will remove all namespaces
        Unmarshaller u = null;
        try {
            u = context.createUnmarshaller();
        } catch (JAXBException e1) {
            throw new Exception("JAXBException abend occurred while creating JAXB Unmarshaller", e1);
        }
        try {
            u.setEventHandler(new javax.xml.bind.helpers.DefaultValidationEventHandler());
        } catch (JAXBException e1) {
            throw new Exception("JAXBException abend occurred while creating JAXB Event Handler", e1);
        }

        try {
            // Set up the connection object
            if (false) { // env.hasProxy()) {
                httpUrlConnection = (HttpsURLConnection) url.openConnection(env.getProxy());
            } else {
                httpUrlConnection = (HttpURLConnection) url.openConnection();
            }
            // Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("localproxy.es", 3128));
            // httpUrlConnection = (HttpsURLConnection) url.openConnection(proxy);
            httpUrlConnection.setRequestMethod("POST");
            httpUrlConnection.setUseCaches(false);

            httpUrlConnection.setReadTimeout(15 * 1000);
            httpUrlConnection.setConnectTimeout(10 * 1000);

            httpUrlConnection.setRequestProperty("Accept-Encoding", "gzip,deflate");
            httpUrlConnection.setRequestProperty("Content-Type", "text/xml");

            // httpUrlConnection.setRequestProperty("SOAPAction",soapAction);
            httpUrlConnection.setRequestProperty("SOAPAction", "http://gdcb2bprd02:8080/FindConsignmentSummaries");// 404
            if (false) { // env.authorized) {
                httpUrlConnection.setRequestProperty("Authorization", getAuthorization(env));
            }
            httpUrlConnection.setRequestProperty("Connection", "Keep-Alive");

            // For Local env
            // LOGGER.info("Before --- System proxy user: " + System.getProperty("http.proxyUser"));
            // LOGGER.info("Before --- System proxy pass: " + System.getProperty("http.proxyPassword"));
            // LOGGER.info("Before proxy host: " + System.getProperty("http.proxyHost"));
            // LOGGER.info("Before proxy port: " + System.getProperty("http.proxyPort"));

            // System.setProperty("http.proxyUser", "canoad");
            // System.setProperty("http.proxyPassword", "Holahola11");
            // System.setProperty("http.proxyHost", "localproxy.es");
            // System.setProperty("http.proxyPort", "3128");

            LOGGER.info("After -- System proxy user: " + System.getProperty("http.proxyUser"));
            LOGGER.info("After -- System proxy pass: " + System.getProperty("http.proxyPassword"));
            LOGGER.info("After proxy host: " + System.getProperty("http.proxyHost"));
            LOGGER.info("After proxy port: " + System.getProperty("http.proxyPort"));

            // httpUrlConnection.setDoInput(true);
            httpUrlConnection.setDoOutput(true);

            // httpUrlConnection.connect();
            // post the XML message
            out = httpUrlConnection.getOutputStream();
            LOGGER.info("WsMicroliseComms - sending request");
            out.write(soapBody);// soapBody is correct!
            out.flush();
            // read the response
            LOGGER.info("WsMicroliseComms - response received:" + httpUrlConnection.getContentEncoding());
            // LOGGER.info(IOUtils.toString(httpUrlConnection.getInputStream()));
            int respcode = httpUrlConnection.getResponseCode();
            switch (respcode) {
            case HttpURLConnection.HTTP_OK:
                break; // fine, go on
            /*
             * case HttpURLConnection.HTTP_GATEWAY_TIMEOUT: throw new Exception(httpUrlConnection.getResponseCode() +
             * " Remote server communication is timing out. Please try again later."); case
             * HttpURLConnection.HTTP_UNAVAILABLE: throw new Exception(httpUrlConnection.getResponseCode() +
             * " Remote server communication is unavailable. Please try again later.");
             */
            default:
                LOGGER.info("WsMicroliseComms - Http unexpected response: " + respcode);
                throw new IOException(String.valueOf(respcode));
            }

            XMLReader reader = XMLReaderFactory.createXMLReader();
            InputSource is;
            if ("gzip".equals(httpUrlConnection.getContentEncoding())) {
                is = new InputSource(new GZIPInputStream(httpUrlConnection.getInputStream()));
            } else {
                is = new InputSource(httpUrlConnection.getInputStream());
            }
            nsf.setParent(reader); // load a reader to the filter

            // Create a SAXSource specifying the filter
            SAXSource source = new SAXSource(nsf, is);

            Object o = u.unmarshal(source);
            LOGGER.info("WsMicroliseComms - object has been unmarshalled");
            return jaxbClass.cast(o);
            // in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            // String inputLine;

            // while ((inputLine = in.readLine()) != null) {
            // response.append(inputLine);
            // }
            // in.close();

        } catch (SocketTimeoutException e1) {
            LOGGER.info("WsMicroliseComms - Socket Timeout! - throwing dummy 504 exception");
            throw new IOException("504", e1);
        } catch (Exception e2) {
            LOGGER.info("WsMicroliseComms - Exception: " + e2.getMessage());
            throw new Exception("Abend caught during comms with Microlise WebServices", e2);
        } finally {
            // if (out != null) {
            // out = null;
            // }
            if (in != null) {
                in = null;
            }
            if (httpUrlConnection != null) {
                try {
                    httpUrlConnection.getInputStream().close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                httpUrlConnection.disconnect();
                httpUrlConnection = null;
            }
        }
    }

    private static String getAuthorization(WsMicroliseEnvironment env) throws Exception {
        try {
            // userpass = username + ":" + password
            String microliseUser = env.getAuthorization(); // null
            LOGGER.debug("user: " + env.getAuthorization());
            String encoding = Base64.getEncoder().encodeToString(microliseUser.getBytes()); // nullPointer
            return BASIC + encoding;
        } catch (Exception e) {
            throw new Exception(
                    "-- (getAuthorization) Abend while trying to access microlise.usernamepassword from context store",
                    e);
        }
    }

    private static FindConsignmentSummariesResponse wsClientWsdl(String startDeliveryDate, String endDeliveryDate)
            throws DatatypeConfigurationException {
        MicroliseSmartNetSoap webservice = new MicroliseSmartNetV1().getMicroliseSmartNet();
        FindConsignmentSummaries consSummaries = new FindConsignmentSummaries();
        ConsignmentFilter consFilter = new ConsignmentFilter();
        XMLGregorianCalendar startDate = null, endDate = null;
        LocalDate locStartDeliveryDate = null, locEndDesliveryDate = null;
        LOGGER.info("-- startDeliveryDate: " + startDeliveryDate);
        LOGGER.info("-- endDeliveryDate: " + endDeliveryDate);
        // NOT NEEDED IT WAS ALREADY DONE, THE SEARCH STRING COMES ALREADY FORMATTED ..
        // try {
        // LocalDate fromDate = LocalDate.parse(startDeliveryDate,FORMATTERYYYYMMDD);
        // LOGGER.info("-- startDeliveryDate in LocalDate var: " + fromDate + " and format: " + FORMATTERYYYYMMDD);
        // locStartDeliveryDate = checkyyyymmdd (fromDate.format(FORMATTERYYYYMMDD));
        // LOGGER.info("-- locStartDeliveryDate var: " + locStartDeliveryDate);
        // LocalDate endDateVar = LocalDate.parse(endDeliveryDate, FORMATTERYYYYMMDD);
        // locEndDesliveryDate = checkyyyymmdd(endDateVar.format(FORMATTERYYYYMMDD));
        // } catch (Exception e1) {
        // // TODO Auto-generated catch block
        // LOGGER.error(" -- Date parsing went wrong -- " + e1);;
        // }
        try {
            // startDate = ((DatatypeFactory.newInstance().newXMLGregorianCalendar(locStartDeliveryDate.toString()) ==
            // null) ? DatatypeFactory.newInstance().newXMLGregorianCalendar("2018-10-31T00:00:00") :

            // private static final DateTimeFormatter FORMATTERALL = DateTimeFormatter.ofPattern("yyyy-MM-dd
            // hh:mm:ss");//2018-10-31 23:59:59
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

            LOGGER.info(" -- String to parse: " + startDeliveryDate.replace("T", " ").trim());
            Date locDate = format.parse(startDeliveryDate.replace("T", " ").trim());
            GregorianCalendar calendar = new GregorianCalendar();
            calendar.setTime(locDate);
            startDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
            LOGGER.info("-- startDate: " + startDate);

            locDate = (Date) format.parse(endDeliveryDate.replace("T", " ").trim());
            calendar.setTime(locDate);
            endDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
            LOGGER.info("-- endDate: " + endDate);

            // startDate = new DatatypeFactory.newInstance().newXMLGregorianCalendar(locStartDeliveryDate.toString();
            // endDate = ((DatatypeFactory.newInstance().newXMLGregorianCalendar(locEndDesliveryDate.toString()) ==
            // null) ? DatatypeFactory.newInstance().newXMLGregorianCalendar("2018-10-31T23:59:59") :
            // endDate = ((DatatypeFactory.newInstance().newXMLGregorianCalendar(locEndDesliveryDate.toString()) ==
            // null) ? DatatypeFactory.newInstance().newXMLGregorianCalendar(endDeliveryDate) :
            // DatatypeFactory.newInstance().newXMLGregorianCalendar(locEndDesliveryDate.toString()) );
            // endDate = DatatypeFactory.newInstance().newXMLGregorianCalendar("2018-10-31T23:59:59");
        } catch (DatatypeConfigurationException e) {
            // This causes the information goes to the screen...
            LOGGER.error("Problem setting up the consingments date: " + e);
            throw new DatatypeConfigurationException(e); // solving that..
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        consFilter.setEarliestDeliveryDate(startDate);
        consFilter.setLatestDeliveryDate(endDate);
        consSummaries.setConsignmentFilter(consFilter);
        FindConsignmentSummariesResponse resp = webservice.findConsignmentSummaries(consSummaries);
        LOGGER.info("Recovered: " + resp.getFindConsignmentSummariesResult().getConsignmentSummary().size());
        // or flush an output stream directly for the existing code
        return resp;
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
                LocalDate ret = LocalDate.parse(input, FORMATTERYYYYMMDD);
                return ret;
            } catch (Exception e) {
                LOGGER.error("-- The input string: " + input + " ,and the formatter: " + FORMATTERYYYYMMDD);
                throw new Exception("Invalid Date check exception", e);
            }
        }
        throw new Exception("Date check exception");
    }

}
