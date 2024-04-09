package com.ceva.cevatrack.transformer;

import com.ceva.framework.ejb.profile.transformer.AbstractEntityTransformer;
import com.ceva.cevatrack.domain.AppUserProfile;
import com.ceva.cevatrack.vo.AppUserProfileVO;

/**
 * AppUserProfileTransformer.
 * 
 * @author pruthvi.janapareddy
 *
 */
public interface AppUserProfileTransformer extends AbstractEntityTransformer<AppUserProfile> {
  public AppUserProfileVO transformToVO(AppUserProfile profile);

  public AppUserProfile transformToProfile(AppUserProfileVO profileVO);

  public AppUserProfile transformExistingToProfile(AppUserProfileVO profileVO,
      AppUserProfile profile);

}
