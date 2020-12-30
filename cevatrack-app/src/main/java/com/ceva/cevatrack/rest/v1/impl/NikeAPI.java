package com.ceva.cevatrack.rest.v1.impl;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.xml.ws.WebServiceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ceva.cevatrack.domain.OrderLookUp;
import com.ceva.cevatrack.domain.SmartNetClient;
import com.ceva.cevatrack.domain.SmartNetClientTest;
import com.ceva.cevatrack.repository.SmartNetClientRepository;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import javax.ws.rs.PathParam;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.Consumes;


import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Disposes;
//import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.io.File;
import java.io.InputStream;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.ceva.security.aaf.exception.AAFException;
import com.ceva.security.aaf.service.AAFUserService;
import com.ceva.security.aaf.service.AAFUserServiceImpl;

import com.ceva.security.aaf.domain.AAFUser;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;

import com.ceva.cevatrack.wsmicrolise.WsMicroliseComms;
import com.ceva.cevatrack.wsmicrolise.getconsignment.Consignment;

/*
 * Input Pack List number from Nike
 * Output
 * 		- Date Shipped (DB)
 *      - Estimated Arrival Date (ETA from WS)
 *      - Actual Arrival Date (ATA from WS)
 *      
 * Responses: 403 Auth failed, 400 empty Input number, 404 input not found in DB, 200 OK, (even if not found in WS then output just shipped date
 * 500 INternal server error when WS down??--> opposite to the requirements mentioned before..
 */
@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON) //Now the result is a JSON document for all the methods of this class, you can apply this notation individually but ignores it..
//@Produces(MediaType.APPLICATION_XML)
//@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Transactional
public class NikeAPI implements Serializable{
			
	private static final long serialVersionUID = 1L;
	private String id;

//	@Inject
//    @PersistenceUnitName(value="generalPU")
    //private EntityManagerFactory entityManagerFactory;
	
//	@PersistenceContext(unitName = "cevatrackPU") //Not working..
//	protected EntityManager em;
    //private static String PERSISTENCE_UNIT_NAME = "cevatrackPU";
	private static String PERSISTENCE_UNIT_NAME = "cevatrackPU";
    private static EntityManagerFactory emf;
    //private static EntityManager em;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(NikeAPI.class);
	Subject currentUser;
	
    @Inject
    private SmartNetClientRepository smartNetClientRepository;
	
    @Inject
    private EntityManager entityManager;
	
//	@Inject
//	private EntityManagerFactory emf;  //Not found in the context...
    /*
     * It returns http code 400 when there is no input parameter
     */
	@GET
    @Path("/getPackListNumberInfo")
	@Produces(MediaType.APPLICATION_XML)
	public Response getPackListNumberInfo(){
		LOGGER.info("--- The methos was called without parameters..");
		return Response.status(400).entity("The request is malformed").build();
	}
	/*
	 * It returns the info for the Pack List Number passed as a parameter
	 */
	@GET
    @Path("/getPackListNumberInfo/{id}")
    public Response getPackListNumberInfo(@PathParam("id") String id, @Context HttpHeaders headers, @Context HttpServletRequest req){ //throws JSONException{
		LOGGER.info(" -- Starting getPackListNumberInfo REST method..");
		
		//ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		Map<String, String[]> parameters = req.getParameterMap();//nothing
		
		   for (Object key: parameters.keySet())
		    {
		            String keyStr = (String)key;
		            String[] value = (String[])parameters.get(keyStr);
		            LOGGER.info("Key" + (String)key + "   :   " + Arrays.toString(value));
		    }
		
		
		if (id == null || id == "" || id == "null"){ //Redirect to localhost tomcat welcome page
			return Response.status(400).entity("No parameter was included on the call..").build();			
		}
		
		//List<String> authHeaders = headers.getRequestHeader(HttpHeaders.AUTHORIZATION);//KO, null
		this.id = id; // Temporal assignment (not needed)
				
		int response_auth = authenticateUser(headers);
		
		switch (response_auth){
			case -2:
				LOGGER.error(" -- Authentication error code: -2");
				Response.status(403).entity("There was a problem with the user Login").build();
				break;
			case -3:
				LOGGER.error(" -- Authentication error code: -3");
				Response.status(403).entity("There was a problem with the user Login").build();
				break;
			case 0:
				LOGGER.info(" Authentication of the user was correct! ");
				break;
			case 1:
				LOGGER.error(" -- Authentication error code: 1");
				Response.status(403).entity("Unknown Account exception").build();
				break;
			case 2:
				LOGGER.error(" -- Authentication error code: 2");
				Response.status(403).entity("Incorrect user - password combination").build();
				break;
			case 3:
				LOGGER.error(" -- Authentication error code: 3");
				Response.status(403).entity("Basic authentication is required").build();
				break;			
		}
		
		
		int resultDB = createEntityManager();
		
		if (resultDB == -1){
			return Response.status(500).entity("Data base was not accessible").build();
		}
		
		String[] consignment_dateShipped = read_nike_consignment(id);
		
		if (consignment_dateShipped == null){
			LOGGER.info("-- The pack list number did not returned any result.. ");
			return Response.status(400).entity("The pack list number is incorrect").build();
		}
				
		// Create data Bean response		
		
		return createResponse(consignment_dateShipped);
				
    }
	
