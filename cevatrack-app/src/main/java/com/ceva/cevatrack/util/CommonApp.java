package com.ceva.cevatrack.util;

import java.io.IOException;
import javax.faces.context.FacesContext;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// import com.ceva.cevatrack.vo.AppUserVO;
import com.ceva.cevatrack.vo.AppUserVO;
import com.ceva.cevatrack.webapp.ConsignmentView;
// import com.ceva.cevatrack.webapp.ClientView;
import com.ceva.security.aaf.session.SessionManager;

public interface CommonApp {

  static final Logger LOG = LoggerFactory.getLogger(ConsignmentView.class);

  default public Boolean checkLogin() throws IOException {
    LOG.info(" --Inside of Logger");
    SecurityUtils.getSubject();
    final Logger LOGGER = LoggerFactory.getLogger(CommonApp.class);
    AppUserVO appUser = new AppUserVO();
    appUser = (AppUserVO) SessionManager.getSession().getAttribute("APP_USER");
    if (appUser == null) {
      LOG.error(" -- There is not user auth v4 ..");
      FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
      return false;
    }
    return true;
  }

}
