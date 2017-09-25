package com.salenko.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractDao {

    @Autowired
    private SessionFactory sessionFactory;

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public Object insert(Object entity) {
        getSession().persist(entity);
        getSession().flush();
        return entity;
    }

    public void update(Object entity) {
        getSession().update(entity);
    }

    public void delete(Object entity) {
        getSession().delete(entity);
    }

    public Object findByField(Class className, String fieldName, Object obj) {
        Criteria criteria = getSession().createCriteria(className);
        criteria.add(Restrictions.eq(fieldName, obj));
        return criteria.uniqueResult();
    }
}
