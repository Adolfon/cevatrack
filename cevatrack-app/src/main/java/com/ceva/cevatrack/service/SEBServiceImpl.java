package com.ceva.cevatrack.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import org.apache.shiro.subject.Subject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ceva.cevatrack.domain.SEBOrder;
import com.ceva.cevatrack.repository.SEBRepository;
import com.ceva.cevatrack.util.ApplicationGlobalConfig;
import com.ceva.cevatrack.vo.Drop;
import com.ceva.cevatrack.vo.JourneyVO;


@Stateless(name = "com.ceva.cevatrack.service.SEBServiceImpl")
public class SEBServiceImpl implements SEBService{
	private static final Logger log = LoggerFactory.getLogger(SEBServiceImpl.class);
	private String AUTOMOVILE_SITE_PERM = "am_site:view:";
	private String AUTOMOVILE_CLIENT_PERM = "am_client:view:";
	private String AUTOMOVILE_CLIENT_PERM_GLOBAL = "am_client:view:*";
	private String AUTOMOVILE_SITE_PERM_GLOBAL = "am_site:view:*";
	
    @Inject
    private SEBRepository seb;
    	
	public List<JourneyVO> getJourneysByDateTime(String fromdate, int extraDays){
		log.info(" -- Start getJourneysByDateTime method --");
		SimpleDateFormat formatter =new SimpleDateFormat("yyyy-MM-dd");
		 Date date1;
		try {
			date1 = formatter.parse(fromdate);
			return transformToVOList(seb.searchJourneysByDateHours(date1, 0));
		} catch (ParseException e) {
			log.error(" -- Error parsing date or transforming data");
		}
		return null;
	}
	
	public List<JourneyVO> getJourneysByDateTimeUserFiltered(String fromdate, Subject user){
		log.info(" -- Start getJourneysByDateTimeUserFiltered method --");
		SimpleDateFormat formatter =new SimpleDateFormat("yyyy-MM-dd");
		 Date date1;
		try {
			date1 = formatter.parse(fromdate);
			return transformToVOListForUser(seb.searchJourneysByDateHours(date1, 0), user);
		} catch (ParseException e) {
			log.error(" -- Error parsing date or transforming data");
		}
		return null;
	}
	
