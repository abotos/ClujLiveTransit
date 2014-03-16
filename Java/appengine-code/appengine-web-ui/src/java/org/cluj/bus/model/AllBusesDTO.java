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
package org.cluj.bus.model;

import java.util.Collection;

public class AllBusesDTO
{
    private Collection<BusDisplayInfo> busDisplayInfos;

    public Collection<BusDisplayInfo> getBusDisplayInfos()
    {
        return busDisplayInfos;
    }

    public void setBusDisplayInfos(Collection<BusDisplayInfo> busDisplayInfos)
    {
        this.busDisplayInfos = busDisplayInfos;
    }
}
