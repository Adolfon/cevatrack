/**
 * Copyright (c) 2016 CEVA Logistics, Inc. All Rights Reserved.
 * 
 */

package com.ceva.cevatrack.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.ceva.framework.domain.AbstractEntity;

/**
 * The persistent class for the todo database table.
 * 
 */
@Entity
@Table(name = "todo")
public class Todo extends AbstractEntity<Long> implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;
  private String description;
  private Integer priority;
  private Date duedate;
  private String owner;

  /**
   * Default Constructor.
   */
  public Todo() {
    /**
     * Zero Parameter Constructor
     */
  }

  public Todo(Long id, String title, String desc, Integer priority, Date date, String owner) {
    this(title, desc, priority, date, owner);
    this.id = id;
  }

  /**
   * Todo Constructor.
   * 
   * @param title Title
   * @param desc Description
   * @param priority Priority
   * @param date Date
   * @param owner Owner
   */
  public Todo(String title, String desc, Integer priority, Date date, String owner) {
    this.title = title;
    this.description = desc;
    this.priority = priority;
    this.duedate = date;
    this.owner = owner;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getPriority() {
    return priority;
  }

  public void setPriority(Integer priority) {
    this.priority = priority;
  }

  public Date getDuedate() {
    return duedate;
  }

  public void setDuedate(Date duedate) {
    this.duedate = duedate;
  }

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }

}
