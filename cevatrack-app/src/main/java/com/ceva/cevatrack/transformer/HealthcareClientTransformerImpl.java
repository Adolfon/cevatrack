package com.ceva.cevatrack.transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.Singleton;

import com.ceva.cevatrack.domain.HealthcareClient;
import com.ceva.cevatrack.vo.SmartNetClientVO;
import com.ceva.framework.ejb.profile.transformer.AbstractEntityTransformerImpl;

@Singleton
public class HealthcareClientTransformerImpl extends AbstractEntityTransformerImpl<HealthcareClient>{

    public SmartNetClientVO transformToVO(HealthcareClient healthcareClient) {
        if (healthcareClient == null) {
            return null;
        }
        return new SmartNetClientVO(healthcareClient.getId(), healthcareClient.getName(), healthcareClient.getPrefixes(),
        		healthcareClient.getImage(), healthcareClient.getAafInstance());
    }

    public List<SmartNetClientVO> transformToVO(List<HealthcareClient> healthcareClient) {
        if (healthcareClient == null || healthcareClient.isEmpty()) {
            return new ArrayList<SmartNetClientVO>();
        }

        return healthcareClient.stream().map(u -> transformToVO(u)).collect(Collectors.toList());
    }

    public HealthcareClient transformToHealthcareClient(SmartNetClientVO smartNetClient) {
        if (smartNetClient == null) {
            return null;
        }
        return new HealthcareClient(smartNetClient.getId(), smartNetClient.getName(), smartNetClient.getPrefixes(),
                smartNetClient.getImage(), smartNetClient.getAafInstance());
    }

    public void convertToHealthcareClient(SmartNetClientVO smartNetClientVo, HealthcareClient healthcareClient) {
        if (smartNetClientVo == null || healthcareClient == null) {
            return;
        }
        healthcareClient.setId(smartNetClientVo.getId());
        healthcareClient.setName(smartNetClientVo.getName());
        healthcareClient.setPrefixes(smartNetClientVo.getPrefixes());
        healthcareClient.setImage(smartNetClientVo.getImage());
        healthcareClient.setAafInstance(smartNetClientVo.getAafInstance());
    }


}
