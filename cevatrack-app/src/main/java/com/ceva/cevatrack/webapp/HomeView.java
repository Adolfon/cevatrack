package com.ceva.cevatrack.webapp;

import java.io.IOException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ceva.cevatrack.util.CommonApp;

/**
 * @author alexandru.castravet
 *
 */
@ManagedBean(name = "dtHomeView")
@ViewScoped
public class HomeView implements CommonApp {

    private static final Logger log = LoggerFactory.getLogger(HomeView.class);
    
private boolean sn = false;
private boolean tn = false;


	public void init() {
        log.info(" -- In the HomeView class --");
        try {
            checkLogin();
        } catch (IOException e) {
            log.error(" -- There is not user in the session when calling HomeView class ", e);
        }
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser.isPermitted("sn_microlise:tibco:uat") || currentUser.isPermitted("sn_microlise:tibco:prod") 
        		|| currentUser.isPermitted("sn_microlise:direct:uat") || currentUser.isPermitted("sn_microlise:direct:prod")) {
            this.sn = true;
            log.info("access for smartnet granted..");
            
        }
        if (currentUser.isPermitted("tn_microlise:tibco:uat") || currentUser.isPermitted("tn_microlise:tibco:prod")) {
            this.tn = true;
            log.info("access for technet granted..");
        }
                
        
    }
	
	public boolean isSn() {
		return sn;
	}


	public void setSn(boolean sn) {
		this.sn = sn;
	}


	public boolean isTn() {
		return tn;
	}


	public void setTn(boolean tn) {
		this.tn = tn;
	}


	

}
