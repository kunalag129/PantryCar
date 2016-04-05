package com.pantrycar.system.representations.railway;

/**
 * Created by kunal.agarwal on 08/05/15.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

/**
 * Created by kunal.agarwal on 06/05/15.
 */

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrainDetails {

    private String trainNum;
    private String number;
    private String trainName;
    private String name;
    private String srcArrivalTime;
    private String destArrivalTime;
    private String srcDepartureTime;
    private String destDepartureTime;
}

