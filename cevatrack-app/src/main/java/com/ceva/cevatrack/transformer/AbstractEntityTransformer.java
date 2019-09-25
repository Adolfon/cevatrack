package com.ceva.cevatrack.transformer;

/**
 * AbstractEntityTransformer.
 * 
 * @deprecated Since 3.2. Moved class to common-ejb as
 *             {@link com.ceva.framework.ejb.profile.transformer.AbstractEntityTransformer}
 * @author mohammad.rasheed
 * @param <T>
 *            This describes entity transformer .
 */
@Deprecated
public interface AbstractEntityTransformer<T> {

    void prepareForCreate(T entity);

    void prepareForUpdate(T entity);

}
