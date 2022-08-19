package com.ceva.cevatrack.repository;

import java.util.List;

import org.apache.deltaspike.data.api.AbstractFullEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ceva.cevatrack.domain.ClausedConsignmentDetail44;
import com.ceva.cevatrack.domain.Consignment44;
import com.ceva.cevatrack.domain.ConsignmentDetail44;
import com.ceva.cevatrack.domain.Driver44;
import com.ceva.cevatrack.domain.Vehicle44;

@Repository(forEntity = Consignment44.class)
public abstract class P44_Details_Repository extends AbstractFullEntityRepository<ConsignmentDetail44, String>{
	private static final Logger LOG = LoggerFactory.getLogger(P44_Details_Repository.class);

    public ConsignmentDetail44 findByRef(String ref){
    	return findBy(ref);
    }

    public List<ConsignmentDetail44> getConsignmentDetails(String consignmentRef){
    	LOG.info("-- Consignment details id from consignment44 class: " + consignmentRef);
    	@SuppressWarnings("unchecked")
		List<ConsignmentDetail44> details = entityManager().createQuery("SELECT cd FROM ConsignmentDetail44 cd WHERE cd.consignmentReference = :r")
    			.setParameter("r", consignmentRef).getResultList();
    	if (details == null){
    		return null;
    	}
    	LOG.info("-- Consignment details found: " + details.size());
    	if (details.size() > 0){
    		LOG.info(" -- First element: " + details.get(0));
    	}
    	return details;
    }
}
