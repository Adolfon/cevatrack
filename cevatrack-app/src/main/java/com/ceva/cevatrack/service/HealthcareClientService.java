package com.ceva.cevatrack.service;

import com.ceva.cevatrack.vo.SmartNetClientVO;
import java.util.List;


public interface HealthcareClientService {

  public List<SmartNetClientVO> searchHealthcareClient();

  public SmartNetClientVO getHealthcareClient(String clientName);

}
