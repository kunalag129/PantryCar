package com.pantrycar.system.core;

import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by kunal.agarwal on 21/04/15.
 */

@Entity
@Table(name = "passwords")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Password extends Model {

    @NotBlank
    @Column(name = "password")
    private String password;
}
