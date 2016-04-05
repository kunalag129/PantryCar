package com.pantrycar.system.services;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.pantrycar.system.core.Payment;
import com.pantrycar.system.dao.PaymentDAO;
import com.pantrycar.system.external.PaymentGatewayService;
import com.pantrycar.system.representations.payment.PaymentLinkInfo;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * Created by kunal.agarwal on 13/02/16.
 */

@Slf4j
public class PaymentService {
    private final Provider<PaymentDAO> daoProvider;
    private final Provider<OrderPaymentService> orderPaymentServiceProvider;

    @Inject
    public PaymentService(Provider<PaymentDAO> daoProvider, Provider<OrderPaymentService> orderPaymentServiceProvider) {
        this.daoProvider = daoProvider;
        this.orderPaymentServiceProvider = orderPaymentServiceProvider;
    }

    public Payment storePaymentConfirmation(Payment paymentDetails) throws UnirestException {
        Payment payment = daoProvider.get().persist(paymentDetails);
        orderPaymentServiceProvider.get().updatePayment(payment.getPaymentRequestId(), payment.getAmount());
        return payment;
    }

    private Payment createPaymentObject(Map<String, String> paymentDetails) {
        Payment payment = Payment.builder()
                .amount(paymentDetails.get("amount"))
                .buyer(paymentDetails.get("buyer"))
                .buyerName(paymentDetails.get("buyer_name"))
                .buyerPhone(paymentDetails.get("buyer_phone"))
                .currency(paymentDetails.get("currency"))
                .fees(paymentDetails.get("fees"))
                .longurl(paymentDetails.get("longurl"))
                .mac(paymentDetails.get("mac"))
                .paymentId(paymentDetails.get("payment_id"))
                .paymentRequestId(paymentDetails.get("payment_request_id"))
                .purpose(paymentDetails.get("purpose"))
                .shorturl(paymentDetails.get("shorturl"))
                .status(paymentDetails.get("status"))
                .build();

        return payment;
    }

    public Payment getByPaymentId(String paymentId) {
        return daoProvider.get().findUniqueByColumn("paymentId", paymentId);
    }

    public Payment getByPaymentRequestId(String paymentRequestId) {
        return daoProvider.get().findUniqueByColumn("paymentRequestId", paymentRequestId);
    }

    public Payment getByColumn(String columnName, Object value) {
        return daoProvider.get().findUniqueByColumn(columnName, value);
    }

    public PaymentLinkInfo getPaymentLink(double amountBilled) {
        return PaymentGatewayService.getPaymentInfo(amountBilled);
    }
}
