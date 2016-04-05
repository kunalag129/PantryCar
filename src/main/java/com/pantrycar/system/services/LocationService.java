package com.pantrycar.system.services;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.pantrycar.system.core.Location;
import com.pantrycar.system.dao.LocationDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kunal.agarwal on 12/11/15.
 */
public class LocationService {
    private static final Logger logger = LoggerFactory.getLogger(LocationService.class);
    private final Provider<LocationDAO> daoProvider;

    @Inject
    public LocationService(Provider<LocationDAO> locationDAOProvider) {
        this.daoProvider = locationDAOProvider;
    }

    private Location getUniqueLocation(Map<String, Object> conditions) {
        return daoProvider.get().findUniqueByColumns(conditions);
    }

    public Location findOrCreate(Location location) {
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("address", location.getAddress());
        conditions.put("city", location.getCity());
        conditions.put("state", location.getState());
        conditions.put("pincode", location.getPincode());
        Location uniquelocation = getUniqueLocation(conditions);
        if (uniquelocation == null)
            return daoProvider.get().persist(location);
        return uniquelocation;
    }
}
