package com.pantrycar.system.representations.railway;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pantrycar.system.representations.BasicResponse;
import lombok.*;

import java.util.List;

/**
 * Created by kunal.agarwal on 26/02/16.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class StationDetailsList extends BasicResponse{

    @Singular
    @JsonIgnoreProperties({"responseStatus", "responseCode"})
    List<StationDetails> stations;
}
