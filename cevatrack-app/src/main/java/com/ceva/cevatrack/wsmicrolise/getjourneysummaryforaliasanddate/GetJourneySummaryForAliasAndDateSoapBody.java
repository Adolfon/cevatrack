package com.ceva.cevatrack.wsmicrolise.getjourneysummaryforaliasanddate;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author brian.goodall
 *
 */
public class GetJourneySummaryForAliasAndDateSoapBody {
    protected GetJourneySummaryForAliasAndDateResponse getJourneySummaryForAliasAndDateResponse;

    @XmlElement(name = "GetJourneySummaryForAliasAndDateResponse", namespace = "http://microlise.com/TMC")
    public GetJourneySummaryForAliasAndDateResponse getGetJourneySummaryForAliasAndDateResponse() {
        return getJourneySummaryForAliasAndDateResponse;
    }

    public void setGetJourneySummaryForAliasAndDateResponse(GetJourneySummaryForAliasAndDateResponse value) {
        this.getJourneySummaryForAliasAndDateResponse = value;
    }
}
