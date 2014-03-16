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
import org.cluj.bus.model.AllBusesDTO;
import org.cluj.bus.model.Bus;
import org.cluj.bus.model.BusDisplayInfo;
import org.cluj.bus.services.JPARepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BusesServlet extends HttpServlet
{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse httpServletResponse) throws ServletException, IOException
    {
        ServletUtils.sendResponse(httpServletResponse, getResponseString());
    }

    private String getResponseString()
    {
        List<Bus> buses = new JPARepository<>(Bus.class).findAll();

        Map<String, BusDisplayInfo> busDisplayInfos = new HashMap<>();

        for (Bus bus : buses)
        {
            String displayId = bus.getDisplayImage();

            BusDisplayInfo busDisplayInfo = busDisplayInfos.get(displayId);
            if (busDisplayInfo == null)
            {
                busDisplayInfo = new BusDisplayInfo();
                busDisplayInfo.setDisplayId(displayId);
                busDisplayInfos.put(displayId, busDisplayInfo);
            }

            Map<String, String> busInfosWithDirections = busDisplayInfo.getBusInfosWithDirections();
            if (busInfosWithDirections == null)
            {
                busInfosWithDirections = new HashMap<>();
                busDisplayInfo.setBusInfosWithDirections(busInfosWithDirections);
            }

            busInfosWithDirections.put(bus.getBusinessId(), bus.getName());
        }

        AllBusesDTO allBusesDTO = new AllBusesDTO();
        allBusesDTO.setBusDisplayInfos(busDisplayInfos.values());

        return new Gson().toJson(allBusesDTO);
    }
}
