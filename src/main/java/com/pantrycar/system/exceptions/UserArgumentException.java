package com.pantrycar.system.exceptions;

import io.dropwizard.jackson.JsonSnakeCase;
import lombok.Getter;
import lombok.Setter;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by rahul.parida on 25/08/15.
 */
@Setter
@Getter
@JsonSnakeCase
public class UserArgumentException extends WebApplicationException {
    public UserArgumentException(String msg) {
        super(Response.status(400).entity(msg).type(MediaType.APPLICATION_JSON).build());
    }
}
