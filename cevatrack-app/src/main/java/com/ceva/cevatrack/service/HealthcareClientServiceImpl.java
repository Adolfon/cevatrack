package com.ceva.cevatrack.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ceva.cevatrack.domain.AutomotiveClient;
import com.ceva.cevatrack.domain.HealthcareClient;
import com.ceva.cevatrack.repository.HealthcareClientRepository;
import com.ceva.cevatrack.transformer.AutomotiveClientTransformer;
import com.ceva.cevatrack.transformer.HealthcareClientTransformerImpl;
import com.ceva.cevatrack.vo.SmartNetClientVO;
import com.ceva.cevatrack.webapp.DetailView;

/*
 * Reusing the Automotive type of data because it the same table schema just different table name
 */
@Stateless(name = "com.ceva.cevatrack.service.HealthcareClientServiceImpl")
public class HealthcareClientServiceImpl implements HealthcareClientService {

    @Inject
    private HealthcareClientRepository hcClientRepository;

    @Inject
    private HealthcareClientTransformerImpl hcClientTransformer;

    private static final Logger LOG = LoggerFactory.getLogger(HealthcareClientServiceImpl.class);
    private static final String PERM = "hc_client:view:";
    private static final String PERM_GLOBAL = "hc_client:view";

    @Override
    public List<SmartNetClientVO> searchHealthcareClient() {
        Subject currentUser = SecurityUtils.getSubject();
        List<HealthcareClient> matchingSmartNetClients = (List<HealthcareClient>) hcClientRepository.findAll();
        LOG.info(" -- Founded healthcare clients: " + matchingSmartNetClients.size());
        matchingSmartNetClients.stream().forEach(e -> LOG.info(e.getPrefixes()));
        List<SmartNetClientVO> result;
        if (currentUser.isPermitted(PERM_GLOBAL)) {
        	LOG.info(" -- was permitted in global.. ");
        	result = hcClientTransformer.transformToVO(matchingSmartNetClients);
        	LOG.info(" -- After transformation health clients are: " + result.size());
            return result;
        }
        List<HealthcareClient> filteredSmartNetClients = matchingSmartNetClients.stream()
                .filter(client -> currentUser.isPermitted(PERM + client.getAafInstance())).collect(Collectors.toList());
        result = hcClientTransformer.transformToVO(filteredSmartNetClients);
        LOG.info(" -- After transformation health clients out of global are: " + result.size());
        result.stream().forEach(e -> LOG.info(e.getPrefixes()));
        return result;
    }

    @Override
    public SmartNetClientVO getHealthcareClient(String prefix) {
        return searchHealthcareClient().stream().filter(client -> prefix.equals(client.getPrefixes())).findAny()
                .orElse(null);
    }


}
