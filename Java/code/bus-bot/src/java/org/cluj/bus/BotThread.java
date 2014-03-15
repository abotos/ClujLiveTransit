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
package org.cluj.bus;

import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.cluj.bus.constants.Constants;
import org.cluj.bus.pojo.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BotThread extends Thread
{
    private static final Logger LOGGER = Logger.getLogger(BotThread.class);

    private static final int END_OF_TRIP_WAIT_INTERVAL = 10000;

    private final String busId;

    private final Collection<WayPointInfo> wayPointInfoCollection;

    private final String busUpdateServletUrl;

    private final String tripStatusServletUrl;

    public static final int SIGMA = 1000;

    public static final int MU = 10000;

    private boolean running = true;

    public BotThread(String host, String busId, Collection<WayPointInfo> wayPointInfoCollection)
    {
        this.busId = busId;
        this.wayPointInfoCollection = wayPointInfoCollection;
        this.busUpdateServletUrl = String.format("http://%s:8080/bustracker/busUpdate", host);
        this.tripStatusServletUrl = String.format("http://%s:8080/bustracker/tripStatus", host);
    }

    public void setRunning(boolean running)
    {
        this.running = running;
    }

    @Override
    public void run()
    {
        while (running)
        {
            String tripId;
            try
            {
                tripId = startTrip();

                runTrip(tripId);

                endTrip(tripId);

                LOGGER.info("Trip ended. Waiting in the depot...");
                Thread.sleep(END_OF_TRIP_WAIT_INTERVAL);
            }
            catch (InterruptedException e)
            {
                LOGGER.error("InterruptedException while sleeping", e);
            }
            catch (IOException e)
            {
                LOGGER.error("IOException while sending update", e);
            }
        }
    }

    private String startTrip() throws IOException
    {
        final TripStatus tripStatus = new TripStatus();
        tripStatus.setStatus(Status.STARTED);
        tripStatus.setBusId(busId);
        final CloseableHttpResponse response = getCloseableHttpResponse(tripStatusServletUrl, Constants.TRIP_STATUS_PARAMETER_KEY, tripStatus);
        final HttpEntity entity = response.getEntity();
        //TODO: get charset encoding from response!!!
        final String jsonReply = IOUtils.toString(entity.getContent(), "utf-8");
        final TripInfo tripInfo = new Gson().fromJson(jsonReply, TripInfo.class);
        final String tripId = tripInfo.getTripId();
        LOGGER.info("Started trip " + tripId);
        return tripId;
    }

    private void endTrip(String tripId) throws IOException
    {
        final TripStatus tripStatus = new TripStatus();
        tripStatus.setTripId(tripId);
        tripStatus.setBusId(busId);
        tripStatus.setStatus(Status.ENDED);
        final CloseableHttpResponse response = getCloseableHttpResponse(tripStatusServletUrl, Constants.TRIP_STATUS_PARAMETER_KEY, tripStatus);
        final HttpEntity entity = response.getEntity();
        //TODO: get charset encoding from response!!!
        final String jsonReply = IOUtils.toString(entity.getContent(), "utf-8");
        final String status = new Gson().fromJson(jsonReply, String.class);
        if (status.equals("OK"))
        {
            LOGGER.info("Got response " + status);
        }
        else
        {
            LOGGER.error("An error occurred " + status);
        }
    }

    public void runTrip(String tripId) throws IOException, InterruptedException
    {
        for (WayPointInfo wayPointInfo : this.wayPointInfoCollection)
        {
            final BusLocation busLocation = new BusLocation();
            busLocation.setBusId(this.busId);
            busLocation.setTripId(tripId);
            busLocation.setCoordinate(wayPointInfo.getCoordinate());
            sendLocationUpdate(busLocation);
            if (wayPointInfo.isStation())
            {
                LOGGER.info("Just passed " + wayPointInfo.getCoordinate());
            }
            Thread.sleep(getStandardGaussian(SIGMA, MU));
        }
    }

    private CloseableHttpResponse getCloseableHttpResponse(String servletAddress, String parameterKey, Object object) throws IOException
    {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(servletAddress);
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair(parameterKey, new Gson().toJson(object)));
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

        return closeableHttpClient.execute(httpPost);
    }

    private void sendLocationUpdate(BusLocation location) throws IOException
    {
        final CloseableHttpResponse response = getCloseableHttpResponse(busUpdateServletUrl, Constants.LOCATION_PARAMETER_KEY, location);
    }

    private long getStandardGaussian(long sigma, long mu)
    {
        double r, x, y;
        do
        {
            x = 2.0 * Math.random() - 1.0;
            y = 2.0 * Math.random() - 1.0;
            r = x * x + y * y;
        } while (r > 1 || r == 0);

        double z = x * Math.sqrt(-2.0 * Math.log(r) / r);

        return (long) (z * sigma + mu);
    }
}
