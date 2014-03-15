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

import org.apache.log4j.Logger;
import org.cluj.bus.pojo.WayPointInfo;

import java.io.IOException;
import java.util.Collection;

public class BusBotRunner
{
    private static final Logger LOGGER = Logger.getLogger(BusBotRunner.class);

    private final String host;
    private final String busId;
    private final Collection<WayPointInfo> coordinates;

    public BusBotRunner(String host, String busId, Collection<WayPointInfo> coordinates)
    {
        this.host = host;
        this.busId = busId;
        this.coordinates = coordinates;
    }

    public void runBot()
    {
        // TODO Log some stuff here
        BotThread botThread = new BotThread(host, busId, coordinates);

        botThread.start();

        try
        {
            // Wait for some input
            int read = System.in.read();

            botThread.setRunning(false);

            botThread.join();
        }
        catch (InterruptedException | IOException e)
        {
            LOGGER.error("Exception while stopping BotThread", e);
        }
    }
}
