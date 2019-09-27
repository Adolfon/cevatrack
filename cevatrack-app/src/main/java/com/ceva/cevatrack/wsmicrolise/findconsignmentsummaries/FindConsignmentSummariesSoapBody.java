package com.ceva.cevatrack.wsmicrolise.findconsignmentsummaries;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author brian.goodall
 *
 */
public class FindConsignmentSummariesSoapBody {
    protected FindConsignmentSummariesResponse findConsignmentSummariesResponse;

    // @XmlElement(name = "FindConsignmentSummariesResponse", namespace = "http://microlise.com/TMC")
    @XmlElement(name = "FindConsignmentSummariesResponse")
    public FindConsignmentSummariesResponse getFindConsignmentSummariesResponse() {
        return findConsignmentSummariesResponse;
    }

    public void setFindConsignmentSummariesResponse(FindConsignmentSummariesResponse value) {
        this.findConsignmentSummariesResponse = value;
    }
}