    private Response createResponse(String[] consignment_dateShipped) {
    	LOGGER.info(" -- Starting method createResponse with parameters, consignment: " + consignment_dateShipped[0] + " and, date shipiped: " + consignment_dateShipped[1] );
    	
    	//Set the variables in the Bean and send it as a entity body for the response
    	NikeResponseBean responseBean = new NikeResponseBean();
    	responseBean.setDate_shipped(consignment_dateShipped[1]);
    	responseBean.setActual_arrival_date("");
    	responseBean.setEstimated_arrival_date("");
    	
    	List<SmartNetClientTest> matchingSmartNetClients = entityManager.createQuery("SELECT sn FROM SmartNetClientTest sn WHERE sn.prefixes LIKE :p").setParameter("p", "NIKE").getResultList();
		//entityManager().createQuery("SELECT sp FROM SparePart sp WHERE sp.process_id LIKE :p").setParameter("p", processId).getResultList();
        //List<SmartNetClient> matchingSmartNetClients = smartNetClientRepository.findAll();
		if (matchingSmartNetClients.get(0) != null){
			LOGGER.info(" prefixes field is: " + matchingSmartNetClients.get(0).getPrefixes() );
		}
		LOGGER.info(" -- query was successfull: " + matchingSmartNetClients.size());
//		closeEntityManager(entityManager);
        //return Response.ok().entity("Service online, param: " +id + ", clients recovered: " + matchingSmartNetClients.size()).build();		
		//return Response.ok().entity(responseBean).build();
		WsMicroliseComms ws = new WsMicroliseComms();
		Consignment resultWS = null;
		try {
			resultWS = ws.getConsignment(consignment_dateShipped[0], "smartnet");
	    	responseBean.setActual_arrival_date(resultWS.getAtaString());
	    	//responseBean.setEstimated_arrival_date(resultWS.getEtaString());//This info is null..
	    	responseBean.setEstimated_arrival_date(resultWS.getPtaString());
	    	LOGGER.info(" -- WS getConsignment call was successfull, recoverd output Bean: " + responseBean);
		} catch (Exception e) {
			LOGGER.error(" -- Querying the consignment caused and exception, only shipped date will be returned..  ", e);
			Response.ok().entity("{ " + "'shipped date': '" + responseBean.getDate_shipped() + "'}").build();
		}
		
		return Response.ok().entity("{'arrival date': '" + responseBean.getActual_arrival_date() + "', 'estimated date': '" + responseBean.getEstimated_arrival_date() + "', 'shipped date': '" + responseBean.getDate_shipped() + "'}").build();
	}
    
	private String[] read_nike_consignment(String id) {
		LOGGER.info("-- String method read_nike_consignment..");
		OrderLookUp OLResult = null;
		try{
			OLResult = (OrderLookUp) entityManager.createQuery("SELECT ol FROM OrderLookUp ol WHERE ol.client_ref LIKE :p").setParameter("p", id).getSingleResult();
		}catch(Exception e){
			LOGGER.warn(" -- The query with parameter: " + id + ", did not return an expected result..");
		}
		if (OLResult != null){
			LOGGER.info(" -- The id: " + id + " returned " + OLResult);
			String pattern = "yyyy-MM-dd HH:mm:ss"; //"MM/dd/yyyy HH:mm:ss";
			DateFormat df = new SimpleDateFormat(pattern);
			String date_shipped = df.format(OLResult.getShipped_date());
			String[] stringRes = {OLResult.getMl_order_ref() , date_shipped};
			return stringRes;
		}else{
			LOGGER.info("No results for the id: " + id + " matching the table..");
			return null;
		}		
		
	}
	
