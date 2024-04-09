/**
 * 
 * Copyright (c) 2016 CEVA Logistics, Inc. All Rights Reserved.
 */

package com.ceva.cevatrack.presentation;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import com.ceva.security.aaf.session.SessionManager;
import com.ceva.cevatrack.vo.AppUserVO;

/**
 * AppUserBean.
 * 
 * @author mohammad.rasheed
 *
 */
@Named
@ViewScoped
public class AppUserBean implements Serializable {

  /**
   * serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  private AppUserVO appUser;

  @PostConstruct
  public void init() {
    this.appUser = (AppUserVO) SessionManager.getSession().getAttribute("APP_USER");
  }

  public AppUserVO getAppUser() {
    return appUser;
  }

  public void setAppUser(AppUserVO appUser) {
    this.appUser = appUser;
  }
}
