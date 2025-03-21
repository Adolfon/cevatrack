package com.ceva.cevatrack.service;
import java.util.List;

import com.ceva.cevatrack.vo.MicrolisePhotoVO;

public interface MicrolisePhotoService {


    public String getPhotoRef(String order, String ativity);
    public List<MicrolisePhotoVO> getPhotoRef(String order);
    public List<MicrolisePhotoVO> getJLRPhotoRefs(String order,String loadId);
}
