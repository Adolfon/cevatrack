package com.ceva.cevatrack.domain;

import java.io.Serializable;

public class NameId implements Serializable {

  private String client_id;
  private String shipment_number;

  public NameId(String clientid, String shipmentnumber) {
    this.client_id = clientid;
    this.shipment_number = shipmentnumber;
  }

  public NameId() {
    super();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((client_id == null) ? 0 : client_id.hashCode());
    result = prime * result + ((shipment_number == null) ? 0 : shipment_number.hashCode());
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
    NameId other = (NameId) obj;
    if (client_id == null) {
      if (other.client_id != null)
        return false;
    } else if (!client_id.equals(other.client_id))
      return false;
    if (shipment_number == null) {
      if (other.shipment_number != null)
        return false;
    } else if (!shipment_number.equals(other.shipment_number))
      return false;
    return true;
  }

}

