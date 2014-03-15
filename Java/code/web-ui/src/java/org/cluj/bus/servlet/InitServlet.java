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
package org.cluj.bus.servlet;

import org.apache.log4j.Logger;
import org.cluj.bus.AnnotatedClassProvider;
import org.cluj.bus.db.HibernateUtil;
import org.cluj.bus.logging.LogInitializer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.net.MalformedURLException;
import java.net.URL;

public class InitServlet extends HttpServlet
{

    private static final String HIBERNATE_INIT_FILE_KEY = "initFile";

    private static Logger LOGGER;

    @Override
    public void init() throws ServletException
    {
        initLogging();
        LOGGER.info("Application is starting");

        try
        {
            initDatabase();
        }
        catch (Throwable throwable)
        {
            throw new ServletException("Unhandled exception during application init", throwable);
        }
    }

    @Override
    public void destroy()
    {
        HibernateUtil.closeSessionFactory();
    }

    private void initLogging()
    {
        LogInitializer.configureLOG4J();
        LOGGER = Logger.getLogger(InitServlet.class);
    }

    private void initDatabase() throws MalformedURLException
    {
        String initFile = getServletConfig().getInitParameter(HIBERNATE_INIT_FILE_KEY);

        final String path = getServletContext().getRealPath(initFile);

        LOGGER.info("Webapp DB init file [" + path + "]");

        final URL url = getServletContext().getResource(initFile);

        if (url == null)
        {
            throw new IllegalStateException("Could not find the init file [" + path + "].");
        }

        HibernateUtil.configureHibernate(url, AnnotatedClassProvider.getAnnotatedClasses());
    }
}
