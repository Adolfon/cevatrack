package com.ceva.cevatrack.wsmicrolise;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.security.Policy.Parameters;
import java.util.Collection;
import java.util.HashMap;
import java.util.InvalidPropertiesFormatException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.faces.context.FacesContext;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.xmlbeans.SystemProperties;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * @author brian.goodall
 *
 */
public class WsMicroliseEnvironment {
    private static final Logger LOGGER = LoggerFactory.getLogger(WsMicroliseEnvironment.class);

    private static final String GETCONSIGNMENTBODY = "microlise.body.getconsignment.";
    private static final String GETCONSIGNMENTSOAPACTION = "microlise.soapapaction.getconsignment.";
    private static final String FINDCONSIGNMENTSUMMARIESBODY = "microlise.body.findconsignmentsummaries.";
    private static final String FINDCONSIGNMENTSUMMARIESSOAPACTION = "microlise.soapapaction.findconsignmentsummaries.";
    private static final String AUTHHEADER = "microlise.usernamepassword.";
    private static final String URL = "microlise.url.";
    private static final String PROXYSERVER = "microlise.proxyserver.";
    private static final String PROXYPORT = "microlise.proxyport.";

    public boolean proxied = false;
    public boolean authorized = false;
    public String envDescription;

    Map<String, String> parameters = null;

    private static final String DIRECTPROD = "sn_microlise:direct:prod";
    private static final String DIRECTUAT = "sn_microlise:direct:uat";
    private static final String TIBCOUAT = "sn_microlise:tibco:uat";
    private static final String TIBCOPROD = "sn_microlise:tibco:prod";
    // private static final String TIBCOPROD = "sn_microlise:tibco:prod";
    // This is for the web service interface, if you have this perm. you are 
    // the WS.
    //private static final String TECHNET_DIRECT_PROD = "technet:direct:prod";
    private static final String TECHNETWEB_UAT = "tn_microlise:tibco:uat";
    private static final String TECHNETWEB_PROD = "tn_microlise:tibco:prod";
    
    private static final String AUTOMOTIVE_WEB_UAT = "am_microlise:tibco:uat";
    private static final String AUTOMOTIVE_WEB_PROD = "am_microlise:tibco:prod";

