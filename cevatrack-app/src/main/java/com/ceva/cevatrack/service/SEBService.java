package com.ceva.cevatrack.service;

import java.util.List;

import org.apache.shiro.subject.Subject;

import com.ceva.cevatrack.vo.JourneyVO;



public interface SEBService{
    	
	public List<JourneyVO> getJourneysByDateTime(String fromdate, int extraDays);
	
	public JourneyVO getJourneyByRouteId(String routeID);
	
	public List<JourneyVO> getJourneysByDateTimeUserFiltered(String fromdate, Subject user);

}
