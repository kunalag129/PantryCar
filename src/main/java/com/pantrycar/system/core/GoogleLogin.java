package com.pantrycar.system.core;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;

/**
 * Created by kunal.agarwal on 18/04/15.
 */
@Getter
@Setter
@Entity
@DiscriminatorValue("Google")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GoogleLogin extends SocialAuth {
    @Column(name = "token")
    protected String token;

    @Column(name = "bio")
    @Type(type = "text")
    protected String bio;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "user_id")
    private Customer customer;
}
