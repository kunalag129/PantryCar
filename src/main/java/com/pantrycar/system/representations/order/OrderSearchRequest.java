package com.pantrycar.system.representations.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pantrycar.system.enums.OrderStatus;
import lombok.*;

import java.util.Date;
import java.util.List;

/**
 * Created by kunal.agarwal on 18/11/15.
 */

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class OrderSearchRequest {
    private List<String> orderIds;
    private List<String> customerIds;
    private List<String> restaurantIds;
    private List<String> stationCodes;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Calcutta")
    private Date fromOrderDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Calcutta")
    private Date toOrderDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Calcutta")
    private Date fromDeliveryDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Calcutta")
    private Date toDeliveryDate;

    private String modeOfPayment;
    private List<OrderStatus> status;
    private List<String> pnrs;
    private List<String> trainNos;

}
