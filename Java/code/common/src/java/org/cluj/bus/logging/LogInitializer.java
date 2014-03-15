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
package org.cluj.bus.logging;

import org.apache.log4j.*;

import java.util.Enumeration;

public class LogInitializer
{

    public static final String DEFAULT_LOG_LAYOUT = "[%20.20t] %40.40c [%5.5p] (%d{yyyy-MM-dd HH:mm:ss.SSS}) %m%n";


    public static void configureLOG4J()
    {
        configureLOG4J(DEFAULT_LOG_LAYOUT);
    }

    public static void configureLOG4J(String layout)
    {
        final Enumeration loggers = LogManager.getCurrentLoggers();
        while (loggers.hasMoreElements())
        {
            final Logger logger = (Logger) loggers.nextElement();
            logger.removeAllAppenders();
        }

        Logger.getRootLogger().removeAllAppenders();

        Logger.getRootLogger().addAppender(new ConsoleAppender(new PatternLayout(layout)));

        Logger.getRootLogger().setLevel(Level.INFO);
        Logger.getRootLogger().getLoggerRepository().setThreshold(Level.DEBUG);

        Logger.getLogger("org.hibernate").setLevel(Level.WARN);
    }
}
