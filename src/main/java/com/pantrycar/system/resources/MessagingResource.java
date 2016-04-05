//package com.pantrycar.system.resources;
//
//import com.codahale.metrics.annotation.Timed;
//import com.mashape.unirest.http.exceptions.UnirestException;
//import com.pantrycar.system.external.Messaging.MessageTemplates;
//import com.pantrycar.system.services.SmsService;
//import io.dropwizard.hibernate.UnitOfWork;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.ws.rs.GET;
//import javax.ws.rs.Path;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//
//import static com.pantrycar.system.PantryCarApplication.getInstance;
//
///**
// * Created by kunal.agarwal on 22/11/15.
// */
//@Path("/messaging")
//@Produces(MediaType.APPLICATION_JSON)
//public class MessagingResource {
//    private static final Logger logger = LoggerFactory.getLogger(MessagingResource.class);
//
//    @GET
//    @Timed
//    @UnitOfWork
//    public Response sendSms() throws UnirestException {
//        return Response.ok(getInstance(SmsService.class).sendSms(MessageTemplates.ORDER_RECEIVE, order)).build();
//    }
//}
