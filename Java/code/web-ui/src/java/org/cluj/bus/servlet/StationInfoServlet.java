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
import org.cluj.bus.db.HibernateServiceProvider;
import org.cluj.bus.db.IReadService;
import org.cluj.bus.model.StationInfo;
import org.cluj.bus.pojo.Coordinate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StationInfoServlet extends HttpServlet
{

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException
    {
        String stationId = httpServletRequest.getParameter(ServletUtils.STATION_ID_PARAMETER_KEY);

        ServletUtils.sendResponse(httpServletResponse, getResponseString(stationId));
    }

    private String getResponseString(String stationId)
    {
        IReadService readService = HibernateServiceProvider.getINSTANCE().getReadService();
        Station station = (Station) readService.loadFirst(Station.class, "businessId", stationId);
        StationInfo stationInfo = new StationInfo();

        stationInfo.setStationId(stationId);
        stationInfo.setDisplayName(station.getName());
        stationInfo.setCoordinate(new Coordinate(station.getLatitude(), station.getLongitude()));

        return new Gson().toJson(stationInfo);
    }
}
