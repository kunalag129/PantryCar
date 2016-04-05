package com.pantrycar.system.dao;

import com.pantrycar.system.core.Model;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by kunal.agarwal on 11/11/15.
 */
public abstract class DAO<E> extends AbstractDAO<E> {

    public DAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public E getById(int id) {
        return get(id);
    }

    public Session getSession() {
        return currentSession();
    }

    public List<E> findByColumns(Map<String, Object> conditions) {
        Criteria criteria = criteria();
        for (String key : conditions.keySet()) {
            criteria.add(Restrictions.eq(key, conditions.get(key)));
        }
        return list(criteria);
    }

    public E findUniqueByColumn(String column, Object value) {
        if (value != null) {
            Criteria criteria = criteria().add(Restrictions.eq(column, value));
            return uniqueResult(criteria);
        }
        return null;
    }

    public List<E> findByColumn(String column, Object value) {
        if (value != null) {
            Criteria criteria = criteria().add(Restrictions.eq(column, value));
            return list(criteria);
        }
        return new ArrayList<>();
    }

    public List<E> findByColumn(String column, List<Object> values) {
        if (values != null && !values.isEmpty()) {
            Criteria criteria = criteria().add(Restrictions.in(column, values));
            return list(criteria);
        }
        return new ArrayList<>();
    }

    public E findUniqueByColumns(Map<String, Object> conditions) {
        Criteria criteria = criteria();
        for (String key : conditions.keySet()) {
            criteria.add(Restrictions.eq(key, conditions.get(key)));
        }
        return uniqueResult(criteria);
    }

    public List<E> findByCriterias(List<Criterion> criterions) {
        Criteria criteria = criteria();
        for (Criterion criterion : criterions)
            criteria.add(criterion);
        return list(criteria);
    }

    public List<E> list() {
        return list(criteria());
    }

    private void updateTimeStamps(E entity) {
        if (entity instanceof Model) {
            Date date = new Date();
            if (((Model) entity).getCreatedAt() == null) {
                ((Model) entity).setCreatedAt(date);
            }
            ((Model) entity).setUpdatedAt(date);
        }
    }

    public E persist(E entity) {
        updateTimeStamps(entity);
        entity = super.persist(entity);
        currentSession().flush();
        return entity;
    }
}
