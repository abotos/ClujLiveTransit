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

import javax.persistence.EntityManager;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clazz);
        final Root<T> root = criteriaQuery.from(clazz);
        criteriaQuery.select(root);
        final Path<Object> path = root.get(field);
        criteriaQuery.where(criteriaBuilder.equal(path, value));

        return entityManager.createQuery(criteriaQuery).getResultList().get(0);
    }

    @Override
    public List<T> findAll(Map<String, Object> restrictions)
    {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clazz);
        final Root<T> root = criteriaQuery.from(clazz);
        final List<Predicate> predicates = new ArrayList<Predicate>();
        criteriaQuery.select(root);
        final Set<Map.Entry<String, Object>> entries = restrictions.entrySet();
        for (Map.Entry<String, Object> entry : entries)
        {
            predicates.add(criteriaBuilder.equal(root.get(entry.getKey()), entry.getValue()));
        }
        criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    //TODO: refactor this
    public List<T> findAll(String field, Object value)
    {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clazz);
        final Root<T> root = criteriaQuery.from(clazz);
        criteriaQuery.select(root);
        final Path<Object> path = root.get(field);
        criteriaQuery.where(criteriaBuilder.equal(path, value));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<T> findAll()
    {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clazz);
        final Root<T> root = criteriaQuery.from(clazz);
        criteriaQuery.select(root);

        return entityManager.createQuery(criteriaQuery).getResultList();
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
