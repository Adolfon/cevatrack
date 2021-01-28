package com.ceva.cevatrack.transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Singleton;

import com.ceva.cevatrack.domain.AutomotiveClient;
import com.ceva.cevatrack.vo.SmartNetClientVO;
import com.ceva.framework.ejb.profile.transformer.AbstractEntityTransformerImpl;

@Singleton
public class AutomotiveClientTransformerImpl extends AbstractEntityTransformerImpl<AutomotiveClient>
        implements AutomotiveClientTransformer {

    @Override
    public SmartNetClientVO transformToVO(AutomotiveClient automotiveClient) {
        if (automotiveClient == null) {
            return null;
        }
        return new SmartNetClientVO(automotiveClient.getId(), automotiveClient.getName(), automotiveClient.getPrefixes(),
        		automotiveClient.getImage(), automotiveClient.getAafInstance());
    }

    @Override
    public List<SmartNetClientVO> transformToVO(List<AutomotiveClient> automotiveClient) {
        if (automotiveClient == null || automotiveClient.isEmpty()) {
            return new ArrayList<SmartNetClientVO>();
        }

        return automotiveClient.stream().map(u -> transformToVO(u)).collect(Collectors.toList());
    }

    @Override
    public AutomotiveClient transformToAutomotiveClient(SmartNetClientVO smartNetClient) {
        if (smartNetClient == null) {
            return null;
        }
        return new AutomotiveClient(smartNetClient.getId(), smartNetClient.getName(), smartNetClient.getPrefixes(),
                smartNetClient.getImage(), smartNetClient.getAafInstance());
    }

    @Override
    public void convertToAutomotiveClient(SmartNetClientVO smartNetClientVo, AutomotiveClient automotiveClient) {
        if (smartNetClientVo == null || automotiveClient == null) {
            return;
        }
        automotiveClient.setId(smartNetClientVo.getId());
        automotiveClient.setName(smartNetClientVo.getName());
        automotiveClient.setPrefixes(smartNetClientVo.getPrefixes());
        automotiveClient.setImage(smartNetClientVo.getImage());
        automotiveClient.setAafInstance(smartNetClientVo.getAafInstance());
    }


}
