
package com.ceva.cevatrack.wsmicrolise.getjourneysummaryforaliasanddate;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author brian.goodall
 *
 */
public class JourneySummarySiteDeplay {

    protected ArrayList<PlannedDelay> plannedDelays;

    @XmlElement(name = "PlannedDelays")
    public ArrayList<PlannedDelay> getPlannedDelays() {
        return plannedDelays;
    }

    public void setPlannedDelays(ArrayList<PlannedDelay> value) {
        this.plannedDelays = value;
    }

}
