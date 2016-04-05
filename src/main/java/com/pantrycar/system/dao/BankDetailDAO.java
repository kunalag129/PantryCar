package com.pantrycar.system.dao;

import com.google.inject.Inject;
import com.pantrycar.system.core.BankDetail;
import org.hibernate.SessionFactory;

/**
 * Created by kunal.agarwal on 13/11/15.
 */
public class BankDetailDAO extends DAO<BankDetail> {

    @Inject
    public BankDetailDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
