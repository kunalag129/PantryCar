package com.pantrycar.system.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * Created by kunal.agarwal on 21/05/15.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "offers")
public class Offer extends Model {

    @Column(name = "promo_code")
    private String promoCode;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "used_offer_count")
    private int usedOfferCount;

    @Column(name = "available_offer_count")
    private int availableOfferCount;

    @Column(name = "offer_type")
    private String offerType;

    @Column(name = "offer_value")
    private double offerValue;

    @Column(name = "max_offer_limit")
    private int maxOfferLimit;

    @Column(name = "start_date")
    private Timestamp startDate;

    @Column(name = "expiry_date")
    private Timestamp expiryDate;

}
