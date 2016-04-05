package com.pantrycar.system.dao;

import com.google.inject.Inject;
import com.pantrycar.system.core.Customer;
import com.pantrycar.system.core.Order;
import com.pantrycar.system.enums.OrderStatus;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by kunal.agarwal on 15/11/15.
 */
public class OrderDAO extends DAO<Order> {

    @Inject
    public OrderDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Order getLastTempOrderByCustomer(Customer customer) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, -6);
        Date expiryTime = calendar.getTime();
        Criteria criteria = criteria().add(Restrictions.eq("customer", customer))
                .add(Restrictions.in("status", Arrays.asList(OrderStatus.created, OrderStatus.checkout_in_progress)))
                        .add(Restrictions.between("createdAt", expiryTime, date))
                        .addOrder(org.hibernate.criterion.Order.desc("createdAt"))
                        .setMaxResults(1);
        return uniqueResult(criteria);
    }


}
