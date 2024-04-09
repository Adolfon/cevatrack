package com.ceva.cevatrack.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import com.ceva.cevatrack.vo.LogVO;

/**
 * LogManagerUtil.
 * 
 * @author varsha.chowdary
 *
 */
public class LogManagerUtil implements Serializable {

  private LogManager logManager = LogManager.getLogManager();

  /**
   * Find all classes that use logger.
   * 
   */
  public List<LogVO> findAllClassNames() {
    List<LogVO> logs = new ArrayList<>();
    try {
      Enumeration<String> loggerNames = logManager.getLoggerNames();
      while (loggerNames.hasMoreElements()) {
        String loggerName = loggerNames.nextElement();
        if (isClass(loggerName)) {
          logs.add(new LogVO(loggerName, logManager.getLogger(loggerName).getLevel()));
        }
      }
    } catch (SecurityException e) {
      e.printStackTrace();
    }
    return logs;
  }

  /**
   * Check whether given logger name is a class or not.
   *
   * @param className ClassName
   * @return true if the argument passed is a class.
   */
  private boolean isClass(String className) {
    try {
      Class.forName(className);
      return true;
    } catch (ClassNotFoundException classNotFoundException) {
      return false;
    }
  }

  /**
   * Change log level for selected class.
   *
   * @param className className
   * @param logLevel new Log level
   */
  public void updateLogLevel(String className, Level logLevel) {
    logManager.getLogger(className).setLevel(logLevel);
  }
}
