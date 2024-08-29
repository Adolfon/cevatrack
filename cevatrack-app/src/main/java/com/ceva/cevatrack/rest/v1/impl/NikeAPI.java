package com.ceva.cevatrack.rest.v1.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
//import java.time.LocalDate;
//import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
//import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;

import javax.inject.Inject;
//import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
//import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ceva.cevatrack.domain.AAFUser4Nike;
import com.ceva.cevatrack.domain.OrderLookUp;
import com.ceva.cevatrack.rest.v1.impl.data.PlayerType3;
import com.ceva.cevatrack.util.ApplicationGlobalConfig;
//import com.ceva.cevatrack.domain.SmartNetClientTest;
//import com.ceva.cevatrack.repository.SmartNetClientRepository;
import com.ceva.cevatrack.wsmicrolise.WsMicroliseComms;
import com.ceva.cevatrack.wsmicrolise.getconsignment.Consignment;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/*
 * Input Pack List number from Nike
 * Output
 * 		- Date Shipped (DB)
 *      - Estimated Arrival Date (ETA from WS)
 *      - Actual Arrival Date (ATA from WS)
 *      
 * Responses: 403 Auth failed, 400 empty Input number, 404 input not found in DB, 200 OK, (even if not found in WS then output just shipped
 * 500 INternal server error when WS down??--> opposite to the requirements mentioned before..
 */
@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON) //Now the result is a JSON document for all the methods of this class, you can apply this notation individually but ignores it..
//@Produces(MediaType.APPLICATION_XML)
//@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
//@Transactional
public class NikeAPI implements Serializable{
			
	private static final long serialVersionUID = 1L;
	private String id;
	String userppal = null; 
	AAFUser4Nike userA = null;
	private final static int DPD_DELIVERED_CODE = 1;
	private final static int DPD_DELIVERED_IMAGE_COPY_AVAILABLE_CODE = 65; //has the same time as delivered event
	private final static int DPD_OUT_FOR_DELIVER_CODE = 15;
	private final static int DPD_PARCEL_DATA_CODE = 0;
	private final static int DPD_NO_CODE = -1;
	private final static int DPD_RTC_CODE = 53;
	private final static int DPD_FAILING_CALLING_CARD_ONCE_CODE = 8;
	private final static int DPD_FAILING_CALLING_CARD_TWICE_CODE = -8;

//	@Inject
//    @PersistenceUnitName(value="generalPU")
    //private EntityManagerFactory entityManagerFactory;
	
//	@PersistenceContext(unitName = "cevatrackPU") //Not working..
//	protected EntityManager em;
    //private static String PERSISTENCE_UNIT_NAME = "cevatrackPU";
	private static String PERSISTENCE_UNIT_NAME = "cevatrackPULocal";
    private static EntityManagerFactory emf;
    //private static EntityManager em;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(NikeAPI.class);
	Subject currentUser;
	//private static final DateTimeFormatter FORMATWS = DateTimeFormatter.ofPattern("dd MMM hh:mm a");
	private static final DateTimeFormatter FORMATWS0 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");	
	private static final DateTimeFormatter FORMATWS2 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"); //2021-01-21T06:17:43.877
	private static final String STRNIKE = "yyyy-MM-dd HH:mm:ss"; //"MM/dd/yyyy HH:mm:ss";
	private static final DateTimeFormatter FORMATNIKE = DateTimeFormatter.ofPattern(STRNIKE);
	private String environment = "";
	
//    @Inject
//    private SmartNetClientRepository smartNetClientRepository; //OK
	
    @Inject
    private EntityManager entityManager;
	
//	@Inject
//	private EntityManagerFactory emf;  //Not found in the context...
    /*
     * It returns http code 400 when there is no input parameter
     */
	@GET
    @Path("/getPackListNumberInfo")
	//@Produces(MediaType.APPLICATION_XML) // Actually it returns XML type, regardless the class config
	@Produces(MediaType.APPLICATION_JSON) 
	public Response getPackListNumberInfo(){
		LOGGER.info("--- The methos was called without parameters..");
		return Response.status(404).entity("The request is malformed").build();
	}
	/*
	 * Test the DPD web service
	 */
	@GET
    @Path("/getDPDTestInfo")	
	@Produces(MediaType.APPLICATION_XML + "; charset=UTF-8") //Test this charset notation
	public Response getDPDTestInfo(){
		LOGGER.info("--- Method for testing the DPD web service called..");
		getDPDInformation("15501550604980K");
		LOGGER.info(" -- And now the getDPDInformation_jaxb method.. --");		
		return Response.status(200).entity("The request was successfully executed").build();
	}
	/*
	 * Error--> No message body reader has been found for class com.ceva.cevatrack.rest.v1.impl.data.PlayerType, ContentType: application/json
	 * check this out--> https://stackoverflow.com/questions/48690701/cxf-restful-no-message-body-reader-has-been-found-for-request-class-contenttyp
	 */
//	@POST
//    @Path("/insertTestInfo")	
//	@Consumes({MediaType.APPLICATION_XML})
//	@Produces(MediaType.APPLICATION_JSON) 
//	public Response insertTestInfo(PlayerType info){
//		LOGGER.info("--- Method for testing the input of information in JSON form ---");
//		LOGGER.info(" -- -- Player: " + info);
//		return Response.status(200).entity("The request was successfully executed").build();
//	}
	
