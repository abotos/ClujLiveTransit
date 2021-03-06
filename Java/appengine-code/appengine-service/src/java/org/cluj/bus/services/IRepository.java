/*
 * *************************************************************************
 * Copyright (C) FRS Belgium NV ("FRSGlobal"). All rights reserved.
 *
 * This computer program is protected by copyright law and international
 * treaties. Unauthorized reproduction or distribution of this program,
 * or any portion of it, may result in severe civil and criminal penalties,
 * and will be prosecuted to the maximum extent possible under the law.
 * *************************************************************************
 */
package org.cluj.bus.services;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface IRepository<T> extends Serializable
{
    void save(T entity);

    T findFirst(String field, Object value);

    List<T> findAll(Map<String, Object> restrictions);

    List<T> findAll(String field, Object value);

    List<T> findAll();

    void flush();

    void close();
}
