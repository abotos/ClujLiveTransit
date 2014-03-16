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

import java.util.Map;

public class BusDisplayInfo
{
    private String displayId;

    private Map<String, String> busInfosWithDirections;

    public String getDisplayId()
    {
        return displayId;
    }

    public void setDisplayId(String displayId)
    {
        this.displayId = displayId;
    }

    public Map<String, String> getBusInfosWithDirections()
    {
        return busInfosWithDirections;
    }

    public void setBusInfosWithDirections(Map<String, String> busInfosWithDirections)
    {
        this.busInfosWithDirections = busInfosWithDirections;
    }
}
