package com.clujtracker.app.core;

import android.app.Activity;

import com.clujtracker.app.core.api.Api;
import com.clujtracker.app.core.api.Api_I;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by Alex on 3/14/14.
 */
public class MapRefresher extends Thread {

    private final GoogleMap map;
    private final Activity activity;
    private final Api_I api;

    private boolean isActive;

    public MapRefresher(GoogleMap map, Activity activity) {
        this.map = map;
        this.activity = activity;
        this.api = Api.getInstance();
        isActive = true;
    }

    public static LatLng CLUJCENTER = new LatLng(46.772327, 23.595355);

    @Override
    public void run() {
        while (isActive) {
            api.getCurrentPositions();
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    map.clear();
                    CLUJCENTER = new LatLng(CLUJCENTER.latitude + 0.0001, CLUJCENTER.longitude + 0.00001);
                    map.addMarker(new MarkerOptions().position(CLUJCENTER)
                            .title("43B"));

                }
            });
        }
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

}
