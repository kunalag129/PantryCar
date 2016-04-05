package com.pantrycar.system.core;

import com.pantrycar.system.enums.OrderStatus;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by kunal.agarwal on 21/05/15.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
@Builder
public class Order extends Model {

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "station_id")
    private Station station;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "order_date", nullable = true)
    private Date orderDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "delivery_date", nullable = true)
    private Date deliveryDate;

    @Column(name = "internal_id")
    private String internalId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "comments")
    private String comments;

    @Column(name = "customer_instruction")
    private String customerInstructions;

    @Column(name = "mode_of_payment")
    private String modeOfPayment;

    @Column(name = "amount_billed")
    private double amountBilled;

    @Column(name = "amount_received")
    private double amountReceived;

    @Column(name = "pnr")
    private String pnr;

    @Column(name = "coach")
    private String coach;

    @Column(name = "train_no")
    private String trainNo;

    @Column(name = "seat_no")
    private String seatNo;

    @ManyToOne
    @JoinColumn(name = "offer_id")
    private Offer offer;

    @Column(name = "discount_amount")
    private double discountAmount;

    @Column(name = "discount_reason")
    private String discountReason;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    @Column(name = "payment_request_id")
    private String paymentRequestId;

    @Column(name = "payment_url")
    private String paymentUrl;

    @ManyToOne
    @JoinColumn(name = "contact_id")
    private Contact contact;
}
