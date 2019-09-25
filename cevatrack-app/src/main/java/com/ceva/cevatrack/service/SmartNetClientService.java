package com.ceva.cevatrack.service;

import com.ceva.cevatrack.vo.SmartNetClientVO;
import java.util.List;

/**
 * @author BRIAN.GOODALL
 *
 */
public interface SmartNetClientService {

    public List<SmartNetClientVO> searchSmartNetClient();

    public SmartNetClientVO getSmartNetClient(String clientCode);

}
