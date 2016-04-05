package com.pantrycar.system.representations.railway;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

/**
 * Created by kunal.agarwal on 20/11/15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PassengerDetails {
    private String sr;
    private String bookingStatus;
    private String currentStatus;
}
