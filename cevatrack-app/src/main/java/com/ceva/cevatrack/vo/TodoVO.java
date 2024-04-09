/**
 * Copyright (c) 2016 CEVA Logistics, Inc. All Rights Reserved.
 */

package com.ceva.cevatrack.vo;

import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.johnzon.mapper.JohnzonConverter;
import com.ceva.cjf.base.web.resource.LdapUserVO;
import com.ceva.framework.vo.BaseVO;
import com.ceva.cevatrack.util.LocalDateConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

/**
 * TodoVO.
 * 
 * @author mohammad.rasheed
 *
 */
public class TodoVO extends BaseVO {
  private static final long serialVersionUID = 1L;

  public static final String FIND_ALL = "TODO.findAll";

  private String title;
  @ApiModelProperty(required = true, example = "todo description")
  private String description;
  private Integer priority;
  @JohnzonConverter(LocalDateConverter.class)
  @ApiModelProperty(required = true, example = "2001-07-04T12:08:56.235-0700")
  private Date duedate;
  @JsonIgnore
  private Date startdate;
  @JsonIgnore
  private Date enddate;
  private String owner;
  private LdapUserVO ldapUser;
  private List<FileUploaderVO> fileUploaderVOList;
  private Map<String, InputStream> files;
  private Map<String, String> filesBase64;

  /**
   * Default Constructor.
   */
  public TodoVO() {
    /**
     * Zero Parameter Constructor
     */
  }

  /**
   * Constructor.
   * 
   * @param id Id
   * @param title Title
   * @param desc Description
   * @param priority Priority
   * @param date Date
   * @param owner Owner
   */
  public TodoVO(Long id, String title, String desc, Integer priority, Date date, String owner) {
    super.id = id;
    this.title = title;
    this.description = desc;
    this.priority = priority;
    this.duedate = date;
    this.owner = owner;
  }

  /**
   * Gets files base64.
   * 
   * @return filesBase64.
   */
  public Map<String, String> getFilesBase64() {
    return filesBase64;
  }

  /**
   * Sets the files base64.
   * 
   * @param filesBase64 the file.
   */
  public void setFilesBase64(Map<String, String> filesBase64) {
    this.filesBase64 = filesBase64;
  }

  /**
   * Gets the title.
   * 
   * @return title the title.
   */
  public String getTitle() {
    return title;
  }

  /**
   * Gets the title.
   * 
   * @param title the title.
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Gets the description.
   * 
   * @return description.
   */
  public String getDescription() {
    return description;
  }

  /**
   * Sets the description.
   * 
   * @param description the description.
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Gets the priority.
   * 
   * @return priority.
   */
  public Integer getPriority() {
    return priority;
  }

  /**
   * Sets the priority.
   * 
   * @param priority the priority.
   */
  public void setPriority(Integer priority) {
    this.priority = priority;
  }

  /**
   * Gets the duedate.
   * 
   * @return duedate.
   */
  public Date getDuedate() {
    return duedate;
  }

  /**
   * Sets the duedate.
   * 
   * @param duedate the duedate.
   */
  public void setDuedate(Date duedate) {
    this.duedate = duedate;
  }

  /**
   * Gets the owner.
   * 
   * @return owner.
   */
  public String getOwner() {
    return owner;
  }

  /**
   * Sets the owner.
   * 
   * @param owner the owner.
   */
  public void setOwner(String owner) {
    this.owner = owner;
  }

  /**
   * Gets the Ldap user.
   * 
   * @return ldapUser.
   */
  public LdapUserVO getLdapUser() {
    return ldapUser;
  }

  /**
   * Sets the Ldap user.
   * 
   * @param ldapUser the ldapUser.
   */
  public void setLdapUser(LdapUserVO ldapUser) {
    this.ldapUser = ldapUser;
  }

  /**
   * Gets the start date.
   * 
   * @return start date.
   */
  public Date getStartdate() {
    return startdate;
  }

  /**
   * Sets the start date.
   * 
   * @param startdate the startdate.
   */
  public void setStartdate(Date startdate) {
    this.startdate = startdate;
  }

  /**
   * Gets the end date.
   * 
   * @return end date.
   */
  public Date getEnddate() {
    return enddate;
  }

  /**
   * Sets the end date.
   * 
   * @param enddate the end date.
   */
  public void setEnddate(Date enddate) {
    this.enddate = enddate;
  }

  /**
   * Gets the file uploader VO List.
   * 
   * @return fileUploaderVOList.
   */
  public List<FileUploaderVO> getFileUploaderVOList() {
    return fileUploaderVOList;
  }

  /**
   * Sets the file uploader VO List.
   * 
   * @param fileUploaderVOList the fileUploaderVOList.
   */
  public void setFileUploaderVOList(List<FileUploaderVO> fileUploaderVOList) {
    this.fileUploaderVOList = fileUploaderVOList;
  }

  /**
   * Gets the files.
   * 
   * @return files.
   */
  public Map<String, InputStream> getFiles() {
    return files;
  }

  /**
   * Sets the files.
   * 
   * @param files the files.
   */
  public void setFiles(Map<String, InputStream> files) {
    this.files = files;
  }

}
