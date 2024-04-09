package com.ceva.cevatrack.presentation;

import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import com.ceva.security.aaf.web.AAFUserManagementBean;
import com.ceva.cevatrack.service.UserService;
import com.ceva.cevatrack.vo.AppUserVO;

/**
 * CJFUserController.
 * 
 * @author mohammad.rasheed
 *
 */
@Named(value = "cjfUserController")
@ViewScoped
public class CJFUserController extends AAFUserManagementBean {

  @EJB
  private UserService userservice;

  @Override
  protected void onCreateUserSuccess() {

    AppUserVO user = new AppUserVO();
    user.setAAFUser(super.user);
    userservice.createUser(user);
  }
}
