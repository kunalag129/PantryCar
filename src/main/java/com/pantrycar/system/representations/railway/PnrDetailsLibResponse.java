package com.pantrycar.system.representations.railway;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

/**
 * Created by kunal.agarwal on 20/11/15.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)

public class PnrDetailsLibResponse {
    private int responseCode;
    private String pnr;
    private String trainNum;
    private String trainName;
    private String doj;
    private StationDetails fromStation;
    private StationDetails toStation;
    private StationDetails reservationUpto;
    private StationDetails boardingPoint;

    @JsonProperty("class")
    private String boardingClass;

    private int noOfPassengers;
    private String chartPrepared;
    private List<PassengerDetails> passengers;
    private String error;
}
