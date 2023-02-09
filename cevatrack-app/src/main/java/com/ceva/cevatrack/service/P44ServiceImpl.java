package com.ceva.cevatrack.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

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
import com.ceva.cevatrack.wsmicrolise.findconsignmentsummaries.ConsignmentSummary;
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
    
    public List<Consignment44> getListConsignmentsByDate(Date stDate, Date edDate){
    	return p44.getConsingmentByDate(stDate, edDate);
    }
    
    public List<Consignment44> getListConsignmentsByDate(String stDate, String edDate){
    	DateTimeFormatter FORMATDATE = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    	DateTimeFormatter FORMATDATES = DateTimeFormatter.ofPattern("yyyyMMdd");
    	//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH);
    	LocalDate fromDate = LocalDate.parse(stDate, FORMATDATES);
    	LocalDate untilDate = LocalDate.parse(edDate, FORMATDATES);
    	ZoneId londonZone = TimeZone.getTimeZone("Europe/London").toZoneId();
    	Date datefrm = Date.from(fromDate.atStartOfDay(londonZone).toInstant());
    	Date dateuntil = Date.from(untilDate.atStartOfDay(londonZone).toInstant());
    	return p44.getConsingmentByDate(datefrm, dateuntil);
    }
    
    public List<ConsignmentSummary> getListConsignmentsByDateToCS(String stDate, String edDate){
    	List<Consignment44> res = this.getListConsignmentsByDate(stDate, edDate);
    	return c44ToCS(res);
    }

	private List<ConsignmentSummary> c44ToCS(List<Consignment44> res) {
		log.info(" -- Converting a list of consignment44 type to ConsignmentSummary type ..");
		if (res == null){
			log.warn(" the parameter was null ");
			return null;
		}
		ArrayList<ConsignmentSummary> result = new ArrayList<>();
		ConsignmentSummary item;
		String pattern = "yyyy-MM-dd HH:mm:ss";
		DateFormat df = new SimpleDateFormat(pattern);
		for (Consignment44 ele: res){
			Consignment cons = this.getConsignment(ele.getConsignmentreference());
			log.info(" -- Consignment successfully recovered: " + cons);
			item = new ConsignmentSummary();
			if (ele.getSitename() != null){
				item.setSiteName(ele.getSitename());
			}
			if (ele.getSiteid() !=null){
				item.setSiteId(ele.getSiteid());
			}
			if (ele.getPta() != null){
				item.setPta2(df.format(ele.getPta()));
			}
			if (cons.getPtaString() != null){
				item.setPta(cons.getPtaString());
			}
			if (ele.getPostcode() != null){
				item.setPostCode(ele.getPostcode());
			}
			//item.setOrderReferences(ele.getConsignmentcustomerorderref());
			if (ele.getCounty() != null){
				item.setCounty(ele.getCounty());
			}
			if (ele.getCountry() != null){
				item.setCountry(ele.getCountry());
			}
			if (ele.getContactnumber() != null){
				item.setContactNumber(ele.getContactnumber());
			}
			if (ele.getContactname() != null){
				item.setContactName(ele.getContactname());
			}			
			item.setConsignmentStatus(cons.getConsignmentStatus());			
			if (ele.getConsignmentreference() != null){
				item.setConsignmentReference(ele.getConsignmentreference());
			}
			if (ele.getConsignmentheaderid() != null){
				item.setConsignmentHeaderId(ele.getConsignmentheaderid());
			}
			if (ele.getAta() != null){
				item.setAta(df.format(ele.getAta()));
			}
			if (ele.getAddress3() != null){
				item.setAddress3(ele.getAddress3());
			}
			if (ele.getAddress2() != null){
				item.setAddress2(ele.getAddress2());
			}
			if (ele.getAddress1() != null){
				item.setAddress3(ele.getAddress3());
			}
			result.add(item);
		}				
		return result;
	}
    	
}
