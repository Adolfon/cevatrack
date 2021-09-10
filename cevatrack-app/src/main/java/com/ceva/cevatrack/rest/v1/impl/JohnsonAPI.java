package com.ceva.cevatrack.rest.v1.impl;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.ZoneId;
//import java.time.LocalDate;
//import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
//import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;

import javax.inject.Inject;
//import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.credential.PasswordMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ceva.cevatrack.domain.AAFUser4Nike;
import com.ceva.cevatrack.domain.ClientsAPI;

/*
 * Input Reference number from Johnson
 * Output
 * 		ClientsAPI data base row
 *      
 * Responses: 403 Auth failed, 404 input not found in DB, 200 OK
 */
@Path("/johnson")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON) //Now the result is a JSON document for all the methods of this class, you can apply this notation individually but ignores it..
//@Produces(MediaType.APPLICATION_XML)
//@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
//@Transactional
public class JohnsonAPI implements Serializable{
			
	private static final long serialVersionUID = 1L;
	private String id;
	String userppal = null; 

	private static String PERSISTENCE_UNIT_NAME = "cevatrackPULocal";
    private static EntityManagerFactory emf;
    //private static EntityManager em;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JohnsonAPI.class);
	Subject currentUser;
	//private static final DateTimeFormatter FORMATWS = DateTimeFormatter.ofPattern("dd MMM hh:mm a");
	private static final DateTimeFormatter FORMATDATE = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");	
	
