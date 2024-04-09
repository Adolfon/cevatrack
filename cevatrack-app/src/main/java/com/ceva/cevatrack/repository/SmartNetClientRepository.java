package com.ceva.cevatrack.repository;

import com.ceva.cevatrack.domain.SmartNetClient;
import java.util.Optional;
import org.apache.deltaspike.data.api.AbstractFullEntityRepository;
import org.apache.deltaspike.data.api.Repository;

/**
 * @author brian.goodall
 *
 */
@Repository(forEntity = SmartNetClient.class)
public abstract class SmartNetClientRepository
    extends AbstractFullEntityRepository<SmartNetClient, Long> {

  public abstract Optional<SmartNetClient> findByName(String name);

  public abstract Optional<SmartNetClient> findByInstance(String instance);

}
