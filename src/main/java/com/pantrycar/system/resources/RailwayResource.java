package com.pantrycar.system.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.pantrycar.system.services.RailwayService;
import io.dropwizard.hibernate.UnitOfWork;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by kunal.agarwal on 19/11/15.
 */

@Slf4j
@Path("/railways")
@Produces(MediaType.APPLICATION_JSON)
public class RailwayResource {
    private static final Logger logger = LoggerFactory.getLogger(RailwayResource.class);
    private final Provider<RailwayService> railwayServiceProvider;

    @Inject
    public RailwayResource(Provider<RailwayService> railwayServiceProvider) {
        this.railwayServiceProvider = railwayServiceProvider;
    }

    @GET
    @Timed
    @UnitOfWork
    @Path("/get_stations_from_pnr/{pnr}")
    public Response getInfoFromPnr(@PathParam("pnr") String pnr) {
        return Response.ok(railwayServiceProvider.get().getPnrDetails(pnr)).build();
    }

    @GET
    @Timed
    @UnitOfWork
    @Path("/get_trains_between_locations")
    public Response getTrainsBetweenLocations(@QueryParam("src") String sourceCode, @QueryParam("dest") String destinationCode, @QueryParam("date") String date) {
        sourceCode = sourceCode.toUpperCase();
        destinationCode = destinationCode.toUpperCase();
        return Response.ok(railwayServiceProvider.get().getTrainsBetweenLocations(sourceCode, destinationCode, date)).build();
    }

    @GET
    @Timed
    @UnitOfWork
    @Path("/get_stations_between_locations")
    public Response getStationsBetweenLocations(@QueryParam("src") String sourceCode, @QueryParam("dest") String destinationCode, @QueryParam("date") String date, @QueryParam("train") String trainNum) {
        sourceCode = sourceCode.toUpperCase();
        destinationCode = destinationCode.toUpperCase();
        return Response.ok(railwayServiceProvider.get().getStationsBetweenLocations(sourceCode, destinationCode, date, trainNum)).build();
    }
}
