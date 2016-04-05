package com.pantrycar.system.representations.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pantrycar.system.enums.OrderStatus;
import com.pantrycar.system.representations.BasicResponse;
import com.pantrycar.system.representations.restaurant.RestaurantDetail;
import io.dropwizard.jackson.JsonSnakeCase;
import lombok.*;

import java.util.Date;
import java.util.List;

/**
 * Created by kunal.agarwal on 15/11/15.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@ToString
@EqualsAndHashCode
@JsonSnakeCase
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetails extends BasicResponse {

    private String orderId;
    private String customerId;
    private String restaurantId;
    private String stationCode;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Calcutta")
    private Date orderDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Calcutta")
    private Date deliveryDate;
    private String customerInstructions;
    private String modeOfPayment;
    private double amountBilled;
    private double amountReceived;
    private String pnr;
    private String trainNo;
    private String seatNo;
    private String coachNo;
    private OrderStatus status;
    private String paymentRequestId;
    private String paymentUrl;
    private List<OrderItemDetails> orderItems;

    @JsonIgnoreProperties({"responseStatus", "responseCode"})
    private RestaurantDetail restaurantDetail;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Getter
    @Setter
    @ToString
    @EqualsAndHashCode
    @AllArgsConstructor
    @JsonSnakeCase
    @NoArgsConstructor
    @Builder
    public static class OrderItemDetails {
        private int menuItemId;
        private int quantity;
        private String name;
        private double perItemCost;
    }
}
