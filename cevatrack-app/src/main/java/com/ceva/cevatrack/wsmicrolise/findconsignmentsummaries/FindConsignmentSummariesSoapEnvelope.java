package com.ceva.cevatrack.wsmicrolise.findconsignmentsummaries;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author brian.goodall
 *
 */
// @XmlRootElement(name = "Envelope", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
@XmlRootElement(name = "Envelope")
public class FindConsignmentSummariesSoapEnvelope {

    protected FindConsignmentSummariesSoapBody findConsignmentSummariesSoapBody;

    // @XmlElement(name = "Body", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
    @XmlElement(name = "Body")
    public FindConsignmentSummariesSoapBody getFindConsignmentSummariesSoapBody() {
        return findConsignmentSummariesSoapBody;
    }

    public void setFindConsignmentSummariesSoapBody(FindConsignmentSummariesSoapBody value) {
        this.findConsignmentSummariesSoapBody = value;
    }
}
