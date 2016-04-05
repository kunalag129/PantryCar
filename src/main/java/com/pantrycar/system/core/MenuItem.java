package com.pantrycar.system.core;

import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;

/**
 * Created by kunal.agarwal on 08/07/15.
 */

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "items")
public class MenuItem extends Model {

    @ManyToOne
    @JoinColumn(name = "category_id")
    @Where(clause = "is_active = 1")
    private MenuCategory category;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private double price;

    @Column(name = "preference_order")
    private short preferenceOrder;

    @Column(name = "is_active")
    private boolean isActive = true;
}
