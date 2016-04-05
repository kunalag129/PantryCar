package com.pantrycar.system.external;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.pantrycar.system.representations.payment.PaymentLinkInfo;
import com.pantrycar.system.representations.payment.PaymentLinkRequest;

import java.io.IOException;

/**
 * Created by kunal.agarwal on 14/02/16.
 */
public class PaymentGatewayService {
    private final static String API_KEY = "80e540bd8a3ba6b26dac7fb367116984";
    private final static String API_TOKEN = "dc4e8bc933d955aa7c85569bd5b0209d";
    private final static String url = "https://www.instamojo.com/api/1.1/payment-requests/";
    private final static ObjectMapper MAPPER = new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);

    public static PaymentLinkInfo getPaymentInfo(double amount) {
        PaymentLinkRequest paymentLinkRequest = new PaymentLinkRequest();
        paymentLinkRequest.setAmount(amount);
        System.out.println("=====================================");
        System.out.println(paymentLinkRequest);
        System.out.println("-------------------------------------");
        HttpResponse<String> response;
        try {
            response = Unirest.post(url)
                    .header("X-Api-Key", API_KEY)
                    .header("X-Auth-Token", API_TOKEN)
                    .header("Content-Type", "application/json")
            .body(MAPPER.writeValueAsString(paymentLinkRequest))
                    .asString();
            System.out.println("=====================================");
            System.out.println(response.getBody().toString());
            System.out.println("-------------------------------------");
            return MAPPER.readValue(response.getBody(), PaymentLinkInfo.class);
        } catch (UnirestException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
