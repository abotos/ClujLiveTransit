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

public class HibernateServiceProvider
{
    private static HibernateServiceProvider INSTANCE = new HibernateServiceProvider();

    private HibernateServiceProvider()
    {
    }

    public static HibernateServiceProvider getINSTANCE()
    {
        return INSTANCE;
    }

    public IWriteService getWriteService()
    {
        return new WriteService();
    }

    public IReadService getReadService()
    {
        return new ReadService();
    }
}
