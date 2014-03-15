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
package org.cluj.bus.servlet;

import com.google.gson.Gson;
import org.cluj.bus.Station;
import org.cluj.bus.convert.StationConverter;
import org.cluj.bus.db.HibernateServiceProvider;
import org.cluj.bus.db.IReadService;
import org.cluj.bus.model.AllStationsDTO;
import org.cluj.bus.model.StationInfo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class StationsServlet extends HttpServlet
{

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException
    {
        ServletUtils.sendResponse(httpServletResponse, getResponseString());
    }

    private String getResponseString()
    {
        IReadService readService = HibernateServiceProvider.getINSTANCE().getReadService();

        Collection<Object> allStations = readService.load(Station.class);

        Collection<StationInfo> allStationInfos = new ArrayList<StationInfo>();

        for (Object station : allStations)
        {
            allStationInfos.add(StationConverter.getStationInfo((Station) station));
        }

        AllStationsDTO response = new AllStationsDTO();
        response.setAllStations(allStationInfos);

        return new Gson().toJson(response);
    }
}
