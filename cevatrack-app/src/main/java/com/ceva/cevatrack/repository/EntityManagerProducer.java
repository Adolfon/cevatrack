package com.ceva.cevatrack.repository;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
//import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.deltaspike.*;
import javax.enterprise.inject.Disposes;
import javax.enterprise.context.RequestScoped;

/**
 * EntityManagerProducer.
 * 
 * @author mohammad.rasheed
 *
 */
// It is actually producing the em for the whole app
public class EntityManagerProducer {
//    @Produces
//    @PersistenceContext(unitName = "cevatrackPU")
//    private EntityManager em;
	
	
	
	 //or manual bootstrapping
    @PersistenceContext(unitName = "cevatrackPU")
    private EntityManager entityManager;

    @Produces
    @RequestScoped
    protected EntityManager createEntityManager()
    {
        return this.entityManager;
    }

//    protected void closeEntityManager(@Disposes EntityManager entityManager)
//    {
//        if (entityManager.isOpen())
//        {
//            entityManager.close();
//        }
//    }

}
