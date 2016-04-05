package com.pantrycar.system.services;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.pantrycar.system.core.Customer;
import com.pantrycar.system.core.Order;
import com.pantrycar.system.dao.OrderDAO;
import com.pantrycar.system.enums.OrderStatus;
import com.pantrycar.system.representations.BasicResponse;
import com.pantrycar.system.representations.constants.PantryCarConstants;
import com.pantrycar.system.representations.order.OrderDetails;
import com.pantrycar.system.representations.order.OrderDetailsList;
import com.pantrycar.system.representations.payment.PaymentLinkInfo;
import com.pantrycar.system.representations.restaurant.RestaurantDetail;
import org.hibernate.criterion.Criterion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by kunal.agarwal on 15/11/15.
 */
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private final Provider<OrderDAO> daoProvider;
    private final Provider<OrderItemService> orderItemServiceProvider;
    private final Provider<PaymentService> paymentServiceProvider;
    private final Provider<SmsService> smsServiceProvider;
    private final Provider<RestaurantService> restaurantServiceProvider;

    @Inject
    public OrderService(Provider<OrderDAO> daoProvider, Provider<OrderItemService> orderItemServiceProvider, Provider<CustomerService> customerServiceProvider, Provider<RestaurantService> restaurantServiceProvider, Provider<StationService> stationServiceProvider, Provider<PaymentService> paymentServiceProvider, Provider<SmsService> smsServiceProvider, Provider<RestaurantService> restaurantServiceProvider1) {
        this.daoProvider = daoProvider;
        this.orderItemServiceProvider = orderItemServiceProvider;
        this.paymentServiceProvider = paymentServiceProvider;
        this.smsServiceProvider = smsServiceProvider;
        this.restaurantServiceProvider = restaurantServiceProvider1;
    }

    public OrderDetails getOrderDetails(Order order) {

        RestaurantDetail restaurantDetail = restaurantServiceProvider.get().getDetails(order.getRestaurant());

        OrderDetails orderDetails = OrderDetails.builder()
                .orderId(order.getInternalId())
                .customerId(order.getCustomer().getEmailId())
                .restaurantId(order.getRestaurant().getInternalId())
                .stationCode(order.getStation().getStationCode())
                .orderDate(order.getOrderDate())
                .deliveryDate(order.getDeliveryDate())
                .status(order.getStatus())
                .customerInstructions(order.getCustomerInstructions())
                .modeOfPayment(order.getModeOfPayment())
                .amountBilled(order.getAmountBilled())
                .amountReceived(order.getAmountReceived())
                .pnr(order.getPnr())
                .trainNo(order.getTrainNo())
                .seatNo(order.getSeatNo())
                .paymentRequestId(order.getPaymentRequestId())
                .paymentUrl(order.getPaymentUrl())
                .orderItems(orderItemServiceProvider.get().getOrderItemDetails(order.getOrderItems()))
                .restaurantDetail(restaurantDetail)
                .build();
        return orderDetails;
    }

    public OrderDetailsList getOrderDetails(List<Order> orders) {
        List<OrderDetails> orderDetailsList = orders.stream().map(order -> getOrderDetails(order)).collect(Collectors.toList());
        return new OrderDetailsList(orders.size(), orderDetailsList);
    }

    public OrderDetails getTempOrderByCustomer(Customer customer) {
        Order order = daoProvider.get().getLastTempOrderByCustomer(customer);
        if (order == null) {
            return new OrderDetails().error(404, "No order created within 6 hours");
        }
        return getOrderDetails(order);
    }

    public List<Order> getOrdersFromCritera(List<Criterion> criterions) {
        return daoProvider.get().findByCriterias(criterions);
    }

    public Order createOrder(Order order) {
        order = daoProvider.get().persist(order);
        order.setInternalId("PCO" + order.getId());
        order = daoProvider.get().persist(order);
        return order;
    }

    public List<Order> getByInternalIds(List<String> internalIds) {
        return daoProvider.get().findByColumn("internalId", internalIds);
    }

    public Order getByInternalId(String internalId) {
        return daoProvider.get().findUniqueByColumn("internalId", internalId);
    }

    public Order getByColumn(String columnName, Object value) {
        return daoProvider.get().findUniqueByColumn(columnName, value);
    }

    public Order update(Order order) {
        return daoProvider.get().persist(order);
    }

    public OrderDetails checkout(String orderId, OrderDetails orderDetails) throws UnirestException {
        if(orderDetails.getModeOfPayment() == null)
            return new BasicResponse().error(400, "No payment mode is present with the checkout details");
        if(!PantryCarConstants.MODE_OF_PAYMENTS.contains(orderDetails.getModeOfPayment().toLowerCase()))
            return new BasicResponse().error(400, "Payment detail is invalid in the checkout details");

        Order order = getByInternalId(orderId);
        if(order == null) {
            return new BasicResponse().error(400, "No order found with this order id");
        }

        if(PantryCarConstants.PREPAID.equalsIgnoreCase(orderDetails.getModeOfPayment())) {
            if (order.getPaymentRequestId() != null)
                return getOrderDetails(order);
            getPaymentDetails(order);
            order.setModeOfPayment(PantryCarConstants.PREPAID);
            order.setStatus(OrderStatus.checkout_in_progress);
        }
        else {
            order.setModeOfPayment(PantryCarConstants.COD);
            order.setStatus(OrderStatus.ready_to_process);
            smsServiceProvider.get().sendSms("order_receive", order);
            smsServiceProvider.get().sendSms("cod_payment", order);
            smsServiceProvider.get().sendSms("order_track", order);
        }

        order.setPnr(orderDetails.getPnr());
        order.setCoach(orderDetails.getCoachNo());
        order.setSeatNo(orderDetails.getSeatNo());

        return getOrderDetails(update(order));
    }

    public Object getDeliveryDetails(String orderId) {

        return null;
    }

    public Object getPaymentLink(String orderId) {
        Order order = getByInternalId(orderId);
        if(order.getPaymentRequestId() != null )
            return getOrderDetails(order);
        getPaymentDetails(order);
        return getOrderDetails(update(order));
    }

    private void getPaymentDetails(Order order) {
        PaymentLinkInfo paymentLinkInfo = paymentServiceProvider.get().getPaymentLink(order.getAmountBilled());
        order.setPaymentRequestId(paymentLinkInfo.getPaymentRequest().getId());
        order.setPaymentUrl(paymentLinkInfo.getPaymentRequest().getLongurl());
    }

    public OrderDetails getOrderDetails(String orderId) {
        Order order = getByInternalId(orderId);
        if(order == null) {
            return new BasicResponse().error(400, "No order found with this order id");
        }

        return getOrderDetails(order);
    }
}
