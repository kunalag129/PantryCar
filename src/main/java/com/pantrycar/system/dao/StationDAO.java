package com.pantrycar.system.dao;

import com.google.inject.Inject;
import com.pantrycar.system.core.Station;
import org.hibernate.SessionFactory;

/**
 * Created by kunal.agarwal on 12/11/15.
 */
public class StationDAO extends DAO<Station> {

    @Inject
    public StationDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
