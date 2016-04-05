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
@Builder
public class TrainRouteDetails {
    private String number;
    private String name;
    private String fullName;
    private List<TrainStoppage> route;
}
