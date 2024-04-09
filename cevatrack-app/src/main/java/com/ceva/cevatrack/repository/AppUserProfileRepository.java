package com.ceva.cevatrack.repository;

import java.util.Optional;
import org.apache.deltaspike.data.api.AbstractFullEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import com.ceva.cevatrack.domain.AppUserProfile;

/**
 * AppUserProfileRepository.
 * 
 * @author pruthvi.janapareddy
 *
 */
@Repository(forEntity = AppUserProfile.class)
public abstract class AppUserProfileRepository
    extends AbstractFullEntityRepository<AppUserProfile, Long> {

  public abstract Optional<AppUserProfile> findByUserProfileId(Long userProfileId);

}