    private static final String HEALTHCARE_WEB_UAT = "hc_microlise:tibco:uat";
    private static final String HEALTHCARE_WEB_PROD = "hc_microlise:tibco:prod";
    /**
     * set up based on user. Based on permissions setup environment
     * The problem arise when an user has permission en different environments
     * at the same time
     * and you want to grant permission based in the request or network that 
     * he is at that moment.. with this solution he wil be accessing the WS of 
     * the first environment condition (if) and never the other ones..
     * So I need to introduce a parameter of what is his network and then ask if 
     * he has permission in that network
     */
    public WsMicroliseEnvironment(String network) {
    	LOGGER.info(" -- starting environment, param: " + network);
    	Map<String, String> local = null;  
    	Properties systemProps = System.getProperties();
    	try{
    		this.parameters = FacesContext.getCurrentInstance().getExternalContext().getInitParameterMap();//problem for WS
    		LOGGER.info(" -- Existing parameters: " + parameters.size() + ", " + systemProps.getProperty("environment.code")
    		+ ",network: " + network);
    		if (systemProps.get("environment.code").toString().trim().equalsIgnoreCase("LOCAL")){
    			LOGGER.info(" -- In the local environment JSF container..");
    			local = loadCevaResponsiveXmlJAXRSProps();
    		}
    	}catch (Exception e){
    		LOGGER.error(" -- FacesContext.getCurrentInstance().getExternalContext().getInitParameterMap() ---> is NULL.., charging local properties..");
    		this.parameters = new HashMap<String, String>();
    		local = loadCevaResponsiveXmlJAXRSProps();
    		LOGGER.info(" -- Existing parameters after local properties loaded: " + parameters.size());
    	}
        if (local != null) {
        	LOGGER.info(" -- adding local file parameters, before size: " + parameters.size());
        	HashMap<String, String> impl = new HashMap<>(local);
        	parameters = impl;
        	LOGGER.info(" -- adding local file parameters, after size: " + parameters.size());
        }
        // first of all decide the environment based on the user
        Subject currentUser = SecurityUtils.getSubject();
        envDescription = "tibco.prod"; //by default
        // Next time do this with a Map 
        if (network != null && network.equalsIgnoreCase("smartnet")){
	        if (currentUser.isPermitted(DIRECTPROD)) {
	            envDescription = "direct.prod";
	        }
	        if (currentUser.isPermitted(TIBCOUAT)) {
	            envDescription = "tibco.uat";
	        }
	        if (currentUser.isPermitted(DIRECTUAT)) {
	            envDescription = "direct.uat";
	        }	    
	        if (currentUser.isPermitted(TIBCOPROD)) {
	            envDescription = "tibco.prod";
	        }
        }
        if (network != null && network.equalsIgnoreCase("technet")){
	        if (currentUser.isPermitted(TECHNETWEB_UAT)){  //user permissions in DB (action connect)
	        	//envDescription = "technetweb.tibco.uat"; //chain to be added to the property name in war.xml
	        	envDescription = "technetweb.tibco.uat"; //Trying the same connection as the WS of technet --> "http://localhost:8080/cevatrack/techNet?wsdl"
	        }
	        if (currentUser.isPermitted(TECHNETWEB_PROD)){  
	        	envDescription = "technetweb.tibco.prod"; 
	        }
        }
        if (network != null && network.equalsIgnoreCase("automotive")){
	        if (currentUser.isPermitted(AUTOMOTIVE_WEB_UAT)){  //user permissions in DB (action connect)
	        	//envDescription = "technetweb.tibco.uat"; //chain to be added to the property name in war.xml
	        	envDescription = "automotive_web.tibco.uat"; //Trying the same connection as the WS of technet --> "http://localhost:8080/cevatrack/techNet?wsdl"
	        }
	        if (currentUser.isPermitted(AUTOMOTIVE_WEB_PROD)){  
	        	envDescription = "automotive_web.tibco.prod"; 
	        }
        }
        if (network != null && network.equalsIgnoreCase("healthcare")){
	        if (currentUser.isPermitted(HEALTHCARE_WEB_UAT)){  //user permissions in DB (action connect)
	        	//envDescription = "technetweb.tibco.uat"; //chain to be added to the property name in war.xml
	        	envDescription = "healthcare_web.tibco.uat"; //Trying the same connection as the WS of technet --> "http://localhost:8080/cevatrack/techNet?wsdl"
	        }
	        if (currentUser.isPermitted(HEALTHCARE_WEB_PROD)){  
	        	envDescription = "healthcare_web.tibco.prod"; 
	        }
        }
        if (network != null && network.equalsIgnoreCase("HealthLink")){
        	envDescription = "health_link.tibco.prod";
        }
        LOGGER.info("envinronment description selected: " + envDescription);
        setAuthorized();
        setProxied();
    }

    private Map<String, String> loadCevaResponsiveXmlJAXRSProps() {
    	String resourceName = "nike-jaxrs-config.xml"; 
    	ClassLoader loader = Thread.currentThread().getContextClassLoader();
    	Properties props = new Properties();    	
    	try {
    		InputStream resourceStream = loader.getResourceAsStream(resourceName);
			props.loadFromXML(resourceStream);
		} catch (InvalidPropertiesFormatException e) {			
			LOGGER.error("InvalidPropertiesFormatException....", e);
		} catch (IOException e) {			
			LOGGER.error("IOException....", e);
		}
    	LOGGER.info(" --- Properties resource returned");
		return (Map) props;
	}

	public WsMicroliseEnvironment(Boolean WS) {
        // first of all decide the environment based on the user
        // Subject currentUser = SecurityUtils.getSubject();
        if (WS) {
            envDescription = "technetdirect.prod";
            this.parameters = new HashMap<String, String>();
        }
        setAuthorized();
        setProxied();
    }

