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

import org.cluj.bus.ISpecification;

import java.io.Serializable;
import java.util.List;

public interface IRepository<T> extends Serializable
{
    void save(T entity);

    List<T> findAll(ISpecification<T> specification);

    List<T> findAll();

    void flush();

    void close();
}