	@POST
    @Path("/insertTestInfo")	
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON) 
	public Response insertTestInfo(String info){
		LOGGER.info("--- Method for testing the input of information in JSON form ---");
		ObjectMapper objectMapper = new ObjectMapper();
		PlayerType3 player = null;
		try {
			LOGGER.info(" -- Converting input String into a class --");
			player = objectMapper.readValue(info, PlayerType3.class);
		} catch (JsonParseException e) {
			LOGGER.info(" Error 1 JsonParseException ",e);
		} catch (JsonMappingException e) {
			LOGGER.info(" Error 2 JsonMappingException ",e);
		} catch (IOException e) {
			LOGGER.info(" Error 3 IOException ",e);
		}
		LOGGER.info(" -- -- Player name: " + player.getName());
		LOGGER.info(" -- Converting object to String --");
		String result = null;
		try {
			result = objectMapper.writeValueAsString(player);
		} catch (JsonGenerationException e) {
			LOGGER.error(" -- Error 4 JsonGenerationException ", e);
		} catch (JsonMappingException e) {
			LOGGER.error(" -- Error 5 JsonMappingException ", e);
		} catch (IOException e) {
			LOGGER.error(" -- Error 6 IOException ", e);
		}
		LOGGER.info(" result: " + result);
		String playerJsonArray = 
				  "[{ \"color\" : \"Black\", \"type\" : \"BMW\" }, { \"color\" : \"Red\", \"type\" : \"FIAT\" }]";
				try {
					List<PlayerType3> listCar = objectMapper.readValue(playerJsonArray, new TypeReference<List<PlayerType3>>(){});
				} catch (JsonParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JsonMappingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		return Response.status(200).entity("The request was successfully executed, object: " + result).build();
	}
	
//	@POST
//    @Path("/insertTestInfo")	
//	@Consumes({MediaType.APPLICATION_JSON})
//	@Produces(MediaType.APPLICATION_JSON) 
//	public Response insertTestInfo(PlayerType2 info){
//		LOGGER.info("--- Method for testing the input of information in JSON form ---");
//		LOGGER.info(" -- -- Player json: " + info);
//		return Response.status(200).entity("The request was successfully executed: \n" + info).build();
//	}
//	private void createService(Class<?> serviceClass, String address, Object serviceImpl) {
//	    JAXRSServerFactoryBean srvFactory = new JAXRSServerFactoryBean();
//	    srvFactory.setServiceClass(serviceClass);
//	    srvFactory.setAddress("/rest"+address);
//	    srvFactory.setServiceBean(serviceImpl);
//	    srvFactory.getInInterceptors().add(new CustomInInterceptor());
//	    srvFactory.getOutInterceptors().add(new CustomOutInterceptor());
//	    srvFactory.setProvider(new JacksonJsonProvider());
//	    srvFactory.create();
//	}
	/*
	 * It returns the info for the Pack List Number passed as a parameter
	 */
	@GET
    @Path("/getPackListNumberInfo/{id}")
    public Response getPackListNumberInfo(@PathParam("id") String id, @Context HttpHeaders headers, @Context HttpServletRequest req){ //throws JSONException{
		LOGGER.info(" -- Starting getPackListNumberInfo REST method..");
		//ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//		Map<String, String[]> parameters = req.getParameterMap();//nothing
//		
//		   for (Object key: parameters.keySet())
//		    {
//		            String keyStr = (String)key;
//		            String[] value = (String[])parameters.get(keyStr);
//		            LOGGER.info("Key" + (String)key + "   :   " + Arrays.toString(value));
//		    }
		
		
		if (id == null || id == "" || id == "null"){ //Redirect to localhost tomcat welcome page
			return Response.status(404).entity("No parameter was included on the call..").build();			
		}
		
		//List<String> authHeaders = headers.getRequestHeader(HttpHeaders.AUTHORIZATION);//KO, null
		this.id = id;				
				
		int response_auth = authenticateUser(headers);
		
		switch (response_auth){
			case -2:
				LOGGER.error(" -- Authentication error code: -2");
				return Response.status(403).entity("There was a problem with the user Login").build();			
			case -3:
				LOGGER.error(" -- Authentication error code: -3 (Unsupport coding exception)");
				return Response.status(403).entity("There was a problem with the user Login (unsupport encoding) ").build();		
			case -4:
				LOGGER.error(" -- Authentication error code: -4");
				return Response.status(403).entity("There was a problem with the user Login (Not a Nike user) ").build();				
			case 0:
				LOGGER.info(" Authentication of the user was correct! ");
				break;
			case 1:
				LOGGER.error(" -- Authentication error code: 1");
				return Response.status(403).entity("Unknown Account exception").build();				
			case 2:
				LOGGER.error(" -- Authentication error code: 2");
				return Response.status(403).entity("Incorrect user - password combination").build();				
			case 3:
				LOGGER.error(" -- Authentication error code: 3");
				return Response.status(403).entity("Basic authentication is required").build();	
			case 4:
				LOGGER.error(" -- Authentication error code: 4");
				return Response.status(403).entity("User is blocked, too many attempts").build();	
				
		}		
		
		int resultDB = createEntityManager();		
		
		if (resultDB == -1){
			return Response.status(500).entity("Data base was not accessible").build();
		}
		

		String[] consignment_dateShipped = read_nike_consignment(id);
		
		if (consignment_dateShipped == null){
			LOGGER.info("-- The pack list number did not returned any result.. ");
			return Response.status(404).entity("The pack list number is incorrect").build();			
		}
				
		// Create data Bean response		
		
		return createResponse(consignment_dateShipped);
				
    }
	
	/*
	 * check if the client is Active and put the user inactive after n login attempts
	 */
    private int firstCheckUser(String userName) {
    	LOGGER.info(" --- First user security checks..");
    	if (userName == null || "".equals(userName)){
    		return 2;
    	}
    	if (entityManager!= null && entityManager.isOpen()){
    		entityManager.clear();
    		LOGGER.info(" -- cache cleared..");
    	}
    	if ( 0 == createEntityManager()){
			AAFUser4Nike user = null;
	    	try{
	    		user = (AAFUser4Nike) entityManager.createQuery("SELECT us FROM AAFUser4Nike us WHERE us.userPrincipal = ?1").
						setParameter(1, userName).getSingleResult();
	    		LOGGER.info(" -- the user recovered was: " + user);
	    		if (user == null || user.getPassword() == null || "".equals(user.getPassword())){
	    			return 2;
	    		}else{
	    			LOGGER.info("-- Setting this user pass");
		    		this.userA = new AAFUser4Nike();	 
		    		this.userA.setPassword(user.getPassword());		    		
	    		}
	    		if (user.getIsLocked() || !user.getIsActive()){
	    			return 4;
	    		}
	    		if (user.getIncorrectLoginAttempts() > 3){
	    			return 4;
	    		}
	    		
	    		
			}catch(Exception e){
				LOGGER.warn(" -- The user query with ppal: " + this.userppal + ", did not return an expected result..", e);
				return 2;
			}   
    	}else{
    		LOGGER.warn(" -- There was an error creating the DB");
    		return -1;
    	}
		return 0;
	}
	private Response createResponse(String[] consignment_dateShipped) {
    	LOGGER.info(" -- Starting method createResponse with parameters, consignment: " + consignment_dateShipped[0] + ", date shipiped: " + consignment_dateShipped[1] + 
    			" ,sent to microlise: " + consignment_dateShipped[2]);
    	
    	//Set the variables in the Bean and send it as a entity body for the response
    	NikeResponseBean responseBean = new NikeResponseBean();
    	responseBean.setDate_shipped(consignment_dateShipped[1]);
    	responseBean.setActual_arrival_date("");
    	responseBean.setEstimated_arrival_date("");
    	responseBean.setSent_to_ml(consignment_dateShipped[2]);
    	responseBean.setEta_no_ml(consignment_dateShipped[3]);
    	
    	//Case 1 not sent to Microlise yet (load date returned = shipped date as per data base script update: first shipped and load dates are same, later update only shipped date) 
    	if (responseBean.getSent_to_ml() == null || "".equals(responseBean.getSent_to_ml())){
    		//Check on the 'tracking_reference_suspense' table, if found the query to DPD
    		String[] ATA_DPD = DPD_WS(this.id, consignment_dateShipped[4]);
    		if (ATA_DPD == null){
    			LOGGER.info(" -- initial PARCEL info was NOT found for DPD WS, could be an error --");
    			if (responseBean.getEta_no_ml() == null || "".equals(responseBean.getEta_no_ml())){
    				return Response.ok().entity("{ " + "\"shipped\": \"" + responseBean.getDate_shipped() + "\"}").build();
    			}else{
    				return Response.ok().entity("{ " + "\"shipped\": \"" + responseBean.getDate_shipped()
    					+ "\", \"ETA\": \"" + responseBean.getEta_no_ml() + "\"}").build();
    			}	    				
    		}else{
    			LOGGER.info(" -- ATA, ETA or parcel date WAS found for DPD WS --" + ATA_DPD);
    			if (ATA_DPD[0] != null){// Delivered ATA as a last code
	    			return Response.ok().entity("{ " + "\"shipped\": \"" + ATA_DPD[3]  
					+ "\", \"ATA\": \"" + ATA_DPD[0] + "\"}").build();
    			}
				if (ATA_DPD[1] != null){//Out for delivery DPD ETA
					return Response.ok().entity("{ " + "\"shipped\": \"" + ATA_DPD[3]  
					+ "\", \"ETA\": \"" + ATA_DPD[1] + "\"}").build();
    			}
    			if (ATA_DPD[6] != null){// PARCEL RECONSIGNED RTC OR ANY CODE 53 
    									//LIKE FOR EXAMPLE PARCEL RECONSIGNED
    				String ATA = "Return in progress";
    				return Response.ok().entity("{ " + "\"shipped\": \"" + ATA_DPD[3]  
    						+ "\", \"ATA\": \"" + ATA + "\"}").build();
    			}
    			if (ATA_DPD[5] != null){// FAILED CALLING CARD STRING TWICE//TEST OK
    				String ATA = "Delivery Failed. Contact Nike";
    				return Response.ok().entity("{ " + "\"shipped\": \"" + ATA_DPD[3]  
    						+ "\", \"ATA\": \"" + ATA + "\"}").build();
    			}
    			if (ATA_DPD[4] != null){//FAILED CALLING CARD STRING ONCE
    				String ETA = "Delivery Failed. Awaiting Second Attempt";
    				return Response.ok().entity("{ " + "\"shipped\": \"" + ATA_DPD[3]  
    						+ "\", \"ETA\": \"" + ETA + "\"}").build();
    			}
				if (ATA_DPD[2] != null){//Initial parcel data available or other info but before OUt for delivery or any errors
					return Response.ok().entity("{ " + "\"shipped\": \"" + ATA_DPD[3]  
					+ "\", \"ETA\": \"" + "Awaiting ETA Confirmation" + "\"}").build();
    			}
				//ATA_DPD not null but most likely there is a problem with the delivery
				return Response.ok().entity("{ " + "\"shipped\": \"" + responseBean.getDate_shipped()
				+ "\", \"ETA\": \"" + responseBean.getEta_no_ml() + "\"}").build();    				   				    			
    		}
    	}
    	//Case 2, has been sent to Microlise
		WsMicroliseComms ws = new WsMicroliseComms();
		Consignment resultWS = null;
		try {
			resultWS = ws.getConsignment(consignment_dateShipped[0], "smartnet");
			if (resultWS == null){
				LOGGER.warn(" -- No response from the WS..");
				return Response.ok().entity("{ " + "\"shipped\": \"" + responseBean.getDate_shipped() + 
						"\", \"Microlise\": \"" + responseBean.getSent_to_ml() + "\"}").build();
			}
			LOGGER.info(" -- The recovered result from the WS: " + resultWS.toString());
			if(!currentUser.isPermitted("sn_site:view:" + resultWS.getSiteId())){
				LOGGER.warn(" -- The current user does not have permission for viewing the site: " + resultWS.getSiteId());
				return Response.status(403).entity("User does not have permission for site: " + resultWS.getSiteId()).build();
			}
			LOGGER.info("Dates  pta, eta, ata: " + resultWS.getPta() + ", " + resultWS.getEta() + ", " + resultWS.getAta());
			LOGGER.info("Dates  String pta, eta, ata: " + resultWS.getPtaString() + ", " + resultWS.getEtaString() + ", " + resultWS.getAtaString());
			LOGGER.info("Dates pta, eta, ata: " + resultWS.getPta() + ", " + resultWS.getEta() + ", " + resultWS.getAta());
			
			LOGGER.info("-- More values sizes pta, eta, ata: " +  resultWS.getPta().trim().length() + ", " + resultWS.getEta().trim().length() + ", " + resultWS.getAta().trim().length());
			// Change ETA and ATA form WS to Date and format to my format, erasing 0001-01-01T00:00:00  (ATA is only available in Delivered STATUS)
			//--> format: PTA 2021-01-21T09:11:52 , ATA: 2021-01-21T10:50:24.863
			 LocalDateTime ldt = null;
			if (!"0001-01-01T00:00:00".equals(resultWS.getEta())){
				if(resultWS.getEta().trim().length() == 19){
					ldt = LocalDateTime.parse(resultWS.getEta(), FORMATWS0);
				}
				if(resultWS.getEta().trim().length() == 21){
					ldt = LocalDateTime.parse(resultWS.getEta()+"00", FORMATWS2);
				}
				if(resultWS.getEta().trim().length() == 22){
					ldt = LocalDateTime.parse(resultWS.getEta()+"0", FORMATWS2);
				}
				if(resultWS.getEta().trim().length() == 23){
					ldt = LocalDateTime.parse(resultWS.getEta(), FORMATWS2);
				}
				responseBean.setEstimated_arrival_date(ldt.format(FORMATNIKE));               
			}                

			if (!"0001-01-01T00:00:00".equals(resultWS.getAta())){
				//ldt = LocalDateTime.parse(resultWS.getAta(), FORMATWS2);
				if(resultWS.getAta().trim().length() == 19){
					ldt = LocalDateTime.parse(resultWS.getAta(), FORMATWS0);
				}
				if(resultWS.getAta().trim().length() == 21){
					ldt = LocalDateTime.parse(resultWS.getAta()+"00", FORMATWS2);
				}
				if(resultWS.getAta().trim().length() == 22){
					ldt = LocalDateTime.parse(resultWS.getAta()+"0", FORMATWS2);
				}
				if(resultWS.getAta().trim().length() == 23){
					ldt = LocalDateTime.parse(resultWS.getAta(), FORMATWS2);
				}
				responseBean.setActual_arrival_date(ldt.format(FORMATNIKE));               
			}	    	
	    	LOGGER.info(" -- WS getConsignment call was successfull, recoverd output Bean: " + responseBean);
		} catch (Exception e) {
			LOGGER.error(" -- Querying the consignment caused and exception, only shipped and sent_to_ml will be returned..  ", e);
			return Response.ok().entity("{ " + "\"shipped\": \"" + responseBean.getDate_shipped() + 
					"\", \"Microlise\": \"" + responseBean.getSent_to_ml() + "\"}").build();
		}
		if ("".equals(responseBean.getActual_arrival_date()) && "".equals(responseBean.getEstimated_arrival_date())){
			LOGGER.info(" -- Both fields came empty in the Microlise WS --");
			return Response.ok().entity("{\"shipped\": \"" + responseBean.getDate_shipped() + 
					"\", \"Microlise\": \"" + responseBean.getSent_to_ml() + "\"}").build();
		}
		if ("".equals(responseBean.getActual_arrival_date())){
			return Response.ok().entity("{\"ETA\": \"" + responseBean.getEstimated_arrival_date() + "\", \"shipped\": \"" + responseBean.getDate_shipped() + 
					"\", \"Microlise\": \"" + responseBean.getSent_to_ml() + "\"}").build();
		}
		if ("".equals(responseBean.getEstimated_arrival_date())){
			return Response.ok().entity("{\"ATA\": \"" + responseBean.getActual_arrival_date() + "\", \"shipped\": \"" + responseBean.getDate_shipped() + 
					"\", \"Microlise\": \"" + responseBean.getSent_to_ml() + "\"}").build();			
		}
		//Usually both arent there, but just in case
		return Response.ok().entity("{\"ATA\": \"" + responseBean.getActual_arrival_date() + "\", \"ETA\": \"" + responseBean.getEstimated_arrival_date() + "\", \"shipped\": \"" + responseBean.getDate_shipped() + 
				"\", \"Microlise\": \"" + responseBean.getSent_to_ml() + "\"}").build();
	}
    
	private String[] DPD_WS(String order_id, String tracking_number) {
		LOGGER.info(" -- DPD_WS method, parameter: " + order_id + " ,tracking number: " + tracking_number);
//		Tracking_reference_suspense trs_var = null;
//		try{
//			trs_var = (Tracking_reference_suspense) entityManager.createQuery("SELECT tr FROM Tracking_reference_suspense tr WHERE tr.order_number = ?1").
//					setParameter(1, order_id).getSingleResult();
//		}catch(Exception e){
//			LOGGER.warn(" -- The query with parameter: " + order_id + ", did not return an expected result..\n", e);
//			return null;
//		}
//		LOGGER.info(" -- DPD tracking row recovered: " + trs_var);
		if (tracking_number == null || "".equals(tracking_number)){
			return null;
		}else{
			return getDPDInformation(tracking_number);
		}
		
	}
	private String[] read_nike_consignment(String id) {
		LOGGER.info("-- String method read_nike_consignment..");
		OrderLookUp OLResult = null;
		try{
			OLResult = (OrderLookUp) entityManager.createQuery("SELECT ol FROM OrderLookUp ol WHERE ol.client_ref = ?1 AND ol.client = ?2").
					setParameter(1, id).setParameter(2, "NIKE").getSingleResult();
		}catch(Exception e){
			LOGGER.warn(" -- The query with parameter: " + id + ", did not return an expected result..\n", e);
			return null;
		}
		if (OLResult != null){
			LOGGER.info(" -- The id: " + id + " returned " + OLResult);			
			String pattern = "yyyy-MM-dd HH:mm:ss";
			TimeZone zone = TimeZone.getTimeZone("Europe/London");			
			DateFormat df = new SimpleDateFormat(pattern);
			df.setTimeZone(zone);		
			String date_shipped = "";
			String sent_to_ml = null;
			String eta_no_ml = null;
			try{
				if (OLResult.getShipped_date() != null){
					date_shipped = df.format(OLResult.getShipped_date());
				}
				if (OLResult.getSent_to_ml() != null){
					sent_to_ml = df.format(OLResult.getSent_to_ml());
				}
				if (OLResult.getEta_no_ml() != null){
					eta_no_ml = df.format(OLResult.getEta_no_ml());
				}
			}catch (Exception e){
				LOGGER.error(" -- Formatting exception when converting date from the data base --\n", e);
				return null;
			}
			String[] stringRes = {OLResult.getMl_order_ref() , date_shipped , sent_to_ml , eta_no_ml, OLResult.getTracking_reference()};
			return stringRes;
		}else{
			LOGGER.info("No results for the id: " + id + " matching the table..");
			return null;
		}		
		
	}
	
	private int createEntityManager() {
		if (this.entityManager != null && entityManager.isOpen()){
			LOGGER.warn(" -- The entity manager was already created..");
			return 0;
		}
		int result = -1; 
		EntityManagerFactory emfact = null;
		Properties systemProps = System.getProperties();//OK
//		for (Map.Entry m:systemProps.entrySet()){
//			LOGGER.info(" key: " + m.getKey() + " ,value: " + m.getValue());
//		}
		if (systemProps.get("environment.code") != null){
			LOGGER.info(" -- Value of the property 'environment.code': " + systemProps.get("environment.code"));
			LOGGER.info(" -- 2nd -- Value of the property 'environment.code': " + systemProps.get("environment.code").toString().trim());
			Map<String, String> properties = new HashMap<String, String>();
			properties.put("openjpa.ConnectionDriverName", "org.postgresql.Driver");
			properties.put("globalAlternatives.org.apache.deltaspike.jpa.spi.transaction.TransactionStrategy", "org.apache.deltaspike.jpa.impl.transaction.ContainerManagedTransactionStrategy");
			//TODO use a properties file
			if (systemProps.get("environment.code").toString().trim().equalsIgnoreCase("LOCAL")){
				LOGGER.info(" ----------- We are in the Local environment and its DB configuration!!");//OK
				properties.put("openjpa.ConnectionURL", "jdbc:postgresql://localhost:5432/cevatrack_v6?currentSchema=cevatrack_mix");					
				properties.put("openjpa.ConnectionUserName", "postgres");
				properties.put("openjpa.ConnectionPassword", "holahola");
				this.environment = "LOCAL";
			}
			if (systemProps.get("environment.code").toString().trim().equalsIgnoreCase("CEVA-CEVATRACK-DEVL")){
				LOGGER.info(" ----------- We are in the Development environment and its DB configuration!!");//OK
				properties.put("openjpa.ConnectionURL", "jdbc:postgresql://iahlvlpgdb002:5432/cevatrack_responsive");	
				properties.put("openjpa.ConnectionUserName", "cevatrack_responsive");
				properties.put("openjpa.ConnectionPassword", "Cevatrack_responsive@2024");
				this.environment = "DEV";
			}
			if (systemProps.get("environment.code").toString().trim().equalsIgnoreCase("CEVA-CEVATRACK-TEST")){
				LOGGER.info(" ----------- We are in the TEST environment and its DB configuration!!");//OK
				properties.put("openjpa.ConnectionURL", "jdbc:postgresql://glsdvlpgdb074:5432/cevatrack_responsive?currentSchema=cevatrack_responsive");
				properties.put("openjpa.ConnectionUserName", "cevatrack_responsive");
				properties.put("openjpa.ConnectionPassword", "NqHyxoHGwA56P");
				this.environment = "TEST";
			}
			if (systemProps.get("environment.code").toString().trim().equalsIgnoreCase("CEVA-CEVATRACK-STAGE")){
				LOGGER.info(" ----------- We are in the Cevatrack (PROD) environment and its DB configuration!!");//OK
				properties.put("openjpa.ConnectionURL", "jdbc:postgresql://glpdvlpgdb074:5432/cevatrack_responsive?currentSchema=cevatrack_responsive");	
				properties.put("openjpa.ConnectionUserName", "cevatrack_responsive");
				properties.put("openjpa.ConnectionPassword", "LqEKbxqZvUWb");
				this.environment = "PROD";
			}
			
			emfact = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME, properties);
			
		}else{
			LOGGER.error(" System properties get('environment.code') is null ..");
			return -1;
			}
						
		if (emfact != null){
			LOGGER.info("I created manually the context...");
			if (entityManager == null || !entityManager.isOpen()){	
				try{
					entityManager = emfact.createEntityManager();
				}catch (Exception e){
					LOGGER.error(" --- It was not possible to create the entity manager.." + e.getCause() + ", " + e.getMessage(), e);
					return -1;
				}
				LOGGER.info(" entityManager created manually..");						
			}
			result = 0;
		}
		
//		if (smartNetClientRepository == null){//null
//			LOGGER.info("smartNetClientRepository was null..");
//		}	
				
		if (entityManager == null){//Not null--> OK
			LOGGER.info(" entity manager was null.... from the new notation..");//here
			result = -1;
		}		
		return result;
	}
	
	private int authenticateUser(HttpHeaders headers) {
		int result = -2;
    	LOGGER.info("--------Auth available? : " + headers.getHeaderString("Authorization")); //OK
		LOGGER.info(" ------------------------ headers size: " + headers.getRequestHeaders().size());		
		for(String header : headers.getRequestHeaders().keySet()){ //OK
		    LOGGER.info(" header: " + header + ", value: " + headers.getHeaderString(header));
		}
		LOGGER.info(" ------------------------ headers end");

    	String authHeader = headers.getHeaderString("Authorization"); //OK, it needs "Authenticate pre-emptively" option enable in SOAPUI Basic 
		String[] tokens;
		if (authHeader == null){
			return 3;
		}
		if (authHeader.startsWith("Basic")){ //NPE if not retrieved..
			LOGGER.info(" -- Basic authentication detected.."); //OK
		}else{
			return 3;
		}
		try {
			tokens = (new String(Base64.getDecoder().decode(authHeader.split(" ")[1]), "UTF-8")).split(":");
			final String username = tokens[0];
			final String password = tokens[1];
			LOGGER.info(" user in the Basic Auth: " + username + " ,password: " + password); //OK
			int firstCheck = firstCheckUser(username);
			LOGGER.info(" -- firstCheckUser method returned: " + firstCheck);
			if (firstCheck == 4){			
				return 4; //Too many attempts
			}		
			if (firstCheck == 2){
				return 2; //Bad user/pass combination
			}		
			//LOGGER.info(" -- encrypting password: " + "Holaquetal123!" + ", result: " + encryptUsingShiroConfig("Holaquetal123!"));
			result = doShiroLogin(username, password);
			this.userppal = username;
			
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("--- Error recovering tokens of the Authetication... response HTTP status 403 Forbidden");
			result = -3;
		}
		return result;				
	}
	
	public void initDB() {
        emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }
    
    
    public EntityManager produceEntityManager() {
        entityManager = emf.createEntityManager();
        return entityManager;
    }
    
    //Be aware that the user authentication is done through the cevatrackPU
    private int doShiroLogin_old(String userName, String password){
    	LOGGER.info(" -- Doing shiro login..");
    	if (password == null || "".equals(password)){
    		return 2;
    	}
    	int result = -1;
		try {
				UsernamePasswordToken token = new UsernamePasswordToken(userName, password);			
				long startTime = System.currentTimeMillis();
				SecurityUtils.getSubject().login(token);
				// Now you should be able to recover the subject:  SecurityUtils.getSubject()
				long endTime = System.currentTimeMillis();
				long totalTime = endTime - startTime;
				if (totalTime > 1000L) {
					LOGGER.warn("Time taken for shiro login {} ", totalTime);
				} else {
					LOGGER.info("Time taken for shiro login {} ", totalTime);
				}	
				
		        this.currentUser = SecurityUtils.getSubject();
			    if (currentUser.isPermitted("sn_client:view:nike")) {
			    	LOGGER.info(" -- This user has permission to view Nike instances..");	
			    	result = 0;
		        }else{
		        	LOGGER.info(" -- This user does NOT have permission to view Nike instances..");
		        	return -4;
		        }
		        LOGGER.info( " -- User was successfully logged in..");
		        //Logout after recovering the currentUser..
				SecurityUtils.getSecurityManager().logout(SecurityUtils.getSubject());
				LOGGER.info(" -- User was successfully logged out..");				
		        
		} catch (UnknownAccountException var11) {
			LOGGER.warn("-- catching the UnknownAccountException error messages: " + var11.getMessage(), var11);	
			result = 1;
		} catch (AuthenticationException var12) {
			LOGGER.warn(" -- catching the AuthenticationException error messages: " + var12.getMessage(), var12); // TODO filter the authentication error log
			result = 2;
			addNumberAttemptUser(userName);
//			if (!var12.getMessage().contains("LDAP naming error")){ //Not interested in LDAP error atm
				//LOGGER.warn(var12.getMessage(), var12);
			//}
		}	
		if (result == 0){
			resetNumberAttemptUser(userName);			
		}
		return result;
	}
  //Be aware that the user authentication is done through the cevatrackPU
    private int doShiroLogin(String userName, String password){
    	LOGGER.info(" -- Doing shiro login..");
    	if (password == null || "".equals(password)){
    		return 2;
    	}
    	int result = -1;
		try {				
			if (pass_validation(userA.getPassword(), password)){
				result = 0;
			}else{
				LOGGER.warn("-- Launching AuthenticationException as wrong user/pass combination.." );
				throw new AuthenticationException("Launching Auth Exception, pass: " + password + " is wrong");
			}
		        
		} catch (UnknownAccountException var11) {
			LOGGER.warn("-- catching the UnknownAccountException error messages: " + var11.getMessage(), var11);	
			result = 1;
		} catch (AuthenticationException var12) {
			LOGGER.warn(" -- catching the AuthenticationException error messages: " + var12.getMessage(), var12); // TODO filter the authentication error log
			result = 2;
			addNumberAttemptUser(userName);
//			if (!var12.getMessage().contains("LDAP naming error")){ //Not interested in LDAP error atm
				//LOGGER.warn(var12.getMessage(), var12);
			//}
		}	
		if (result == 0){
			resetNumberAttemptUser(userName);			
		}
		return result;
	}
    
  //TODO validate the user permissions (Johnson)
  	private boolean pass_validation(String pass_encoded, String pass_text){ 
  		LOGGER.info("-- pass text: " + pass_text + "\n pass encoded: " + pass_encoded);
  		String[] mcf = pass_encoded.split("\\$");
  		int iteration_count = Integer.parseInt(mcf[3]);
  		String b64_salt = mcf[4];
  		String b64_hash = mcf[5];
  		byte[] salt = Base64.getDecoder().decode(b64_salt.getBytes());
  		try {			
  			String hash = new Sha256Hash(pass_text, salt, iteration_count).toBase64();;
  		    LOGGER.info("Original MCF hash: " + b64_hash);
  			LOGGER.info("Created hash: " + hash);
  			LOGGER.info("-- Password: " + pass_text);

  		  if (hash.equals(b64_hash)) {
  		      LOGGER.info("Success! Both hashes match!");
  		      return true;
  		  } else {
  		      LOGGER.warn("--- Passwords do not match");
  		      return false;
  		  }
  		} catch (Exception e) {
  		      LOGGER.error("-- Exception", e);
  		}
  		return false;
  	}
  	
    //@Transactional
    private void addNumberAttemptUser(String userName) {	
    	LOGGER.info(" -- Adding an icorrret number of attempt to the user: " + userName);
		if (0 == createEntityManager()){
			//entityManager.getTransaction().begin();//only for ResourceLocalTransactionStrategy, no need for reading..
			AAFUser4Nike user = (AAFUser4Nike) entityManager.createQuery("SELECT us FROM AAFUser4Nike us WHERE us.userPrincipal = ?1").
					setParameter(1, userName).getSingleResult();
			LOGGER.info(" -- user recovered: " + user);
			//entityManager.getTransaction().commit();
			if (user == null){
				LOGGER.info(" -- User was not found so the users name is incorrect..");
				return;
			}			
			entityManager.getTransaction().begin();
			int attempts = user.getIncorrectLoginAttempts() + 1;
			entityManager.createQuery("UPDATE AAFUser4Nike us SET us.incorrectLoginAttempts = ?1 WHERE us.userPrincipal = ?2").
					setParameter(1, attempts).
					setParameter(2, userName).executeUpdate();
			entityManager.flush();
			entityManager.getTransaction().commit();
			entityManager.clear(); //It is the only thing that makes it refresh on DB for this kind of query TODO improve the solution with other type of query (persist)
			LOGGER.info(" -- user after cleanning: " + user);
			LOGGER.info(" -- Number of incorrect attempts for user: " + userName + ", = " + attempts);    		
		}else{
			LOGGER.error(" -- Entitiy manager was not created successfully..");
		}
		
	}
    //@Transactional
    private void resetNumberAttemptUser(String userName) {	
    	LOGGER.info(" -- Resetting the number of attempts to the user: " + userName);
		if (0 == createEntityManager()){
			try{
				entityManager.getTransaction().begin();
				entityManager.createQuery("UPDATE AAFUser4Nike us SET us.incorrectLoginAttempts = ?1 WHERE us.userPrincipal = ?2").
						setParameter(1, 0).
						setParameter(2, this.userppal).executeUpdate();
				//entityManager.persist(AAFUser4Nike);
				entityManager.flush();
				entityManager.getTransaction().commit();
				entityManager.clear();
			}catch (Exception e){
				LOGGER.error("-- Error trying to reset attempts, " + e.getMessage() + ", cause: " + e.getCause(), e);				
				return;
			}
			LOGGER.info(" -- Number of incorrect attempts reseted to 0 ");    		
		}else{
			LOGGER.error(" -- Entitiy manager was not created successfully..");
		}
		
	}
    
    private String[] getDPDInformation(String tracking_number){
    	LOGGER.info("-- getDPDInformation method, parameter: " + tracking_number);
    	StringBuilder response = new StringBuilder();
    	String[] info = new String[7]; 
    	try { 
    		Proxy proxy = null;
    		HttpURLConnection connection = null;
    		URL url = null;
    		url = new URL(ApplicationGlobalConfig.DPD_PROD_URI);
    		LOGGER.info(" -- Environment configuration for the DPD call: " + this.environment);
    		if (this.environment.equals("LOCAL")){    
    			LOGGER.info("-- Configuring for LOCAL");
    			//proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ApplicationGlobalConfig.DEV_PROXY_1, ApplicationGlobalConfig.DEV_PROXY_PORT));
    			//only read access, and the test url does not work anymore..
    			url = new URL(ApplicationGlobalConfig.DPD_PROD_URI);
    			connection = (HttpURLConnection) url.openConnection(); 
    		}
    		if (this.environment.equals("DEV")){
    			LOGGER.info("-- Configuring for DEV");
    			proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ApplicationGlobalConfig.DEV_PROXY_1, ApplicationGlobalConfig.DEV_PROXY_PORT));
    			connection = (HttpURLConnection) url.openConnection(proxy);
    		}
    		if (this.environment.equals("TEST")){
    			LOGGER.info("-- Configuring for TEST");
    			proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ApplicationGlobalConfig.GDC_PROXY, ApplicationGlobalConfig.GDC_PROXY_PORT));
    			connection = (HttpURLConnection) url.openConnection(proxy);
    		}
    		if (this.environment.equals("PROD")){
    			LOGGER.info("-- Configuring for PROD");
    			proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ApplicationGlobalConfig.GDC_PROXY, ApplicationGlobalConfig.GDC_PROXY_PORT));
    			connection = (HttpURLConnection) url.openConnection(proxy);
    		}    		                                  
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("POST");
            //Headers
            connection.setRequestProperty("Accept", "application/xml");
            connection.setRequestProperty("Content-Type", "application/xml");
            //Write XML
            String reqXML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+
            					"<trackingrequest>" + 
            						"<user>CEVALOGISTICS2</user>" +
            						"<password>UBVgXJU7fuUwJjaQQa</password>" +
            						"<trackingnumbers>" +
            							"<trackingnumber>" + tracking_number + "</trackingnumber>" +
            						"</trackingnumbers>" +
            					"</trackingrequest>";
            OutputStream os = connection.getOutputStream();
            byte[] b = reqXML.getBytes("UTF-8");
            os.write(b);
 //JAXB
    //      JAXBContext jaxbContext = JAXBContext.newInstance(DPD_request.class);;
	//		jaxbContext.createMarshaller().marshal(dpd_response, os);
