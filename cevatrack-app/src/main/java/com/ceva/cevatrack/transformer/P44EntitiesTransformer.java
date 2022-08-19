package com.ceva.cevatrack.transformer;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ceva.cevatrack.domain.ClausedConsignmentDetail44;
import com.ceva.cevatrack.domain.Consignment44;
import com.ceva.cevatrack.domain.ConsignmentDetail44;
import com.ceva.cevatrack.domain.Driver44;
import com.ceva.cevatrack.domain.Vehicle44;
import com.ceva.cevatrack.vo.ConsignmentDetailVO44;
import com.ceva.cevatrack.vo.ConsignmentVO44;
import com.ceva.cevatrack.wsmicrolise.getconsignment.ClausedConsignmentDetail;
import com.ceva.cevatrack.wsmicrolise.getconsignment.Consignment;
import com.ceva.cevatrack.wsmicrolise.getconsignment.ConsignmentDetail;
import com.ceva.cevatrack.wsmicrolise.getconsignment.Driver;
import com.ceva.cevatrack.wsmicrolise.getconsignment.Vehicle;

public class P44EntitiesTransformer {
	private static final Logger log = LoggerFactory.getLogger(P44EntitiesTransformer.class);

	public ConsignmentDetailVO44 getConsignmentVO(ConsignmentDetail44 cd){
		ConsignmentDetailVO44 vo = new ConsignmentDetailVO44();		
		return null;
	}
	
	public ConsignmentVO44 getConsignmentVO(Consignment44 obj, List<ConsignmentDetailVO44> cdvo, List<Driver44> drivers, List<Vehicle44> vehicles ){
		//TODO, not needed right now
		ConsignmentVO44 vo = new ConsignmentVO44();
		vo.setActualstarttime(obj.getActualstarttime());
		vo.setAddress1(obj.getAddress1());
		vo.setAddress2(obj.getAddress2());
		vo.setAddress3(obj.getAddress2());
		vo.setAta(obj.getAta());
		vo.setAtd(obj.getAtd());		
		vo.setActualstarttime(obj.getActualstarttime());
		// ETC..(Rest of the no list properties..
		vo.setConsignmentDetails(cdvo);
		return vo;
	}
	
	public Consignment getViewConsignment(Consignment44 obj, List<ConsignmentDetail44> cdvo, List<Driver44> drivers, List<Vehicle44> vehicles ){
		Consignment result = new Consignment();
		
		return result;
	}
	
	public static Consignment getViewConsignment(Consignment44 obj, ArrayList<ConsignmentDetail> cd, List<Driver44> drivers, List<Vehicle44> vehicles ){
		if (obj == null){
			log.error(" -- Consignment input was null..");
			return null;
		}
		Consignment cons = new Consignment();
		if (cd != null){
			log.info(" -- Consignment detail size input is: " + cd.size());			
			cons.setConsignmentDetails(cd);
		}
		ArrayList<Driver> dview = new ArrayList<>();
		for (Driver44 driver : drivers){
			dview.add(P44EntitiesTransformer.getViewDriver(driver));
		}
		log.info(" -- Drivers added to consignment: " + dview.size());
		cons.setDrivers(dview);
		ArrayList<Vehicle> dvehicle = new ArrayList<>();
		for (Vehicle44 vehicle : vehicles){
			dvehicle.add(P44EntitiesTransformer.getViewVehicle(vehicle));
		}
		log.info(" -- Vehicles added: " + dvehicle.size());
		cons.setVehicles(dvehicle);		
		//Now the root fields of Consignment
		cons.setActualStartTime(obj.getActualstarttime());
		cons.setAddress1(obj.getAddress1());
		cons.setAddress2(obj.getAddress2());
		cons.setAddress3(obj.getAddress3());
		cons.setAta(obj.getAta());
		cons.setAtd(obj.getAtd());
		cons.setClauseCode(obj.getClausecode());
		cons.setConsignmentCustomerOrderRef(obj.getConsignmentcustomerorderref());
		cons.setConsignmentHeaderId(obj.getConsignmentheaderid());
		cons.setConsignmentHeaderInfo1(obj.getConsignmentheaderinfo1());
		cons.setConsignmentHeaderInfo2(obj.getConsignmentHeaderInfo2());
		cons.setConsignmentHeaderInfo3(obj.getConsignmentHeaderInfo3());
		cons.setConsignmentHeaderInfo4(obj.getConsignmentHeaderInfo4());
		cons.setConsignmentHeaderInfo5(obj.getConsignmentHeaderInfo5());
		cons.setConsignmentHeaderInfo6(obj.getConsignmentHeaderInfo6());
		cons.setConsignmentHeaderInfo7(obj.getConsignmentHeaderInfo7());
		cons.setConsignmentHeaderInfo8(obj.getConsignmentHeaderInfo8());
		cons.setConsignmentHeaderInfo9(obj.getConsignmentHeaderInfo9());
		cons.setConsignmentHeaderInfo10(obj.getConsignmentHeaderInfo10());
		cons.setConsignmentHeaderInfo11(obj.getConsignmentHeaderInfo11());
		cons.setConsignmentHeaderInfo12(obj.getConsignmentHeaderInfo12());
		cons.setConsignmentReference(obj.getConsignmentreference());
		cons.setConsignmentStatus(obj.getConsignmentstatus());
		cons.setContactName(obj.getContactname());
		cons.setContactNumber(obj.getContactnumber());
		cons.setCountry(obj.getCountry());
		cons.setCounty(obj.getCounty());
		cons.setDurationOnSite(obj.getDurationonsite());
		cons.setEta(obj.getEta());
		cons.setEtd(obj.getEtd());
		cons.setIsAdHoc(obj.getIsadhoc());
		cons.setIsTranshipment(obj.getIstranshipment());
		cons.setJourneyActualStartTime(obj.getJourneyactualstarttime());
		cons.setJourneyAlias(obj.getJourneyalias());
		cons.setJourneyDropId(obj.getJourneydropId());
		cons.setJourneyId(obj.getJourneyid());
		cons.setLabel1(obj.getLabel1());
		cons.setLabel2(obj.getLabel2());
		cons.setLabel3(obj.getLabel3());
		cons.setLabel4(obj.getLabel4());
		cons.setParentCode(obj.getParentcode());
		cons.setPlannedStartTime(obj.getPlannedstarttime());
		cons.setPodStartTime(obj.getPodstarttime());
		cons.setPostCode(obj.getPostcode());
		cons.setPta(obj.getPta());
		cons.setPtd(obj.getPtd());
		cons.setRouteId(obj.getRouteid());
		cons.setSignatory(obj.getSignatory());
		cons.setSignature(obj.getSignature());
		cons.setSiteGeoRadius(obj.getSitegeoradius());
		cons.setSiteId(obj.getSiteid());
		cons.setSiteLat(obj.getSitelat());
		cons.setSiteLong(obj.getSitelong());
		cons.setSiteName(obj.getSitename());
		cons.setTimeWindowEnd(obj.getTimewindowend());
		cons.setTimeWindowStart(obj.getTimewindowstart());				
		return cons;
	}
	
