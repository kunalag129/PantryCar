package com.pantrycar.system.representations.railway;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

/**
 * Created by kunal.agarwal on 21/11/15.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)

public class TrainRouteLibResponse {
    private int responseCode;
    private TrainRouteDetails train;
    private String error;

    public TrainRouteLibResponse sanitize() {
        List<TrainStoppage> stops = this.getTrain().getRoute();
        for (TrainStoppage stoppage : stops) {
            stoppage.setStoppageTime(stoppage.getStopTime());
            stoppage.setStationCode(stoppage.getStation().getCode());
            stoppage.setStationName(stoppage.getStation().getName());
        }
        return this;
    }
}
