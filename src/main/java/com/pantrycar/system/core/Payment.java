package com.pantrycar.system.core;

import io.dropwizard.jackson.JsonSnakeCase;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by kunal.agarwal on 13/02/16.
 */
@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "payments")
public class Payment extends Model{
    private String amount;
    private String buyer;

    @Column(name = "buyer_name")
    private String buyerName;

    @Column(name = "buyer_phone")
    private String buyerPhone;
    private String currency;
    private String fees;
    private String longurl;
    private String mac;

    @Column(name = "payment_id")
    private String paymentId;

    @Column(name = "payment_request_id")
    private String paymentRequestId;
    private String purpose;
    private String shorturl;
    private String status;
}
