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

public class MapBoundsInfo
{
    private Coordinate northEast;

    private Coordinate southWest;

    public Coordinate getNorthEast()
    {
        return northEast;
    }

    public void setNorthEast(Coordinate northEast)
    {
        this.northEast = northEast;
    }

    public Coordinate getSouthWest()
    {
        return southWest;
    }

    public void setSouthWest(Coordinate southWest)
    {
        this.southWest = southWest;
    }
}
