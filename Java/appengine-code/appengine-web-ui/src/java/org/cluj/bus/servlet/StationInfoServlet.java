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
import org.cluj.bus.convert.StationConverter;
import org.cluj.bus.model.Station;
import org.cluj.bus.model.StationInfo;
import org.cluj.bus.services.JPARepository;

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
        final String stationId = httpServletRequest.getParameter(ServletUtils.STATION_ID_PARAMETER_KEY);

        ServletUtils.sendResponse(httpServletResponse, getResponseString(stationId));
    }

    private String getResponseString(String stationId)
    {
        final Station station = new JPARepository<>(Station.class).findFirst("businessId", stationId);

        final StationInfo stationInfo = StationConverter.getStationInfo(station);

        return new Gson().toJson(stationInfo);
    }
}