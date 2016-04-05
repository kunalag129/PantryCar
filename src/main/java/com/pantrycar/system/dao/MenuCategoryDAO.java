package com.pantrycar.system.dao;

import com.google.inject.Inject;
import com.pantrycar.system.core.MenuCategory;
import org.hibernate.SessionFactory;

/**
 * Created by kunal.agarwal on 13/11/15.
 */
public class MenuCategoryDAO extends DAO<MenuCategory> {

    @Inject
    public MenuCategoryDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
