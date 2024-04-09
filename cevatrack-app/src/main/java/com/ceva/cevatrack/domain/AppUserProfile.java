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
 * The persistent class for the file_uploader database table.
 * 
 */
@Entity
@Table(name = "app_user_profile")
public class AppUserProfile extends AbstractEntity<Long> implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "app_user_profile_id", nullable = false)
  private Long id;

  @Column(name = "user_profile_id")
  private Long userProfileId;

  @Column(name = "enable_todo_notification")
  private boolean enableTodoNotification;

  /**
   * Default Constructor.
   */
  public AppUserProfile() {
    /**
     * Zero Parameter Constructor
     */
  }

  /**
   * AppUserProfile Constructor.
   * 
   * @param id Id
   * @param userProfileId UserProfileId
   * @param enableTodoNotification EnableTodoNotification
   */
  public AppUserProfile(Long id, Long userProfileId, boolean enableTodoNotification) {
    this.id = id;
    this.userProfileId = userProfileId;
    this.enableTodoNotification = enableTodoNotification;
  }

  /**
   * getter for ID.
   * 
   * @return
   */
  public Long getId() {
    return id;
  }

  /**
   * Setter for ID.
   * 
   * @param id ID
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * getter for userProfileID.
   * 
   * @return the userProfileId
   */
  public Long getUserProfileId() {
    return userProfileId;
  }

  /**
   * setter for userProfileID.
   * 
   * @param userProfileId the userProfileId to set
   */
  public void setUserProfileId(Long userProfileId) {
    this.userProfileId = userProfileId;
  }

  /**
   * getter for enableTodoNotification.
   * 
   * @return the enableTodoNotification
   */
  public boolean isEnableTodoNotification() {
    return enableTodoNotification;
  }

  /**
   * setter for enableTodoNotification.
   * 
   * @param enableTodoNotification the enableTodoNotification to set
   */
  public void setEnableTodoNotification(boolean enableTodoNotification) {
    this.enableTodoNotification = enableTodoNotification;
  }
}
