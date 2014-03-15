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

import javax.persistence.*;

@Entity
@Table(name = "STATION_BUS_VIEW")
@AttributeOverride(name = "id", column = @Column(name = "ID", nullable = false))
@SequenceGenerator(name = "SEQ", sequenceName = "S_STATION_BUS")
public class StationBus
{
    @Id()
    @GeneratedValue(generator = "SEQ")
    @Column(name = "ID", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "STATION_ID")
    private Station station;

    @OneToOne
    @JoinColumn(name = "BUS_ID")
    private Bus bus;

    @Column(name = "STATION_ORDER")
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