//JAXB
            os.flush();
            os.close();
            //Read XML
    		InputStream inputStream = connection.getInputStream();
    		byte[] res = new byte[2048];
    		int i = 0;    		
    		while ((i = inputStream.read(res)) != -1) {
    			response.append(new String(res, 0, i));
    		}
    		inputStream.close();
            int respcode = connection.getResponseCode();
            connection.disconnect();
            LOGGER.info(" -- HTTP Response was: " + respcode);
            if (respcode != 200){
            	LOGGER.error(" No 200 http response code..");
            	return null;
            }            
            LOGGER.info(" -- XML of the response is: " + response.toString());
            //ETA test response
            //response = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><trackingresponse><trackingdetails><trackingdetail><trackingnumber>15501343768532Y</trackingnumber><parcelnumber>15501343768532</parcelnumber><consignmentnumber>1343545927</consignmentnumber><deliveryaddress1>Chris Robshaw</deliveryaddress1><deliveryaddress2>Fruit Fields Farm Five Oaks Road</deliveryaddress2><deliverylocality>West Sussex</deliverylocality><deliverypostcode>RH13 0RL</deliverypostcode><deliverycountrycode>GB</deliverycountrycode><etastart>11:33</etastart><etaend>12:33</etaend><signedfordate>2021-12-08</signedfordate><parcelcode>15501343768532*19700</parcelcode><trackingevents><trackingevent><date>2021-12-08T07:03:00Z</date><code>15</code><type>OUT FOR DELIVERY</type><description>On vehicle for delivery</description><locality>Crawley</locality><countrycode>GB</countrycode><imagekey></imagekey></trackingevent><trackingevent><date>2021-12-08T03:47:00Z</date><code>4</code><type>CONFIRMED AT DEPOT</type><description>Confirmed at depot</description><locality>Crawley</locality><countrycode>GB</countrycode><imagekey></imagekey></trackingevent><trackingevent><date>2021-12-08T03:47:00Z</date><code>4</code><type>CONFIRMED AT DEPOT</type><description>Confirmed at depot</description><locality>Crawley</locality><countrycode>GB</countrycode><imagekey></imagekey></trackingevent><trackingevent><date>2021-12-07T21:20:00Z</date><code>9</code><type>HUB SCAN</type><description>Confirmed at Hub</description><locality>Hub 4 - Hinckley</locality><countrycode>GB</countrycode><imagekey></imagekey></trackingevent><trackingevent><date>2021-12-07T15:48:00Z</date><code>0</code><type>PARCEL DATA</type><description>Customer data received</description><locality>Sheffield</locality><countrycode>GB</countrycode><imagekey></imagekey></trackingevent></trackingevents></trackingdetail></trackingdetails></trackingresponse>");
            //OUT FOR DELIVERY test response
            //response = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><trackingresponse><trackingdetails><trackingdetail><trackingnumber>15505360977288X</trackingnumber><parcelnumber>15505360977288</parcelnumber><consignmentnumber>5360694779</consignmentnumber><deliveryaddress1>Fanatics Supplies Ltd</deliveryaddress1><deliveryaddress2>67 Grace Road</deliveryaddress2><deliverylocality>Leicester</deliverylocality><deliverypostcode>LE2 8AD</deliverypostcode><deliverycountrycode>GB</deliverycountrycode><etastart>1970-01-01T10:15:00+01:00</etastart><etaend>1970-01-01T11:15:00+01:00</etaend><parcelcode>15505360977288*19813</parcelcode><trackingevents><trackingevent><date>2022-03-31T06:27:00+01:00</date><code>15</code><type>OUT FOR DELIVERY</type><description>On vehicle for delivery between 10:15 - 11:15</description><locality>Leicester</locality><countrycode>GB</countrycode><etastart>10:15</etastart><etaend>11:15</etaend><imagekey></imagekey></trackingevent><trackingevent><date>2022-03-31T01:23:00+01:00</date><code>4</code><type>CONFIRMED AT DEPOT</type><description>Confirmed at depot</description><locality>Leicester</locality><countrycode>GB</countrycode><imagekey></imagekey></trackingevent><trackingevent><date>2022-03-31T01:19:00+01:00</date><code>4</code><type>CONFIRMED AT DEPOT</type><description>Confirmed at depot</description><locality>Leicester</locality><countrycode>GB</countrycode><imagekey></imagekey></trackingevent><trackingevent><date>2022-03-30T22:06:00+01:00</date><code>9</code><type>HUB SCAN</type><description>Confirmed at Hub</description><locality>Hub 4 - Hinckley</locality><countrycode>GB</countrycode><imagekey></imagekey></trackingevent><trackingevent><date>2022-03-30T16:05:00+01:00</date><code>0</code><type>PARCEL DATA</type><description>Customer data received</description><locality>Sheffield</locality><countrycode>GB</countrycode><imagekey></imagekey></trackingevent></trackingevents></trackingdetail></trackingdetails></trackingresponse>");
            //LOGGER.info(" -- Test response: " + response);
            //response = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><trackingresponse><trackingdetails><trackingdetail><trackingnumber>155020221598468</trackingnumber><parcelnumber>15502022159846</parcelnumber><consignmentnumber>2022159829</consignmentnumber><deliveryaddress1>Sm Mark Simkin</deliveryaddress1><deliveryaddress2>The Fa</deliveryaddress2><deliverylocality>Unit B Beacon Business Park</deliverylocality><deliverycity>Weston Rd</deliverycity><deliverycounty>Stafford</deliverycounty><deliverypostcode>ST18 0WL</deliverypostcode><deliverycountrycode>GB</deliverycountrycode><etastart>1970-01-01T10:55:00+01:00</etastart><etaend>1970-01-01T11:55:00+01:00</etaend><parcelcode>15502022159846*19749</parcelcode><trackingevents><trackingevent><date>2022-01-26T09:49:00Z</date><code>15</code><type>OUT FOR DELIVERY</type><description>On vehicle for delivery between 10:55 - 11:55</description><locality>Stoke</locality><countrycode>GB</countrycode><etastart>10:55</etastart><etaend>11:55</etaend><imagekey></imagekey></trackingevent><trackingevent><date>2022-01-26T04:50:00Z</date><code>4</code><type>CONFIRMED AT DEPOT</type><description>Confirmed at depot</description><locality>Stoke</locality><countrycode>GB</countrycode><imagekey></imagekey></trackingevent><trackingevent><date>2022-01-26T00:09:00Z</date><code>9</code><type>HUB SCAN</type><description>Confirmed at Hub</description><locality>Hub 4 - Hinckley</locality><countrycode>GB</countrycode><imagekey></imagekey></trackingevent><trackingevent><date>2022-01-25T11:35:00Z</date><code>0</code><type>PARCEL DATA</type><description>Customer data received</description><locality>Sheffield</locality><countrycode>GB</countrycode><imagekey></imagekey></trackingevent></trackingevents></trackingdetail></trackingdetails></trackingresponse>");
            //Response test for parcel data retrieval only
            //response = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><trackingresponse><trackingdetails><trackingdetail><trackingnumber>15505359452076I</trackingnumber><parcelnumber>15505359452076</parcelnumber><consignmentnumber>5359191246</consignmentnumber><deliveryaddress1>Nick Rivett Sport Ltd</deliveryaddress1><deliveryaddress2>64 Church Road Hove</deliveryaddress2><deliverylocality>Brighton</deliverylocality><deliverypostcode>BN3 2FP</deliverypostcode><deliverycountrycode>GB</deliverycountrycode><parcelcode>15505359452076*19814</parcelcode><trackingevents><trackingevent><date>2022-03-31T18:36:00+01:00</date><code>0</code><type>PARCEL DATA</type><description>Customer data received</description><locality>Sheffield</locality><countrycode>GB</countrycode><imagekey></imagekey></trackingevent></trackingevents></trackingdetail></trackingdetails></trackingresponse>");
            //LOGGER.info("response test: " + response);
            //parcel with problems in delivery situation
            //response = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><trackingresponse><trackingdetails><trackingdetail><trackingnumber>15502027343989N</trackingnumber><parcelnumber>15502027343989</parcelnumber><consignmentnumber>2027294408</consignmentnumber><deliveryaddress1>Summus Sports - Kjt</deliveryaddress1><deliveryaddress2>280 Baldwins Lane</deliveryaddress2><deliverylocality>Rickmansworth</deliverylocality><deliverypostcode>WD3 3LD</deliverypostcode><deliverycountrycode>GB</deliverycountrycode><relabelledas>1550 8002 446 245 8</relabelledas><yellowcardnumber>35465072</yellowcardnumber><parcelcode>15502027343989*19752</parcelcode><trackingevents><trackingevent><date>2022-01-31T16:15:00Z</date><code>62</code><type>DEPOT OUTBOUND</type><description>Parcel left depot</description><locality>Dunstable</locality><countrycode>GB</countrycode><imagekey></imagekey></trackingevent><trackingevent><date>2022-01-31T11:33:00Z</date><code>53</code><type>PARCEL RECONSIGNED RTC</type><description>Parcel reconsigned for onward journey (Return To Consignor)</description><locality>Dunstable</locality><countrycode>GB</countrycode><imagekey></imagekey></trackingevent><trackingevent><date>2022-01-31T00:20:00Z</date><code>4</code><type>CONFIRMED AT DEPOT</type><description>Confirmed at depot</description><locality>Dunstable</locality><countrycode>GB</countrycode><imagekey></imagekey></trackingevent><trackingevent><date>2022-01-31T00:18:00Z</date><code>4</code><type>CONFIRMED AT DEPOT</type><description>Confirmed at depot</description><locality>Dunstable</locality><countrycode>GB</countrycode><imagekey></imagekey></trackingevent><trackingevent><date>2022-01-30T15:27:00Z</date><code>68</code><type>FAILED NOTIFICATION RECEIVED</type><description>Unable to deliver, notification received</description><locality>Dunstable</locality><countrycode>GB</countrycode><imagekey></imagekey></trackingevent><trackingevent><date>2022-01-30T15:25:00Z</date><code>58</code><type>REQUEST FURTHER INSTRUCTIONS</type><description>Further instructions requested</description><locality>Dunstable</locality><countrycode>GB</countrycode><imagekey></imagekey></trackingevent><trackingevent><date>2022-01-30T15:23:00Z</date><code>8</code><type>FAILED CALLING CARD</type><description>Unable to deliver, calling card left - 35465072</description><locality>Dunstable</locality><countrycode>GB</countrycode><imagekey>S5^0022*19754*868*3600*41*008**1</imagekey></trackingevent><trackingevent><date>2022-01-30T07:55:00Z</date><code>15</code><type>OUT FOR DELIVERY</type><description>On vehicle for delivery</description><locality>Dunstable</locality><countrycode>GB</countrycode><imagekey></imagekey></trackingevent><trackingevent><date>2022-01-30T02:32:00Z</date><code>4</code><type>CONFIRMED AT DEPOT</type><description>Confirmed at depot</description><locality>Dunstable</locality><countrycode>GB</countrycode><imagekey></imagekey></trackingevent><trackingevent><date>2022-01-30T02:27:00Z</date><code>4</code><type>CONFIRMED AT DEPOT</type><description>Confirmed at depot</description><locality>Dunstable</locality><countrycode>GB</countrycode><imagekey></imagekey></trackingevent><trackingevent><date>2022-01-29T12:22:00Z</date><code>8</code><type>FAILED CALLING CARD</type><description>Unable to deliver, calling card left - 12823420</description><locality>Dunstable</locality><countrycode>GB</countrycode><imagekey>S5^0022*19753*667*3600*72*008**1</imagekey></trackingevent><trackingevent><date>2022-01-29T05:56:00Z</date><code>15</code><type>OUT FOR DELIVERY</type><description>On vehicle for delivery</description><locality>Dunstable</locality><countrycode>GB</countrycode><imagekey></imagekey></trackingevent><trackingevent><date>2022-01-29T01:14:00Z</date><code>4</code><type>CONFIRMED AT DEPOT</type><description>Confirmed at depot</description><locality>Dunstable</locality><countrycode>GB</countrycode><imagekey></imagekey></trackingevent><trackingevent><date>2022-01-29T01:12:00Z</date><code>4</code><type>CONFIRMED AT DEPOT</type><description>Confirmed at depot</description><locality>Dunstable</locality><countrycode>GB</countrycode><imagekey></imagekey></trackingevent><trackingevent><date>2022-01-28T22:57:00Z</date><code>9</code><type>HUB SCAN</type><description>Confirmed at Hub</description><locality>Hub 4 - Hinckley</locality><countrycode>GB</countrycode><imagekey></imagekey></trackingevent><trackingevent><date>2022-01-28T10:31:00Z</date><code>0</code><type>PARCEL DATA</type><description>Customer data received</description><locality>Sheffield</locality><countrycode>GB</countrycode><imagekey></imagekey></trackingevent></trackingevents></trackingdetail></trackingdetails></trackingresponse>");
            //no trackingevents
           // response = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><trackingresponse><trackingdetails><trackingdetail><trackingnumber>155020221598468</trackingnumber><parcelnumber>15502022159846</parcelnumber><consignmentnumber>2022159829</consignmentnumber><deliveryaddress1>Sm Mark Simkin</deliveryaddress1><deliveryaddress2>The Fa</deliveryaddress2><deliverylocality>Unit B Beacon Business Park</deliverylocality><deliverycity>Weston Rd</deliverycity><deliverycounty>Stafford</deliverycounty><deliverypostcode>ST18 0WL</deliverypostcode><deliverycountrycode>GB</deliverycountrycode><etastart>1970-01-01T10:55:00+01:00</etastart><etaend>1970-01-01T11:55:00+01:00</etaend><parcelcode>15502022159846*19749</parcelcode><trackingevents></trackingevents></trackingdetail></trackingdetails></trackingresponse>");
            //paper copy delivery test
            //response = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><trackingresponse><trackingdetails><trackingdetail><trackingnumber>15502022579536S</trackingnumber><parcelnumber>15502022579536</parcelnumber><consignmentnumber>2022579536</consignmentnumber><deliveryaddress1>Fitness Footwear</deliveryaddress1><deliveryaddress2>Unit 6 North Kettering Business Par</deliveryaddress2><deliverylocality>Kettering</deliverylocality><deliverypostcode>NN14 1UA</deliverypostcode><deliverycountrycode>GB</deliverycountrycode><signedfordate>2022-01-27</signedfordate><parcelcode>15502022579536*19750</parcelcode><trackingevents><trackingevent><date>2022-01-27T15:21:00Z</date><code>65</code><type>PAPER IMAGE</type><description>Customer Authorisation image available</description><locality>Hinckley</locality><countrycode>GB</countrycode><imagekey>X^28012200001072000000016074894Y</imagekey></trackingevent><trackingevent><date>2022-01-27T15:21:00Z</date><code>1</code><type>DELIVERED</type><description>Delivered (Manifest delivery), received by ANA</description><locality>Hinckley</locality><countrycode>GB</countrycode><imagekey>P^</imagekey></trackingevent><trackingevent><date>2022-01-27T09:21:00Z</date><code>6</code><type>REDELIVERY REQUEST</type><description>Redelivery request (bookin) made for Thu 27 January 2022 at 11:00</description><locality>Hinckley</locality><countrycode>GB</countrycode><imagekey></imagekey></trackingevent><trackingevent><date>2022-01-27T05:57:00Z</date><code>4</code><type>CONFIRMED AT DEPOT</type><description>Confirmed at depot</description><locality>Hinckley</locality><countrycode>GB</countrycode><imagekey></imagekey></trackingevent><trackingevent><date>2022-01-26T22:54:00Z</date><code>9</code><type>HUB SCAN</type><description>Confirmed at Hub</description><locality>Hub 4 - Hinckley</locality><countrycode>GB</countrycode><imagekey></imagekey></trackingevent><trackingevent><date>2022-01-26T11:49:00Z</date><code>0</code><type>PARCEL DATA</type><description>Customer data received</description><locality>Sheffield</locality><countrycode>GB</countrycode><imagekey></imagekey></trackingevent></trackingevents></trackingdetail></trackingdetails></trackingresponse>");            
        } catch(Exception e) {
            LOGGER.error(" -- Error in DPD WS call -- ", e);
            return null;
        }
    	String stringResponse = response.toString();  
        // TEST=============
