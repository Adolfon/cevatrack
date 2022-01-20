package com.ceva.cevatrack.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the generated link database table.
 * 
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = "mailer_client")
public class MailerClient implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String client;
    private String feedback_link;
    
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public String getFeedback_link() {
		return feedback_link;
	}
	public void setFeedback_link(String feedback_link) {
		this.feedback_link = feedback_link;
	}
	@Override
	public String toString() {
		return "MailerClient [client=" + client + ", feedback_link=" + feedback_link + "]";
	}
}
