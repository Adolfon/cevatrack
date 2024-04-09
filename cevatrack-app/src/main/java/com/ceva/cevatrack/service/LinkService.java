package com.ceva.cevatrack.service;

import com.ceva.cevatrack.domain.LinkBean;



public interface LinkService {


  public LinkBean getLinkBeanById(String link);

  public String getURL_rate_link(String client);

}
