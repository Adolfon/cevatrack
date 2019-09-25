package com.ceva.cevatrack.jpa;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ApplicationScoped
@Alternative
public class TestEntityManagerProducer {

    private static String PERSISTENCE_UNIT_NAME = "templateTestPU";
    private EntityManager entityManager;
    private EntityManagerFactory emf;

    @PostConstruct
    public void initDB() {
        emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

    @Produces
    @RequestScoped
    public EntityManager produceEntityManager() {
        entityManager = emf.createEntityManager();
        return entityManager;
    }

    /**
     * closes the entity manage.
     * 
     * @param entityManager
     *            class
     */
    public void closeEntityManager(@Disposes EntityManager entityManager) {
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
    }

}
