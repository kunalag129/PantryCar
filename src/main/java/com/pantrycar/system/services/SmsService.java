package com.pantrycar.system.services;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.pantrycar.system.core.Order;
import com.pantrycar.system.external.Messaging.MessageTemplates;
import com.pantrycar.system.external.Messaging.MessagingService;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

/**
 * Created by kunal.agarwal on 22/11/15.
 */

@Slf4j
public class SmsService {

    private final Provider<MessagingService> serviceProvider;

    @Inject
    public SmsService(Provider<MessagingService> serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public Object sendSms(String templateName, Order order) throws UnirestException {
        List<String> placeHolders = null;
        String message = getMessageTemplate(templateName, order);
        placeHolders = getMessagePlaceHolders(templateName, order);
        String recipient = order.getCustomer().getContactNo();
        return serviceProvider.get().sendSms(message, recipient, placeHolders);
    }

    private List<String> getMessagePlaceHolders(String templateName, Order order) {
        switch (templateName) {
            case "order_receive":
                return Arrays.asList(order.getCustomer().getName(), order.getInternalId());

            case "order_dispatch":
                return Arrays.asList("Ram", "1234567890");

            case "cod_payment":
                return Arrays.asList(String.valueOf(order.getAmountBilled()));

            case "prepaid_payment":
                return Arrays.asList(String.valueOf(order.getAmountReceived()));

            case "order_track":
                return Arrays.asList(order.getInternalId(), "www.pantrycar.net/track");

            case "order_delivery":
                return Arrays.asList(order.getInternalId(), "1234567890", "care@pantrycar.net");

            default:
                return null;
        }
    }

    private String getMessageTemplate(String templateName, Order order) {
        switch (templateName) {
            case "order_receive":
                return MessageTemplates.ORDER_RECEIVE;

            case "order_dispatch":
                return MessageTemplates.ORDER_DISPATCH;

            case "cod_payment":
                return MessageTemplates.COD_PAYMENT;

            case "prepaid_payment":
                return MessageTemplates.PREPAID_PAYMENT;

            case "order_track":
                return MessageTemplates.ORDER_TRACK;

            case "order_delivery":
                return MessageTemplates.ORDER_DELIVERY;

         default:
             return null;
        }
    }
}