	private int createEntityManager() {
		int result = -1; 
		EntityManagerFactory emfact = null;
		Properties systemProps = System.getProperties();//OK
//		for (Map.Entry m:systemProps.entrySet()){
//			LOGGER.info(" key: " + m.getKey() + " ,value: " + m.getValue());
//		}
		// application.environment= Cevatrack, TEST, Development    from environment.properties which comes from system.properties (in this file you have environment.code= LOCAL
		if (systemProps.get("environment.code") != null){
			LOGGER.info(" -- Value of the property 'environment.code': " + systemProps.get("environment.code"));
			LOGGER.info(" -- 2nd -- Value of the property 'environment.code': " + systemProps.get("environment.code").toString().trim());
			Map<String, String> properties = new HashMap<String, String>();
			properties.put("openjpa.ConnectionDriverName", "org.postgresql.Driver");			
			//TODO use a properties file
			if (systemProps.get("environment.code").toString().trim().equalsIgnoreCase("LOCAL")){
				LOGGER.info(" ----------- We are in the Local environment and its DB configuration!!");//OK
				properties.put("openjpa.ConnectionURL", "jdbc:postgresql://localhost:5432/cevatrack_v6?currentSchema=cevatrack_mix");	
				properties.put("openjpa.ConnectionUserName", "postgres");
				properties.put("openjpa.ConnectionPassword", "holahola");	
			}
			if (systemProps.get("environment.code").toString().trim().equalsIgnoreCase("Development")){
				LOGGER.info(" ----------- We are in the Development environment and its DB configuration!!");//OK
				properties.put("openjpa.ConnectionURL", "jdbc:postgresql://iahlvlpgdb002:5432/cevatrack_responsive");	
				properties.put("openjpa.ConnectionUserName", "cevatrack_responsive");
				properties.put("openjpa.ConnectionPassword", "cevatrack_responsive");				
			}
			if (systemProps.get("environment.code").toString().trim().equalsIgnoreCase("TEST")){
				LOGGER.info(" ----------- We are in the TEST environment and its DB configuration!!");//OK
				properties.put("openjpa.ConnectionURL", "jdbc:postgresql://glsdvlpgdb074:5432/cevatrack_responsive?currentSchema=cevatrack_responsive");
				properties.put("openjpa.ConnectionUserName", "cevatrack_responsive");
				properties.put("openjpa.ConnectionPassword", "NqHyxoHGwA56P");				
			}
			if (systemProps.get("environment.code").toString().trim().equalsIgnoreCase("Cevatrack")){
				LOGGER.info(" ----------- We are in the Cevatrack (PROD) environment and its DB configuration!!");//OK
				properties.put("openjpa.ConnectionURL", "jdbc:postgresql://glpdvlpgdb074:5432/cevatrack_responsive?currentSchema=cevatrack_responsive");	
				properties.put("openjpa.ConnectionUserName", "cevatrack_responsive");
				properties.put("openjpa.ConnectionPassword", "LqEKbxqZvUWb");				
			}
			
			emfact = Persistence.createEntityManagerFactory("cevatrackPU", properties);
			
		}else{
			LOGGER.error(" System properties get('environment.code') is null ..");
			return -1;
			}
						
		if (emfact != null){
			LOGGER.info("I created manually the context...");
			if (entityManager == null){				
				entityManager = emfact.createEntityManager();
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
		if (authHeader.startsWith("Basic")){
			LOGGER.info(" -- Basic authentication detected.."); //OK
		}else{
			return 3;
		}
		try {
			tokens = (new String(Base64.getDecoder().decode(authHeader.split(" ")[1]), "UTF-8")).split(":");
			final String username = tokens[0];
			final String password = tokens[1];
			LOGGER.info(" user in the Basic Auth: " + username + " ,password: " + password); //OK	
			//AAFUserService aaf = new AAFUserServiceImpl();
			//AAFUser user = (AAFUser) this.userRepository.findByUserPrincipal(userName.toLowerCase()).orElse((Object) null);
			
			LOGGER.info(" -- encrypting password: " + "Holaquetal123!" + ", result: " + encryptUsingShiroConfig("Holaquetal123!"));
			result = doShiroLogin(username, password);
			
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
    
    private int doShiroLogin(String userName, String password){
    	LOGGER.info(" -- Doing shiro login..");
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
		        //if (currentUser.isPermitted("aaf_permission:time:*")) {
			    if (currentUser.isPermitted("sn_client:view:nike")) {
		            //LOGGER.info(" -- Permission for no limit session time enable for this user.. ");
			    	LOGGER.info(" -- This user has permission to view Nike instances..");	
			    	result = 0;
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
//			if (!var12.getMessage().contains("LDAP naming error")){ //Not interested in LDAP error atm
				//LOGGER.warn(var12.getMessage(), var12);
			//}
		}	
		return result;
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

	
//    public static void createEntityManager() {
//        // Change the default path of the persistence file
//        Thread.currentThread().setContextClassLoader(new ClassLoader() {
//          @Override
//          public Enumeration<URL> getResources(String name) throws IOException {
//            if (name.equals("META-INF/persistence.xml")) {
//              return Collections
//                  .enumeration(Arrays.asList(new File(PATH_FILE_PERSISTENCE).toURI().toURL()));
//            }
//            return super.getResources(name);
//          }
//        });
//        LOGGER.info(" createEntityManager ok..");
//        emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
//        // if it is not possible to create EntityManager,
//        // try adding org.postgresql.Driver or change the run configuration using JRE
//        em = emf.createEntityManager();
//      }
    /**
     * closes the entity manage.
     * 
     * @param entityManager
     *            class
     */
//    public void closeEntityManager(@Disposes EntityManager entityManager) {
//        if (entityManager != null && entityManager.isOpen()) {
//            entityManager.close();
//        }
//    }

}
