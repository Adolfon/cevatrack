package com.ceva.cevatrack.repository;

import java.util.Optional;
import org.apache.deltaspike.data.api.AbstractFullEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import com.ceva.cevatrack.domain.HealthcareClient;
import com.ceva.cevatrack.domain.SmartNetClient;


@Repository(forEntity = HealthcareClient.class)
public abstract class HealthcareClientRepository
    extends AbstractFullEntityRepository<HealthcareClient, Long> {

  public abstract Optional<SmartNetClient> findByName(String name);

  public abstract Optional<SmartNetClient> findByInstance(String instance);

}
