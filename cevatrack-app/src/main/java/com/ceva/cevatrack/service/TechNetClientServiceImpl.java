package com.ceva.cevatrack.service;

import com.ceva.cevatrack.domain.TechNetClient;
import com.ceva.cevatrack.domain.User;
import com.ceva.cevatrack.repository.TechNetClientRepository;
import com.ceva.cevatrack.transformer.TechNetClientTransformer;
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

@Stateless(name = "com.ceva.cevatrack.service.SmartNetClientServiceImpl")
public class TechNetClientServiceImpl implements TechNetClientService {

    @Inject
    private TechNetClientRepository techNetClientRepository;

    @Inject
    private TechNetClientTransformer techNetClientTransformer;

    private static final String PERM = "technet_client:view:";
    private static final String PERM_GLOBAL = "technet_client:view";

    @Override
    public List<SmartNetClientVO> searchTechNetClients() {
        Subject currentUser = SecurityUtils.getSubject();
        List<TechNetClient> matchingSmartNetClients = techNetClientRepository.findAll();
        if (currentUser.isPermitted(PERM_GLOBAL)) {
            return techNetClientTransformer.transformToVO(matchingSmartNetClients);
        }
        List<TechNetClient> filteredSmartNetClients = matchingSmartNetClients.stream()
                .filter(client -> currentUser.isPermitted(PERM + client.getAafInstance())).collect(Collectors.toList());
        return techNetClientTransformer.transformToVO(filteredSmartNetClients);
    }

    @Override
    public SmartNetClientVO getTechNetClient(String clientName) {
        return searchTechNetClients().stream().filter(client -> clientName.equals(client.getName())).findAny()
                .orElse(null);
    }

}
