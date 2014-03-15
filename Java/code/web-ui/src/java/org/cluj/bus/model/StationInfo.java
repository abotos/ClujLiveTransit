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

import org.cluj.bus.pojo.Coordinate;

public class StationInfo
{

    private String stationId;

    private String displayName;

    private Coordinate coordinate;

    public String getStationId()
    {
        return stationId;
    }

    public void setStationId(String stationId)
    {
        this.stationId = stationId;
    }

    public String getDisplayName()
    {
        return displayName;
    }

    public void setDisplayName(String displayName)
    {
        this.displayName = displayName;
    }

    public Coordinate getCoordinate()
    {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate)
    {
        this.coordinate = coordinate;
    }
}
