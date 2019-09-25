package com.ceva.cevatrack.presentation;

import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ceva.cjf.base.web.ErrorReportManagedBean;
import com.ceva.security.aaf.service.AAFUserService;
import com.ceva.security.aaf.vo.AAFUserVO;

@Named(value = "errorReportManagedBean")
@ViewScoped
public class AppErrorReportManagedBean extends ErrorReportManagedBean implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppErrorReportManagedBean.class);
    @Inject
    private AAFUserService userService;

    /**
     * This method is used to configure the user details in report page by default if user logged in.
     */
    public void initUserDetails() {
        LOGGER.info("In Intialize UserDetails subclass MethodEntry");
        Subject subject = SecurityUtils.getSubject();
        if (null != subject && null != subject.getPrincipal()) {
            AAFUserVO userVo = userService.getUser(subject.getPrincipal().toString());
            LOGGER.info("After Getting userDetails");
            reportVo.setLoginId(userVo.getUserPrincipal());
            reportVo.setEmail(userVo.getEmail());
            reportVo.setName(userVo.getFirstName() + " " + userVo.getLastName());
            userLoggedIn = true;
        }
        LOGGER.info("initUserDetails Method Exit");
    }

}
