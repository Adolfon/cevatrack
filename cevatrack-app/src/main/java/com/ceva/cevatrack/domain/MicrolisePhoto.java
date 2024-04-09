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
 * /* load_id text NULL, order_number text NOT NULL, journey_alias text NULL, call_sequence text
 * NULL, actual_call_sequence text NULL, activity_name text NOT NULL, activity_image_no text NULL,
 * activity_start timestamptz NULL, activity_end timestamptz NULL, created_timestamp timestamptz
 * NULL, updated_timestamp timestamptz NULL, CONSTRAINT ml_image_pkey PRIMARY KEY (order_number,
 * activity_name)
 */

@Entity
@Table(name = "ml_image")
@IdClass(MicrolisePhotoId.class)
public class MicrolisePhoto implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "order_number")
  private String order_number;

  @Id
  @Column(name = "activity_name")
  private String activity_name;

  @Id
  @Column(name = "activity_image_no")
  private String activity_image_no;

  public String getOrder_number() {
    return order_number;
  }

  public void setOrder_number(String order_number) {
    this.order_number = order_number;
  }

  public String getActivity_name() {
    return activity_name;
  }

  public void setActivity_name(String activity_name) {
    this.activity_name = activity_name;
  }

  public String getActivity_image_no() {
    return activity_image_no;
  }

  public void setActivity_image_no(String activity_image_no) {
    this.activity_image_no = activity_image_no;
  }

  @Override
  public String toString() {
    return "MicrolisePhoto [order_number=" + order_number + ", activity_name=" + activity_name
        + ", activity_image_no=" + activity_image_no + "]";
  }



}
