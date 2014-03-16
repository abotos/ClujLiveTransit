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
package org.cluj.bus.util;

import org.junit.Test;

import java.util.Date;

public class ScheduleUtilitiesTest
{
    @Test
    public void testGetStartTime() throws Exception
    {
        Date time1 = ScheduleUtilities.getStartTime("12:41");

        Date time2 = ScheduleUtilities.getStartTime("06:01");

        Date time3 = ScheduleUtilities.getStartTime("5:09");
    }
}
