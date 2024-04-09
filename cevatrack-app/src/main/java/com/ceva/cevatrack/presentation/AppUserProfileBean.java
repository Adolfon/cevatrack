package com.ceva.cevatrack.presentation;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ceva.cjf.base.web.UserProfileBean;
import com.ceva.framework.ejb.profile.service.AppUserProfileService;
import com.ceva.framework.ejb.service.profile.vo.UserProfileVo;
import com.ceva.cevatrack.vo.AppUserProfileVO;

/**
 * This binds the app user profile data.
 * 
 * @author pruthvi.janapareddy
 *
 */
@ManagedBean(name = "appUserProfileBean")
@RequestScoped
public class AppUserProfileBean extends UserProfileBean {

  private static final Logger LOGGER = LoggerFactory.getLogger(AppUserProfileBean.class);

  /**
   * serialVersionUID.
   */
  private static final long serialVersionUID = 1L;

  private boolean enableTodoNotification;

  @Inject
  private AppUserProfileService appService;

  protected AppUserProfileVO profileVo;

  @Override
  protected void updateAppUserProfile() {
    profileVo.setEnableTodoNotification(this.isEnableTodoNotification());
    LOGGER.info("Updating custom AppProfile Data");
  }

  @Override
  protected void getAppUserProfileData() {
    LOGGER.info("Retriving Custom AppProfile Data");
    UserProfileVo userProfileVo = appService.findProfileByUserProfileId(this.getUserProfileId());
    if (userProfileVo != null) {
      profileVo = (AppUserProfileVO) userProfileVo;
      this.setEnableTodoNotification(profileVo.isEnableTodoNotification());
    } else {
      profileVo = new AppUserProfileVO();
    }
    super.profileVo = profileVo;
  }

  /**
   * getter for Enable Todo Notification.
   * 
   * @return the enableTodoNotification
   */
  public boolean isEnableTodoNotification() {
    return enableTodoNotification;
  }

  /**
   * setter for Enable Todo Notification.
   * 
   * @param enableTodoNotification the enableTodoNotification to set
   */
  public void setEnableTodoNotification(boolean enableTodoNotification) {
    this.enableTodoNotification = enableTodoNotification;
  }

}
