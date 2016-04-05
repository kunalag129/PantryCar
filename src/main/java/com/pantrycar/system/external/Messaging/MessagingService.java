package com.pantrycar.system.external.Messaging;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.Getter;
import lombok.Setter;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kunal.agarwal on 25/11/15.
 */
@Getter
@Setter
public class MessagingService {
    private static MessagingService instance = null;
    private static final String USER="kunalag";
    private static final String PASSWORD="kunvib@129";
    private static final String SENDERID="PNTYCR";
    private static final String URL="http://cloud.smsindiahub.in/vendorsms/pushsms.aspx?";

    public static MessagingService get() {
        if (instance == null)
            instance = new MessagingService();
        return instance;
    }

    public Object sendSms(String message, String recipient, List<String> placeHolders) throws UnirestException {
        System.out.println("Sending message:");
        HttpResponse<String> res = Unirest.get("http://cloud.smsindiahub.in/vendorsms/pushsms.aspx")
                .queryString("user", USER)
                .queryString("password", PASSWORD)
                .queryString("msisdn", recipient)
                .queryString("sid", SENDERID)
                .queryString("msg", MessageFormat.format(message, placeHolders.toArray()))
                .queryString("fl", 0)
                .queryString("gwid", 2)
                .asString();
        System.out.println(res.getBody());
        return null;
    }

    public static void main(String[] args) throws UnirestException {
//        MessagingService.get().sendSms(MessageFormat.format(MessageTemplates.ORDER_RECEIVE, "Mr. X", "PCO1"));
//        MessagingService.get().sendSms(MessageFormat.format(MessageTemplates.COD_PAYMENT, "340.00"));
//        MessagingService.get().sendSms(MessageFormat.format(MessageTemplates.PREPAID_PAYMENT, "134.00"));
//        MessagingService.get().sendSms(MessageFormat.format(MessageTemplates.ORDER_TRACK, "PCO1", "www.pantrycar.net/track/PCO1"));
//       MessagingService.get().sendSms(MessageFormat.format(MessageTemplates.ORDER_DISPATCH, "Ram", "1234567890"));
//        MessagingService.get().sendSms(MessageFormat.format(MessageTemplates.ORDER_DELIVERY, "PCO1", "1234567890", "care@pantrycar.net"));
        System.out.println(MessageFormat.format(MessageTemplates.ORDER_DELIVERY, Arrays.asList("PCO1", "1234567890", "care@pantrycar.net").toArray()));
//        new MessagingService().sendSms();
    }
}
