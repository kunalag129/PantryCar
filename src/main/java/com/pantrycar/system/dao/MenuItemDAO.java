package com.pantrycar.system.dao;

import com.google.inject.Inject;
import com.pantrycar.system.core.MenuItem;
import org.hibernate.SessionFactory;

/**
 * Created by kunal.agarwal on 13/11/15.
 */
public class MenuItemDAO extends DAO<MenuItem> {

    @Inject
    public MenuItemDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