//    	stringResponse = ApplicationGlobalConfig.TEST_RESPONSE;
        // TEST=============
    	if (!stringResponse.contains("<trackingevents>")){
    		LOGGER.error("-- The response does not contain tracking events --");
    		return null;
    	}
    	stringResponse = stringResponse.substring(stringResponse.indexOf("<trackingevents>"), stringResponse.indexOf("</trackingevents>"));
    	int code = checkLastEventCode(stringResponse);
    	LOGGER.info(" -- Response to evaluate from events: " + stringResponse);
    	if (code == DPD_NO_CODE){
    		LOGGER.info(" -- DPD not deliveried, out for delivery or parcel data situations --");
			return null;  
    	}
		LOGGER.info(" -- The response does include a valid code (" + code + ") --" );
    	info[3] = retrieveInitialParcelDate(stringResponse);
		//No matter what is the last event, if we have "Parcel Reconsigned RTC" in the response.
    	if (stringResponse.contains("PARCEL RECONSIGNED RTC")){
    		info[6] = "DPD_FAILING_CALLING_CARD_TWICE";
    		return info;
    	}
    	//We have that RTC or not RTC, parcel reconsigned is code 53
    	if (code == DPD_RTC_CODE ){
    		info[6] = "PARCEL_RECONSIGNED";
    		return info;
    	}
		if (code == DPD_OUT_FOR_DELIVER_CODE){
			LOGGER.info(" -- Out for delivery situation --");
			if (stringResponse.contains("<etastart>") && stringResponse.contains("<etaend>")){
				int pos = stringResponse.indexOf("<trackingevent><date>");
        		int pos_size = "<trackingevent><date>".length();
        		String date_response = stringResponse.substring(pos + pos_size, pos + pos_size + 19);
        		date_response = date_response.substring(0,date_response.indexOf("T")+1);	        		
        		LOGGER.info(" -- Date out for delivery without ETA: " + date_response);
//				int eta_pos = stringResponse.indexOf("<etastart>");
//        		int eta_pos_size = "<etastart>".length();
//        		String eta_start_time= stringResponse.substring(eta_pos + eta_pos_size, eta_pos + eta_pos_size + 5);
//        		LOGGER.info(" -- Eta start time: " + eta_start_time);
        		int etaend_pos = stringResponse.indexOf("<etaend>");
        		int etaend_pos_size = "<etaend>".length();
        		String eta_end_time= stringResponse.substring(etaend_pos + etaend_pos_size, etaend_pos + etaend_pos_size + 5);
        		LOGGER.info(" -- Eta end time: " + eta_end_time);
        		info[1] = date_response + eta_end_time;
        		info[1] = info[1].replace("Z", "");
        		LOGGER.info("-- Returning info, out for delivery with etas: " + info[1]);
        		return info;
			}else{
    			LOGGER.info(" -- The response includes OUT FOR DELIVERED --");
    			int pos = stringResponse.indexOf("<trackingevent><date>");
        		int pos_size = "<trackingevent><date>".length();
        		String date_response = stringResponse.substring(pos + pos_size, pos + pos_size + 19);
        		LOGGER.info(" -- Date out for delivery: " + date_response);
        		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        		LocalDateTime ldt = LocalDateTime.parse(date_response, dtf);
        		ldt = ldt.plusHours(2);
        		String ldts = ldt.format(dtf);
        		ldts = ldts.replace("Z", "");
        		LOGGER.info(" -- ETA (2 hours): " + ldts);             		
        		info[1] = ldts;
    			return info;
			}
		}
		if (code == DPD_PARCEL_DATA_CODE){
			LOGGER.info(" -- The response includes PARCEL DATA, trying to return this date into shipped label --");
			try{
				info[2] = retrieveInitialParcelDate(stringResponse);
    			return info;
			}catch(Exception e){
				LOGGER.error(" -- Error extracting the parcel data -- ", e);
			}
		}			  		    	    	
    	if (code == DPD_DELIVERED_CODE || code == DPD_DELIVERED_IMAGE_COPY_AVAILABLE_CODE){
    		int pos = stringResponse.indexOf("<trackingevent><date>");
    		int pos_size = "<trackingevent><date>".length();
    		String date_response = stringResponse.substring(pos + pos_size, pos + pos_size + 19);
    		LOGGER.info(" -- Substring result: " + date_response + " ,pos is: " + pos + ", search string lengh: " + pos_size);
    		info[0] = date_response;
    		return info;
    	}  
    	//We have received twice "Failed - Calling Card"
    	if (2 == StringUtils.countMatches(stringResponse, "FAILED CALLING CARD")){
    		info[5] = "DPD_FAILING_CALLING_CARD_TWICE";
    		return info;
    	}
    	//We have received once "Failed - Calling Card"
    	if (1 == StringUtils.countMatches(stringResponse, "FAILED CALLING CARD")){
    		info[4] = "DPD_FAILING_CALLING_CARD_ONCE";
    		return info;
    	}
    	if (stringResponse.contains("PARCEL DATA")){
    		LOGGER.info(" -- Contains parcel data info but it is not the last event, not out for delivery yet --");
    		String[] events = stringResponse.split("<trackingevent>");
    		if (events.length < 2){
    			LOGGER.info(" -- Elements lengh < 2, there are not tracking envents --");
    			return null;
    		}
    		if (events.length > 2){
    			LOGGER.info("Number of events: " + events.length + " ,last event: " + events[events.length - 1] + " and before: " + events[events.length - 2]);
    		}
	    	String first_event = events[events.length-1];//The biggest number is the first event
	    	String first_event_date = first_event.substring(first_event.indexOf("<date>") + "<date>".length()
					, first_event.indexOf("</date>"));
			LOGGER.info("-- Last event date extracted for parcel data last option:" + first_event_date);
			if (first_event_date.contains("+")){
				first_event_date = first_event_date.substring(0, first_event_date.indexOf("+"));
			}
			first_event_date = first_event_date.replace("Z", "");
			LOGGER.info(" final format: " + first_event_date);
			info[2]= first_event_date; //actually Parcel data date (first event)
			return info;    	
    	}
    	LOGGER.info(" -- DPD not deliveried, out for delivery or parcel data situations --");
		return null;
    }    
    
	private String retrieveInitialParcelDate(String stringResponse) {
		if (stringResponse.contains("PARCEL DATA")){
    		LOGGER.info(" -- Contains parcel data info --");
    		String[] events = stringResponse.split("<trackingevent>");    		
    		if (events.length < 2){
    			LOGGER.info(" -- Elements lengh < 2, there are not tracking envents --");
    			return null;
    		}
	    	String first_event = events[events.length-1];
	    	String first_event_date = first_event.substring(first_event.indexOf("<date>") + "<date>".length()
					, first_event.indexOf("</date>"));
			LOGGER.info("-- Last event date extracted for parcel data last option:" + first_event_date);
			if (first_event_date.contains("+")){
				first_event_date = first_event_date.substring(0, first_event_date.indexOf("+"));
			}
			first_event_date = first_event_date.replace("Z", "");
			LOGGER.info(" final format: " + first_event_date);
			//LOGGER.info(" -- The real last event is: " + events[1] + " ,and its date is: " + getEventDate(events[1]));
			LOGGER.info("event date: " + getEventDate(first_event) + " ,equals? " + first_event_date);
			return first_event_date;    	
    	}
		LOGGER.warn("-- It does not include PARCEL DATA string --");
		return "";
	}
	
	String getEventDate(String event){
		if (event == null || "".equals(event) || !event.contains("<date>")){
			return "";
		}
		String last_event_date = event.substring(event.indexOf("<date>") + "<date>".length()
				, event.indexOf("</date>"));
		LOGGER.info("-- Last event date extracted from event method:" + last_event_date);
		if (last_event_date.contains("+")){
			last_event_date = last_event_date.substring(0, last_event_date.indexOf("+"));
		}
		last_event_date = last_event_date.replace("Z", "");
		LOGGER.info(" final format: " + last_event_date);
		return last_event_date; 
	}
	
	private int checkLastEventCode(String sr) {
		LOGGER.info("-- Checking last event code in DPD --");
		if (StringUtils.isBlank(sr) || !sr.contains("<code>")){
			return DPD_NO_CODE;
		}
		String[] events = sr.split("<trackingevent>");	
		if (events.length < 2){
			LOGGER.info(" -- Elements lengh < 2, there are not tracking events --");
			return DPD_NO_CODE;
		}
		LOGGER.info("events size: " + events.length);
		for(int i= 0; i<events.length; i++){
			LOGGER.info("array pos: " + i + "\n" + events[i]);
		}	
		String last_event = events[1];
		LOGGER.info("-- Last event string is: " + last_event);
		String last_event_code = last_event.substring(last_event.indexOf("<code>") + "<code>".length(), last_event.indexOf("</code>"));
		LOGGER.info("-- Last event code extracted:" + last_event_code);
		return Integer.valueOf(last_event_code.trim());
	}
	//Just in case you want just to compare the password obtained through a db query
	private String encryptUsingShiroConfig(String plainText) {
		DefaultSecurityManager securityManager = (DefaultSecurityManager) SecurityUtils.getSecurityManager();
		JdbcRealm realm = (JdbcRealm) securityManager.getRealms().stream().filter((r) -> {
			return r instanceof JdbcRealm;
		}).findFirst().get();
		PasswordMatcher passwordMatcher = (PasswordMatcher) realm.getCredentialsMatcher();
		return passwordMatcher.getPasswordService().encryptPassword(plainText);
	}

}