	private List<JourneyVO> transformToVOListForUser(List<SEBOrder> seblist, Subject user) {
		if (seblist == null || seblist.isEmpty()){
			return null;
		}
		log.info(" -- the size of the list of journeys to convert is: " + seblist.size());
		List<JourneyVO> allj = new ArrayList<JourneyVO>();
		List<Drop> ldrop = new ArrayList<Drop>();
		JourneyVO jvo = null;
		for (int i = 0; i < seblist.size(); i++){					
			//First case set the header and drop 1
			if (i == 0){
				log.info("first journey-drop: " + seblist.get(i).getRoute_id());
				jvo = setJourney(seblist.get(i));				
				//Drop
				ldrop.add(addtypeOfDropPermitted(seblist.get(i),user));				
				//jvo.setDrop(ldrop);
				log.info("added first one: jvo: " + jvo + " ,drop: " + ldrop.get(0));
			}else{
				log.info(" --Same as before (trip)? " + seblist.get(i-1).getRoute_id().equals(seblist.get(i).getRoute_id()));
				//Not first case, compare to the row before to see if it is the same trip
				if (seblist.get(i-1).getRoute_id().equals(seblist.get(i).getRoute_id())){					
					//Same trip, then add Drop only
					log.info(" -- Same trip? now: " + seblist.get(i).getRoute_id() + " before: " + seblist.get(i-1).getRoute_id() + " ,drop size: " + ldrop.size());
					//Do we have any drop already?											
					if (ldrop.get(ldrop.size()-1).getDropNumber() == seblist.get(i).getDrop_number()){
					log.info(" -- We have already drops for the trip: " + seblist.get(i).getRoute_id() + " ,number of drops: " + ldrop.size() + 
							" ,this drop number: " + seblist.get(i).getDrop_number());
					 //Same drop, update references that are contained on this drop
						List<String> refs = ldrop.get(ldrop.size()-1).getReferences();
						refs.add(new String(seblist.get(i).getMl_order_ref()));
						ldrop.get(ldrop.size()-1).setReferences(refs);						
					}else{
						ldrop.add(addtypeOfDropPermitted(seblist.get(i),user));										
					}					
				}else{
					jvo.setDrop(ldrop); //Before drops added to the last trip
					allj.add(jvo);//Different Journey starts, I add the old one
					log.info(" -- Different trip from before: " + seblist.get(i-1).getRoute_id() + " ,number of drops: " + jvo.getDrop().size());
					//Different trip, then set header and first drop
					jvo = setJourney(seblist.get(i));
					//Drop
					ldrop = new ArrayList<Drop>();
					ldrop.add(addtypeOfDropPermitted(seblist.get(i),user));						
					//jvo.setDrop(ldrop);
					log.info("-- new Journey: " + jvo.getRouteID() + " ,with first drop: " + ldrop.get(0).getAddress() + " ,number: " + ldrop.get(0).getDropNumber() + " i:" + i);
				}
			}
			log.info(" i value: " + i + " ,size so far jvo: " + allj.size());
			if (i == seblist.size()-1){
				log.info(" -- last section, i: " + i + " ,seblist.size: " + seblist.size());
				if (i == 0){
					//There was only one trip one drop
					log.info(" -- Only one row trip -- " + jvo.getRouteID());					
				}else {
					//The last trip has more than 1 drop
					//last one needs to be added if drops have been the same..
					log.info(" -- Adding the last trip: " + jvo.getRouteID() + " ,with number of drops: " + ldrop.size());					
				}
				jvo.setDrop(ldrop);
				allj.add(jvo);
			}
		}//for
		log.info(" -- Resulted VO, number of Journeys: " + allj.size());
		allj.forEach(c -> {
			log.debug("\n--- Journey: " + c.getRouteID() + " ,number of drops: " + c.getDrop().size());
			c.getDrop().forEach(d -> log.info("\n Drop: " + d.getDropNumber() + " ,references: " + d.getReferences()));
		});
		return allj;
	}
	
	private Drop addtypeOfDropPermitted(SEBOrder sebOrder, Subject user) {
		if (sebOrder.getSite_id() == null){
			log.info("permitted in client globally? " + sebOrder.getClient() + ": " + user.isPermitted(AUTOMOVILE_CLIENT_PERM_GLOBAL + sebOrder.getClient()));
			if (user.isPermittedAll(AUTOMOVILE_CLIENT_PERM_GLOBAL, AUTOMOVILE_SITE_PERM_GLOBAL)){
				return(setdrop(sebOrder));
			}else{
				if (user.isPermittedAll(AUTOMOVILE_CLIENT_PERM + sebOrder.getClient(), AUTOMOVILE_SITE_PERM_GLOBAL)){
					return(setdrop(sebOrder));
				}else{
					return(setdropHidden(sebOrder));
				}					
			}
		}else{
			if (user.isPermittedAll(AUTOMOVILE_CLIENT_PERM + sebOrder.getClient(), AUTOMOVILE_SITE_PERM + sebOrder.getSite_id())){
				log.info("this user has perm in this site: " + sebOrder.getSite_id() + " and client: " + sebOrder.getClient());
				return(setdrop(sebOrder));
			}else{
				log.info("this user has NOT perm in this site: " + sebOrder.getSite_id() + " and client: " + sebOrder.getClient());
				return(setdropHidden(sebOrder));
			}
		}
	}

