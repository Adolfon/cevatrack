package com.ceva.cevatrack.domain;

import java.io.Serializable;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import com.ceva.framework.domain.AbstractEntity;

/**
 *     /*
     * CREATE TABLE cevatrack_responsive.jlr_consignment_image (
	load_id text NOT NULL,
	journey_drop_id text NOT NULL,
	image_no text NOT NULL,
	is_proof bool NULL,
	is_clause bool NULL,
	journey_alias text NULL,
	call_sequence text NULL,
	actual_call_sequence text NULL,
	site_name text NULL,
	site_info_1 text NULL,
	site_info_2 text NULL,
	clause text NULL,
	description text NULL,
	ata timestamptz NULL,
	atd timestamptz NULL,
	created_timestamp timestamptz NULL,
	updated_timestamp timestamptz NULL,
	CONSTRAINT jlr_consignment_image_pk PRIMARY KEY (load_id, journey_drop_id, image_no)
);
     */

@Entity
@Table(name = "jlr_consignment_image")
@IdClass(JLRMicrolisePhotoId1.class)
public class JLRMicrolisePhoto1 implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="load_id")
    private String load_id;

	@Id
    @Column(name="journey_drop_id")
    private String journey_drop_id;
    
    @Id
    @Column(name="image_no")
	private String image_no;

    @Column(name="site_name")
	private String site_name;
    
    @Column(name="description")
	private String description;
        
    public String getLoad_id() {
		return load_id;
	}

	public void setLoad_id(String load_id) {
		this.load_id = load_id;
	}

	public String getJourney_drop_id() {
		return journey_drop_id;
	}

	public void setJourney_drop_id(String journey_drop_id) {
		this.journey_drop_id = journey_drop_id;
	}

	public String getImage_no() {
		return image_no;
	}

	public void setImage_no(String image_no) {
		this.image_no = image_no;
	}

	public String getSite_name() {
		return site_name;
	}

	public void setSite_name(String site_name) {
		this.site_name = site_name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
    
    
}
