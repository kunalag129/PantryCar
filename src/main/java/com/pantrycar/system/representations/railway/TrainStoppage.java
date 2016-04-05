package com.pantrycar.system.representations.railway;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

/**
 * Created by kunal.agarwal on 05/05/15.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class TrainStoppage {
    private String arrivalTime;
    private String departureTime;
    private String stoppageTime;
    private String stopTime;
    private String day;
    private String stationCode;
    private String stationName;
    private StationDetails station;
}
