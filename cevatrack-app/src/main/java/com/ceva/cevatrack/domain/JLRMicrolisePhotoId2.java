package com.ceva.cevatrack.domain;

import java.io.Serializable;

public class JLRMicrolisePhotoId2 implements Serializable{
  
  private String order_number;
  private String detail_no;
  private String image_no;
  
  public JLRMicrolisePhotoId2(String oderid, String detail_no, String image_no) {
    this.order_number = oderid;
    this.detail_no = detail_no;
    this.image_no = image_no;
  }
  
  public JLRMicrolisePhotoId2() {
    super();
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

@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((detail_no == null) ? 0 : detail_no.hashCode());
	result = prime * result + ((image_no == null) ? 0 : image_no.hashCode());
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
	JLRMicrolisePhotoId2 other = (JLRMicrolisePhotoId2) obj;
	if (detail_no == null) {
		if (other.detail_no != null)
			return false;
	} else if (!detail_no.equals(other.detail_no))
		return false;
	if (image_no == null) {
		if (other.image_no != null)
			return false;
	} else if (!image_no.equals(other.image_no))
		return false;
	if (order_number == null) {
		if (other.order_number != null)
			return false;
	} else if (!order_number.equals(other.order_number))
		return false;
	return true;
}


}

