package com.pantrycar.system.dao;

import com.google.inject.Inject;
import com.pantrycar.system.core.Customer;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by kunal.agarwal on 13/11/15.
 */
public class CustomerDAO extends DAO<Customer> {

    @Inject
    public CustomerDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    List<Customer> getCustomersByEmailIds(List<String> emailIds) {
        return criteria().add(Restrictions.in("emailId", emailIds)).list();
    }
}
