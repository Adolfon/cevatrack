/**
 * 
 * Copyright (c) 2016 CEVA Logistics, Inc. All Rights Reserved.
 */

package com.ceva.cevatrack.presentation;

import java.util.Locale;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ceva.cevatrack.util.SystemSessionConfig;
import com.ceva.cjf.base.web.presentation.LocaleController;
import com.ceva.framework.ejb.profile.service.UserProfileService;
import com.ceva.framework.ejb.service.profile.vo.UserProfileVo;
import com.ceva.framework.ejb.service.property.ConfigProperty;
import com.ceva.security.aaf.enums.ErrorCode;
import com.ceva.security.aaf.session.SessionManager;
import com.ceva.security.aaf.utils.AAFConstants;
import com.ceva.security.aaf.web.LoginBean;
import com.ceva.cevatrack.vo.AppUserVO;

/**
 * LoginController.
 * 
 * @author mohammad.rasheed
 *
 */
@Named
@ViewScoped
public class LoginController extends LoginBean {
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Inject
    private SystemSessionConfig sysConf;

    @Inject
    private UserProfileService profileService;

    @Inject
    @ConfigProperty(key = "change.password.page", defaultValue = AAFConstants.DEFAULT_CHANGE_PASSWORD_PAGE)
    private String changePasswordPage = "change-password.xhtml";

    @Override
    protected String afterLogin() {
        LOGGER.trace("User {} authenticated OK.", aafUser.getUserPrincipal());
        setUserToSession();
        menusVissibilityPermissions();
        // return "list-todo?faces-redirect=true";
        return "home.xhtml?faces-redirect=true";
    }

    private void menusVissibilityPermissions() {
    	Subject currentUser = SecurityUtils.getSubject();
    	if (currentUser.isPermitted("sn_microlise:tibco:uat") || currentUser.isPermitted("sn_microlise:tibco:prod") 
         		|| currentUser.isPermitted("sn_microlise:direct:uat") || currentUser.isPermitted("sn_microlise:direct:prod")) {
    		 SessionManager.getSession().setAttribute("SN_VISIBLE", true);
             
         }
         if (currentUser.isPermitted("tn_microlise:tibco:uat") || currentUser.isPermitted("tn_microlise:tibco:prod")) {
        	 SessionManager.getSession().setAttribute("TN_VISIBLE", true);
         }
         if (currentUser.isPermitted("am_microlise:tibco:uat") || currentUser.isPermitted("am_microlise:tibco:prod")) {
        	 SessionManager.getSession().setAttribute("AM_VISIBLE", true);
         }
         if (currentUser.isPermitted("hc_microlise:tibco:uat") || currentUser.isPermitted("hc_microlise:tibco:prod")) {
        	 SessionManager.getSession().setAttribute("HC_VISIBLE", true);
         }		
	}

	@Override
    public String handlePasswordChangeRequired(ErrorCode errorCode) {
        setUserToSession();
        return changePasswordPage + "?faces-redirect=true";
    }

    private void setUserToSessionOld() {
        LOGGER.info("-- Method setUserToSession starts");
        AppUserVO appUser = new AppUserVO();
        appUser.setAAFUser(aafUser);
      // Local environment
        //this.sysConf.SystemSessionConfig();
      // --
        SessionManager.getSession().setAttribute("APP_USER", appUser);
        LOGGER.info(" -- getSessionTimeoutTime(): " + this.getSessionTimeoutTime() + ",for user: "
                + appUser.getAAFUser().getFirstName() + ",LastName: " + appUser.getAAFUser().getLastName() + ", email: "
                + appUser.getAAFUser().getEmail());
        Subject currentUser = SecurityUtils.getSubject();

        if (currentUser.isPermitted("aaf_permission:time:*")) {
            LOGGER.info(" -- Permission for no limit session time enable.. ");
            HttpSession httpsession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext()
                    .getSession(false);
            this.setSessionTimeoutTime(0); // Never expires
            httpsession.setMaxInactiveInterval(0); // Never expires
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("neverExpires", true);
            LOGGER.info("Now the session TimeOUT time is: " + this.getSessionTimeoutTime()
                    + ", MaxInactiveInterval time: " + httpsession.getMaxInactiveInterval());
        }
    }

    private void setUserToSession() {
        AppUserVO appUser = new AppUserVO();
        appUser.setAAFUser(aafUser);
        
       // For local properties
        //this.sysConf.SystemSessionConfig();
       // --
        SessionManager.getSession().setAttribute("APP_USER", appUser);

        // Set locale based on user profile
        UserProfileVo profileVo = profileService.findUserByUserPrincipal(aafUser.getUserPrincipal());
        if ((profileVo != null) && (profileVo.getLanguage() != null)) {
            Locale locale = new Locale(profileVo.getLanguage());
            FacesContext context = FacesContext.getCurrentInstance();
            context.getViewRoot().setLocale(locale);

            // Get locale controller and set user profile language
            LocaleController localeController = (LocaleController) context.getApplication()
                    .evaluateExpressionGet(context, "#{localeController}", LocaleController.class);
            localeController.setSelectedLanguage(profileVo.getLanguage());
            localeController.setLocale(locale);
        }
    }
}
