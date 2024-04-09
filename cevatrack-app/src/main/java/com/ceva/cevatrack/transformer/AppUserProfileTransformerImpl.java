package com.ceva.cevatrack.transformer;

import javax.ejb.Singleton;
import com.ceva.framework.ejb.profile.transformer.AbstractEntityTransformerImpl;
import com.ceva.cevatrack.domain.AppUserProfile;
import com.ceva.cevatrack.vo.AppUserProfileVO;

/**
 * App User Profile Transformer.
 * 
 * @author pruthvi.janapareddy
 *
 */
@Singleton
public class AppUserProfileTransformerImpl extends AbstractEntityTransformerImpl<AppUserProfile>
    implements AppUserProfileTransformer {

  @Override
  public AppUserProfileVO transformToVO(AppUserProfile profile) {
    if (profile == null) {
      return null;
    }
    // convert the entity into vo
    AppUserProfileVO vo = new AppUserProfileVO();
    vo.setId(profile.getId());
    vo.setUserProfileId(profile.getUserProfileId());
    vo.setEnableTodoNotification(profile.isEnableTodoNotification());
    return vo;
  }

  @Override
  public AppUserProfile transformToProfile(AppUserProfileVO profileVO) {
    if (profileVO == null) {
      return null;
    }
    // convert the vo into entity
    AppUserProfile profile = new AppUserProfile();
    profile.setId(profileVO.getId());
    profile.setUserProfileId(profileVO.getUserProfileId());
    profile.setEnableTodoNotification(profileVO.isEnableTodoNotification());
    return profile;
  }

  @Override
  public AppUserProfile transformExistingToProfile(AppUserProfileVO profileVO,
      AppUserProfile profile) {
    if (profileVO == null) {
      return profile;
    }
    // convert the vo into entity
    profile.setUserProfileId(profileVO.getUserProfileId());
    profile.setEnableTodoNotification(profileVO.isEnableTodoNotification());
    return profile;
  }

}
