package com.ceva.cevatrack.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ceva.cevatrack.domain.ClausedConsignmentDetail44;
import com.ceva.cevatrack.domain.Consignment44;
import com.ceva.cevatrack.domain.ConsignmentDetail44;
import com.ceva.cevatrack.domain.Driver44;
import com.ceva.cevatrack.domain.Vehicle44;
import com.ceva.cevatrack.repository.P44_Claused_Details_Repository;
import com.ceva.cevatrack.repository.P44_Details_Repository;
import com.ceva.cevatrack.repository.P44_Repository;
import com.ceva.cevatrack.transformer.P44EntitiesTransformer;
import com.ceva.cevatrack.wsmicrolise.getconsignment.ClausedConsignmentDetail;
import com.ceva.cevatrack.wsmicrolise.getconsignment.Consignment;
import com.ceva.cevatrack.wsmicrolise.getconsignment.ConsignmentDetail;


@Stateless(name = "com.ceva.cevatrack.service.P44ServiceImpl")
public class P44ServiceImpl implements P44Service{
	private static final Logger log = LoggerFactory.getLogger(P44ServiceImpl.class);
    
    @Inject
    private P44_Repository p44;
    @Inject
    private P44_Details_Repository p44_details;
    @Inject
    private P44_Claused_Details_Repository p44_cdetails;
    	
    	
    public Consignment getConsignment(String ref){
	    log.info("-- Starting method that recovers consignment44 by reference: " + ref + " --");
	    log.info(" -- p44 ref: " + p44);
	    if (p44 == null){
	    	log.error("-- p44 repository was null....");
	    	return null;
	    }
	    Consignment44 consignment = p44.getConsingmentByRef(ref);
	    if (consignment == null){
	    	log.warn(" -- Not consignment found with ref: " + ref);
	    	return null;
	    }
	    log.info(" -- Consignment recovered: " + consignment);
	    List<ConsignmentDetail44> cdetails = p44_details.getConsignmentDetails(ref);
	    ArrayList<ConsignmentDetail> cdv = null;
	    if (cdetails == null){
	    	log.warn(" -- No consignment details found for ref: " + ref + " ,contents tab will be empty..");	    	
	    }else{	  
	    	log.info(" -- Details are not null, size: " + cdetails.size());
		    ArrayList<ClausedConsignmentDetail> cdvo = new ArrayList<ClausedConsignmentDetail>();
		    cdv = new ArrayList<>();
		    for (int i = 0; i < cdetails.size(); i++){
		    	List<ClausedConsignmentDetail44> ccdetails = p44_cdetails.getClausedConsignmentDetails(cdetails.get(i).getDetailRef());	
		    	ClausedConsignmentDetail ccdv = null;
		    	for (int j = 0; j < ccdetails.size(); j++){	
		    		ccdv = P44EntitiesTransformer.getViewClausedDetail(ccdetails.get(j));
			    	cdvo.add(ccdv);
		    	}
		    	cdv.add(i, P44EntitiesTransformer.getViewConsignmentDetail(cdetails.get(i)));
		    }	
		    log.info(" -- Consignment details transformed available in this consignment: " + cdv.size());	
	    }    	    	  
	    List<Driver44> drivers = p44.getDrivers(ref);
	    List<Vehicle44> vehicles = p44.getVehicles(ref);	
	    Consignment cvo = P44EntitiesTransformer.getViewConsignment(consignment, cdv, drivers, vehicles);
	    log.info(" -- Consignment created: " + cvo);
	    return cvo;
    }
    	
}
