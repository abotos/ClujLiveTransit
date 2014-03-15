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

import com.google.gson.Gson;
import org.cluj.bus.pojo.BusLocation;

public class BusHttpClient
{

    public static void sendUpdate(BusLocation location)
    {
        String json = new Gson().toJson(location);
    }
}
