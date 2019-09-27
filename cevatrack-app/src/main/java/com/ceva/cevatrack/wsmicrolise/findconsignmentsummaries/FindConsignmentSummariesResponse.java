
package com.ceva.cevatrack.wsmicrolise.findconsignmentsummaries;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author brian.goodall
 *
 */
public class FindConsignmentSummariesResponse {

    protected ArrayList<ConsignmentSummary> findConsignmentSummariesResult;

    // @XmlElementWrapper(name = "FindConsignmentSummariesResult", namespace = "http://microlise.com/TMC")
    // @XmlElement(name = "ConsignmentSummary", namespace = "http://microlise.com/TMC")
    @XmlElementWrapper(name = "FindConsignmentSummariesResult")
    @XmlElement(name = "ConsignmentSummary")
    public ArrayList<ConsignmentSummary> getFindConsignmentSummariesResult() {
        return findConsignmentSummariesResult;
    }

    public void setFindConsignmentSummariesResult(ArrayList<ConsignmentSummary> value) {
        this.findConsignmentSummariesResult = value;
    }

}
