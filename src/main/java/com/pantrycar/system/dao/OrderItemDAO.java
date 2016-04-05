package com.pantrycar.system.dao;

import com.google.inject.Inject;
import com.pantrycar.system.core.OrderItem;
import org.hibernate.SessionFactory;

/**
 * Created by kunal.agarwal on 15/11/15.
 */
public class OrderItemDAO extends DAO<OrderItem> {

    @Inject
    public OrderItemDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
