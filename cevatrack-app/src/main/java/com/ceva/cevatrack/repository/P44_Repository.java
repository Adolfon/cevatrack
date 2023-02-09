package com.ceva.cevatrack.repository;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.persistence.NoResultException;

import org.apache.deltaspike.data.api.AbstractFullEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ceva.cevatrack.domain.ClausedConsignmentDetail44;
import com.ceva.cevatrack.domain.Consignment44;
import com.ceva.cevatrack.domain.ConsignmentDetail44;
import com.ceva.cevatrack.domain.Driver44;
import com.ceva.cevatrack.domain.Vehicle44;

@Repository(forEntity = Consignment44.class)
public abstract class P44_Repository extends AbstractFullEntityRepository<Consignment44, String>{
	private static final Logger LOG = LoggerFactory.getLogger(P44_Repository.class);
    public Consignment44 findByRef(String ref){
    	return findBy(ref);
    }
    
    public Consignment44 getConsingmentByRef(String ref) {
    	LOG.info(" -- search consingment44 method --");
        try {
        	Consignment44 cons = (Consignment44) entityManager().createQuery("SELECT c FROM Consignment44 c WHERE c.consignmentreference = :r")
        			.setParameter("r", ref).getSingleResult();
          LOG.info(" -- Result of the query: " + cons);
          return cons; 
        }
        catch(NoResultException nre){
          LOG.warn(" -- The query to consingment data base did not return any result, maybe bc it is not a p44 consignment, parameter: " + ref);
          return null;
        }
        catch (Exception e) {
          LOG.error(" -- Error executing consingment record search with ref: " + ref + " ,cause: " + e.getMessage(), e);
          return null;
        }
      }

    public List<ConsignmentDetail44> getConsignmentDetails(String consignmentRef){
    	LOG.info("-- Consignment details id from consignment44 class: " + consignmentRef);
    	@SuppressWarnings("unchecked")
		List<ConsignmentDetail44> details = entityManager().createQuery("SELECT cd FROM ConsignmentDetail44 cd WHERE cd.consignmentReference = :r")
    			.setParameter("r", consignmentRef).getResultList();
    	if (details == null){
    		return null;
    	}
    	LOG.info("-- Consignment details found: " + details.size() + " ,first: " + details.get(0));    		      	    	
    	return details;
    }
    
    public List<ClausedConsignmentDetail44> getClausedConsignmentDetails(String cdRef){
    	LOG.info("-- Claused consignment details consignments reference passed: " + cdRef);
    	@SuppressWarnings("unchecked")
		List<ClausedConsignmentDetail44> ccdetails = entityManager().createQuery("SELECT ccd FROM ClausedConsignmentDetail44 ccd WHERE ccd.consignmentDetailRef = :p")
    			.setParameter("p", cdRef).getResultList();
    	if (ccdetails == null){
    		return null;
    	}
    	LOG.info("-- Claused consignment details found: " + ccdetails.size() + " ,first: " + ccdetails.get(0));    	 	    	
    	return ccdetails;
    }
    
    public List<Driver44> getDrivers(String cRef){
    	LOG.info("-- Drivers consignments reference parameter: " + cRef);
    	@SuppressWarnings("unchecked")
		List<Driver44> drivers = entityManager().createQuery("SELECT d FROM Driver44 d WHERE d.consignmentReference = :p")
    			.setParameter("p", cRef).getResultList();
    	if (drivers == null){
    		return null;
    	}
    	if (drivers.size() >0){
    		LOG.info("-- Drivers found: " + drivers.size() + " ,first: " + drivers.get(0));
    	}
    	return drivers;
    }
    
    public List<Vehicle44> getVehicles(String cRef){
    	LOG.info("-- Vehicles consignments reference parameter: " + cRef);
    	@SuppressWarnings("unchecked")
		List<Vehicle44> vehicles = entityManager().createQuery("SELECT v FROM Vehicle44 v WHERE v.consignmentReference = :p")
    			.setParameter("p", cRef).getResultList();
    	if (vehicles == null){
    		return null;
    	}
    	if (vehicles.size() > 0){
    		LOG.info("-- Vehicles found: " + vehicles.size() + " ,first: " + vehicles.get(0));
    	}
    	return vehicles;
    }
    /*
     * Get the consignments between startDate and endDate
     */
    public List<Consignment44> getConsingmentByDate(Date startDate, Date endDate) {
    	LOG.info(" -- search consingment44 method --");
        try {
        	DateTimeFormatter FORMATDATE = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        	ZoneId londonZone = TimeZone.getTimeZone("Europe/London").toZoneId();
        	LocalDateTime ldt = LocalDateTime.ofInstant(startDate.toInstant(), londonZone);
        	//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	String frmDate = ldt.format(FORMATDATE);
        	String enDate = ldt.format(FORMATDATE); 
        	enDate = enDate.substring(0, 10) + " 23:59:59";
        	Calendar c1 = Calendar.getInstance(),c2 = Calendar.getInstance();
        	c1.setTime(startDate);
        	c2.setTime(endDate);
        	c1.set(Calendar.HOUR,0);
        	c1.set(Calendar.MINUTE, 0);
        	c1.set(Calendar.SECOND, 0);
        	c2.set(Calendar.HOUR,23);
        	c2.set(Calendar.MINUTE, 59);
        	c2.set(Calendar.SECOND, 59);        	
        	frmDate = frmDate.substring(0, 10) + " 00:00:00";             	
        	LOG.info(" -- From date str: " + frmDate + " ,endDate str: " + enDate + " \n-- Date formats from: " + startDate + " ,end: " + endDate
        			+ "\nCalendar format, start: " + c1.getTime() + " ,end:" + c2.getTime());
        	List<Consignment44> cons = (List<Consignment44>) entityManager().createQuery("SELECT c FROM Consignment44 c WHERE c.pta BETWEEN :stDate AND :edDate")
        			.setParameter("stDate", c1.getTime())
        				.setParameter("edDate", c2.getTime())
        					.getResultList();
        	if (cons == null){ 
        		LOG.warn(" -- The result was null --");
        		return null;
        	}
          LOG.info(" -- Result of the query, size: " + cons.size());
          return cons; 
        }
        catch(NoResultException nre){
          LOG.warn(" -- The query to consingment data base did not return any result");
          return null;
        }
        catch (Exception e) {
          LOG.error(" -- Error executing consingment record date search with start date: " + startDate + " ,end date: " + endDate, e);
          return null;
        }
      }
}
