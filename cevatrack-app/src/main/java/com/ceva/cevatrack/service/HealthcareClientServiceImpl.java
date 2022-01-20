package com.ceva.cevatrack.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.ceva.cevatrack.domain.AutomotiveClient;
import com.ceva.cevatrack.domain.HealthcareClient;
import com.ceva.cevatrack.repository.HealthcareClientRepository;
import com.ceva.cevatrack.transformer.AutomotiveClientTransformer;
import com.ceva.cevatrack.transformer.HealthcareClientTransformerImpl;
import com.ceva.cevatrack.vo.SmartNetClientVO;

/*
 * Reusing the Automotive type of data because it the same table schema just different table name
 */
@Stateless(name = "com.ceva.cevatrack.service.HealthcareClientServiceImpl")
public class HealthcareClientServiceImpl implements HealthcareClientService {

    @Inject
    private HealthcareClientRepository hcClientRepository;

    @Inject
    private HealthcareClientTransformerImpl hcClientTransformer;

    private static final String PERM = "hc_client:view:";
    private static final String PERM_GLOBAL = "hc_client:view";

    @Override
    public List<SmartNetClientVO> searchHealthcareClient() {
        Subject currentUser = SecurityUtils.getSubject();
        List<HealthcareClient> matchingSmartNetClients = (List<HealthcareClient>) hcClientRepository.findAll();
        if (currentUser.isPermitted(PERM_GLOBAL)) {
            return hcClientTransformer.transformToVO(matchingSmartNetClients);
        }
        List<HealthcareClient> filteredSmartNetClients = matchingSmartNetClients.stream()
                .filter(client -> currentUser.isPermitted(PERM + client.getAafInstance())).collect(Collectors.toList());
        return hcClientTransformer.transformToVO(filteredSmartNetClients);
    }

    @Override
    public SmartNetClientVO getHealthcareClient(String clientName) {
        return searchHealthcareClient().stream().filter(client -> clientName.equals(client.getName())).findAny()
                .orElse(null);
    }


}
