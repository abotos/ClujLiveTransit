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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ServletUtils
{

    static final String STATION_ID_PARAMETER_KEY = "stationId";

    static final String MIME_ENCODING = "UTF-8";
    static final String RESPONSE_CONTENT_TYPE_ENCODING = "text/plain;charset=" + MIME_ENCODING;

    public static void sendResponse(HttpServletResponse httpServletResponse, String data) throws ServletException, IOException
    {
        httpServletResponse.setContentType(ServletUtils.RESPONSE_CONTENT_TYPE_ENCODING);
        httpServletResponse.setCharacterEncoding(ServletUtils.MIME_ENCODING);

        PrintWriter writer = httpServletResponse.getWriter();
        writer.write(data);
        writer.flush();
    }
}