	private List<JourneyVO> transformToVOList(List<SEBOrder> seblist) {
		if (seblist == null || seblist.isEmpty()){
			return null;
		}
		log.info(" -- the size of the list of journeys to convert is: " + seblist.size());
		List<JourneyVO> allj = new ArrayList<JourneyVO>();
		List<Drop> ldrop = new ArrayList<Drop>();
		JourneyVO jvo = null;
		for (int i = 0; i < seblist.size(); i++){					
			//First case set the header and drop 1
			if (i == 0){
				log.info("first journey-drop: " + seblist.get(i).getRoute_id());
				jvo = setJourney(seblist.get(i));				
				//Drop			
				ldrop.add(setdrop(seblist.get(i)));
				//jvo.setDrop(ldrop);
				log.info("added first one: jvo: " + jvo + " ,drop: " + ldrop.get(0));
			}else{
				log.info(" --Same as before (trip)? " + seblist.get(i-1).getRoute_id().equals(seblist.get(i).getRoute_id()));
				//Not first case, compare to the row before to see if it is the same trip
				if (seblist.get(i-1).getRoute_id().equals(seblist.get(i).getRoute_id())){					
					//Same trip, then add Drop only
					log.info(" -- Same trip? now: " + seblist.get(i).getRoute_id() + " before: " + seblist.get(i-1).getRoute_id() + " ,drop size: " + ldrop.size());
					//Do we have any drop already?											
					if (ldrop.get(ldrop.size()-1).getDropNumber() == seblist.get(i).getDrop_number()){
					log.info(" -- We have already drops for the trip: " + seblist.get(i).getRoute_id() + " ,number of drops: " + ldrop.size() + 
							" ,this drop number: " + seblist.get(i).getDrop_number());
					 //Same drop, update references that are contained on this drop
						List<String> refs = ldrop.get(ldrop.size()-1).getReferences();
						refs.add(new String(seblist.get(i).getMl_order_ref()));
						ldrop.get(ldrop.size()-1).setReferences(refs);						
					}else{						
						ldrop.add(setdrop(seblist.get(i)));
						//jvo.setDrop(ldrop);						
					}					
				}else{
					jvo.setDrop(ldrop); //Before drops added to the last trip
					allj.add(jvo);//Different Journey starts, I add the old one
					log.info(" -- Different trip from before: " + seblist.get(i-1).getRoute_id() + " ,number of drops: " + jvo.getDrop().size());
					//Different trip, then set header and first drop
					jvo = setJourney(seblist.get(i));
					//Drop
					ldrop = new ArrayList<Drop>();					
					ldrop.add(setdrop(seblist.get(i)));
					//jvo.setDrop(ldrop);
					log.info("-- new Journey: " + jvo.getRouteID() + " ,with first drop: " + ldrop.get(0).getAddress() + " ,number: " + ldrop.get(0).getDropNumber() + " i:" + i);
				}
			}
			log.info(" i value: " + i + " ,size so far jvo: " + allj.size());
			if (i == seblist.size()-1){
				log.info(" -- last section, i: " + i + " ,seblist.size: " + seblist.size());
				if (i == 0){
					//There was only one trip one drop
					log.info(" -- Only one row trip -- " + jvo.getRouteID());					
				}else {
					//The last trip has more than 1 drop
					//last one needs to be added if drops have been the same..
					log.info(" -- Adding the last trip: " + jvo.getRouteID() + " ,with number of drops: " + ldrop.size());					
				}
				jvo.setDrop(ldrop);
				allj.add(jvo);
			}
		}//for
		log.info(" -- Resulted VO, number of Journeys: " + allj.size());
		allj.forEach(c -> {
			log.debug("\n--- Journey: " + c.getRouteID() + " ,number of drops: " + c.getDrop().size());
			c.getDrop().forEach(d -> log.info("\n Drop: " + d.getDropNumber() + " ,references: " + d.getReferences()));
		});
		return allj;
	}


	private JourneyVO setJourney(SEBOrder sebOrder) {
		JourneyVO jvo = new JourneyVO();
		jvo.setDriver(sebOrder.getDriver());
		jvo.setLoadID(sebOrder.getLoad_id());
		jvo.setRouteID(sebOrder.getRoute_id());
		jvo.setSeb_date(sebOrder.getSeb_date());
		jvo.setVehicule(sebOrder.getVehicle());
		return jvo;
	}


