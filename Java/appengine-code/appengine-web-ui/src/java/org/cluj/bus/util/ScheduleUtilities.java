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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ScheduleUtilities
{

    public static Date getStartTime(String startTime) throws ParseException
    {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date time = dateFormat.parse(startTime);

        Calendar tempCalendar = Calendar.getInstance();
        tempCalendar.setTime(time);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, tempCalendar.get(Calendar.HOUR));
        calendar.set(Calendar.MINUTE, tempCalendar.get(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, 0);

        return calendar.getTime();
    }
}
