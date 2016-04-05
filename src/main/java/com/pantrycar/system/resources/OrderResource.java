package com.pantrycar.system.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.pantrycar.system.core.Customer;
import com.pantrycar.system.representations.BasicResponse;
import com.pantrycar.system.representations.order.OrderDetails;
import com.pantrycar.system.representations.order.OrderSearchRequest;
import com.pantrycar.system.representations.payment.PaymentConfirmation;
import com.pantrycar.system.services.*;
import io.dropwizard.hibernate.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by kunal.agarwal on 15/11/15.
 */

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
public class OrderResource {
    private static final Logger logger = LoggerFactory.getLogger(OrderResource.class);
    private final Provider<OrderService> serviceProvider;
    private final Provider<OrderCreateService> orderCreateServiceProvider;
    private final Provider<OrderSearchService> orderSearchServiceProvider;
    private final Provider<OrderPaymentService> orderPaymentServiceProvider;
    private final Provider<CustomerService> customerServiceProvider;

    @Inject
    public OrderResource(Provider<OrderService> serviceProvider, Provider<OrderCreateService> orderCreateServiceProvider, Provider<OrderSearchService> orderSearchServiceProvider, Provider<OrderPaymentService> orderPaymentServiceProvider, Provider<CustomerService> customerServiceProvider) {
        this.serviceProvider = serviceProvider;
        this.orderCreateServiceProvider = orderCreateServiceProvider;
        this.orderSearchServiceProvider = orderSearchServiceProvider;
        this.orderPaymentServiceProvider = orderPaymentServiceProvider;
        this.customerServiceProvider = customerServiceProvider;
    }

    @POST
    @Timed
    @UnitOfWork
    public Response createOrder(OrderDetails orderDetails) {
        String validRequestMessage = orderCreateServiceProvider.get().validateOrderCreationRequest(orderDetails);
        if (validRequestMessage.equals("Valid"))
            return Response.ok(orderCreateServiceProvider.get().createOrder(orderDetails)).build();
        else
            return Response.ok(new BasicResponse().error(400, validRequestMessage)).build();
    }

    @GET
    @Timed
    @UnitOfWork
    @Path("/get_temp_order_by_customer/{emailId:.+}")
    public Response getTempOrderByCustomer(@PathParam("emailId") String emailId) {
        Customer customer = customerServiceProvider.get().getCustomerByEmailId(emailId);
        if (customer != null) {
            return Response.ok(serviceProvider.get().getTempOrderByCustomer(customer)).build();
        } else
            return Response.ok(new BasicResponse().error(404, "No customer with this Id")).build();
    }

    @POST
    @Timed
    @UnitOfWork
    @Path("/search")
    public Response searchOrders(OrderSearchRequest request) {
        return Response.ok(orderSearchServiceProvider.get().search(request)).build();
    }

    @GET
    @Timed
    @UnitOfWork
    @Path("/{orderId}")
    public Response getOrder(@PathParam("orderId") String orderId) {
        return Response.ok(serviceProvider.get().getOrderDetails(orderId)).build();
    }


    @PUT
    @Timed
    @UnitOfWork
    @Path("/confirm")
    public Response updatePayment(PaymentConfirmation paymentConfirmation) throws UnirestException {
        return Response.ok(orderPaymentServiceProvider.get().getOrderDetails(paymentConfirmation.getPaymentRequestId())).build();
    }

    @PUT
    @Timed
    @UnitOfWork
    @Path("/{orderId}/checkout")
    public Response confirmOrder(@PathParam("orderId") String orderId, OrderDetails orderDetails) throws UnirestException {
        return Response.ok(serviceProvider.get().checkout(orderId, orderDetails)).build();
    }

    @PUT
    @Timed
    @UnitOfWork
    @Path("/{orderId}/paymentLink")
    public Response getPaymentLink(@PathParam("orderId") String orderId) {
        return Response.ok(serviceProvider.get().getPaymentLink(orderId)).build();
    }

    @GET
    @Timed
    @UnitOfWork
    @Path("/{orderId}/deliveryDetails")
    public Response getDeliveryDetails(@PathParam("orderId") String orderId) {
        return Response.ok(serviceProvider.get().getDeliveryDetails(orderId)).build();
    }
}