    /**
     * @return retrieves the file properties
     */
    public Map<String, String> loadCevaResponsiveXmlProps() {
        final String filePathAndName = "";
        final Properties properties = new Properties();
        try {
            File directory = new File("./");// C:\Users\canoad\applications\neon64\eclipse\.
            //LOGGER.debug(directory.getAbsolutePath());
            LOGGER.warn(directory.getAbsolutePath());
            // java.net.URL url = getClass().getResource("CEVAtrack-responsive.xml");//null
            // String path = "C:/Users/canoad/git/CEVAtrack-responsive/CEVAtrack-responsive-app/local_tomee/conf"
            // + "/Catalina/localhost/CEVAtrack-responsive_mod.xml";
            String path = "C:/Users/canoad/git2/cevatrack/cevatrack-app/local_tomee/conf"
                    + "/Catalina/localhost/cevatrack_mod.xml";
            File file = new File(path);
            final FileInputStream in = new FileInputStream(file);
            properties.loadFromXML(in);
            in.close();
        } catch (FileNotFoundException fnfEx) {
            System.err.println("Could not read properties from file " + filePathAndName);
            return null;
        } catch (IOException ioEx) {
            System.err.println("IOException encountered while reading from " + filePathAndName);
            return null;
        }
        return (Map) properties;
    }

    /**
     * check whether the connection should have an authorization header (user and password, basic security for WS)
     */
    public void setAuthorized() {
        // LOGGER.debug("Test Debug log --- parameters size: " + parameters.size());// Working as FINE in the log
        // LOGGER.warn("Test Warn log--- parameters size: " + parameters.size()); //WARNING
        // LOGGER.error("Test Error log--- parameters size: " + parameters.size());//SEVERE
         LOGGER.info("-- Info in setAuthorized--- parameters size: " + parameters.size());//INFO
        // LOGGER.trace("Test Trace log--- parameters size: " + parameters.size());//NO SHOW--
        //test.putAll(parameters); //Here null pointer exception
        //parameters = new HashMap<String, String>(test);
        //LOGGER.warn("parameters size: " + parameters.size());
        if (parameters.containsKey(AUTHHEADER + envDescription)) {// microlise.usernamepassword. tibco.prod
            authorized = true;
        }
        LOGGER.info("Authorization Required: " + authorized + " , for parameter: " + AUTHHEADER + envDescription);
    }

    /**
     * check whether the connection requires a proxy
     */
    public void setProxied() {
        if (parameters.containsKey(PROXYSERVER + envDescription)) { // microlise.proxyserver.
            proxied = true;
        }
        LOGGER.info("Proxy Required: {0}", Boolean.toString(proxied));
    }

    /**
     * @param consignmentReference
     *            is the con being sought
     * @return the request body containing the con
     * @throws Exception
     *             if unable to get context string
     */
    public String getGetConsignmentBody(String consignmentReference) throws Exception {
        try {
            String base = parameters.get(GETCONSIGNMENTBODY + envDescription);
            return base.replace("#CONREF#", consignmentReference);// NULL?
        } catch (Exception e) {
            throw new Exception("No body context found for getConsignment call " + envDescription,e);
        }
    }

    public String getEnvDescription() {
        return envDescription;
    }

    /**
     * @return the soap action string
     * @throws Exception
     *             if unable to locate
     */
    public String getGetConsignmentSoapAction() throws Exception {
        try {
            String base = parameters.get(GETCONSIGNMENTSOAPACTION + envDescription);
            return base;
        } catch (Exception e) {
            throw new Exception("No SOAP Action context found for getConsignment call " + envDescription,e);
        }
    }

