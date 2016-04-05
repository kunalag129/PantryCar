package com.pantrycar.system.dao;

import com.google.inject.Inject;
import com.pantrycar.system.core.TaxDetail;
import org.hibernate.SessionFactory;

/**
 * Created by kunal.agarwal on 13/11/15.
 */
public class TaxDetailDAO extends DAO<TaxDetail> {

    @Inject
    public TaxDetailDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
