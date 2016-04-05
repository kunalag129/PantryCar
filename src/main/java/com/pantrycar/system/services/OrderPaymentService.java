package com.pantrycar.system.services;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.pantrycar.system.core.Order;
import com.pantrycar.system.core.Payment;
import com.pantrycar.system.enums.OrderStatus;
import com.pantrycar.system.representations.BasicResponse;
import com.pantrycar.system.representations.order.OrderDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by kunal.agarwal on 28/11/15.
 */
public class OrderPaymentService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    private final Provider<OrderService> orderServiceProvider;
    private final Provider<PaymentService> paymentServiceProvider;
    private final Provider<SmsService> smsServiceProvider;

    @Inject
    public OrderPaymentService(Provider<OrderService> orderServiceProvider, Provider<PaymentService> paymentServiceProvider, Provider<SmsService> smsServiceProvider) {
        this.orderServiceProvider = orderServiceProvider;
        this.paymentServiceProvider = paymentServiceProvider;
        this.smsServiceProvider = smsServiceProvider;
    }

    public OrderDetails updatePayment(String paymentRequestId, String amount) throws UnirestException {
        Order order = orderServiceProvider.get().getByColumn("paymentRequestId", paymentRequestId);
        if(order == null) {
            return new BasicResponse().error(400, "No order found with this payment request id");
        }

        order.setAmountReceived(Double.valueOf(amount));
        order.setStatus(OrderStatus.ready_to_process);
        order = orderServiceProvider.get().update(order);
        smsServiceProvider.get().sendSms("order_receive", order);
        smsServiceProvider.get().sendSms("prepaid_payment", order);
        smsServiceProvider.get().sendSms("order_track", order);
        return orderServiceProvider.get().getOrderDetails(order);
    }

    public OrderDetails getOrderDetails(String paymentRequestId) throws UnirestException {
        Order order = orderServiceProvider.get().getByColumn("paymentRequestId", paymentRequestId);
        Payment payment = paymentServiceProvider.get().getByPaymentRequestId(paymentRequestId);
        if(order == null) {
            return new BasicResponse().error(400, "No order found with this payment request id");
        }
        if(payment == null) {
            return new BasicResponse().error(400, "No payment detail found with this payment request id");
        }
        if(order.getStatus().equals(OrderStatus.checkout_in_progress)) {
            order.setStatus(OrderStatus.ready_to_process);
            order = orderServiceProvider.get().update(order);
            smsServiceProvider.get().sendSms("order_receive", order);
            smsServiceProvider.get().sendSms("prepaid_payment", order);
            smsServiceProvider.get().sendSms("order_track", order);
        }
        return orderServiceProvider.get().getOrderDetails(order);
    }
}
