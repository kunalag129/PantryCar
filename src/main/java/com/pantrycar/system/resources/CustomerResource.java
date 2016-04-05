package com.pantrycar.system.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.pantrycar.system.core.Customer;
import com.pantrycar.system.representations.BasicResponse;
import com.pantrycar.system.representations.customer.CustomerDetail;
import com.pantrycar.system.services.CustomerService;
import io.dropwizard.hibernate.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by kunal.agarwal on 13/11/15.
 */
@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
public class CustomerResource {
    private static final Logger logger = LoggerFactory.getLogger(CustomerResource.class);
    private final Provider<CustomerService> serviceProvider;

    @Inject
    public CustomerResource(Provider<CustomerService> serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    @GET
    @Path("/{emailId:.+}")
    @Timed
    @UnitOfWork
    public Response getCustomerDetails(@PathParam("emailId") String emailId) {
        Customer customer = serviceProvider.get().getCustomerByEmailId(emailId);
        if (customer != null) {
            return Response.ok(serviceProvider.get().getCustomerDetailsResponse(customer)).build();
        } else
            return Response.ok(new BasicResponse().error(404, "Please enter valid email id for details")).build();
    }

    @GET
    @Path("/get_customer_from_token")
    @Timed
    @UnitOfWork
    public Response getCustomerFromToken(@QueryParam("token") String token) {
        Customer customer = serviceProvider.get().getUniqueCustomerByColumn("rememberToken", token);
        if (customer != null) {
            return Response.ok(serviceProvider.get().getCustomerDetailsResponse(customer)).build();
        } else
            return Response.ok(new BasicResponse().error(404, "Invalid token")).build();
    }

    @POST
    @Timed
    @UnitOfWork
    public Response registerCustomer(CustomerDetail customerDetail) {
        Customer customer = serviceProvider.get().getCustomerByEmailId(customerDetail.getEmailId());
        if (customer == null) {
            return Response.ok(serviceProvider.get().register(customerDetail)).build();
        } else
            return Response.ok(new BasicResponse().error(400, "Customer already exists")).build();
    }

    @PUT
    @Timed
    @UnitOfWork
    @Path("/{emailId:.+}/update_remember_token")
    public Response updateRememberToken(@PathParam("emailId") String emailId, CustomerDetail customerDetail) {
        Customer customer = serviceProvider.get().getCustomerByEmailId(emailId);
        if (customer != null) {
            return Response.ok(serviceProvider.get().updateRememberToken(customer, customerDetail.getRememberToken())).build();
        } else
            return Response.ok(new BasicResponse().error(404, "No customer with this emailId")).build();
    }

    @PUT
    @Timed
    @UnitOfWork
    @Path("/{emailId:.+}/update_verification_token")
    public Response updateVerificationToken(@PathParam("emailId") String emailId, CustomerDetail customerDetail) {
        Customer customer = serviceProvider.get().getCustomerByEmailId(emailId);
        if (customer != null) {
            return Response.ok(serviceProvider.get().updateVerificationToken(customer, customerDetail.getVerificationToken())).build();
        } else
            return Response.ok(new BasicResponse().error(404, "No customer with this emailId")).build();
    }

    @PUT
    @Timed
    @UnitOfWork
    @Path("/{emailId:.+}/update_password_reset_token")
    public Response updatePasswordResetToken(@PathParam("emailId") String emailId, CustomerDetail customerDetail) {
        Customer customer = serviceProvider.get().getCustomerByEmailId(emailId);
        if (customer != null) {
            return Response.ok(serviceProvider.get().updatePasswordResetToken(customer, customerDetail.getPassResetToken())).build();
        } else
            return Response.ok(new BasicResponse().error(404, "No customer with this emailId")).build();
    }


    @PUT
    @Timed
    @UnitOfWork
    @Path("/reset_password")
    public Response resetPassword(CustomerDetail customerDetail) {
        Customer customer = serviceProvider.get().getUniqueCustomerByColumn("passResetToken", customerDetail.getPassResetToken());
        if (customer != null) {
            return Response.ok(serviceProvider.get().resetPassword(customer, customerDetail.getLoginPass())).build();
        } else
            return Response.ok(new BasicResponse().error(404, "No customer with this token")).build();
    }

    @PUT
    @Timed
    @UnitOfWork
    @Path("/set_customer_verified")
    public Response setCustomerVerified(CustomerDetail customerDetail) {
        Customer customer = serviceProvider.get().getUniqueCustomerByColumn("verificationToken", customerDetail.getVerificationToken());
        if (customer != null) {
            return Response.ok(serviceProvider.get().markCustomerVerified(customer)).build();
        } else
            return Response.ok(new BasicResponse().error(404, "No customer with this verification token")).build();
    }

    @POST
    @Timed
    @UnitOfWork
    @Path("/login")
    public Response customerLogin(CustomerDetail customerDetail) {
        Customer customer = serviceProvider.get().getCustomerByEmailId(customerDetail.getEmailId());
        if (customer != null) {
            return Response.ok(serviceProvider.get().validateLogin(customer, customerDetail)).build();
        } else
            return Response.ok(new BasicResponse().error(404, "No customer with this emailId")).build();
    }
}
