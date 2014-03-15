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
package org.cluj.bus.services;

import org.cluj.bus.EMFService;
import org.cluj.bus.ISpecification;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.List;

public class JPARepository<T> implements IRepository<T>
{
    private EntityManager entityManager;

    private final Class<T> clazz;

    public JPARepository(Class<T> clazz)
    {
        this.entityManager = EMFService.get().createEntityManager();
        this.clazz = clazz;
    }

    @Override
    public void save(T entity)
    {
        try
        {
            entityManager.getTransaction().begin();
            entityManager.persist(entity);
            entityManager.getTransaction().commit();
        }
        finally
        {
            if (entityManager.getTransaction().isActive())
            {
                entityManager.getTransaction().rollback();
            }
        }
    }

    public T findFirst(String field, Object value)
    {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clazz);
        final Root<T> root = criteriaQuery.from(clazz);
        criteriaQuery.select(root);
        final Path<Object> path = root.get(field);
        criteriaQuery.where(criteriaBuilder.equal(path, value));

        return entityManager.createQuery(criteriaQuery).getResultList().get(0);
    }

    @Override
    public List<T> findAll(ISpecification<T> specification)
    {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> q = cb.createQuery(clazz);
        Root<T> root = q.from(clazz);

        q.where(specification.toPredicate(root, q, cb));

        return entityManager.createQuery(q).getResultList();
    }

    @Override
    public List<T> findAll()
    {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> q = cb.createQuery(clazz);
        Root<T> root = q.from(clazz);
        q.select(root);

        return entityManager.createQuery(q).getResultList();
    }

    @Override
    public void flush()
    {
        entityManager.flush();
    }

    @Override
    public void close()
    {
        entityManager.close();
    }
}
