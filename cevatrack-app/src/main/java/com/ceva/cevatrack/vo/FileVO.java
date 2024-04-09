package com.ceva.cevatrack.vo;

import java.io.Serializable;

/**
 * FileVO.
 * 
 * @author varsha.anumolu
 *
 */
public class FileVO implements Serializable {

  private static final long serialVersionUID = 2398666602472057338L;

  private String name;

  private String lastModified;

  private String size;

  private String filePath;

  /**
   * Constructor.
   */
  public FileVO(String name, String lastModified, String size, String filePath) {
    this.name = name;
    this.lastModified = lastModified;
    this.size = size;
    this.filePath = filePath;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSize() {
    return size;
  }

  public void setSize(String size) {
    this.size = size;
  }

  public String getLastModified() {
    return lastModified;
  }

  public void setLastModified(String lastModified) {
    this.lastModified = lastModified;
  }

  public String getFilePath() {
    return filePath;
  }

  public void setFilePath(String filePath) {
    this.filePath = filePath;
  }

}
