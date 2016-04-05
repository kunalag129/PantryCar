package com.pantrycar.system.core;

/**
 * Created by kunal.agarwal on 18/04/15.
 */

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Getter
@Setter
@Entity
@DiscriminatorValue("Facebook")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FacebookLogin extends SocialAuth {
    @Column(name = "token")
    protected String token;

    @Column(name = "bio")
    @Type(type = "text")
    protected String bio;

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "user_id")
    private Customer customer;
}
