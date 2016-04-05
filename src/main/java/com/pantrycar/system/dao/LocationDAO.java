package com.pantrycar.system.dao;

import com.google.inject.Inject;
import com.pantrycar.system.core.Location;
import org.hibernate.SessionFactory;

/**
 * Created by kunal.agarwal on 12/11/15.
 */
public class LocationDAO extends DAO<Location> {

    @Inject
    public LocationDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }


}
