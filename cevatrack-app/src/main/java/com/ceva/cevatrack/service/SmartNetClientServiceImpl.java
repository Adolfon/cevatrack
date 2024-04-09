package com.ceva.cevatrack.service;

import com.ceva.cevatrack.domain.SmartNetClient;
import com.ceva.cevatrack.domain.User;
import com.ceva.cevatrack.repository.SmartNetClientRepository;
import com.ceva.cevatrack.transformer.SmartNetClientTransformer;
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

/**
 * @author brian.goodall
 *
 */
@Stateless(name = "com.ceva.cevatrack.service.SmartNetClientServiceImpl")
public class SmartNetClientServiceImpl implements SmartNetClientService {

  @Inject
  private SmartNetClientRepository smartNetClientRepository;

  @Inject
  private SmartNetClientTransformer smartNetClientTransformer;

  private static final String PERM = "sn_client:view:";
  private static final String PERM_GLOBAL = "sn_client:view";

  @Override
  public List<SmartNetClientVO> searchSmartNetClient() {
    Subject currentUser = SecurityUtils.getSubject();
    List<SmartNetClient> matchingSmartNetClients = smartNetClientRepository.findAll();
    if (currentUser.isPermitted(PERM_GLOBAL)) {
      return smartNetClientTransformer.transformToVO(matchingSmartNetClients);
    }
    List<SmartNetClient> filteredSmartNetClients = matchingSmartNetClients.stream()
        .filter(client -> currentUser.isPermitted(PERM + client.getAafInstance()))
        .collect(Collectors.toList());
    return smartNetClientTransformer.transformToVO(filteredSmartNetClients);
  }

  @Override
  public SmartNetClientVO getSmartNetClient(String prefix) {
    return searchSmartNetClient().stream().filter(client -> prefix.equals(client.getPrefixes()))
        .findAny().orElse(null);
  }

}
