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

import java.util.ArrayList;
import java.util.Collection;

public class AnnotatedClassProvider
{

    private static final Collection<Class> ANNOTATED_CLASSES = new ArrayList<Class>();

    static
    {
        ANNOTATED_CLASSES.add(BusLocationUpdate.class);
        ANNOTATED_CLASSES.add(Bus.class);
        ANNOTATED_CLASSES.add(Station.class);
        ANNOTATED_CLASSES.add(Trip.class);
        ANNOTATED_CLASSES.add(StationBus.class);
    }

    public static Collection<Class> getAnnotatedClasses()
    {
        return ANNOTATED_CLASSES;
    }
}
