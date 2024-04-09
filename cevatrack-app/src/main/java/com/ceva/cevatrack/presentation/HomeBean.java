package com.ceva.cevatrack.presentation;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ceva.cevatrack.wsmicrolise.WsMicroliseEnvironment;
import com.ceva.cjf.base.web.jsf.CJFBaseBean;


@Named
@SessionScoped
public class HomeBean implements Serializable {

  private static final Logger log = LoggerFactory.getLogger(WsMicroliseEnvironment.class);

  private static final long serialVersionUID = 1L;
  /**
   * Access to the different application view through permissions
   */

  private String name;
  private boolean seb_view;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @PostConstruct
  private void init() {
    Subject currentUser = SecurityUtils.getSubject();
    if (currentUser.isPermitted("cevatrack:view:SEB")) {
      log.info(" -- User is allowed on SEB view --");
      this.seb_view = true;
    } else {
      log.info(" -- User not allowed on SEB view --");
    }
    this.name = "TEST";
  }

  public boolean isSeb_view() {
    return seb_view;
  }

  public void setSeb_view(boolean seb_view) {
    this.seb_view = seb_view;
  }
}
