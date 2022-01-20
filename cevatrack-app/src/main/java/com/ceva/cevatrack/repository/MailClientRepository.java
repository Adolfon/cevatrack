package com.ceva.cevatrack.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.deltaspike.data.api.AbstractFullEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import com.ceva.cevatrack.domain.MailerClient;

@Repository(forEntity = MailerClient.class)
	public abstract class MailClientRepository extends AbstractFullEntityRepository<MailerClient, String> {
	private static final Logger LOG = LoggerFactory.getLogger(MailClientRepository.class);
	
	public MailerClient searchById(String client){
		return (MailerClient) findBy(client);
	}	
    
    public MailerClient searchRecordByClient(String client) {
    	LOG.info(" -- searchRecordByClient method --");
        try {
          MailerClient mc = (MailerClient) entityManager().createQuery("SELECT cl FROM MailerClient cl WHERE cl.client = :p").setParameter("p", client).getSingleResult();
          LOG.info(" -- Result of the query: " + mc);//OK
          return mc; 
        }catch (Exception e) {
          LOG.error(" -- Error executing link bean record search with link: " + client + " ,cause: " + e.getMessage(), e);
          return null;
        }
      }
	
}
