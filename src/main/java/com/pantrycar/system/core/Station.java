package com.pantrycar.system.core;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kunal.agarwal on 21/05/15.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "stations")
public class Station extends Model {

    @Column(name = "station_full_name")
    private String stationFullName;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "station_short_name")
    private String stationShortName;

    @Column(name = "station_code")
    private String stationCode;

    @Column(name = "is_active")
    private boolean isActive;

    @OneToMany(mappedBy = "station")
    private List<Restaurant> restaurants = new ArrayList<>();
}
