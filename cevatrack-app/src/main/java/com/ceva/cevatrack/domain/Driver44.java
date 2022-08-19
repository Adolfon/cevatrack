
package com.ceva.cevatrack.domain;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Access(AccessType.FIELD)
@Table(name = "p44_consignment_driver")
public class Driver44 implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	String displayName;
    String fullName;
    String contactNumber;
    int journeyId;
    String consignmentReference;

    public String getConsignmentReference() {
		return consignmentReference;
	}

	public void setConsignmentReference(String consignmentReference) {
		this.consignmentReference = consignmentReference;
	}

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String value) {
        this.displayName = value;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String value) {
        this.fullName = value;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String value) {
        this.contactNumber = value;
    }

    public int getJourneyId() {
        return journeyId;
    }

    public void setJourneyId(int value) {
        this.journeyId = value;
    }

	@Override
	public String toString() {
		return "Driver44 [displayName=" + displayName + ", fullName=" + fullName + ", contactNumber=" + contactNumber
				+ ", journeyId=" + journeyId + ", consignmentReference=" + consignmentReference + "]";
	}
    

}
