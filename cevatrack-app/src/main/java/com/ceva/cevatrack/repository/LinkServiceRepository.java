package com.ceva.cevatrack.repository;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.deltaspike.data.api.AbstractFullEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import com.ceva.cevatrack.domain.LinkBean;

@Repository(forEntity = LinkBean.class)
	public abstract class LinkServiceRepository extends AbstractFullEntityRepository<LinkBean, String> {
	private static final Logger LOG = LoggerFactory.getLogger(LinkServiceRepository.class);
	
	public LinkBean searchById(LinkBean lb){
		return (LinkBean) findBy(lb);
	}
	
    public abstract Optional<LinkBean> findByName(String name);

    public abstract Optional<LinkBean> findByInstance(String instance);
    
    public LinkBean searchRecordByLink(String link) {
    	LOG.info(" -- searchRecordByLink method --");
        try {
          LinkBean linkb = (LinkBean) entityManager().createQuery("SELECT lb FROM LinkBean lb WHERE lb.access_token = :p").setParameter("p", link).getSingleResult();
          LOG.info(" -- Result of the query: " + linkb);//OK
          return linkb; 
        }catch (Exception e) {
          LOG.error(" -- Error executing link bean record search with link: " + link + " ,cause: " + e.getMessage(), e);
          return null;
        }
      }
    
    public LinkBean searchRecordByLinkAndClient(String link, String client) {
    	LOG.info(" -- searchRecordByLink method --");
        try {
          LinkBean linkb = (LinkBean) entityManager().createQuery("SELECT lb FROM LinkBean lb WHERE lb.access_token = :p and lb.client = :c")
        		  .setParameter("p", link)
        		  	.setParameter("c", client)
        		  		.getSingleResult();
          LOG.info(" -- Result of the query: " + linkb);//OK
          return linkb; 
        }catch (Exception e) {
          LOG.error(" -- Error executing link bean record search with link: " + link + ", client: " + client + " ,cause: " + e.getMessage(), e);
          return null;
        }
      }
    
    public LinkBean searchRecordByOrderOrRef(String order_ref, String client_ref) {
    	LOG.info(" -- searchRecordByOrderOrRef method --");
    	LinkBean linkb = null;
        try {
          linkb = (LinkBean) entityManager().createQuery("SELECT lb FROM LinkBean lb WHERE (lb.client_ref = :p) OR (lb.order_number = :c)")
        		  .setParameter("p", client_ref)
        		  	.setParameter("c", order_ref)
        		  		.getSingleResult();
          LOG.info(" -- Result any of both parameters query: " + linkb);
        }catch (Exception e) {
        	LOG.error(" -- Result any of both parameters query: " + linkb, e);
        }       
        return linkb;
      }
	
}