package com.pantrycar.system.representations.payment;

import lombok.Data;

/**
 * Created by kunal.agarwal on 14/02/16.
 */

@Data
public class PaymentLinkInfo {
    boolean success;
    PaymentLinkRequest paymentRequest;
}
