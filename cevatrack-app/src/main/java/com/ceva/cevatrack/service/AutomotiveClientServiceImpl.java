package com.ceva.cevatrack.service;

import com.ceva.cevatrack.domain.AutomotiveClient;
import com.ceva.cevatrack.domain.SmartNetClient;
import com.ceva.cevatrack.domain.User;
import com.ceva.cevatrack.repository.AutomotiveClientRepository;
import com.ceva.cevatrack.repository.SmartNetClientRepository;
import com.ceva.cevatrack.transformer.AutomotiveClientTransformer;
import com.ceva.cevatrack.vo.SmartNetClientVO;
import com.ceva.cevatrack.webapp.ConsignmentView;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Stateless(name = "com.ceva.cevatrack.service.AutomotiveClientServiceImpl")
public class AutomotiveClientServiceImpl implements AutomotiveClientService {
  private static final Logger log = LoggerFactory.getLogger(AutomotiveClientServiceImpl.class);
  @Inject
  private AutomotiveClientRepository amClientRepository;

  @Inject
  private AutomotiveClientTransformer automotiveClientTransformer;

  private static final String PERM = "am_client:view:";
  private static final String PERM_GLOBAL = "am_client:view";

  @Override
  public List<SmartNetClientVO> searchAutomotiveClient() {
    Subject currentUser = SecurityUtils.getSubject();
    List<AutomotiveClient> matchingAutomotiveClients = amClientRepository.findAll();
    if (currentUser.isPermitted(PERM_GLOBAL)) {
      log.info(" -- user permitted --, clients: " + matchingAutomotiveClients);
      return automotiveClientTransformer.transformToVO(matchingAutomotiveClients);
    }
    log.info(" -- user not globally permitted --, clients: " + matchingAutomotiveClients);
    List<AutomotiveClient> filteredSmartNetClients = matchingAutomotiveClients.stream()
        .filter(client -> currentUser.isPermitted(PERM + client.getAafInstance()))
        .collect(Collectors.toList());
    log.info(" -- Filtered: " + filteredSmartNetClients);
    return automotiveClientTransformer.transformToVO(filteredSmartNetClients);
  }

  @Override
  public SmartNetClientVO getAutomotiveClient(String prefix) {
    return searchAutomotiveClient().stream().filter(client -> prefix.equals(client.getPrefixes()))
        .findAny().orElse(null);
  }

  @Override
  public SmartNetClientVO getAutomotiveClientByAAFName(String clientName) {
    return searchAutomotiveClient().stream()
        .filter(client -> clientName.equals(client.getAafInstance())).findAny().orElse(null);
  }


}
