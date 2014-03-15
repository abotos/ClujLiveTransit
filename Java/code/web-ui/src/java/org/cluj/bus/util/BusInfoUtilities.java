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
package org.cluj.bus.util;

import org.cluj.bus.BusLocationUpdate;
import org.cluj.bus.Station;
import org.cluj.bus.model.MapBoundsInfo;
import org.cluj.bus.pojo.Coordinate;

import java.util.List;

public class BusInfoUtilities
{

    public static final double PROXIMITY_CONSTANT = 1.5;
    public static final int MINUTES_IN_A_DAY = 1440;
    public static final int MINUTE_IN_MILLISECONDS = 60000;

    public static boolean isInViewPort(BusLocationUpdate latest, MapBoundsInfo mapBoundsInfo)
    {
        Coordinate southWest = mapBoundsInfo.getSouthWest();
        Coordinate northEast = mapBoundsInfo.getNorthEast();
        Double latitude = latest.getLatitude();
        Double longitude = latest.getLongitude();
        return (latitude <= northEast.getLatitude()) && (latitude >= southWest.getLatitude()) && (longitude >= southWest.getLongitude()) && (longitude <= northEast.getLongitude());
    }

    public static boolean isApproaching(BusLocationUpdate latest, BusLocationUpdate previous, double stationLat, double stationLong)
    {
        double y1 = latest.getLatitude();
        double x1 = latest.getLongitude();
        double y2 = previous.getLatitude();
        double x2 = previous.getLongitude();
        double ys = stationLat;
        double xs = stationLong;

        boolean answer;

        if (x1 == x2)
        {
            if (y1 > y2)
            {
                answer = ys >= y1;
            }
            else
            {
                answer = ys <= y1;
            }
        }
        else
        {
            if (y1 == y2)
            {
                if (x1 > x2)
                {
                    answer = xs >= x1;
                }
                else
                {
                    answer = xs <= x1;
                }
            }
            else
            {
                double slope = (y1 - y2) / (x1 - x2);

                double normalSlope = -1 / slope;

                double xIntersection = (ys - y1 + slope * x1 - normalSlope * xs) / (slope - normalSlope);

                if (x1 > x2)
                {
                    answer = xIntersection >= x1;
                }
                else
                {
                    answer = xIntersection <= x1;
                }
            }
        }

        return answer;
    }

    public static int computeETA(Station station, List<BusLocationUpdate> busLocationUpdateList)
    {
        BusLocationUpdate latest = busLocationUpdateList.get(0);

        double averageSpeed = 0;

        for (int index = 0; index < busLocationUpdateList.size() - 2; index++)
        {
            BusLocationUpdate current = busLocationUpdateList.get(index);
            BusLocationUpdate previous = busLocationUpdateList.get(index + 1);

            double distance = distance(current.getLatitude(), current.getLongitude(), previous.getLatitude(), previous.getLongitude());

            double speed = distance / (current.getLastUpdate().getTime() - previous.getLastUpdate().getTime());

            averageSpeed += speed;
        }

        int result = -1;

        if (busLocationUpdateList.size() >= 2)
        {
            averageSpeed /= (busLocationUpdateList.size() - 1);

            double distanceToStation = distance(latest.getLatitude(), latest.getLongitude(), station.getLatitude(), station.getLongitude());

            double timeToStation = (distanceToStation / averageSpeed) / MINUTE_IN_MILLISECONDS;

            result = (int) timeToStation;
        }
        //this might be a computational error
        if(result > MINUTES_IN_A_DAY)
        {
            result = -1;
        }
        return result;
    }

    private static double distance(double latitude1, double longitude1, double latitude2, double longitude2)
    {
        return Math.sqrt((latitude1 - latitude2) * (latitude1 - latitude2) + (longitude1 - longitude2) * (longitude1 - longitude2));
    }

    public static boolean isInProximity(Station station, BusLocationUpdate latest, BusLocationUpdate previous)
    {
        double distanceBetweenWayPoints = distance(latest.getLatitude(), latest.getLongitude(), previous.getLatitude(), previous.getLongitude());
        double distanceToStation = distance(latest.getLatitude(), latest.getLongitude(), station.getLatitude(), station.getLongitude());

        return distanceToStation < distanceBetweenWayPoints * PROXIMITY_CONSTANT;
    }
}