    @Inject
    private EntityManager entityManager;
    /*
     * It returns http code 400 when there is no input parameter
     */
	@GET
    @Path("/getShipmentInfoByRef")
	//@Produces(MediaType.APPLICATION_XML) // Actually it returns XML type, regardless the class config
	@Produces(MediaType.APPLICATION_JSON) 
	public Response getShipmentInfoByRef(){
		LOGGER.info("--- The method was called without parameters..");
		return Response.status(400).entity("The request is malformed").build();
	}
	/*
	 * It returns the info for the Pack List Number passed as a parameter
	 */
	@GET
    @Path("/getShipmentInfoByRef/{ref}")
    public Response getShipmentInfoByRef(@PathParam("ref") String id, @Context HttpHeaders headers, @Context HttpServletRequest req){ //throws JSONException{
		LOGGER.info(" -- Starting getShipmentInfoByRef REST method..");
		if (id == null || id == "" || id == "null"){ //Redirect to localhost tomcat welcome page
			return Response.status(400).entity("No parameter was included on the call..").build();			
		}
		this.id = id;	
				
		int response_auth = authenticateUser(headers);
		
		switch (response_auth){		
			case -3:
				LOGGER.error(" -- Authentication error code: -3 (Unsupport coding exception)");
				return Response.status(403).entity("There was a problem with the user Login (unsupport encoding) ").build();					
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

		ClientsAPI jResponseBean = read_johnson_db_info(id);// Bean johnson
		
		if (jResponseBean == null){
			LOGGER.info("-- The pack list number did not returned any result.. ");
			return Response.status(404).entity("The pack list number is incorrect").build();			
		}
				
		return createResponse(jResponseBean);
				
    }
	
	/*
	 * check if the client is Active and put the user inactive after n login attempts
	 */
    private int validateUser(String userName, String pass) {
    	LOGGER.info(" --- validateUser method, user: " + userName + " ,pass: " + pass);
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
	    		if (user == null || pass == null || "".equals(user) || "".equals(pass)){
	    			return 2;
	    		}
	    		if (user.getIsLocked() || !user.getIsActive()){
	    			return 4;
	    		}
	    		if (user.getIncorrectLoginAttempts() > 3){
	    			return 4;
	    		}
	    		if (comparePlainTextToEncrypted(pass,user.getPassword())){
	    			resetNumberAttemptUser(userName);
	    			return 0;
	    		}else{
	    			LOGGER.info("--Wrong password for user: " + user.getUserPrincipal());
	    			addNumberAttemptUser(user.getUserPrincipal());
	    			return 2;
	    		}
	    			    		
			}catch(Exception e){
				LOGGER.warn(" -- The user query with ppal: " + this.userppal + ", did not return an expected result..", e);
				return 2;
			}   
    	}else{
    		LOGGER.warn(" -- There was an error creating the entity manager");
    		return -1;
    	}		
	}
	private Response createResponse(ClientsAPI cb) {
    	LOGGER.info(" -- Starting method createResponse with parameter, clientsAPIBean: " + cb);
    	String rs = "{";
    	rs = rs + "\n" + "\"carrierName\" : \"CEVA\",";
    	
    	if(cb.getCarrier_name() != null && !"".equals(cb.getCarrier_name())){
    		rs = rs + "\n" + "\"executingCN\":\"" + cb.getCarrier_name() + "\",";
    	}
    	if(cb.getTracking_number() != null && !"".equals(cb.getTracking_number()) ){
    		rs = rs + "\n" + "\"trackingNumber\":\"" + cb.getTracking_number() + "\",";
    	}
    	if(cb.getStatus() != null && !"".equals(cb.getStatus())){
    		rs = rs + "\n" + "\"status\":\"" + cb.getStatus() + "\",";
    	}
    	ZoneId londonZone = TimeZone.getTimeZone("Europe/London").toZoneId();
    	if (cb.getStatus_date() != null){        		
    		LocalDateTime ldt = LocalDateTime.ofInstant(cb.getStatus_date().toInstant(), londonZone);
    		String statDate = ldt.format(FORMATDATE);
    		rs = rs + "\n" + "\"statusDate\":\"" + statDate + "\",";    		
    	}	
    	if (cb.getRevise_delivery_date() != null){
    		LocalDateTime ldt = LocalDateTime.ofInstant(cb.getRevise_delivery_date().toInstant(), londonZone);
    		String revDate = ldt.format(FORMATDATE);
    		rs = rs + "\n" + "\"revisedDeliveryDate\":\"" + revDate + "\",";
    		LOGGER.info(" -- Time customized: " + revDate + "\n -- so far: " + rs);
    	}    	    	
    	rs = rs + "}";
    	if (rs.endsWith(",}")){
    		rs = rs.substring(0, rs.indexOf("}")-1) + "\n}"; //remove the last ","
    	}else{
    		rs = rs.substring(0, rs.indexOf("}")) + "\n}"; 
    	}
    	rs = rs + "}";
		return Response.ok().entity(rs).build();
	}
    
	
	
	private ClientsAPI read_johnson_db_info(String id) {
		LOGGER.info("-- String method read_nike_consignment..id: " + id);
		ClientsAPI clients_API = null;
		try{
			clients_API = (ClientsAPI) entityManager.createQuery("SELECT c FROM ClientsAPI c WHERE c.client_id = ?1 AND c.shipment_number = ?2").
					setParameter(1, "NUTRIC").setParameter(2, id).getSingleResult();
		}catch(Exception e){
			LOGGER.warn(" -- The query with parameter: " + id + ", did not return an expected result..\n", e);
			return null;
		}
		return clients_API;		
	}
	
	private int createEntityManager() {
		if (this.entityManager != null && entityManager.isOpen()){
			LOGGER.warn(" -- The entity manager was already created..");
			return 0;
		}
		int result = -1; 
		EntityManagerFactory emfact = null;
		Properties systemProps = System.getProperties();//OK
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
			}
			if (systemProps.get("environment.code").toString().trim().equalsIgnoreCase("CEVA-CEVATRACK-DEVL")){
				LOGGER.info(" ----------- We are in the Development environment and its DB configuration!!");//OK
				properties.put("openjpa.ConnectionURL", "jdbc:postgresql://iahlvlpgdb002:5432/cevatrack_responsive");	
				properties.put("openjpa.ConnectionUserName", "cevatrack_responsive");
				properties.put("openjpa.ConnectionPassword", "cevatrack_responsive");				
			}
			if (systemProps.get("environment.code").toString().trim().equalsIgnoreCase("CEVA-CEVATRACK-TEST")){
				LOGGER.info(" ----------- We are in the TEST environment and its DB configuration!!");//OK
				properties.put("openjpa.ConnectionURL", "jdbc:postgresql://glsdvlpgdb074:5432/cevatrack_responsive?currentSchema=cevatrack_responsive");
				properties.put("openjpa.ConnectionUserName", "cevatrack_responsive");
				properties.put("openjpa.ConnectionPassword", "NqHyxoHGwA56P");				
			}
			if (systemProps.get("environment.code").toString().trim().equalsIgnoreCase("CEVA-CEVATRACK-STAGE")){
				LOGGER.info(" ----------- We are in the Cevatrack (PROD) environment and its DB configuration!!");//OK
				properties.put("openjpa.ConnectionURL", "jdbc:postgresql://glpdvlpgdb074:5432/cevatrack_responsive?currentSchema=cevatrack_responsive");	
				properties.put("openjpa.ConnectionUserName", "cevatrack_responsive");
				properties.put("openjpa.ConnectionPassword", "LqEKbxqZvUWb");				
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
					LOGGER.error(" --- It was not possible to create the entity manager..");
					return -1;
				}
				LOGGER.info(" entityManager created manually..");						
			}
			result = 0;
		}
				
		if (entityManager == null){//Not null--> OK
			LOGGER.info(" entity manager was null.... from the new notation..");//here
			result = -1;
		}		
		return result;
	}
	
	private int authenticateUser(HttpHeaders headers) {
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
			int firstCheck = validateUser(username, password);
			LOGGER.info(" -- firstCheckUser method returned: " + firstCheck);
			this.userppal = username;
			return firstCheck; //results 0 (OK), 4(more than 3 attempts) and 2(Bad usr/psw combo)			
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("--- Error recovering tokens of the Authetication... response HTTP status 403 Forbidden");
			return -3;
		}			
	}
	
	public void initDB() {
        emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }
    
    
    public EntityManager produceEntityManager() {
        entityManager = emf.createEntityManager();
        return entityManager;
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
			if (attempts > 3){
				entityManager.createQuery("UPDATE AAFUser4Nike us SET us.isLocked = ?1 WHERE us.userPrincipal = ?2").
				setParameter(1, true).
				setParameter(2, userName).executeUpdate();
			}
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
			entityManager.getTransaction().begin();
			entityManager.createQuery("UPDATE AAFUser4Nike us SET us.incorrectLoginAttempts = ?1 WHERE us.userPrincipal = ?2").
					setParameter(1, 0).
					setParameter(2, this.userppal).executeUpdate();
			//entityManager.persist(AAFUser4Nike);
			entityManager.flush();
			entityManager.getTransaction().commit();
			entityManager.clear();
			LOGGER.info(" -- Number of incorrect attempts reseted to 0 ");    		
		}else{
			LOGGER.error(" -- Entitiy manager was not created successfully..");
		}
		
	}
    
	//Just in case you want just to compare the password obtained through a db query
    /*
     * Converts the specified plaintext password (usually acquired from your application's 'new user' or 'password reset'
     * workflow) into a formatted string safe for storage.  The returned string can be safely saved with the
     * corresponding user account record (e.g. as a 'password' attribute).
     * 
     * @param plaintextPassword the raw password as 'byte-backed' object (String, character array, {@link ByteSource},
     *                          etc) usually acquired from your application's 'new user' or 'password reset' workflow.
     * @return the encrypted password, formatted for storage.
     * @throws IllegalArgumentException if the argument cannot be easily converted to bytes as defined by
     */
	private String encryptUsingShiroConfig(String plainText) {
		DefaultSecurityManager securityManager = (DefaultSecurityManager) SecurityUtils.getSecurityManager();
		JdbcRealm realm = (JdbcRealm) securityManager.getRealms().stream().filter((r) -> {
			return r instanceof JdbcRealm;
		}).findFirst().get();
		PasswordMatcher passwordMatcher = (PasswordMatcher) realm.getCredentialsMatcher();		
		return passwordMatcher.getPasswordService().encryptPassword(plainText);
	}
	
	/*
	 * Compares plain text password to the encrypted storaged one
	 * @param plainText is the plain uncrypted password
	 * @param toCompare is the encrypted DB password
	 * @return true if matches the plain encrypted password with the DB encrypted one
	 */
	private boolean comparePlainTextToEncrypted(String plainText, String toCompare) {
		DefaultSecurityManager securityManager = (DefaultSecurityManager) SecurityUtils.getSecurityManager();
		JdbcRealm realm = (JdbcRealm) securityManager.getRealms().stream().filter((r) -> {
			return r instanceof JdbcRealm;
		}).findFirst().get();
		PasswordMatcher passwordMatcher = (PasswordMatcher) realm.getCredentialsMatcher();
		if (passwordMatcher.getPasswordService().passwordsMatch(plainText, toCompare)){
			LOGGER.info(" -- They do match using the service --");
			return true;
		}		
		return false;
	}

}
