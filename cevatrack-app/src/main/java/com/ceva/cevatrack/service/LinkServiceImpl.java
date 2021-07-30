package com.ceva.cevatrack.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ceva.cevatrack.domain.LinkBean;
import com.ceva.cevatrack.repository.LinkServiceRepository;


@Stateless(name = "com.ceva.cevatrack.service.LinkServiceImpl")
public class LinkServiceImpl implements LinkService{
	private static final Logger LOG = LoggerFactory.getLogger(LinkServiceImpl.class);
    
    @Inject
    private LinkServiceRepository linkRepo;

	public LinkBean getLinkBeanById(String link) {
		LOG.info(" -- At EJB, linkRepo is: " + linkRepo + ", link: " + link);
		LOG.info(" -- by the delta method: " + linkRepo.findBy(link));//OK
		return linkRepo.searchRecordByLink(link);
	}    

}
