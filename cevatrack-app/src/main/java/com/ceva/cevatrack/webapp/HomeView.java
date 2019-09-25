package com.ceva.cevatrack.webapp;

import java.io.IOException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeView.class);

    public void init() {
        LOGGER.info(" -- In the HomeView class --");
        try {
            checkLogin();
        } catch (IOException e) {
            LOGGER.error(" -- There is not user in the session when calling HomeView class ", e);
        }
    }

}
