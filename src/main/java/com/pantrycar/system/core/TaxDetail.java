package com.pantrycar.system.core;

/**
 * Created by kunal.agarwal on 03/04/15.
 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tax_details")
public class TaxDetail extends Model {

    @Column(name = "service_tax_number")
    private String serviceTaxNumber;

    @Column(name = "vat_no")
    private String vatNo;

    @Column(name = "food_license")
    private String foodLicense;

    @Column(name = "FSSAI")
    private String fssai;

    @Column(name = "mou_acceptance")
    private String mouAcceptance;

    @Column(name = "is_valid")
    private Boolean isValid;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
}
