package com.ceva.cevatrack.service;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ceva.cevatrack.domain.MicrolisePhoto;
import com.ceva.cevatrack.repository.MircrolisePhotoRepository;
import com.ceva.cevatrack.vo.MicrolisePhotoVO;

@Stateless(name = "com.ceva.cevatrack.service.MicrolisePhotoServiceImpl")
public class MicrolisePhotoServiceImpl implements MicrolisePhotoService {
  private static final Logger log = LoggerFactory.getLogger(MicrolisePhotoServiceImpl.class);
  @Inject
  private MircrolisePhotoRepository microlisePhotoRepository;

  private static final String PERM = "sn_client:view:";
  private static final String PERM_GLOBAL = "sn_client:view";



  @Override
  public String getPhotoRef(String order, String activity) {
    // microlisePhotoRepository.getReference(primaryKey)
    log.info("-- order: " + order + ", activity: " + activity);
    MicrolisePhoto res = microlisePhotoRepository.searchRecordById(order, activity);
    if (res != null) {
      log.info("-- Reference recovered: " + res.getActivity_image_no());
    } else {
      return null;
    }
    return res.getActivity_image_no();
  }

  @Override
  public List<MicrolisePhotoVO> getPhotoRef(String order) {
    // microlisePhotoRepository.getReference(primaryKey)
    log.info("-- order: " + order);
    List<MicrolisePhoto> res = microlisePhotoRepository.searchRecordById(order);
    log.info("-- Recovered: " + res.size());
    List<MicrolisePhotoVO> photos = null;
    if (res != null) {
      log.info("-- Number of references recovered: " + res.size());
      photos = new ArrayList<MicrolisePhotoVO>();
      MicrolisePhotoVO p;
      for (MicrolisePhoto photo : res) {
        p = new MicrolisePhotoVO();
        p.setActivity_image_no(photo.getActivity_image_no());
        p.setActivity_name(photo.getActivity_name());
        p.setOrder_number(photo.getOrder_number());
        photos.add(p);
      }
      return photos;
    } else {
      log.info("-- Res was null..");
      return null;
    }
  }

}
