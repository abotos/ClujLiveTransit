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
import org.cluj.bus.constants.Constants;
import org.cluj.bus.model.Trip;
import org.cluj.bus.pojo.Status;
import org.cluj.bus.pojo.TripInfo;
import org.cluj.bus.pojo.TripStatus;
import org.cluj.bus.services.JPARepository;
import org.cluj.bus.services.TripRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class TripStatusServlet extends HttpServlet
{
    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException
    {
        String tripStatusJson = httpServletRequest.getParameter(Constants.TRIP_STATUS_PARAMETER_KEY);
        final TripStatus tripStatus = new Gson().fromJson(tripStatusJson, TripStatus.class);

        if (Status.STARTED == tripStatus.getStatus())
        {
            final Trip trip = new Trip();
            final String busId = tripStatus.getBusId();
            trip.setBusId(busId);
            final String tripId = UUID.randomUUID().toString();
            trip.setTripId(tripId);
            trip.setIsActive(true);
            final JPARepository<Trip> repository = new JPARepository<>(Trip.class);
            repository.save(trip);

            // In-memory repository
            TripRepository.getInstance().startTrip(busId, tripId);

            ServletUtils.sendResponse(httpServletResponse, getResponseString(busId, tripId));
        }
        else if (Status.ENDED == tripStatus.getStatus())
        {
            //move trip to archive
            final JPARepository<Trip> repository = new JPARepository<>(Trip.class);
            String tripId = tripStatus.getTripId();
            final Trip trip = repository.findFirst("tripId", tripId);
            trip.setIsActive(false);
            repository.save(trip);

            TripRepository.getInstance().endTrip(tripStatus.getBusId(), tripId);

            ServletUtils.sendResponse(httpServletResponse, "OK");
        }
        else
        {
            throw new RuntimeException("Unknown trip status!");
        }
    }

    private String getResponseString(String busId, String tripId)
    {
        final TripInfo tripInfo = new TripInfo();
        tripInfo.setBusId(busId);
        tripInfo.setTripId(tripId);
        return new Gson().toJson(tripInfo);
    }
}