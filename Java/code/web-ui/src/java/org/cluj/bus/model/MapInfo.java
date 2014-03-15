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

public class MapInfo
{

    private StationInfo stationInfo;

    private Collection<BusInfo> busInfos;

    public StationInfo getStationInfo()
    {
        return stationInfo;
    }

    public void setStationInfo(StationInfo stationInfo)
    {
        this.stationInfo = stationInfo;
    }

    public Collection<BusInfo> getBusInfos()
    {
        return busInfos;
    }

    public void setBusInfos(Collection<BusInfo> busInfos)
    {
        this.busInfos = busInfos;
    }
}