    /**
     * @param earliestDate
     *            in ms format
     * @param latestDate
     *            in ms format
     * @return request body
     * @throws Exception
     *             unable to locate the string in the context xml
     */
    public String getFindConsignmentSummariesBody(String earliestDate, String latestDate) throws Exception {
        try {
            String base = parameters.get(FINDCONSIGNMENTSUMMARIESBODY + envDescription);// config new net
            // base = "&lt;soapenv:Envelope xmlns:soapenv=&quot;http://schemas.xmlsoap.org/soap/envelope/&quot;
            // xmlns:fin=&quot;"
            // + "http://www.cevalogistics.com/XSD/MicroliseSmartNet/TMC/FindConsignmentSummaries&quot;&gt;&lt;"
            // + "soapenv:Header/&gt;&lt;soapenv:Body&gt;&lt;fin:FindConsignmentSummaries&gt;&lt;!--Optional:--&gt;"
            // + "&lt;fin:consignmentFilter&gt;&lt;fin:EarliestDeliveryDate&gt;&lt;![CDATA[#EARLIEST#]]&gt;"
            // + "&lt;/fin:EarliestDeliveryDate&gt;&lt;fin:LatestDeliveryDate&gt;&lt;![CDATA[#LATESTDATE#]]&gt;&lt;"
            // + "/fin:LatestDeliveryDate&gt;&lt;/fin:consignmentFilter&gt;&lt;/fin:FindConsignmentSummaries&gt;&lt;"
            // + "/soapenv:Body&gt;&lt;/soapenv:Envelope&gt;";
            LOGGER.info("body for the request: "
                    + base.replace("#EARLIEST#", earliestDate).replace("#LATESTDATE#", latestDate));
            return base.replace("#EARLIEST#", earliestDate).replace("#LATESTDATE#", latestDate);
        } catch (Exception e) {
            throw new Exception("No body context found for findConsignmentSummaries call " + FINDCONSIGNMENTSUMMARIESBODY + envDescription);
        }
    }

    /**
     * @return soap action string
     * @throws Exception
     *             if unable to find
     */
    public String getFindConsignmentSummariesSoapAction() throws Exception {
        try {
            String base = parameters.get(FINDCONSIGNMENTSUMMARIESSOAPACTION + envDescription);
            LOGGER.info(" -- Action to be requested: " + base);
            return base;
        } catch (Exception e) {
            throw new Exception("No SOAP Action context found for findConsignmentSummaries call, " + FINDCONSIGNMENTSUMMARIESSOAPACTION + envDescription);
        }
    }

    /**
     * @return url string for the env
     * @throws Exception
     *             if unable to find
     */
    public String getUrl() throws Exception {
        try {
            String base = parameters.get(URL + envDescription);
            LOGGER.info(" URL for the connection: " + base);
            return base;
        } catch (Exception e) {
            throw new Exception("No url context found: " + envDescription);
        }
    }

    /**
     * @return true if proxy is needed
     */
    public boolean hasProxy() {
        return proxied;
    }

    /**
     * @return the relevant proxy for the connection
     * @throws Exception
     *             if unable to create
     */
    public Proxy getProxy() throws Exception {
        if (!proxied) {
            return null;
        }
        try {
            String proxyserver = parameters.get(PROXYSERVER + envDescription);
            String proxyport = parameters.get(PROXYPORT + envDescription);
            int port = Integer.parseInt(proxyport.trim());
            InetSocketAddress inetaddr = new InetSocketAddress(proxyserver, port);
            return new Proxy(Proxy.Type.HTTP, inetaddr);

        } catch (Exception e) {
            throw new Exception("No url context found: " + envDescription);
        }
    }

    /**
     * @return true if authorization header is needed
     */
    public boolean isAuthorized() {
        return authorized;
    }

    /**
     * @return auth header contents
     * @throws Exception
     *             if unable to find context
     */
    public String getAuthorization() throws Exception {
        if (!authorized) {
            return null;
        }
        try {
            String base = parameters.get(AUTHHEADER + envDescription);// microlise.usernamepassword.direct.prod2
            return base;
        } catch (Exception e) {
            throw new Exception("No authorization context found: " + envDescription);
        }
    }

}