	public Consignment getViewConsignment(ConsignmentVO44 cvo44){
		//TODO
		return null;
	}
	
	public static Driver getViewDriver(Driver44 d44){
		Driver dview = new Driver();
		dview.setContactNumber(d44.getContactNumber());
		dview.setDisplayName(d44.getDisplayName());
		dview.setFullName(d44.getFullName());
		dview.setJourneyId(d44.getJourneyId());
		return dview;
	}
	
	public static Vehicle getViewVehicle(Vehicle44 v44){
		Vehicle vview = new Vehicle();
		vview.setDisplayName(v44.getDisplayName());
		vview.setJourneyId(v44.getJourneyId());
		vview.setLastContactLat(v44.getLastContactLat());
		vview.setLastContactLong(v44.getLastContactLong());
		vview.setLastLocation(v44.getLastLocation());
		return vview;
	}
	
	public static ClausedConsignmentDetail getViewClausedDetail(ClausedConsignmentDetail44 cd44){
		ClausedConsignmentDetail ccdv = new ClausedConsignmentDetail();
		ccdv.setClauseCode(cd44.getClauseCode());
		ccdv.setClauseDescription(cd44.getClauseDescription());
		ccdv.setClausedQuantity(cd44.getClausedQuantity());
		//ccdv.setConsignmentDetailId(cd44.getConsignmentDetailRef()); //Not need id for the view I think
		ccdv.setParentClauseDescription(cd44.getParentClauseDescription());
		ccdv.setParentCode(cd44.getParentCode());	
		return ccdv;
	}
	
	public static ConsignmentDetail getViewConsignmentDetail(ConsignmentDetail44 ccd44){
		ConsignmentDetail cdv = new ConsignmentDetail();
		cdv.setActualQuantity(ccd44.getActualQuantity());
    	cdv.setConsignmentHeaderId(ccd44.getConsignmentHeaderId());
    	cdv.setCustomerDetailRef(ccd44.getCustomerDetailRef());
    	cdv.setDescription(ccd44.getDescription());
    	//cdv.setDetailId(ccd44.getDetailId()); Not needed
    	cdv.setDetailRef(ccd44.getDetailRef());
    	cdv.setInfo1(ccd44.getInfo1());
    	cdv.setInfo2(ccd44.getInfo2());
    	cdv.setInfo3(ccd44.getInfo3());
    	cdv.setInfo4(ccd44.getInfo4());
    	cdv.setOrderRef(ccd44.getOrderRef());
    	cdv.setParentRef(ccd44.getParentRef());
    	cdv.setPlannedQuantity(ccd44.getPlannedQuantity());
    	cdv.setStatus(ccd44.getStatus());
    	return cdv;
	}
	
}
