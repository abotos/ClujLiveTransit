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
import org.cluj.bus.EMFService;
import org.cluj.bus.model.*;
import org.cluj.bus.pojo.Coordinate;
import org.cluj.bus.services.JPARepository;
import org.cluj.bus.util.BusInfoUtilities;
import org.jetbrains.annotations.Nullable;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

public class BusInfoServlet extends HttpServlet
{
    private static final String MAP_BOUNDS_PARAMETER_KEY = "mapBounds";

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException
    {
        String stationId = httpServletRequest.getParameter(ServletUtils.STATION_ID_PARAMETER_KEY);
        String mapBoundsString = httpServletRequest.getParameter(MAP_BOUNDS_PARAMETER_KEY);

        MapBoundsInfo mapBoundsInfo = new Gson().fromJson(mapBoundsString, MapBoundsInfo.class);

        ServletUtils.sendResponse(httpServletResponse, getResponseString(stationId, mapBoundsInfo));
    }

    private String getResponseString(String stationId, @Nullable MapBoundsInfo mapBoundsInfo)
    {
        final Station station = new JPARepository<>(Station.class).findFirst("businessId", stationId);
        final List<StationBus> stationBuses = new JPARepository<>(StationBus.class).findAll("station", station.getBusinessId());


        final Collection<BusInfo> busInfos = new ArrayList<>();
        for (final StationBus stationBus : stationBuses)
        {
            BusInfo busInfo;
            final Bus bus = new JPARepository<>(Bus.class).findFirst("businessId", stationBus.getBus());

            final HashMap<String, Object> restrictions = new HashMap<>(2);
            restrictions.put("busId", bus.getBusinessId());
            restrictions.put("isActive", true);
            final List<Trip> activeTrips = new JPARepository<>(Trip.class).findAll(restrictions);
            final List<IndividualBusInfo> individualBusInfos = new ArrayList<>();
            final EntityManager entityManager = EMFService.get().createEntityManager();
            final EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            for (final Trip activeTrip : activeTrips)
            {
                final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
                final CriteriaQuery<BusLocationUpdate> criteriaQuery = criteriaBuilder.createQuery(BusLocationUpdate.class);
                final Root<BusLocationUpdate> root = criteriaQuery.from(BusLocationUpdate.class);
                criteriaQuery.select(root);
                final Path<Object> path = root.get("trip");
                criteriaQuery.where(criteriaBuilder.equal(path, activeTrip.getTripId())).orderBy(criteriaBuilder.desc(root.get("lastUpdate")));
                final List<BusLocationUpdate> busLocationUpdateList = entityManager.createQuery(criteriaQuery).getResultList();

                int busLocationUpdateSize = busLocationUpdateList.size();
                if (busLocationUpdateSize > 0)
                {
                    final BusLocationUpdate latest = busLocationUpdateList.get(0);

                    boolean isApproaching = false;
                    if (busLocationUpdateSize == 1)
                    {
                        isApproaching = true;
                    }
                    else if (busLocationUpdateSize >= 2)
                    {
                        boolean trackingApproaching = true;

                        for (int index = busLocationUpdateSize - 2; index >= 0; index--)
                        {
                            final BusLocationUpdate previousUpdate = busLocationUpdateList.get(index + 1);
                            final BusLocationUpdate currentUpdate = busLocationUpdateList.get(index);

                            boolean currentApproaching = BusInfoUtilities.isApproaching(currentUpdate, previousUpdate, station.getLatitude(), station.getLongitude());

                            if ((!currentApproaching) && (BusInfoUtilities.isInProximity(station, currentUpdate, previousUpdate)))
                            {
                                trackingApproaching = false;
                                break;
                            }
                        }
                        isApproaching = trackingApproaching;
                    }

                    if (isApproaching)
                    {
                        final IndividualBusInfo individualBusInfo = getIndividualBusInfo(latest);
                        individualBusInfo.setApproaching(true);
                        individualBusInfo.setInViewPort(BusInfoUtilities.isInViewPort(latest, mapBoundsInfo));
                        individualBusInfo.setTimeToArrival(BusInfoUtilities.computeETA(station, busLocationUpdateList));
                        individualBusInfos.add(individualBusInfo);
                    }
                    else
                    {
                        if (BusInfoUtilities.isInViewPort(latest, mapBoundsInfo))
                        {
                            final IndividualBusInfo individualBusInfo = getIndividualBusInfo(latest);
                            individualBusInfo.setApproaching(false);
                            individualBusInfos.add(individualBusInfo);
                        }
                    }
                }
            }
            transaction.commit();

            if (individualBusInfos.size() > 0)
            {
                //ascending order the list based on the estimated time to arrive
                Collections.sort(individualBusInfos, new Comparator<IndividualBusInfo>()
                {
                    @Override
                    public int compare(IndividualBusInfo o1, IndividualBusInfo o2)
                    {
                        if (o1.getTimeToArrival() == -1 || o2.getTimeToArrival() == -1)
                        {
                            // put the buses with no time to arrive at the bottom of the list
                            return 1;
                        }
                        return o1.getTimeToArrival() - o2.getTimeToArrival();
                    }
                });
                busInfo = new BusInfo();
                busInfo.setBusName(bus.getName());
                busInfo.setBusId(bus.getBusinessId());
                busInfo.setBusDisplayImage(bus.getDisplayImage());
                busInfo.setIndividualBusInfos(individualBusInfos);
                busInfos.add(busInfo);
            }
        }

        final AllBusInfosDTO response = new AllBusInfosDTO();
        response.setAllBusInfos(busInfos);

        return new Gson().toJson(response);
    }

    private IndividualBusInfo getIndividualBusInfo(BusLocationUpdate busLocationUpdate)
    {
        final IndividualBusInfo individualBusInfo = new IndividualBusInfo();
        final Double latitude = busLocationUpdate.getLatitude();
        final Double longitude = busLocationUpdate.getLongitude();
        individualBusInfo.setCoordinate(new Coordinate(latitude, longitude));
        return individualBusInfo;
    }
}
