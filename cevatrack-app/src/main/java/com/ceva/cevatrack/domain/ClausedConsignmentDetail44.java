
package com.ceva.cevatrack.domain;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Access(AccessType.FIELD)
@Table(name = "p44_claused_consignment_detail")
public class ClausedConsignmentDetail44 implements Serializable{

	private static final long serialVersionUID = 1L;
	String parentCode;
    String parentClauseDescription;
    @Id
    String clauseCode;
    String clauseDescription;
    int clausedQuantity;
    String consignmentDetailRef;
    
    public String getConsignmentDetailRef() {
		return consignmentDetailRef;
	}

	public void setConsignmentDetailRef(String consignmentDetailRef) {
		this.consignmentDetailRef = consignmentDetailRef;
	}

	public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String value) {
        this.parentCode = value;
    }

    public String getParentClauseDescription() {
        return parentClauseDescription;
    }

    public void setParentClauseDescription(String value) {
        this.parentClauseDescription = value;
    }

    public String getClauseCode() {
        return clauseCode;
    }

    public void setClauseCode(String value) {
        this.clauseCode = value;
    }

    public String getClauseDescription() {
        return clauseDescription;
    }

    public void setClauseDescription(String value) {
        this.clauseDescription = value;
    }

    public int getClausedQuantity() {
        return clausedQuantity;
    }

    public void setClausedQuantity(int value) {
        this.clausedQuantity = value;
    }

}
