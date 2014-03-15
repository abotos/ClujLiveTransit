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

package org.cluj.bus.db;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.io.Serializable;
import java.util.Collection;

public class ReadService implements IReadService
{
    @Override
    public Object load(Class clazz, Serializable id)
    {
        final Session session = HibernateUtil.openSession();
        final Transaction transaction = session.beginTransaction();
        final Object object = session.load(clazz, id);
        transaction.commit();
        session.close();
        return object;
    }

    @Override
    public Object loadFirst(Class clazz, String propertyName, Object value)
    {
        final Session session = HibernateUtil.openSession();
        final Transaction transaction = session.beginTransaction();
        final Object object = session.createCriteria(clazz).add(Restrictions.eq(propertyName, value)).list().get(0);
        transaction.commit();
        session.close();
        return object;
    }

    @Override
    public Collection<Object> load(Class clazz, String propertyName, Object value)
    {
        final Session session = HibernateUtil.openSession();
        final Transaction transaction = session.beginTransaction();
        final Collection<Object> objects = session.createCriteria(clazz).add(Restrictions.eq(propertyName, value)).list();
        transaction.commit();
        session.close();
        return objects;
    }
}
