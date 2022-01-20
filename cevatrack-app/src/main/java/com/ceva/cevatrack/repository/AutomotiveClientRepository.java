package com.ceva.cevatrack.repository;

import com.ceva.cevatrack.domain.AutomotiveClient;
import com.ceva.cevatrack.domain.SmartNetClient;
import java.util.Optional;
import org.apache.deltaspike.data.api.AbstractFullEntityRepository;
import org.apache.deltaspike.data.api.Repository;


@Repository(forEntity = AutomotiveClient.class)
public abstract class AutomotiveClientRepository extends AbstractFullEntityRepository<AutomotiveClient, Long> {

    public abstract Optional<SmartNetClient> findByName(String name);

    public abstract Optional<SmartNetClient> findByInstance(String instance);

}
