package com.ceva.cevatrack.transformer;

import java.util.List;

import com.ceva.cevatrack.domain.AutomotiveClient;
import com.ceva.cevatrack.vo.SmartNetClientVO;
import com.ceva.security.aaf.transformer.AbstractEntityTransformer;


public interface AutomotiveClientTransformer extends AbstractEntityTransformer<AutomotiveClient> {
    public SmartNetClientVO transformToVO(AutomotiveClient automotiveClient);

    public List<SmartNetClientVO> transformToVO(List<AutomotiveClient> automotiveClient);

    public AutomotiveClient transformToAutomotiveClient(SmartNetClientVO automotiveClientVO);

    public void convertToAutomotiveClient(SmartNetClientVO smartNetClientVO, AutomotiveClient automotiveClient);
}
