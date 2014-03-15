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
import org.apache.log4j.Logger;
import org.cluj.bus.BusLocationUpdate;
import org.cluj.bus.Trip;
import org.cluj.bus.constants.Constants;
import org.cluj.bus.db.HibernateServiceProvider;
import org.cluj.bus.pojo.BusLocation;
import org.cluj.bus.pojo.Coordinate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BusUpdateServlet extends HttpServlet
{

    private static final Logger LOGGER = Logger.getLogger(BusUpdateServlet.class);
    private static final String BUS_LOCATION_MSG = "Bus location [busId=%s Lat=%f Long=%f]";

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException
    {
        final String locationJson = httpServletRequest.getParameter(Constants.LOCATION_PARAMETER_KEY);

        final BusLocation busLocation = new Gson().fromJson(locationJson, BusLocation.class);

        final Coordinate coordinate = busLocation.getCoordinate();

        final Trip trip = new Trip();
        trip.setBusId(busLocation.getBusId());
        trip.setTripId(busLocation.getTripId());
        final BusLocationUpdate busLocationUpdate = new BusLocationUpdate();
        busLocationUpdate.setTrip(trip);
        final double latitude = coordinate.getLatitude();
        busLocationUpdate.setLatitude(latitude);
        final double longitude = coordinate.getLongitude();
        busLocationUpdate.setLongitude(longitude);

        final Trip loadedTrip = (Trip) HibernateServiceProvider.getINSTANCE().getReadService().loadFirst(Trip.class, "tripId", busLocation.getTripId());

        busLocationUpdate.setTrip(loadedTrip);

        HibernateServiceProvider.getINSTANCE().getWriteService().save(busLocationUpdate);

        LOGGER.info(String.format(BUS_LOCATION_MSG, busLocation.getBusId(), latitude, longitude));
    }

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException
    {
    }
}
