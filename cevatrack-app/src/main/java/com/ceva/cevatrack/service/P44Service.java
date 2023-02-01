package com.ceva.cevatrack.service;

import java.util.Date;
import java.util.List;

import com.ceva.cevatrack.domain.Consignment44;
import com.ceva.cevatrack.wsmicrolise.findconsignmentsummaries.ConsignmentSummary;
import com.ceva.cevatrack.wsmicrolise.getconsignment.Consignment;


public interface P44Service{
    	
	 public Consignment getConsignment(String ref);
	 
	 public List<Consignment44> getListConsignmentsByDate(Date stDate, Date edDate);
	 
	 public List<Consignment44> getListConsignmentsByDate(String stDate, String edDate);
	 
	 public List<ConsignmentSummary> getListConsignmentsByDateToCS(String stDate, String edDate);

}
