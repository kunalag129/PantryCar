package com.pantrycar.system.resources;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.pantrycar.system.core.Payment;
import com.pantrycar.system.services.PaymentService;
import io.dropwizard.hibernate.UnitOfWork;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by kunal.agarwal on 13/02/16.
 */

@Slf4j
@Path("/payments")
@Produces(MediaType.APPLICATION_JSON)
public class PaymentResource {

    private final Provider<PaymentService> serviceProvider;

    @Inject
    public PaymentResource(Provider<PaymentService> serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Timed
    @UnitOfWork
    @Path("/")
    public Response paymentConfirmation(MultivaluedMap<String, String> formParams) throws IOException, UnirestException {
        ObjectMapper MAPPER = new ObjectMapper();
        MAPPER.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
        Map<String, String> details = formParams.entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().get(0)));
        Payment paymentDetails = MAPPER.readValue(new JSONObject(details).toString(), Payment.class);
        return Response.ok(serviceProvider.get().storePaymentConfirmation(paymentDetails)).build();
    }
}

