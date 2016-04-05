package com.pantrycar.system.dao;

import com.google.inject.Inject;
import com.pantrycar.system.core.Password;
import org.hibernate.SessionFactory;

/**
 * Created by kunal.agarwal on 15/11/15.
 */
public class PasswordDAO extends DAO<Password> {
    @Inject
    public PasswordDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
