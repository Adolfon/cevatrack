package com.ceva.cevatrack.repository;

import java.util.List;
import java.util.Optional;

import org.apache.deltaspike.data.api.AbstractFullEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ceva.cevatrack.domain.MicrolisePhoto;
import com.ceva.cevatrack.domain.MicrolisePhotoId;


@Repository(forEntity = MicrolisePhoto.class)
public abstract class MircrolisePhotoRepository extends AbstractFullEntityRepository<MicrolisePhoto, MicrolisePhotoId> {
	private static final Logger LOG = LoggerFactory.getLogger(MircrolisePhotoRepository.class);
	

    public abstract Optional<MicrolisePhoto> findById(MicrolisePhotoId id);

    public MicrolisePhoto searchRecordById(String order, String activity) {
    	LOG.info(" -- searchRecordById method --");
        try {
          MicrolisePhoto ml_photo = (MicrolisePhoto) entityManager().createQuery("SELECT mp FROM MicrolisePhoto mp WHERE mp.order_number = :p and mp.activity_name = :c")
        		  .setParameter("p", order)
        		  	.setParameter("c", activity)
        		  		.getSingleResult();
          LOG.info(" -- Result of the query: " + ml_photo);
          return ml_photo; 
        }catch (Exception e) {
          LOG.error(" -- Error executing search in table with order: " + order + ", activity: " + activity + " ,cause: " + e.getMessage(), e);
          return null;
        }
      }
    
    public List<MicrolisePhoto> searchRecordById(String order) {
    	LOG.info(" -- searchRecordById method --");
        try {
         List<MicrolisePhoto> ml_photo = (List<MicrolisePhoto>) entityManager().createQuery("SELECT mp FROM MicrolisePhoto mp WHERE mp.order_number = :p ")
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
