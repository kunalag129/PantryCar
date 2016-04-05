package com.pantrycar.system.core;

/**
 * Created by kunal.agarwal on 03/04/15.
 */

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
@Table(name = "bank_details")
public class BankDetail extends Model {

    @Column(name = "bank_account_name")
    private String bankAccountName;

    @Column(name = "bank_account_email")
    private String bankAccountEmail;

    @Column(name = "bank_account_number")
    private String bankAccountNumber;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "ifsc_code")
    private String ifscCode;

    @Column(name = "is_valid")
    private Boolean isValid;

    @ManyToOne
    @JoinColumn(name = "bank_location_id")
    private Location location;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
}
