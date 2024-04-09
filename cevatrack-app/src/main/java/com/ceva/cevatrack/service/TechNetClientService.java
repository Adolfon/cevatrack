package com.ceva.cevatrack.service;

import com.ceva.cevatrack.vo.SmartNetClientVO;
import java.util.List;


public interface TechNetClientService {

  public List<SmartNetClientVO> searchTechNetClients();

  public SmartNetClientVO getTechNetClient(String clientCode);

  List<SmartNetClientVO> searchTechNetClientsForLink();

}
