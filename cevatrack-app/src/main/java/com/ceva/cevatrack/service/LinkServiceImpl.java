package com.ceva.cevatrack.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ceva.cevatrack.domain.LinkBean;
import com.ceva.cevatrack.domain.MailerClient;
import com.ceva.cevatrack.repository.LinkServiceRepository;
import com.ceva.cevatrack.repository.MailClientRepository;


@Stateless(name = "com.ceva.cevatrack.service.LinkServiceImpl")
public class LinkServiceImpl implements LinkService{
	private static final Logger LOG = LoggerFactory.getLogger(LinkServiceImpl.class);
    
    @Inject
    private LinkServiceRepository linkRepo;
    
    @Inject
    private MailClientRepository mcr;

	public LinkBean getLinkBeanById(String link) {
		LOG.info(" -- At EJB, linkRepo is: " + linkRepo + ", link: " + link);
		//LOG.info(" -- by the delta method: " + linkRepo.findBy(link));//OK
		return linkRepo.searchRecordByLink(link);
	}
	
	public LinkBean getLinkBeanBy2Refs(String order_ref, String client_ref) {
		LOG.info(" -- EJB, getLinkBeanBy2Refs, order_ref: " + order_ref + ", client ref " + client_ref);		
		return linkRepo.searchRecordByOrderOrRef(order_ref, client_ref);
	}

	@Override
	public String getURL_rate_link(String client) {
		LOG.info("-- Retrieving the URL for rate us, for client: " + client );
		MailerClient result = mcr.searchRecordByClient(client);
		String feedBackLink;
		if (result == null){
			feedBackLink = "";
		}else{
			feedBackLink = result.getFeedback_link();
		}
		if (feedBackLink == null || feedBackLink.equals("")){
			LOG.warn("-- Client feedback URL was null, trying to recover 'default' url link..");
			result = mcr.searchRecordByClient("default");
		}	
		if (result == null){
			feedBackLink = null;
		}else{
			feedBackLink = result.getFeedback_link();
		}
		/*
		if (result == null){
			feedBackLink = "";
		}else{
			feedBackLink = result.getFeedback_link();
		}
		if (feedBackLink == null || feedBackLink.equals("")){
			LOG.warn("-- Client feedback URL was null for 'default' too, setting 'default' on code..");
			feedBackLink = "https://www.cevalogistics.com/en";
		} 
		*/
		return feedBackLink;
	}    

}
