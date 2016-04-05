package com.pantrycar.system.representations.payment;

import io.dropwizard.jackson.JsonSnakeCase;
import lombok.Data;

/**
 * Created by kunal.agarwal on 13/02/16.
 */
@Data
@JsonSnakeCase
public class PaymentConfirmation {

    String amount;
    String buyer;
    String buyerName;
    String buyerPhone;
    String currency;
    String fees;
    String longurl;
    String mac;
    String paymentId;
    String paymentRequestId;
    String purpose;
    String shorturl;
    String status;
}
