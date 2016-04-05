package com.pantrycar.system.representations.restaurant;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pantrycar.system.representations.BasicResponse;
import lombok.*;

import java.util.List;

/**
 * Created by kunal.agarwal on 12/11/15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaurantDetailList extends BasicResponse {
    private String stationCode;

    @JsonIgnoreProperties({"responseStatus", "responseCode"})
    private List<RestaurantDetail> restaurants;
}
