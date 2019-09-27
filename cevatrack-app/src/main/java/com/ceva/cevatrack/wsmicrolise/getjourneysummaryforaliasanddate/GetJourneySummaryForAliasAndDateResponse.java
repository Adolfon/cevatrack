
package com.ceva.cevatrack.wsmicrolise.getjourneysummaryforaliasanddate;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author brian.goodall
 *
 */
@XmlRootElement(name = "GetJourneySummaryForAliasAndDateResponse", namespace = "http://microlise.com/TMC")
public class GetJourneySummaryForAliasAndDateResponse {

    protected JourneySummaryResponse getJourneySummaryForAliasAndDateResult;

    @XmlElement(name = "GetJourneySummaryForAliasAndDateResult", namespace = "http://microlise.com/TMC")
    public JourneySummaryResponse getGetJourneySummaryForAliasAndDateResult() {
        return getJourneySummaryForAliasAndDateResult;
    }

    public void setGetJourneySummaryForAliasAndDateResult(JourneySummaryResponse value) {
        this.getJourneySummaryForAliasAndDateResult = value;
    }

}
