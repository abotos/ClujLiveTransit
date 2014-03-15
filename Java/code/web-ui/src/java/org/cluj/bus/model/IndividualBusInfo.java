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

public class IndividualBusInfo
{

    private Coordinate coordinate;

    // In minutes
    private int timeToArrival;

    private boolean approaching;

    private boolean inViewPort;

    public Coordinate getCoordinate()
    {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate)
    {
        this.coordinate = coordinate;
    }

    public int getTimeToArrival()
    {
        return timeToArrival;
    }

    public void setTimeToArrival(int timeToArrival)
    {
        this.timeToArrival = timeToArrival;
    }

    public boolean isApproaching()
    {
        return approaching;
    }

    public void setApproaching(boolean approaching)
    {
        this.approaching = approaching;
    }

    public boolean isInViewPort()
    {
        return inViewPort;
    }

    public void setInViewPort(boolean inViewPort)
    {
        this.inViewPort = inViewPort;
    }
}
