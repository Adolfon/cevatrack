package com.ceva.cevatrack.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ceva.cevatrack.domain.JLRMicrolisePhoto1;
import com.ceva.cevatrack.domain.JLRMicrolisePhoto2;
import com.ceva.cevatrack.domain.MicrolisePhoto;
import com.ceva.cevatrack.repository.JLRMircrolisePhotoRepository1;
import com.ceva.cevatrack.repository.JLRMircrolisePhotoRepository2;
import com.ceva.cevatrack.repository.MircrolisePhotoRepository;
import com.ceva.cevatrack.vo.MicrolisePhotoVO;

@Stateless(name = "com.ceva.cevatrack.service.MicrolisePhotoServiceImpl")
public class MicrolisePhotoServiceImpl implements MicrolisePhotoService {
	private static final Logger log = LoggerFactory.getLogger(MicrolisePhotoServiceImpl.class);
    @Inject
    private MircrolisePhotoRepository microlisePhotoRepository;
    @Inject
    private JLRMircrolisePhotoRepository1 jlrPhotoRep1;
    @Inject
    private JLRMircrolisePhotoRepository2 jlrPhotoRep2;

    private static final String PERM = "sn_client:view:";
    private static final String PERM_GLOBAL = "sn_client:view";



	@Override
	public String getPhotoRef(String order, String activity) {
		//microlisePhotoRepository.getReference(primaryKey)		
		log.info("-- order: " + order + ", activity: " + activity);
		MicrolisePhoto res = microlisePhotoRepository.searchRecordById(order, activity);
		if (res != null){
			log.info("-- Reference recovered: " + res.getActivity_image_no());
		}else{
			return null;
		}
		return res.getActivity_image_no();
	}
		
	@Override
	public List<MicrolisePhotoVO> getPhotoRef(String order) {
		//microlisePhotoRepository.getReference(primaryKey)		
		log.info("-- order: " + order);
		List<MicrolisePhoto> res = microlisePhotoRepository.searchRecordById(order);		
		List<MicrolisePhotoVO> photos = null;
		if (res != null){
			log.info("-- Number of references recovered: " + res.size());
			 photos = new ArrayList<MicrolisePhotoVO>();
			 MicrolisePhotoVO p;
			for (MicrolisePhoto photo: res){
				p = new MicrolisePhotoVO();
				p.setActivity_image_no(photo.getActivity_image_no());
				p.setActivity_name(photo.getActivity_name());
				p.setOrder_number(photo.getOrder_number());
				photos.add(p);
			}	
			return photos;
		}else{
			log.info("-- Res was null..");
			return null;
		}
	}

	public List<MicrolisePhotoVO> getJLRPhotoRefs(String order, String loadId){
		log.info("-- order: " + order + ", loadId: " + loadId);
		List<JLRMicrolisePhoto1> res = jlrPhotoRep1.searchRecordById(loadId);
		List<MicrolisePhotoVO> photos = new ArrayList<MicrolisePhotoVO>();
		 MicrolisePhotoVO p;
		if (res != null){
			log.info("-- Number of references recovered: " + res.size());			 			
			for (JLRMicrolisePhoto1 photo: res){
				p = new MicrolisePhotoVO();
				p.setActivity_image_no(photo.getImage_no());//Id
				p.setActivity_name(photo.getDescription());
				p.setOrder_number(photo.getJourney_drop_id());//Not used anyway
				photos.add(p);
			}	
			log.info("-- Total photos added from first table: " + photos.size());
		}else{
			log.warn("-- No entry for JLRPhotoRep1 with loadId: " + loadId);
		}
		//Second retrieval
		List<JLRMicrolisePhoto2> res2 = jlrPhotoRep2.searchRecordById(order);
		if (res2 != null){
			log.info("-- Number of references recovered: " + res2.size());			 
			for (JLRMicrolisePhoto2 photo2: res2){
				p = new MicrolisePhotoVO();
				p.setActivity_image_no(photo2.getImage_no());//photo Id
				p.setActivity_name(photo2.getDescription());
				p.setOrder_number(photo2.getJourney_drop_id());//Not used anyway
				photos.add(p);
			}				
		}else{
			log.warn("-- No entry for JLRPhotoRep2 with order: " + order);
		}
		return photos;
	}
}
