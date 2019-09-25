package com.ceva.cevatrack.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.faces.context.FacesContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// import com.ceva.cevatrack.wsmicrolise.WsMicroliseEnvironment;
import com.ceva.security.aaf.session.SessionManager;

public class SystemSessionConfig implements Serializable {
    private static final Logger LOGGER = LoggerFactory.getLogger(SystemSessionConfig.class);

    public static Map<String, String> loadCevaResponsiveXmlProps() {
        final String filePathAndName = "";
        final Properties properties = new Properties();
        try {
            File directory = new File("./");// C:\Users\canoad\applications\neon64\eclipse\.
            LOGGER.debug(directory.getAbsolutePath());
            LOGGER.warn(directory.getAbsolutePath());
            // java.net.URL url = getClass().getResource("CEVAtrack-responsive.xml");//null
            String path = "C:/Users/canoad/git/CEVAtrack-responsive/CEVAtrack-responsive-app/local_tomee/conf"
                    + "/Catalina/localhost/CEVAtrack-responsive_mod.xml";
            File file = new File(path);
            final FileInputStream in = new FileInputStream(file);
            properties.loadFromXML(in);
            in.close();
        } catch (FileNotFoundException fnfEx) {
            System.err.println("Could not read properties from file " + filePathAndName);
        } catch (IOException ioEx) {
            System.err.println("IOException encountered while reading from " + filePathAndName);
        }
        return (Map) properties;
    }

    public static void SystemSessionConfig() {
        // SessionManager.getSession().setAttribute("APP_USER", null);
        Map<String, String> parameters = new HashMap<>();
        parameters.putAll(FacesContext.getCurrentInstance().getExternalContext().getInitParameterMap());
        LOGGER.info("Number of parameters recovered from before: " + parameters.size());
        Map<String, String> parameters2 = new HashMap<String, String>();
        parameters2.putAll(loadCevaResponsiveXmlProps());
        parameters.putAll(parameters2);
        // FacesContext.getCurrentInstance().getExternalContext().getInitParameterMap().putAll(loadCevaResponsiveXmlProps());;
        // parameters = FacesContext.getCurrentInstance().getExternalContext().getInitParameterMap();
        LOGGER.info("Number of parameters recovered after: " + parameters.size());

    }

}
