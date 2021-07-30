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
    
private int count = 0;

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
	            this.count++;
	            log.info("access for smartnet granted.." + count);        	
        }
        if (currentUser.isPermitted("tn_microlise:tibco:uat") || currentUser.isPermitted("tn_microlise:tibco:prod")) {
	            this.count++; 
	            log.info("access for technet granted.." + count);

        }
        if (currentUser.isPermitted("am_microlise:tibco:uat") || currentUser.isPermitted("am_microlise:tibco:prod")) {	        	
	            this.count++;
	            log.info("access for automotive granted.." + count);
        	}
       
        if (currentUser.isPermitted("hc_microlise:tibco:uat") || currentUser.isPermitted("hc_microlise:tibco:prod")) {
	            this.count++;
	            log.info("access for health care granted.." + count);
        	
        }
        
        if ((this.count % 2) == 0){
        	LOG.info("-- Count of allowed networks is even, so going to present in 2 columns");
        	this.count = 2;
        }
    }
		
	public int getCount() {
		return count;
	}


	public void setCount(int count) {
		this.count = count;
	}

}
