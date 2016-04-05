package com.pantrycar.system.representations.restaurant;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pantrycar.system.representations.BasicResponse;
import com.pantrycar.system.representations.railway.StationDetails;
import lombok.*;

import java.util.Date;

/**
 * Created by kunal.agarwal on 11/11/15.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaurantDetail extends BasicResponse {
    private String id;
    private String name;
    private double distance;
    private String contactNumber;
    private Date openTime;
    private Date closeTime;
    private int slaDetails;
    private double minOrderValue;
    private double deliveryCharges;
    private boolean isOnline;
    private String url;

    @JsonIgnoreProperties({"responseStatus", "responseCode"})
    private StationDetails stationDetails;
}
