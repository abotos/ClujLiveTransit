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

import org.apache.commons.io.IOUtils;
import org.cluj.bus.logging.LogInitializer;
import org.cluj.bus.pojo.Coordinate;
import org.cluj.bus.pojo.WayPointInfo;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Main class of the Bus-bot
 */
public class BusBot
{
    /**
     * @param args the first parameter is the server host name or ip address
     *             the second parameter has to be the bus id
     *             the third parameter is a file with the coordinate points
     * @throws InterruptedException
     * @throws IOException
     */
    public static void main(String[] args) throws InterruptedException, IOException
    {
        LogInitializer.configureLOG4J();

        if (args.length != 3)
        {
            throw new IllegalArgumentException("Bus bot must be started with three arguments!");
        }
        BusBotRunner botRunner = new BusBotRunner(args[0], args[1], getWayPointInfoList(args[2]));
        botRunner.runBot();
    }

    private static Collection<WayPointInfo> getWayPointInfoList(String fileName) throws IOException
    {
        final List<String> strings = IOUtils.readLines(new FileReader(fileName));
        final Collection<WayPointInfo> wayPointInfos = new ArrayList<>(strings.size());
        for (String line : strings)
        {
            final StringTokenizer tokenizer = new StringTokenizer(line, ";");
            final String latitudeString = tokenizer.nextToken();
            final String longitudeString = tokenizer.nextToken();
            final String isStationString = tokenizer.nextToken();
            final Double latitude = Double.parseDouble(latitudeString);
            final Double longitude = Double.parseDouble(longitudeString);
            final Boolean isStation = Boolean.parseBoolean(isStationString);
            final Coordinate coordinate = new Coordinate(latitude, longitude);
            final WayPointInfo wayPointInfo = new WayPointInfo(coordinate, isStation);
            wayPointInfos.add(wayPointInfo);
        }
        return wayPointInfos;
    }
}
