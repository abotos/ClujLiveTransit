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

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface ISpecification<T>
{
    /**
     * Transforms this specification into a predicate.
     *
     * @param root  The starting point, must not be null.
     * @param query The query object to use, must not be null.
     * @param cb    The criteria builder to use, must not be null.
     * @return The created predicate which checks the rules.
     * @throws UnsupportedOperationException If this specification does not support predicate transformation.
     */
    Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) throws UnsupportedOperationException;
}
