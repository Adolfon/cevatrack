package com.ceva.cevatrack.vo;

import java.util.logging.Level;

/**
 * LogVO.
 * 
 * @author varsha.chowdary
 *
 */
public class LogVO {

  private String className;
  private Level logLevel;

  public LogVO(String className, Level logLevel) {
    this.className = className;
    this.logLevel = logLevel;
  }

  public String getClassName() {
    return className;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  public Level getLogLevel() {
    return logLevel;
  }

  public void setLogLevel(Level logLevel) {
    this.logLevel = logLevel;
  }

}
