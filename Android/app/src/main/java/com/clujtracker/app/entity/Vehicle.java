package com.clujtracker.app.entity;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by Alex on 3/15/14.
 */
public class Vehicle implements ClusterItem {
    private final LatLng mPosition;

    public Vehicle(double lat, double lng) {
        mPosition = new LatLng(lat, lng);
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }
}
