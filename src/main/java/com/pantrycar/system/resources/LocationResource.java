package com.pantrycar.system.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.pantrycar.system.core.Location;
import com.pantrycar.system.services.LocationService;
import io.dropwizard.hibernate.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by kunal.agarwal on 13/11/15.
 */

@Path("/locations")
@Produces(MediaType.APPLICATION_JSON)
public class LocationResource {
    private static final Logger logger = LoggerFactory.getLogger(LocationResource.class);
    private final Provider<LocationService> serviceProvider;

    @Inject
    public LocationResource(Provider<LocationService> serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    @POST
    @Timed
    @UnitOfWork
    public Response addLocation(Location location) {
        return Response.ok(serviceProvider.get().findOrCreate(location)).build();
    }
}
