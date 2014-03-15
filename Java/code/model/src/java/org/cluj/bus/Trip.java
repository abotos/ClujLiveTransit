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
@Table(name = "TRIP")
@AttributeOverride(name = "id", column = @Column(name = "ID", nullable = false))
@SequenceGenerator(name = "SEQ", sequenceName = "S_TRIP")
public class Trip
{
    @Id()
    @GeneratedValue(generator = "SEQ")
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "BUS_ID", nullable = false)
    private String busId;

    @Column(name = "TRIP_ID", nullable = false)
    private String tripId;

    @Column(name = "IS_ACTIVE")
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
