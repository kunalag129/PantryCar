package com.pantrycar.system.representations.railway;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pantrycar.system.representations.BasicResponse;
import lombok.*;

/**
 * Created by kunal.agarwal on 20/11/15.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StationDetails extends BasicResponse{
    private String code;
    private String name;

    private String stationFullName;
    private String city;
    private String state;
    private String stationShortName;
    private String stationCode;
    private boolean isActive;
}
