package com.ceva.cevatrack.transformer;

import java.util.List;
import com.ceva.cevatrack.domain.TechNetClient;
import com.ceva.cevatrack.vo.SmartNetClientVO;
import com.ceva.security.aaf.transformer.AbstractEntityTransformer;


public interface TechNetClientTransformer extends AbstractEntityTransformer<TechNetClient> {
    public SmartNetClientVO transformToVO(TechNetClient smartNetClient);

    public List<SmartNetClientVO> transformToVO(List<TechNetClient> smartNetClients);

    public TechNetClient transformToSmartNetClient(SmartNetClientVO smartNetClient);

    public void convertToSmartNetClient(SmartNetClientVO smartNetClientVO, TechNetClient smartNetClient);
}
