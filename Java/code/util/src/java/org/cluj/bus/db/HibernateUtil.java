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
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.net.URL;
import java.util.Collection;

public class HibernateUtil
{

    private static SessionFactory sessionFactory;

    public static synchronized void configureHibernate(URL configFileUrl, final Collection<Class> annotatedClasses)
    {
        if (sessionFactory != null)
        {
            throw new IllegalStateException("Hibernate is already configured");
        }

        Configuration configuration = new Configuration();
        configuration.configure(configFileUrl);

        for (Class annotatedClass : annotatedClasses)
        {
            configuration.addAnnotatedClass(annotatedClass);
        }

        StandardServiceRegistry serviceRegistry =
                new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    public static synchronized void closeSessionFactory()
    {
        if (sessionFactory != null)
        {
            sessionFactory.close();
        }
    }

    public static Session openSession()
    {
        return sessionFactory.openSession();
    }
}
