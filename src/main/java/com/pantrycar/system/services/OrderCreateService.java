package com.pantrycar.system.services;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.pantrycar.system.core.Customer;
import com.pantrycar.system.core.Order;
import com.pantrycar.system.core.Restaurant;
import com.pantrycar.system.core.Station;
import com.pantrycar.system.enums.OrderStatus;
import com.pantrycar.system.representations.order.OrderDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by kunal.agarwal on 18/11/15.
 */
public class OrderCreateService {
    private static final Logger logger = LoggerFactory.getLogger(OrderCreateService.class);
    private final Provider<OrderService> orderServiceProvider;
    private final Provider<OrderItemService> orderItemServiceProvider;
    private final Provider<CustomerService> customerServiceProvider;
    private final Provider<RestaurantService> restaurantServiceProvider;
    private final Provider<StationService> stationServiceProvider;

    @Inject
    public OrderCreateService(Provider<OrderService> orderServiceProvider, Provider<OrderItemService> orderItemServiceProvider, Provider<CustomerService> customerServiceProvider, Provider<RestaurantService> restaurantServiceProvider, Provider<StationService> stationServiceProvider) {
        this.orderServiceProvider = orderServiceProvider;
        this.orderItemServiceProvider = orderItemServiceProvider;
        this.customerServiceProvider = customerServiceProvider;
        this.restaurantServiceProvider = restaurantServiceProvider;
        this.stationServiceProvider = stationServiceProvider;
    }

    public String validateOrderCreationRequest(OrderDetails orderDetails) {
        if (orderDetails.getCustomerId() == null)
            return "Customer field is missing";
        else if (customerServiceProvider.get().getCustomerByEmailId(orderDetails.getCustomerId()) == null)
            return "Customer not present in the system";

        if (orderDetails.getStationCode() == null)
            return "Station field is missing";
        else if (stationServiceProvider.get().getStationByCode(orderDetails.getStationCode()) == null)
            return "Station not present in the system";

        if (orderDetails.getRestaurantId() == null)
            return "Restaurant field is missing";
        else {
            Restaurant restaurant = restaurantServiceProvider.get().getRestaurantByInternalId(orderDetails.getRestaurantId());
            if (restaurant == null)
                return "Restaurant not present in the system";
            else if (orderDetails.getStationCode() != null && !restaurant.getStation().getStationCode().equals(orderDetails.getStationCode()))
                return "Restaurant is not mapped to the mentioned station";
        }

        if (orderDetails.getOrderDate() == null)
            return "Order date field is missing";

        if (orderDetails.getDeliveryDate() == null)
            return "Delivery date field is missing";

        if (orderDetails.getAmountBilled() == 0)
            return "Amount billed field is missing";

        if (orderDetails.getPnr() == null && orderDetails.getTrainNo() == null)
            return "Either PNR or Train no is mandatory, which is missing";

        return orderItemServiceProvider.get().validateOrderItemCreationRequest(orderDetails.getOrderItems(), orderDetails.getAmountBilled());
    }

    public OrderDetails createOrder(OrderDetails orderDetails) {
        Customer customer = customerServiceProvider.get().getCustomerByEmailId(orderDetails.getCustomerId());
        Restaurant restaurant = restaurantServiceProvider.get().getRestaurantByInternalId(orderDetails.getRestaurantId());
        Station station = stationServiceProvider.get().getStationByCode(orderDetails.getStationCode());
        Order order = Order.builder()
                .customer(customer)
                .restaurant(restaurant)
                .station(station)
                .orderDate(orderDetails.getOrderDate())
                .deliveryDate(orderDetails.getDeliveryDate())
                .amountBilled(orderDetails.getAmountBilled())
                .pnr(orderDetails.getPnr())
                .customerInstructions(orderDetails.getCustomerInstructions())
                .status(OrderStatus.created)
                .trainNo(orderDetails.getTrainNo())
                .seatNo(orderDetails.getSeatNo())
                .modeOfPayment(orderDetails.getModeOfPayment())
                .amountReceived(orderDetails.getAmountReceived())
                .build();
        order = orderServiceProvider.get().createOrder(order);
        order.setOrderItems(orderItemServiceProvider.get().createOrderItems(order, orderDetails.getOrderItems()));
        return orderServiceProvider.get().getOrderDetails(order);
    }
}
