package com.ceva.cevatrack.transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Singleton;
import com.ceva.cevatrack.domain.TechNetClient;
import com.ceva.cevatrack.vo.SmartNetClientVO;
import com.ceva.framework.ejb.profile.transformer.AbstractEntityTransformerImpl;

/**
 * @author brian.goodall
 *
 */
@Singleton
public class TechNetClientTransformerImpl extends AbstractEntityTransformerImpl<TechNetClient>
        implements TechNetClientTransformer {

    @Override
    public void prepareForCreate(TechNetClient arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void prepareForUpdate(TechNetClient arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public SmartNetClientVO transformToVO(TechNetClient smartNetClient) {
        if (smartNetClient == null) {
            return null;
        }
        return new SmartNetClientVO(smartNetClient.getId(), smartNetClient.getName(), smartNetClient.getPrefixes(),
                smartNetClient.getImage(), smartNetClient.getAafInstance());
    }

    @Override
    public List<SmartNetClientVO> transformToVO(List<TechNetClient> smartNetClients) {
        if (smartNetClients == null || smartNetClients.isEmpty()) {
            return new ArrayList<SmartNetClientVO>();
        }

        return smartNetClients.stream().map(u -> transformToVO(u)).collect(Collectors.toList());
    }

    @Override
    public TechNetClient transformToSmartNetClient(SmartNetClientVO smartNetClient) {
        if (smartNetClient == null) {
            return null;
        }
        return new TechNetClient(smartNetClient.getId(), smartNetClient.getName(), smartNetClient.getPrefixes(),
                smartNetClient.getImage(), smartNetClient.getAafInstance());
    }

    @Override
    public void convertToSmartNetClient(SmartNetClientVO smartNetClientVo, TechNetClient smartNetClient) {
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
