package com.pantrycar.system.core;

import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

/**
 * Created by kunal.agarwal on 08/07/15.
 */

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "categories")
public class MenuCategory extends Model {

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @Column(name = "name")
    private String name;

    @Column(name = "preference_order")
    private short preferenceOrder;

    @Column(name = "is_active")
    private boolean isActive = true;

    @OneToMany(mappedBy = "category")
    @Where(clause = "is_active = 1")
    List<MenuItem> menuItems;
}
