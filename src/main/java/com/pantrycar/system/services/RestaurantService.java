package com.pantrycar.system.services;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.pantrycar.system.core.BankDetail;
import com.pantrycar.system.core.Restaurant;
import com.pantrycar.system.core.Station;
import com.pantrycar.system.core.TaxDetail;
import com.pantrycar.system.dao.RestaurantDAO;
import com.pantrycar.system.representations.BasicResponse;
import com.pantrycar.system.representations.railway.StationDetails;
import com.pantrycar.system.representations.restaurant.RestaurantDetail;
import com.pantrycar.system.representations.restaurant.RestaurantDetailList;
import com.pantrycar.system.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by kunal.agarwal on 10/11/15.
 */
public class RestaurantService {
    private static final Logger logger = LoggerFactory.getLogger(RestaurantService.class);
    private final Provider<RestaurantDAO> daoProvider;
    private final Provider<LocationService> locationServiceProvider;
    private final Provider<StationService> stationServiceProvider;
    private final Provider<TaxDetailService> taxDetailServiceProvider;
    private final Provider<BankDetailService> bankDetailServiceProvider;

    @Inject
    public RestaurantService(Provider<RestaurantDAO> restaurantDAOProvider, Provider<LocationService> locationServiceProvider, Provider<StationService> stationServiceProvider, Provider<TaxDetailService> taxDetailServiceProvider, Provider<BankDetailService> bankDetailServiceProvider) {
        this.daoProvider = restaurantDAOProvider;
        this.locationServiceProvider = locationServiceProvider;
        this.stationServiceProvider = stationServiceProvider;
        this.taxDetailServiceProvider = taxDetailServiceProvider;
        this.bankDetailServiceProvider = bankDetailServiceProvider;
    }

    public RestaurantDetail getRestaurantById(int id) {
        Restaurant restaurant = daoProvider.get().getById(id);
        return getDetails(restaurant);
    }

    public RestaurantDetail getRestaurantByColumns(Map<String, Object> conditions) {
        Restaurant restaurant = getUniqueRestaurant(conditions);
        if (restaurant == null)
            return new RestaurantDetail().error(404, "No Restaurant available with given conditions");
        return getDetails(restaurant);
    }

    public Restaurant getRestaurantByInternalId(String internalId) {
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("internalId", internalId);
        return getUniqueRestaurant(conditions);
    }

    private Restaurant getUniqueRestaurant(Map<String, Object> conditions) {
        return daoProvider.get().findUniqueByColumns(conditions);
    }

    public RestaurantDetail addRestaurant(Restaurant restaurant) {
        restaurant.setLocation(locationServiceProvider.get().findOrCreate(restaurant.getLocation()));
        restaurant = daoProvider.get().persist(restaurant);
        restaurant.setInternalId("RST" + restaurant.getId());
        restaurant.setUrl(Utils.toPrettyURL(restaurant.getName() + " " + restaurant.getId()));
        restaurant = daoProvider.get().persist(restaurant);
        return getDetails(restaurant);
    }

    public BasicResponse updateBankDetails(Restaurant restaurant, BankDetail bankDetail) {
        bankDetail.setRestaurant(restaurant);
        bankDetailServiceProvider.get().findOrCreate(bankDetail);
        BasicResponse basicResponse = new BasicResponse();
        basicResponse.setMessage("Bank details updated");
        return basicResponse;
    }

    public BasicResponse updateTaxDetails(Restaurant restaurant, TaxDetail taxDetail) {
        taxDetail.setRestaurant(restaurant);
        taxDetailServiceProvider.get().findOrCreate(taxDetail);
        BasicResponse basicResponse = new BasicResponse();
        basicResponse.setMessage("Tax details updated");
        return basicResponse;
    }

    public RestaurantDetail getDetails(Restaurant restaurant) {
        StationDetails stationDetails = stationServiceProvider.get().getStationDetails(restaurant.getStation());

        return RestaurantDetail.builder()
                .id(restaurant.getInternalId())
                .name(restaurant.getName())
                .distance(restaurant.getDistance())
                .contactNumber(restaurant.getContactNo())
                .openTime(restaurant.getOpenTime())
                .closeTime(restaurant.getCloseTime())
                .slaDetails(restaurant.getSlaDetails())
                .minOrderValue(restaurant.getMinimumOrder())
                .deliveryCharges(restaurant.getDeliveryCharges())
                .isOnline(restaurant.isOnline())
                .url(restaurant.getUrl())
                .stationDetails(stationDetails)
                .build();
    }

    public RestaurantDetailList getRestaurantByStation(String stationCode) {
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("stationCode", stationCode);
        Station station = stationServiceProvider.get().getUniqueStation(conditions);
        if (station == null)
            return new RestaurantDetailList().error(404, "No station with this station code present in system");
        List<RestaurantDetail> restaurantDetailList = station.getRestaurants().stream()
                .map(rst -> getDetails(rst))
                .collect(Collectors.toList());
        return new RestaurantDetailList(stationCode, restaurantDetailList);
    }

    public List<Restaurant> getByInternalIds(List internalIds) {
        return daoProvider.get().findByColumn("internalId", internalIds);
    }
}
