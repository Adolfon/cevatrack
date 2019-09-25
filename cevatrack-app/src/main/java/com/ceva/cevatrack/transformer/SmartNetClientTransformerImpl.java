package com.ceva.cevatrack.transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Singleton;
import com.ceva.cevatrack.domain.SmartNetClient;
import com.ceva.cevatrack.vo.SmartNetClientVO;
import com.ceva.framework.ejb.profile.transformer.AbstractEntityTransformerImpl;

/**
 * @author brian.goodall
 *
 */
@Singleton
public class SmartNetClientTransformerImpl extends AbstractEntityTransformerImpl<SmartNetClient>
        implements SmartNetClientTransformer {

    @Override
    public void prepareForCreate(SmartNetClient arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void prepareForUpdate(SmartNetClient arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public SmartNetClientVO transformToVO(SmartNetClient smartNetClient) {
        if (smartNetClient == null) {
            return null;
        }
        return new SmartNetClientVO(smartNetClient.getId(), smartNetClient.getName(), smartNetClient.getPrefixes(),
                smartNetClient.getImage(), smartNetClient.getAafInstance());
    }

    @Override
    public List<SmartNetClientVO> transformToVO(List<SmartNetClient> smartNetClients) {
        if (smartNetClients == null || smartNetClients.isEmpty()) {
            return new ArrayList<SmartNetClientVO>();
        }

        return smartNetClients.stream().map(u -> transformToVO(u)).collect(Collectors.toList());
    }

    @Override
    public SmartNetClient transformToSmartNetClient(SmartNetClientVO smartNetClient) {
        if (smartNetClient == null) {
            return null;
        }
        return new SmartNetClient(smartNetClient.getId(), smartNetClient.getName(), smartNetClient.getPrefixes(),
                smartNetClient.getImage(), smartNetClient.getAafInstance());
    }

    @Override
    public void convertToSmartNetClient(SmartNetClientVO smartNetClientVo, SmartNetClient smartNetClient) {
        if (smartNetClientVo == null || smartNetClient == null) {
            return;
        }
        smartNetClient.setId(smartNetClientVo.getId());
        smartNetClient.setName(smartNetClientVo.getName());
        smartNetClient.setPrefixes(smartNetClientVo.getPrefixes());
        smartNetClient.setImage(smartNetClientVo.getImage());
        smartNetClient.setAafInstance(smartNetClientVo.getAafInstance());
    }

}
