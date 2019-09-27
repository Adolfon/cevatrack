
package com.ceva.cevatrack.wsmicrolise.getconsignment;

import javax.xml.bind.annotation.XmlElement;

/**
 * @author brian.goodall
 *
 */
public class ClausedConsignmentDetail {

    protected String parentCode;
    protected String parentClauseDescription;
    protected String clauseCode;
    protected String clauseDescription;
    protected int clausedQuantity;
    protected int consignmentDetailId;

    @XmlElement(name = "ParentCode")
    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String value) {
        this.parentCode = value;
    }

    @XmlElement(name = "ParentClauseDescription")
    public String getParentClauseDescription() {
        return parentClauseDescription;
    }

    public void setParentClauseDescription(String value) {
        this.parentClauseDescription = value;
    }

    @XmlElement(name = "ClauseCode")
    public String getClauseCode() {
        return clauseCode;
    }

    public void setClauseCode(String value) {
        this.clauseCode = value;
    }

    @XmlElement(name = "ClauseDescription")
    public String getClauseDescription() {
        return clauseDescription;
    }

    public void setClauseDescription(String value) {
        this.clauseDescription = value;
    }

    @XmlElement(name = "ClausedQuantity")
    public int getClausedQuantity() {
        return clausedQuantity;
    }

    public void setClausedQuantity(int value) {
        this.clausedQuantity = value;
    }

    @XmlElement(name = "ConsignmentDetailID")
    public int getConsignmentDetailId() {
        return consignmentDetailId;
    }

    public void setConsignmentDetailId(int value) {
        this.consignmentDetailId = value;
    }

}
