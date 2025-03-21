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
     * CREATE TABLE cevatrack_responsive.jlr_line_image (
	order_number text NOT NULL,
	detail_no text NOT NULL,
	image_no text NOT NULL,
	journey_alias text NULL,
	load_id text NULL,
	journey_drop_id text NULL,
	call_sequence text NULL,
	actual_call_sequence text NULL,
	site_name text NULL,
	site_info_1 text NULL,
	site_info_2 text NULL,
	clause text NULL,
	description text NULL,
	clean text NULL,
	planned_qty int4 NULL,
	actual_qty int4 NULL,
	claused_qty int4 NULL,
	qty_precision int4 NULL,
	ata timestamptz NULL,
	atd timestamptz NULL,
	created_timestamp timestamptz NULL,
	updated_timestamp timestamptz NULL,
	CONSTRAINT jlr_line_image_pk PRIMARY KEY (order_number, detail_no, image_no)
);
     */

@Entity
@Table(name = "jlr_line_image")
@IdClass(JLRMicrolisePhotoId2.class)
public class JLRMicrolisePhoto2 implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="order_number")
    private String order_number;
    
    @Id
    @Column(name="detail_no")
    private String detail_no;
    
    @Id
    @Column(name="image_no")
	private String image_no;
    
    @Column(name="journey_drop_id")
	private String journey_drop_id;
    
    @Column(name="description")
	private String description;
    
    @Column(name="site_name")
	private String site_name;  
    
	public String getJourney_drop_id() {
		return journey_drop_id;
	}

	public void setJourney_drop_id(String journey_drop_id) {
		this.journey_drop_id = journey_drop_id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSite_name() {
		return site_name;
	}

	public void setSite_name(String site_name) {
		this.site_name = site_name;
	}

	public String getOrder_number() {
		return order_number;
	}

	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}

	public String getDetail_no() {
		return detail_no;
	}

	public void setDetail_no(String detail_no) {
		this.detail_no = detail_no;
	}

	public String getImage_no() {
		return image_no;
	}

	public void setImage_no(String image_no) {
		this.image_no = image_no;
	}

    
}
