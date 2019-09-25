package com.ceva.cevatrack.presentation;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import com.ceva.cjf.base.web.jsf.CJFBaseBean;
import com.ceva.cevatrack.util.LogManagerUtil;
import com.ceva.cevatrack.vo.LogVO;

/**
 * LoggerBean.
 * 
 * @author varsha.chowdary
 *
 */
@Named(value = "loggerBean")
@SessionScoped
public class LoggerBean extends CJFBaseBean {

    @Inject
    private LogManagerUtil logManagerUtil;

    private List<LogVO> logs;
    private List<LogVO> filteredLogs;
    private static List<Level> logLevels;

    /**
     * init.
     */
    @PostConstruct
    public void init() {
        logs = logManagerUtil.findAllClassNames();
    }

    static {
        logLevels = new ArrayList<>();
        logLevels.add(Level.SEVERE);
        logLevels.add(Level.WARNING);
        logLevels.add(Level.INFO);
        logLevels.add(Level.CONFIG);
        logLevels.add(Level.FINE);
        logLevels.add(Level.FINER);
        logLevels.add(Level.FINEST);
    }

    /**
     * Change log level for selected class.
     *
     * @param className
     *            className
     * @param logLevel
     *            new Log level {@link Level}
     */
    public void onChangeLogLevel(String className, Level logLevel) {
        if (logLevel == null) {
            addErrorMessage("Please select log level.");
        } else {
            for (LogVO logVO : logs) {
                if (logVO.getClassName().equals(className)) {
                    logVO.setLogLevel(logLevel);
                    logManagerUtil.updateLogLevel(className, logLevel);
                    addInfoMessage("Class log level updated successfully.");
                    break;
                }
            }
        }
    }

    public List<LogVO> getLogs() {
        return logs;
    }

    public void setLogs(List<LogVO> logs) {
        this.logs = logs;
    }

    public List<LogVO> getFilteredLogs() {
        return filteredLogs;
    }

    public void setFilteredLogs(List<LogVO> filteredLogs) {
        this.filteredLogs = filteredLogs;
    }

    public List<Level> getLogLevels() {
        return logLevels;
    }

    public void setLogLevels(List<Level> logLevels) {
        this.logLevels = logLevels;
    }
}
