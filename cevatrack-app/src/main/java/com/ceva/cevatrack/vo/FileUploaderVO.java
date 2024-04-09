package com.ceva.cevatrack.vo;

import java.io.Serializable;

/**
 * FileUploaderVO.
 * 
 * @author murthy.koppaka
 *
 */
public class FileUploaderVO implements Serializable {

  private static final long serialVersionUID = 1L;
  private Long id;
  private Long toDoId;
  private String fileName;
  private String contentType;

  /**
   * Default Constructor.
   */
  public FileUploaderVO() {
    /**
     * Zero Parameter Constructor
     */
  }

  /**
   * Constructor.
   * 
   * @param id Id
   * @param toDoId todoId
   * @param fileName the name of the file
   * @param contentType type of content
   */
  public FileUploaderVO(Long id, Long toDoId, String fileName, String contentType) {
    this.id = id;
    this.toDoId = toDoId;
    this.fileName = fileName;
    this.contentType = contentType;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public String getContentType() {
    return contentType;
  }

  public void setContentType(String contentType) {
    this.contentType = contentType;
  }

  public Long getToDoId() {
    return toDoId;
  }

  public void setToDoId(Long toDoId) {
    this.toDoId = toDoId;
  }

}
