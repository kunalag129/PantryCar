package com.pantrycar.system.representations.customer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.pantrycar.system.representations.BasicResponse;
import lombok.*;

/**
 * Created by kunal.agarwal on 13/11/15.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDetail extends BasicResponse {
    private String name;
    private String contactNo;
    private String emailId;
    private boolean verified;
    private String loginPass;
    private SocialLogin googleLogin;
    private SocialLogin facebookLogin;
    private String rememberToken;
    private String verificationToken;
    private String passResetToken;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class SocialLogin {
        private String token;
        private String bio;
    }
}
