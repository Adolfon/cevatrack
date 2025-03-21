package com.ceva.cevatrack.domain;

import java.io.Serializable;

public class JLRMicrolisePhotoId1 implements Serializable{
  
  private String load_id;
  private String journey_drop_id;
  private String image_no;
  
  public JLRMicrolisePhotoId1(String load_id, String journey_drop_id, String image_no) {
    this.load_id = load_id;
    this.journey_drop_id = journey_drop_id;
    this.image_no = image_no;
  }
  
  public JLRMicrolisePhotoId1() {
    super();
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
	result = prime * result + ((image_no == null) ? 0 : image_no.hashCode());
	result = prime * result + ((journey_drop_id == null) ? 0 : journey_drop_id.hashCode());
	result = prime * result + ((load_id == null) ? 0 : load_id.hashCode());
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
	JLRMicrolisePhotoId1 other = (JLRMicrolisePhotoId1) obj;
	if (image_no == null) {
		if (other.image_no != null)
			return false;
	} else if (!image_no.equals(other.image_no))
		return false;
	if (journey_drop_id == null) {
		if (other.journey_drop_id != null)
			return false;
	} else if (!journey_drop_id.equals(other.journey_drop_id))
		return false;
	if (load_id == null) {
		if (other.load_id != null)
			return false;
	} else if (!load_id.equals(other.load_id))
		return false;
	return true;
}    

}

