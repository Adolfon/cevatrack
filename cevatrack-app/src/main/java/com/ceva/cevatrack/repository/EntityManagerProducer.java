package com.ceva.cevatrack.repository;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * EntityManagerProducer.
 * 
 * @author mohammad.rasheed
 *
 */
public class EntityManagerProducer {
    @Produces
    @PersistenceContext(unitName = "cevatrackPU")
    private EntityManager em;
}
