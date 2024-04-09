package com.ceva.cevatrack.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.ceva.framework.domain.AbstractEntity;

/**
 * The persistent class for the file_uploader database table. ss
 */
@Entity
@Table(name = "file_uploader")
public class FileUploader extends AbstractEntity<Long> implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "todo_id")
  private Long todoId;
  @Column(name = "file_name")
  private String fileName;
  @Column(name = "content_type")
  private String contentType;

  /**
   * Default Constructor.
   */
  public FileUploader() {
    /**
     * Zero Parameter Constructor
     */
  }

  /**
   * FileUploader Constructor.
   * 
   * @param id Id
   * @param todoId TodoId
   * @param fileName FileName
   * @param contentType Type
   */
  public FileUploader(Long id, Long todoId, String fileName, String contentType) {
    this.id = id;
    this.todoId = todoId;
    this.fileName = fileName;
    this.contentType = contentType;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getTodoId() {
    return todoId;
  }

  public void setTodoId(Long todoId) {
    this.todoId = todoId;
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
}
