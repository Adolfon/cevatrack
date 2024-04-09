package com.ceva.cevatrack.presentation;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ceva.cjf.base.web.jsf.CJFBaseBean;
import com.ceva.cjf.base.web.resource.LdapUserVO;

/**
 * LdapSearchTestController.
 * 
 * @author srinivas.lyagala
 *
 */
@Named
@ViewScoped
public class LdapSearchTestController extends CJFBaseBean {

  private static final long serialVersionUID = 1L;

  private static final Logger LOGGER = LoggerFactory.getLogger(LdapSearchTestController.class);
  private LdapUserVO firstUser;
  private LdapUserVO secondUser;
  private LdapUserVO firstUserSelected;
  private LdapUserVO secondUserSelected;

  /**
   * Initialize Method.
   */
  @PostConstruct
  public void init() {
    firstUser = new LdapUserVO();
    secondUser = new LdapUserVO();
    firstUserSelected = new LdapUserVO();
    secondUserSelected = new LdapUserVO();
  }

  /**
   * This is action Method used to call on 1st user Selection.
   * 
   */
  public void select1stUserAction() {
    LOGGER.debug("In Select1st userAction method");
    firstUser = firstUserSelected;
  }

  /**
   * This is action Method used to call on 2nd user Selection.
   */
  public void select2ndUserAction() {
    LOGGER.debug("In Select2nd userAction method");
    secondUser = secondUserSelected;
  }

  /**
   * this is action method to save the users.
   * 
   */
  public void saveUsers() {
    LOGGER.debug("In Save UsersMethod");
    LOGGER.debug("1st User Details:[accountName:" + firstUser.getAccountName() + ",displayName:"
        + firstUser.getDisplayName() + ",userPrincipal:" + firstUser.getPrincipalName() + "]");
    LOGGER.debug("2nd User Details:[accountName:" + secondUser.getAccountName() + ",displayName:"
        + secondUser.getDisplayName() + ",userPrincipal:" + secondUser.getPrincipalName() + "]");
  }

  /**
   * this Method is uses to reset the form.
   * 
   */
  public void resetUsers() {
    LOGGER.debug("In reset Form Method");
    firstUser = new LdapUserVO();
    secondUser = new LdapUserVO();
  }

  public LdapUserVO getFirstUser() {
    return firstUser;
  }

  public void setFirstUser(LdapUserVO firstUser) {
    this.firstUser = firstUser;
  }

  public LdapUserVO getSecondUser() {
    return secondUser;
  }

  public void setSecondUser(LdapUserVO secondUser) {
    this.secondUser = secondUser;
  }

  public LdapUserVO getFirstUserSelected() {
    return firstUserSelected;
  }

  public void setFirstUserSelected(LdapUserVO firstUserSelected) {
    this.firstUserSelected = firstUserSelected;
  }

  public LdapUserVO getSecondUserSelected() {
    return secondUserSelected;
  }

  public void setSecondUserSelected(LdapUserVO secondUserSelected) {
    this.secondUserSelected = secondUserSelected;
  }
}
