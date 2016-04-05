package com.pantrycar.system.representations.payment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.dropwizard.jackson.JsonSnakeCase;
import lombok.*;

/**
 * Created by kunal.agarwal on 14/02/16.
 */
@Data
@JsonSnakeCase
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentLinkRequest {
    private String purpose = "Food bill";
    private String buyerName = "PNTRYCR";
    private String email = "customer@pantrycar.net";
    private String phone = "8527337646";
    private String redirectUrl = "http://pantrycar.net/orderPlaced";
    private boolean allowRepeatedPayments = false;
    private double amount;
    private String id;
    private String longurl;
    private String webhook = "http://52.10.55.210:8080/payments/";
}
