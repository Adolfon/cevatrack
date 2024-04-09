package com.ceva.cevatrack.vo;

public class MicrolisePhotoVO {

  private String order_number;

  private String activity_name;

  private String activity_image_no;

  private byte[] photo;

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

  public byte[] getPhoto() {
    return photo;
  }

  public void setPhoto(byte[] photo) {
    this.photo = photo;
  }

  @Override
  public String toString() {
    return "MicrolisePhotoVO [order_number=" + order_number + ", activity_name=" + activity_name
        + ", activity_image_no=" + activity_image_no + "]";
  }



}


