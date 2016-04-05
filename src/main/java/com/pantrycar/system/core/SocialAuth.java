package com.pantrycar.system.core;

import javax.persistence.*;

/**
 * Created by kunal.agarwal on 18/04/15.
 */


@Entity
@Table(name = "social_auth_tokens")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "token_type")
public class SocialAuth extends Model {
}
