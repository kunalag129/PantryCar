package com.pantrycar.system.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import com.pantrycar.system.core.Station;
import com.pantrycar.system.services.StationService;
import io.dropwizard.hibernate.UnitOfWork;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Provider;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by kunal.agarwal on 26/02/16.
 */

@Slf4j
@Path("/stations")
@Produces(MediaType.APPLICATION_JSON)
public class StationResource {
    private final Provider<StationService> serviceProvider;

    @Inject
    public StationResource(Provider<StationService> serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    @POST
    @Timed
    @UnitOfWork
    public Response onBoardNewStation(Station station) {
        return Response.ok(serviceProvider.get().onBoardNewStation(station)).build();
    }

    @PUT
    @Timed
    @UnitOfWork
    @Path("/{stationCode}/enable")
    public Response enableService(@PathParam("stationCode") String stationCode) {
        return Response.ok(serviceProvider.get().enableService(stationCode)).build();
    }

    @PUT
    @Timed
    @UnitOfWork
    @Path("/{stationCode}/disable")
    public Response disableService(@PathParam("stationCode") String stationCode) {
        return Response.ok(serviceProvider.get().disableService(stationCode)).build();
    }

    @GET
    @Timed
    @UnitOfWork
    @Path("/{stationCode}")
    public Response getDetails(@PathParam("stationCode") String stationCode) {
        return Response.ok(serviceProvider.get().getStationDetailsByCode(stationCode)).build();
    }

    @GET
    @Timed
    @UnitOfWork
    public Response getStationList() {
        return Response.ok(serviceProvider.get().getAllStations()).build();
    }

    @GET
    @Timed
    @UnitOfWork
    @Path("/serviceable")
    public Response getServiceableStationList() {
        return Response.ok(serviceProvider.get().getServiceableStations()).build();
    }
}
