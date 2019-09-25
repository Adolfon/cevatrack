package com.ceva.cevatrack.service;

import javax.ejb.Stateless;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ceva.framework.ejb.profile.service.AppUserProfileService;
import com.ceva.framework.ejb.service.profile.vo.UserProfileVo;
import com.ceva.cevatrack.domain.AppUserProfile;
import com.ceva.cevatrack.repository.AppUserProfileRepository;
import com.ceva.cevatrack.transformer.AppUserProfileTransformer;
import com.ceva.cevatrack.vo.AppUserProfileVO;

/**
 * AppUserProfileServiceImpl.
 * 
 * @author pruthvi.janapareddy
 *
 */
@Stateless
public class AppUserProfileServiceImpl implements AppUserProfileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppUserProfileServiceImpl.class);

    @Inject
    private AppUserProfileRepository appUserProfileRepository;

    @Inject
    private AppUserProfileTransformer appUserProfileTransformer;

    @Override
    public void createOrUpdateProfile(UserProfileVo profileVO) {
        LOGGER.info("IN the AppUser createOrUpdateProfile");
        AppUserProfileVO appProfileVo = (AppUserProfileVO) profileVO;
        AppUserProfile existingProfile = appUserProfileRepository.findByUserProfileId(appProfileVo.getUserProfileId())
                .orElse(null);
        if (existingProfile == null) {
            existingProfile = appUserProfileTransformer.transformToProfile(appProfileVo);
            appUserProfileTransformer.prepareForCreate(existingProfile);
        } else {
            existingProfile = appUserProfileTransformer.transformExistingToProfile(appProfileVo, existingProfile);
            appUserProfileTransformer.prepareForUpdate(existingProfile);
        }
        AppUserProfile profileUpdated = appUserProfileRepository.save(existingProfile);
        appUserProfileTransformer.transformToVO(profileUpdated);
    }

    @Override
    public UserProfileVo findProfileByUserProfileId(Long userProfileId) {
        AppUserProfile profile = appUserProfileRepository.findByUserProfileId(userProfileId).orElse(null);
        AppUserProfileVO profileVO = appUserProfileTransformer.transformToVO(profile);
        UserProfileVo profileVo = new AppUserProfileVO();
        profileVo = profileVO;
        return profileVo;
    }

}
