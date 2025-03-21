package com.ceva.cevatrack.repository;

import java.util.List;
import java.util.Optional;

import org.apache.deltaspike.data.api.AbstractFullEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ceva.cevatrack.domain.JLRMicrolisePhoto1;
import com.ceva.cevatrack.domain.JLRMicrolisePhoto2;
import com.ceva.cevatrack.domain.JLRMicrolisePhotoId1;
import com.ceva.cevatrack.domain.JLRMicrolisePhotoId2;
import com.ceva.cevatrack.domain.MicrolisePhoto;
import com.ceva.cevatrack.domain.MicrolisePhotoId;


@Repository(forEntity = JLRMicrolisePhoto2.class)
public abstract class JLRMircrolisePhotoRepository2 extends AbstractFullEntityRepository<JLRMicrolisePhoto2, JLRMicrolisePhotoId2> {
	private static final Logger LOG = LoggerFactory.getLogger(JLRMircrolisePhotoRepository2.class);
	

    //public abstract Optional<JLRMicrolisePhoto1> findById(JLRMicrolisePhotoId1 id);

    
    public List<JLRMicrolisePhoto2> searchRecordById(String order) {
    	LOG.info(" -- searchRecordById method --");
        try {
         List<JLRMicrolisePhoto2> ml_photo = (List<JLRMicrolisePhoto2>) entityManager().createQuery("SELECT mp FROM JLRMicrolisePhoto2 mp WHERE mp.order_number = :p ")
        		  .setParameter("p", order)        		  
        		  		.getResultList();
          LOG.info(" -- Result of the query: " + ml_photo);
          return ml_photo; 
        }catch (Exception e) {
          LOG.error(" -- Error executing search in table with order: " + order + " ,cause: " + e.getMessage(), e);
          return null;
        }
      }

}
