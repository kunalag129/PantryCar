package com.pantrycar.system.core;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * Created by kunal.agarwal on 18/04/15.
 */

@Entity
@Table(name = "customers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer extends Model {


    @Column(name = "name")
    private String name;

    @Column(name = "contact_no")
    private String contactNo;

    @Column(name = "email_id")
    private String emailId;

    @OneToOne(mappedBy = "customer", cascade = {CascadeType.ALL})
    private GoogleLogin googleLogin;

    @OneToOne(mappedBy = "customer", cascade = {CascadeType.ALL})
    private FacebookLogin facebookLogin;

    @Column(name = "remember_token")
    private String rememberToken;

    @Column(name = "verification_token")
    private String verificationToken;

    @Column(name = "is_verified")
    private boolean verified;

    @Column(name = "pass_reset_token")
    private String passResetToken;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "password_id")
    private Password password;
}
