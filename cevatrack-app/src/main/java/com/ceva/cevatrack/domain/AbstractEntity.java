package com.ceva.cevatrack.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * AbstractEntity.
 * 
 * @deprecated Since 3.2. Moved class to common-ejb as
 *             {@link com.ceva.framework.domain.AbstractEntity}
 * @author mohammad.rasheed.
 * @param <PK> This describes AbstractEntity.
 *
 */
@Deprecated
@MappedSuperclass
public abstract class AbstractEntity<P extends Serializable> implements Serializable {

  @Version
  @Column(name = "version", nullable = false)
  private Short version;

  /**
   * use java time api.
   */
  @Column(name = "created_on", nullable = false)
  private Date createdOn;

  @Column(name = "created_by", nullable = false)
  private String createdBy;

  @Column(name = "updated_on")
  private Date updatedOn;

  @Column(name = "updated_by")
  private String updatedBy;

  @Transient
  public boolean isNew() {
    return null == getId();
  }

  public abstract P getId();

  @Override
  public String toString() {
    return String.format("Entity of type %s with id: %s", this.getClass().getName(), getId());
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 31).append(getId()).toHashCode();
  }

  @SuppressWarnings("rawtypes")
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (this.getId() == null) {
      return false;
    }

    if (obj instanceof AbstractEntity && obj.getClass().equals(getClass())) {
      return this.getId().equals(((AbstractEntity) obj).getId());
    }

    return false;
  }

  public Short getVersion() {
    return version;
  }

  public void setVersion(Short version) {
    this.version = version;
  }

  public Date getCreatedOn() {
    return createdOn;
  }

  public void setCreatedOn(Date createdOn) {
    this.createdOn = createdOn;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public Date getUpdatedOn() {
    return updatedOn;
  }

  public void setUpdatedOn(Date updatedOn) {
    this.updatedOn = updatedOn;
  }

  public String getUpdatedBy() {
    return updatedBy;
  }

  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }

}
