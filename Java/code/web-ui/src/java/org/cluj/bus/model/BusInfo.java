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

import java.util.Collection;

public class BusInfo
{

    private String busId;

    private String busName;

    private String busDisplayImage;

    private Collection<IndividualBusInfo> individualBusInfos;

    public String getBusId()
    {
        return busId;
    }

    public void setBusId(String busId)
    {
        this.busId = busId;
    }

    public String getBusName()
    {
        return busName;
    }

    public void setBusName(String busName)
    {
        this.busName = busName;
    }

    public String getBusDisplayImage()
    {
        return busDisplayImage;
    }

    public void setBusDisplayImage(String busDisplayImage)
    {
        this.busDisplayImage = busDisplayImage;
    }

    public Collection<IndividualBusInfo> getIndividualBusInfos()
    {
        return individualBusInfos;
    }

    public void setIndividualBusInfos(Collection<IndividualBusInfo> individualBusInfos)
    {
        this.individualBusInfos = individualBusInfos;
    }
}
