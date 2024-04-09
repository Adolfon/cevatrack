/*
 * 
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.ceva.cevatrack.presentation.theme;

import java.io.Serializable;
import java.util.Map;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Any;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import com.ceva.framework.ejb.profile.service.UserProfileService;
import com.ceva.framework.ejb.service.profile.vo.UserProfileVo;
import com.ceva.framework.ejb.service.property.ConfigProperty;
import com.ceva.framework.ejb.service.security.UserInfo;

/**
 * User Theme.
 * 
 * @author mohammad.weshah
 *
 */
@Named
@SessionScoped
public class UserPreferences implements Serializable {

  @Inject
  private UserProfileService profileService;

  @Inject
  @Any
  protected UserInfo userInfoImpl;

  @Inject
  @ConfigProperty(key = "user.default.menu.mode", defaultValue = "layout-menu-horizontal")
  protected String userDefaultMenuMode;

  @Inject
  @ConfigProperty(key = "user.default.theme", defaultValue = "cjf")
  protected String userDefaultTheme;

  private String menuMode;

  private String theme;

  private String layout;

  private boolean orientationRtl;

  /**
   * Get application theme.
   */
  public String getTheme() {
    if (SecurityUtils.getSubject().isAuthenticated()) {
      UserProfileVo profileVo = getUserProfileVo();
      if (null != profileVo.getUserProfileId() && StringUtils.isNotEmpty(profileVo.getTheme())) {
        theme = profileVo.getTheme();
      } else {
        theme = userDefaultTheme;
      }
    } else {
      theme = userDefaultTheme;
    }
    return theme;
  }

  /**
   * Set application theme.
   */
  public void setTheme(String theme) {
    UserProfileVo profileVo = getUserProfileVo();
    profileVo.setTheme(theme);
    profileVo = profileService.updateProfile(profileVo);
    this.theme = theme;
    this.layout = theme.split("-")[0];
  }

  /**
   * Get application layout.
   */
  public String getLayout() {
    String themeFromDB = getTheme();
    layout = themeFromDB.split("-")[0];
    return layout;
  }

  public void setLayout(String layout) {
    this.layout = layout;
  }

  /**
   * Get application Menu mode.
   */
  public String getMenuMode() {
    if (SecurityUtils.getSubject().isAuthenticated()) {
      UserProfileVo profileVo = getUserProfileVo();
      if (null != profileVo.getUserProfileId() && StringUtils.isNotEmpty(profileVo.getMenuMode())) {
        menuMode = profileVo.getMenuMode();
      } else {
        menuMode = userDefaultMenuMode;
      }
    } else {
      menuMode = userDefaultMenuMode;
    }
    return menuMode;
  }

  /**
   * Set application Menu mode.
   */
  public void setMenuMode(String menuMode) {
    UserProfileVo profileVo = getUserProfileVo();
    profileVo.setMenuMode(menuMode);
    profileVo = profileService.updateProfile(profileVo);
    this.menuMode = menuMode;
  }

  public boolean isOrientationRtl() {
    return orientationRtl;
  }

  public void setOrientationRtl(boolean orientationRtl) {
    this.orientationRtl = orientationRtl;
  }

  /**
   * Get user Profile Vo.
   */
  public UserProfileVo getUserProfileVo() {
    Map<String, String> userDetails = userInfoImpl.getUserInfo();
    String userPrincipal = userDetails.get("UserPrincipal");
    return profileService.findUserByUserPrincipal(userPrincipal);
  }
}
