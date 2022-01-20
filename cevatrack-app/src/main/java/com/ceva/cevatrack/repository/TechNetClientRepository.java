package com.ceva.cevatrack.repository;

import com.ceva.cevatrack.domain.TechNetClient;
import java.util.Optional;
import org.apache.deltaspike.data.api.AbstractFullEntityRepository;
import org.apache.deltaspike.data.api.Repository;


@Repository(forEntity = TechNetClient.class)
public abstract class TechNetClientRepository extends AbstractFullEntityRepository<TechNetClient, Long> {

    public abstract Optional<TechNetClient> findByName(String name);

    public abstract Optional<TechNetClient> findByInstance(String instance);

}
