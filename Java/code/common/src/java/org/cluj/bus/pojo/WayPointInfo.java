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

package org.cluj.bus.pojo;

public class WayPointInfo
{
    private Coordinate coordinate;

    private boolean isStation;

    public WayPointInfo(Coordinate coordinate, boolean isStation)
    {
        this.coordinate = coordinate;
        this.isStation = isStation;
    }

    public Coordinate getCoordinate()
    {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate)
    {
        this.coordinate = coordinate;
    }

    public boolean isStation()
    {
        return isStation;
    }

    public void setStation(boolean isStation)
    {
        this.isStation = isStation;
    }
}
