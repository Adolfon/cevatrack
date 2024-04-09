package com.ceva.cevatrack.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "aaf_user")
public class AAFUser4Nike {
  @Id
  @Column(name = "user_id", nullable = false)
  private Long userId;
  @Column(name = "user_principal")
  private String userPrincipal;
  @Column(name = "password")
  private String password;
  @Column(name = "first_name")
  private String firstName;
  @Column(name = "last_name")
  private String lastName;
  @Column(name = "email")
  private String email;
  @Column(name = "user_type")
  private String userType;
  @Column(name = "is_active")
  private Boolean isActive;
  @Column(name = "incorrect_login_attempts")
  private Integer incorrectLoginAttempts;
  @Column(name = "is_locked")
  private Boolean isLocked;
  @Column(name = "last_password_change_time")
  private Date lastPasswordChangeTime;
  @Column(name = "is_password_change_required")
  private Boolean isPasswordChangeRequired;
  @Column(name = "account_expiration_date")
  private Date accountExpirationDate;
  @Column(name = "last_successful_login")
  private Date lastSuccessfulLogin;


  public AAFUser4Nike() {
    this.isActive = Boolean.TRUE;
  }

  public AAFUser4Nike(String firstName, String lastName, String userPrincipal, String email) {
    this.isActive = Boolean.TRUE;
    this.firstName = firstName;
    this.lastName = lastName;
    this.userPrincipal = userPrincipal;
    this.email = email;
  }

  public Long getId() {
    return this.userId;
  }

  public String getUserPrincipal() {
    return this.userPrincipal;
  }

  public void setUserPrincipal(String userPrincipal) {
    this.userPrincipal = userPrincipal;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Long getUserId() {
    return this.userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getAAFInstanceName() {
    return this.userPrincipal;
  }

  public String getAAFPermissionDomain() {
    return "aaf_user";
  }

  public String getFirstName() {
    return this.firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Boolean getIsActive() {
    return this.isActive;
  }

  public void setIsActive(Boolean isActive) {
    this.isActive = isActive;
  }

  public Integer getIncorrectLoginAttempts() {
    return this.incorrectLoginAttempts;
  }

  public void setIncorrectLoginAttempts(Integer incorrectLoginAttempts) {
    this.incorrectLoginAttempts = incorrectLoginAttempts;
  }

  public Boolean getIsLocked() {
    return this.isLocked;
  }

  public void setIsLocked(Boolean isLocked) {
    this.isLocked = isLocked;
  }

  public Date getLastPasswordChangeTime() {
    return this.lastPasswordChangeTime;
  }

  public void setLastPasswordChangeTime(Date lastPasswordChangeTime) {
    this.lastPasswordChangeTime = lastPasswordChangeTime;
  }

  public Boolean getIsPasswordChangeRequired() {
    return this.isPasswordChangeRequired;
  }

  public void setIsPasswordChangeRequired(Boolean isPasswordChangeRequired) {
    this.isPasswordChangeRequired = isPasswordChangeRequired;
  }

  public Date getAccountExpirationDate() {
    return this.accountExpirationDate;
  }

  public void setAccountExpirationDate(Date accountExpirationDate) {
    this.accountExpirationDate = accountExpirationDate;
  }

  public Date getLastSuccessfulLogin() {
    return this.lastSuccessfulLogin;
  }

  public void setLastSuccessfulLogin(Date lastSuccessfulLogin) {
    this.lastSuccessfulLogin = lastSuccessfulLogin;
  }

  public String getUserType() {
    return this.userType;
  }

  public void setUserType(String userType) {
    this.userType = userType;
  }

  @Override
  public String toString() {
    return "AAFUser4Nike [userId=" + userId + ", userPrincipal=" + userPrincipal + ", password="
        + password + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
        + ", userType=" + userType + ", isActive=" + isActive + ", incorrectLoginAttempts="
        + incorrectLoginAttempts + ", isLocked=" + isLocked + ", lastPasswordChangeTime="
        + lastPasswordChangeTime + ", isPasswordChangeRequired=" + isPasswordChangeRequired
        + ", accountExpirationDate=" + accountExpirationDate + ", lastSuccessfulLogin="
        + lastSuccessfulLogin + "]";
  }



}
