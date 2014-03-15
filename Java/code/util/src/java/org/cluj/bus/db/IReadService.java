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

package org.cluj.bus.db;

import java.io.Serializable;
import java.util.Collection;

public interface IReadService
{
    Object load(Class clazz, Serializable id);

    Object loadFirst(Class clazz, String propertyName, Object value);

    Collection<Object> load(Class clazz, String propertyName, Object value);
}
