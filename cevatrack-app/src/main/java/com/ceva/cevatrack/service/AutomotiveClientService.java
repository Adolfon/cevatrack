package com.ceva.cevatrack.service;

import com.ceva.cevatrack.vo.SmartNetClientVO;
import java.util.List;


public interface AutomotiveClientService {

    public List<SmartNetClientVO> searchAutomotiveClient();

    public SmartNetClientVO getAutomotiveClient(String clientName);
    
    public SmartNetClientVO getAutomotiveClientByAAFName(String clientName);

}
