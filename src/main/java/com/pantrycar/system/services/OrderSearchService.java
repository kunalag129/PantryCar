package com.pantrycar.system.services;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.pantrycar.system.core.Customer;
import com.pantrycar.system.core.Order;
import com.pantrycar.system.core.Restaurant;
import com.pantrycar.system.core.Station;
import com.pantrycar.system.enums.OrderStatus;
import com.pantrycar.system.representations.order.OrderDetailsList;
import com.pantrycar.system.representations.order.OrderSearchRequest;
import com.pantrycar.system.utils.Utils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by kunal.agarwal on 18/11/15.
 */
public class OrderSearchService {
    private static final Logger logger = LoggerFactory.getLogger(OrderSearchService.class);
    private final Provider<OrderService> orderServiceProvider;
    private final Provider<CustomerService> customerServiceProvider;
    private final Provider<RestaurantService> restaurantServiceProvider;
    private final Provider<StationService> stationServiceProvider;
    private final List<Criterion> criterions;

    @Inject
    public OrderSearchService(Provider<OrderService> orderServiceProvider, Provider<CustomerService> customerServiceProvider, Provider<RestaurantService> restaurantServiceProvider, Provider<StationService> stationServiceProvider) {
        this.orderServiceProvider = orderServiceProvider;
        this.customerServiceProvider = customerServiceProvider;
        this.restaurantServiceProvider = restaurantServiceProvider;
        this.stationServiceProvider = stationServiceProvider;
        this.criterions = new ArrayList<>();
    }

    public OrderDetailsList search(OrderSearchRequest request) {
        addOrderIdsCriteria(request.getOrderIds());
        addCustomerCriteria(request.getCustomerIds());
        addRestaurantCriteria(request.getRestaurantIds());
        addStationCriteria(request.getStationCodes());
        addOrderDateCriteria(request.getFromOrderDate(), request.getToOrderDate());
        addDeliveryDateCriteria(request.getFromDeliveryDate(), request.getToDeliveryDate());
        addPaymentModeCriteria(request.getModeOfPayment());
        addStatusCriteria(request.getStatus());
        addPnrCriteria(request.getPnrs());
        addtrainNoCritera(request.getTrainNos());

        List<Order> orders = orderServiceProvider.get().getOrdersFromCritera(criterions);
        return orderServiceProvider.get().getOrderDetails(orders);
    }

    private void addtrainNoCritera(List<String> trainNos) {
        if (trainNos != null && !trainNos.isEmpty())
            criterions.add(Restrictions.in("trainNo", trainNos));
    }

    private void addPnrCriteria(List<String> pnrs) {
        if (pnrs != null && !pnrs.isEmpty())
            criterions.add(Restrictions.in("pnr", pnrs));
    }

    private void addStatusCriteria(List<OrderStatus> statuses) {
        if (statuses != null && !statuses.isEmpty())
            criterions.add(Restrictions.in("status", statuses));
    }

    private void addPaymentModeCriteria(String modeOfPayment) {
        if (modeOfPayment != null)
            criterions.add(Restrictions.eq("modeOfPayment", modeOfPayment));
    }

    private void addDeliveryDateCriteria(Date from, Date to) {
        Criterion criterion = Utils.getCriteriaForDateRanges("deliveryDate", from, to);
        if (criterion != null)
            criterions.add(criterion);
    }

    private void addOrderDateCriteria(Date from, Date to) {
        Criterion criterion = Utils.getCriteriaForDateRanges("orderDate", from, to);
        if (criterion != null)
            criterions.add(criterion);
    }

    private void addStationCriteria(List<String> stationCodes) {
        List<Station> stations = stationServiceProvider.get().getStationByCode(stationCodes);
        if (stations != null && !stations.isEmpty())
            criterions.add(Restrictions.in("station", stations));
    }

    private void addRestaurantCriteria(List<String> restaurantIds) {
        List<Restaurant> restaurants = restaurantServiceProvider.get().getByInternalIds(restaurantIds);
        if (restaurants != null && !restaurants.isEmpty())
            criterions.add(Restrictions.in("restaurant", restaurants));
    }

    private void addCustomerCriteria(List<String> customerIds) {
        List<Customer> customers = customerServiceProvider.get().getCustomerByEmailId(customerIds);
        if (customers != null && !customers.isEmpty())
            criterions.add(Restrictions.in("customer", customers));
    }

    private void addOrderIdsCriteria(List<String> orderIds) {
        if (orderIds != null && !orderIds.isEmpty())
            criterions.add(Restrictions.in("internalId", orderIds));
    }
}
