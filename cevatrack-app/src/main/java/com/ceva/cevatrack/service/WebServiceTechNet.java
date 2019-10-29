package com.ceva.cevatrack.service;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import com.ceva.cevatrack.wsdl.types.findconsignmentsummaries.ConsignmentFilter;

@WebService
@SOAPBinding(style = Style.RPC)
public interface WebServiceTechNet {

    @WebMethod
    public String healthTest();

    // @WebMethod
    // public ArrayOfConsignmentSummary getFilteredConsignmentSummaries(String fromYyyymmdd, String toYyyymmdd,
    // List<TechNetClientVO> clientsDB) throws Exception;

    @WebMethod
    com.ceva.cevatrack.wsmicrolise.findconsignmentsummaries.FindConsignmentSummariesResponse findConsignmentSummaries(
            ConsignmentFilter cf) throws Exception;

}