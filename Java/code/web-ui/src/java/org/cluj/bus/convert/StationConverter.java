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
package org.cluj.bus.convert;

import org.cluj.bus.Station;
import org.cluj.bus.model.StationInfo;
import org.cluj.bus.pojo.Coordinate;

public class StationConverter
{
    public static StationInfo getStationInfo(Station station)
    {
        StationInfo stationInfo = new org.cluj.bus.model.StationInfo();

        stationInfo.setStationId(station.getBusinessId());
        stationInfo.setDisplayName(station.getName());
        stationInfo.setCoordinate(new Coordinate(station.getLatitude(), station.getLongitude()));
        return stationInfo;
    }

}
