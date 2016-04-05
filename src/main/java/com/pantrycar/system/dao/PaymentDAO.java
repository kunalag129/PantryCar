package com.pantrycar.system.dao;

import com.google.inject.Inject;
import com.pantrycar.system.core.Payment;
import org.hibernate.SessionFactory;

/**
 * Created by kunal.agarwal on 13/02/16.
 */
public class PaymentDAO extends DAO<Payment>  {

    @Inject
    public PaymentDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
