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
import org.cluj.bus.model.BusSchedule;
import org.cluj.bus.model.BusScheduleDTO;
import org.cluj.bus.model.CategorySchedule;
import org.cluj.bus.services.JPARepository;
import org.cluj.bus.util.ScheduleUtilities;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BusScheduleServlet extends HttpServlet
{

    private static final Logger LOGGER = Logger.getLogger(BusScheduleServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException
    {
        String busId = httpServletRequest.getParameter(ServletUtils.BUS_ID_PARAMETER_KEY);

        ServletUtils.sendResponse(httpServletResponse, getResponseString(busId));
    }

    private String getResponseString(String busId)
    {
        List<BusSchedule> busSchedules = new JPARepository<>(BusSchedule.class).findAll("busId", busId);

        Map<String, CategorySchedule> categorySchedules = new HashMap<>();

        for (BusSchedule busSchedule : busSchedules)
        {
            String days = busSchedule.getDays();

            CategorySchedule categorySchedule = categorySchedules.get(days);
            if (categorySchedule == null)
            {
                categorySchedule = new CategorySchedule();
                categorySchedules.put(days, categorySchedule);

                categorySchedule.setDisplayName(busSchedule.getCategory());
                categorySchedule.setApplicableDays(getApplicableDays(days));
            }


            Collection<Date> startTimes = categorySchedule.getStartTimes();
            if (startTimes == null)
            {
                startTimes = new ArrayList<>();
                categorySchedule.setStartTimes(startTimes);
            }

            try
            {
                startTimes.add(ScheduleUtilities.getStartTime(busSchedule.getStartTime()));
            }
            catch (ParseException e)
            {
                LOGGER.log(Level.SEVERE, "Error parsing start time", e);
            }
        }

        BusScheduleDTO schedule = new BusScheduleDTO();
        schedule.setSchedules(categorySchedules.values());

        return new Gson().toJson(schedule);
    }

    private Collection<Integer> getApplicableDays(String days)
    {
        List<Integer> applicableDays = new ArrayList<>();
        for (char aChar : days.toCharArray())
        {
            int day = Integer.parseInt(String.valueOf(aChar));
            applicableDays.add(day);
        }

        return applicableDays;
    }
}
