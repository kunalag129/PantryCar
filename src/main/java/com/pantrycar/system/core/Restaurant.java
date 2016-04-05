package com.pantrycar.system.core;

/**
 * Created by kunal.agarwal on 19/03/15.
 */

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "restaurants")
public class Restaurant extends Model {

    @Column(name = "internal_id")
    private String internalId;

    @Column(name = "name")
    private String name;

    @Column(name = "distance")
    private double distance;

    @Column(name = "contact_no")
    private String contactNo;

    @Column(name = "open_time")
    private Time openTime;

    @Column(name = "close_time")
    private Time closeTime;

    @Column(name = "sla_details")
    private int slaDetails;

    @Column(name = "minimum_order_value")
    private double minimumOrder;

    @Column(name = "delivery_charges")
    private double deliveryCharges;

    @Column(name = "is_online")
    private boolean online;

    @Column(name = "friendly_url")
    private String url;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @OneToMany(mappedBy = "restaurant")
    private List<BankDetail> bankDetails = new ArrayList<BankDetail>();

    @OneToMany(mappedBy = "restaurant")
    private List<TaxDetail> taxDetails = new ArrayList<TaxDetail>();

    @OneToMany(mappedBy = "restaurant")
    private List<MenuCategory> menuCategories;

    @OneToMany(mappedBy = "restaurant")
    private List<Order> orders;

    @OneToMany(mappedBy = "restaurant")
    private List<Contact> contacts;

    @ManyToOne
    @JoinColumn(name = "station_id")
    private Station station;

    @OneToOne
    @JoinColumn(name = "password_id")
    private Password password;
}
