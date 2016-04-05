package com.pantrycar.system.representations.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pantrycar.system.representations.BasicResponse;
import lombok.*;

import java.util.List;

/**
 * Created by kunal.agarwal on 16/11/15.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailsList extends BasicResponse {
    private int numberOfOrders;

    @JsonIgnoreProperties({"responseStatus", "responseCode"})
    private List<OrderDetails> orders;


}
