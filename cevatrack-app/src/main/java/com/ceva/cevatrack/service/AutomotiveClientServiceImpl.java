package com.ceva.cevatrack.service;

import com.ceva.cevatrack.domain.AutomotiveClient;
import com.ceva.cevatrack.domain.SmartNetClient;
import com.ceva.cevatrack.domain.User;
import com.ceva.cevatrack.repository.AutomotiveClientRepository;
import com.ceva.cevatrack.repository.SmartNetClientRepository;
import com.ceva.cevatrack.transformer.AutomotiveClientTransformer;
import com.ceva.cevatrack.vo.SmartNetClientVO;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;


@Stateless(name = "com.ceva.cevatrack.service.AutomotiveClientServiceImpl")
public class AutomotiveClientServiceImpl implements AutomotiveClientService {

    @Inject
    private AutomotiveClientRepository amClientRepository;

    @Inject
    private AutomotiveClientTransformer automotiveClientTransformer;

    private static final String PERM = "am_client:view:";
    private static final String PERM_GLOBAL = "am_client:view";

    @Override
    public List<SmartNetClientVO> searchAutomotiveClient() {
        Subject currentUser = SecurityUtils.getSubject();
        List<AutomotiveClient> matchingSmartNetClients = amClientRepository.findAll();
        if (currentUser.isPermitted(PERM_GLOBAL)) {
            return automotiveClientTransformer.transformToVO(matchingSmartNetClients);
        }
        List<AutomotiveClient> filteredSmartNetClients = matchingSmartNetClients.stream()
                .filter(client -> currentUser.isPermitted(PERM + client.getAafInstance())).collect(Collectors.toList());
        return automotiveClientTransformer.transformToVO(filteredSmartNetClients);
    }

    @Override
    public SmartNetClientVO getAutomotiveClient(String clientName) {
        return searchAutomotiveClient().stream().filter(client -> clientName.equals(client.getName())).findAny()
                .orElse(null);
    }


}
