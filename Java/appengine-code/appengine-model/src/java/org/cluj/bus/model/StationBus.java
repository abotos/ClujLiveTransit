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

package org.cluj.bus.model;

import javax.persistence.*;

@Entity
@Table(name = "STATION_BUS_VIEW")
public class StationBus
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Station station;

    @OneToOne
    private Bus bus;

    @Column
    private Long stationOrder;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Station getStation()
    {
        return station;
    }

    public void setStation(Station station)
    {
        this.station = station;
    }

    public Bus getBus()
    {
        return bus;
    }

    public void setBus(Bus bus)
    {
        this.bus = bus;
    }

    public Long getStationOrder()
    {
        return stationOrder;
    }

    public void setStationOrder(Long stationOrder)
    {
        this.stationOrder = stationOrder;
    }
}
