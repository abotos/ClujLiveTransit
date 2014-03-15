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
@Table(name = "TRIP")
public class Trip
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String busId;

    @Column
    private String tripId;

    @Column
    private Boolean isActive;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getBusId()
    {
        return busId;
    }

    public void setBusId(String busId)
    {
        this.busId = busId;
    }

    public String getTripId()
    {
        return tripId;
    }

    public void setTripId(String tripId)
    {
        this.tripId = tripId;
    }

    public Boolean getIsActive()
    {
        return isActive;
    }

    public void setIsActive(Boolean isActive)
    {
        this.isActive = isActive;
    }
}