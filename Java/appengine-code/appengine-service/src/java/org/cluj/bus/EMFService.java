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
package org.cluj.bus;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EMFService
{

    private static final EntityManagerFactory EMF_INSTANCE = Persistence
            .createEntityManagerFactory("transactions-optional");

    private EMFService()
    {
    }

    public static EntityManagerFactory get()
    {
        return EMF_INSTANCE;
    }
}
