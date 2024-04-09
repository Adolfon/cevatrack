package com.ceva.cevatrack.transformer;

import java.util.Date;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import com.ceva.cevatrack.domain.AbstractEntity;

/**
 * AbstractEntityTransformerImpl.
 * 
 * @deprecated Since 3.2. Moved class to common-ejb as
 *             {@link com.ceva.framework.ejb.profile.transformer.AbstractEntityTransformerImpl}
 * @author mohammad.rasheed.
 * @param <T> attribute
 */
@Deprecated
public class AbstractEntityTransformerImpl<T extends AbstractEntity>
    implements AbstractEntityTransformer<T> {

  @Override
  public void prepareForCreate(T entity) {
    Subject subject = SecurityUtils.getSubject();
    String currentUser =
        subject.getPrincipal() == null ? "Anonymous" : subject.getPrincipal().toString();

    if (entity.getId() == null) {
      entity.setCreatedBy(currentUser);
      entity.setCreatedOn(new Date());
    }
  }

  @Override
  public void prepareForUpdate(T entity) {
    Subject subject = SecurityUtils.getSubject();
    String currentUser =
        subject.getPrincipal() == null ? "Anonymous" : subject.getPrincipal().toString();
    entity.setUpdatedBy(currentUser);
    entity.setUpdatedOn(new Date());
  }
}
