package com.ceva.cevatrack.repository;

import java.util.Optional;
import org.apache.deltaspike.data.api.AbstractFullEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import com.ceva.cevatrack.domain.User;

/**
 * UserRepository.
 * 
 * @author mohammad.rasheed
 *
 */
@Repository(forEntity = User.class)
public abstract class UserRepository extends AbstractFullEntityRepository<User, Long> {

    public abstract Optional<User> findByLogin(String login);

}
