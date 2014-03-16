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
import java.util.Date;

public class CategorySchedule
{
    private String displayName;

    private Collection<Integer> applicableDays;

    private Collection<Date> startTimes;

    public String getDisplayName()
    {
        return displayName;
    }

    public void setDisplayName(String displayName)
    {
        this.displayName = displayName;
    }

    public Collection<Integer> getApplicableDays()
    {
        return applicableDays;
    }

    public void setApplicableDays(Collection<Integer> applicableDays)
    {
        this.applicableDays = applicableDays;
    }

    public Collection<Date> getStartTimes()
    {
        return startTimes;
    }

    public void setStartTimes(Collection<Date> startTimes)
    {
        this.startTimes = startTimes;
    }
}
