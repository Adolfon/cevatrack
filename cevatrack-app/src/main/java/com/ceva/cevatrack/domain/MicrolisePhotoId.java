package com.ceva.cevatrack.domain;

import java.io.Serializable;

public class MicrolisePhotoId implements Serializable {

  private String order_number;
  private String activity_name;
  private String activity_image_no;

  public MicrolisePhotoId(String oderid, String activity) {
    this.order_number = oderid;
    this.activity_name = activity;
  }

  public MicrolisePhotoId() {
    super();
  }


  public String getActivity_image_no() {
    return activity_image_no;
  }

  public void setActivity_image_no(String activity_image_no) {
    this.activity_image_no = activity_image_no;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((activity_image_no == null) ? 0 : activity_image_no.hashCode());
    result = prime * result + ((activity_name == null) ? 0 : activity_name.hashCode());
    result = prime * result + ((order_number == null) ? 0 : order_number.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    MicrolisePhotoId other = (MicrolisePhotoId) obj;
    if (activity_image_no == null) {
      if (other.activity_image_no != null)
        return false;
    } else if (!activity_image_no.equals(other.activity_image_no))
      return false;
    if (activity_name == null) {
      if (other.activity_name != null)
        return false;
    } else if (!activity_name.equals(other.activity_name))
      return false;
    if (order_number == null) {
      if (other.order_number != null)
        return false;
    } else if (!order_number.equals(other.order_number))
      return false;
    return true;
  }

}