	private Drop setdrop(SEBOrder sebOrder) {
		Drop drop = new Drop();
		drop.setSite_name(sebOrder.getSite_name());
		drop.setSite_id(sebOrder.getSite_id());
		drop.setAddress(sebOrder.getLoc_name());
		drop.setATA(sebOrder.getAta());
		drop.setClient(sebOrder.getClient());
		drop.setColour(calculateColor(sebOrder, true));
		drop.setDropNumber(sebOrder.getDrop_number());
		drop.setETA(sebOrder.getEta());
		drop.setPTA(sebOrder.getPta());	
		drop.setDropNumber(sebOrder.getDrop_number());
		if (sebOrder.getEta()==null){
			drop.setShowedTime(sebOrder.getAta());
		}else{
			drop.setShowedTime(sebOrder.getEta());
		}
		List<String> refs = new ArrayList<String>();
		refs.add(new String(sebOrder.getMl_order_ref()));
		drop.setReferences(refs);
		log.info(" -- Adding drop location: " + drop.getAddress() + " ,drop number: " + drop.getDropNumber());
		return drop;
	}
	
	private Drop setdropHidden(SEBOrder sebOrder) {
		Drop drop = new Drop();
		if (sebOrder.getEta()==null){
			drop.setShowedTime(sebOrder.getAta());
		}else{
			drop.setShowedTime(sebOrder.getEta());
		}
		drop.setSite_name(ApplicationGlobalConfig.secret); // Hide name of this clients site
		//drop.setAddress(sebOrder.getLoc_name());
		drop.setAddress(ApplicationGlobalConfig.secret);
		drop.setATA(sebOrder.getAta());
		//drop.setClient(sebOrder.getClient()); 
		drop.setClient(ApplicationGlobalConfig.secret);
		drop.setColour(calculateColor(sebOrder, false));
		drop.setDropNumber(sebOrder.getDrop_number());
		drop.setETA(sebOrder.getEta());
		drop.setPTA(sebOrder.getPta());	
		drop.setDropNumber(sebOrder.getDrop_number());
		List<String> refs = new ArrayList<String>();
		refs.add(new String(sebOrder.getMl_order_ref()));
		drop.setReferences(refs);
		log.info(" -- Adding drop location: " + drop.getAddress() + " ,drop number: " + drop.getDropNumber());
		return drop;
	}


	private String calculateColor(SEBOrder sebOrder, boolean enable) {
		if (!enable){
			log.info(" Hidden color for client: " + sebOrder.getClient() + " ,site: " + sebOrder.getSite_id());
			return "default";
		}
		
		// To be delivered status
		if (sebOrder.getEta() != null && sebOrder.getTime_window_start() != null && sebOrder.getTime_window_end() != null){
			if (sebOrder.getEta().before(sebOrder.getTime_window_start())){
				return "green_border_drop";
			}
			if (sebOrder.getEta().after(sebOrder.getTime_window_end())){
				return "red_border_drop";
			}
			if (sebOrder.getEta().before(sebOrder.getTime_window_end()) || sebOrder.getEta().equals(sebOrder.getTime_window_start()) && 
					sebOrder.getEta().after(sebOrder.getTime_window_start())){
				return "blue_border_drop";
			}						
		}		
		//no ETA and no ATA
		if (sebOrder.getEta() == null && sebOrder.getAta() == null){
			return "black_border_drop";
		}
		// Delivered, Clause Refused Statuses
		if (sebOrder.getAta() != null && sebOrder.getTime_window_start() != null && sebOrder.getTime_window_end() != null){
			if (sebOrder.getAta().before(sebOrder.getTime_window_start())){
				return "green_filled";
			}
			if (sebOrder.getAta().after(sebOrder.getTime_window_end())){
				return "red_filled";
			}
			if (sebOrder.getAta().before(sebOrder.getTime_window_end()) || sebOrder.getEta() != null && sebOrder.getEta().equals(sebOrder.getTime_window_start()) && 
					sebOrder.getEta().after(sebOrder.getTime_window_start())){
				return "blue_filled";
			}
			
		}
		//No window time combination
		return "default";
		
	}


	public JourneyVO getJourneyByRouteId(String routeID){
		return null;
	}

}
