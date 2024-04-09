package com.ceva.cevatrack.transformer;

import java.util.List;
import com.ceva.cevatrack.domain.SmartNetClient;
import com.ceva.cevatrack.vo.SmartNetClientVO;
import com.ceva.security.aaf.transformer.AbstractEntityTransformer;

/**
 * @author brian.goodall
 *
 */
public interface SmartNetClientTransformer extends AbstractEntityTransformer<SmartNetClient> {
  public SmartNetClientVO transformToVO(SmartNetClient smartNetClient);

  public List<SmartNetClientVO> transformToVO(List<SmartNetClient> smartNetClients);

  public SmartNetClient transformToSmartNetClient(SmartNetClientVO smartNetClient);

  public void convertToSmartNetClient(SmartNetClientVO smartNetClientVO,
      SmartNetClient smartNetClient);
}
