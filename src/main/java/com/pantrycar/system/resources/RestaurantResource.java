package com.pantrycar.system.resources;


import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.pantrycar.system.core.BankDetail;
import com.pantrycar.system.core.Restaurant;
import com.pantrycar.system.core.TaxDetail;
import com.pantrycar.system.representations.BasicResponse;
import com.pantrycar.system.services.RestaurantService;
import io.dropwizard.hibernate.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by kunal.agarwal on 24/03/15.
 */

@Path("/restaurants")
@Produces(MediaType.APPLICATION_JSON)
public class RestaurantResource {
    private final Provider<RestaurantService> serviceProvider;

    private static final Logger logger = LoggerFactory.getLogger(RestaurantResource.class);

    @Inject
    public RestaurantResource(Provider<RestaurantService> restaurantServiceProvider) {
        this.serviceProvider = restaurantServiceProvider;
    }

    @GET
    @Timed
    @UnitOfWork
    public Response getRestaurantByUrl(@QueryParam("url") String url) {
        if (url != null && !url.isEmpty()) {
            Map<String, Object> conditions = new HashMap<>();
            conditions.put("url", url);
            return Response.ok(serviceProvider.get().getRestaurantByColumns(conditions)).build();
        } else
            return Response.ok(new BasicResponse().error(404, "Please enter valid url for restaurant")).build();
    }

    @GET
    @Path("/{id}")
    @Timed
    @UnitOfWork
    public Response getRestaurantById(@PathParam("id") String id) {
        if (id != null && !id.isEmpty()) {
            Map<String, Object> conditions = new HashMap<>();
            conditions.put("internalId", id);
            return Response.ok(serviceProvider.get().getRestaurantByColumns(conditions)).build();
        } else
            return Response.ok(new BasicResponse().error(404, "Please enter valid id for restaurant")).build();
    }

    @GET
    @Path("/get_restaurants_by_station/{station_code}")
    @Timed
    @UnitOfWork
    public Response getRestaurantsByStation(@PathParam("station_code") String stationCode) {
        if (stationCode != null && !stationCode.isEmpty()) {
            return Response.ok(serviceProvider.get().getRestaurantByStation(stationCode)).build();
        } else
            return Response.ok(new BasicResponse().error(404, "Please enter valid stationCode for restaurant list")).build();
    }

    @POST
    @Timed
    @UnitOfWork
    public Response addRestaurant(Restaurant restaurant) {
        return Response.ok(serviceProvider.get().addRestaurant(restaurant)).build();
    }


    @POST
    @Path("/{id}/update_bank_details")
    @Timed
    @UnitOfWork
    public Response updateBankDetails(@PathParam("id") String id, BankDetail bankDetail) {
        Restaurant restaurant = serviceProvider.get().getRestaurantByInternalId(id);
        if (restaurant != null) {
            return Response.ok(serviceProvider.get().updateBankDetails(restaurant, bankDetail)).build();
        } else
            return Response.ok(new BasicResponse().error(404, "Please enter valid restaurant id for update bank details")).build();
    }

    @POST
    @Path("/{id}/update_tax_details")
    @Timed
    @UnitOfWork
    public Response updateTaxDetails(@PathParam("id") String id, TaxDetail taxDetail) {
        Restaurant restaurant = serviceProvider.get().getRestaurantByInternalId(id);
        if (restaurant != null) {
            return Response.ok(serviceProvider.get().updateTaxDetails(restaurant, taxDetail)).build();
        } else
            return Response.ok(new BasicResponse().error(404, "Please enter valid restaurant id for update bank details")).build();
    }
}
