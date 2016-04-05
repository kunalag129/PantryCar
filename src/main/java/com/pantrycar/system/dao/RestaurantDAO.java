package com.pantrycar.system.dao;

import com.google.inject.Inject;
import com.pantrycar.system.core.Restaurant;
import org.hibernate.SessionFactory;

/**
 * Created by kunal.agarwal on 11/11/15.
 */
public class RestaurantDAO extends DAO<Restaurant> {

    @Inject
    public RestaurantDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

}
