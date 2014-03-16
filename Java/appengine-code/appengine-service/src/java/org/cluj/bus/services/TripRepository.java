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
package org.cluj.bus.services;

import org.cluj.bus.model.BusLocationUpdate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TripRepository
{
    private static final TripRepository INSTANCE = new TripRepository();

    // Key is the bus id, value is the list of active trips for the bus
    private final Map<String, List<String>> busTrips = new HashMap<>();

    private final Map<String, List<BusLocationUpdate>> tripBusLocationUpdates = new HashMap<>();

    private TripRepository()
    {
    }

    public static TripRepository getInstance()
    {
        return INSTANCE;
    }

    public synchronized void startTrip(String busId, String tripId)
    {
        List<String> tripsForBus = busTrips.get(busId);

        if (tripsForBus == null)
        {
            tripsForBus = new ArrayList<>();
            busTrips.put(busId, tripsForBus);
        }

        tripsForBus.add(tripId);
    }

    public synchronized void endTrip(String busId, String tripId)
    {
        List<String> tripsForBus = busTrips.get(busId);

        tripsForBus.remove(tripId);
    }

    public synchronized void addBusLocationUpdate(BusLocationUpdate busLocationUpdate)
    {
        String tripId = busLocationUpdate.getTrip();

        List<BusLocationUpdate> locationUpdatesForTrip = tripBusLocationUpdates.get(tripId);

        if (locationUpdatesForTrip == null)
        {
            locationUpdatesForTrip = new ArrayList<>();
            tripBusLocationUpdates.put(tripId, locationUpdatesForTrip);
        }

        locationUpdatesForTrip.add(0, busLocationUpdate);
    }
}
