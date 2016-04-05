package com.pantrycar.system.external.Messaging;

/**
 * Created by kunal.agarwal on 17/02/16.
 */
public class MessageTemplates {
    public static final String  ORDER_DISPATCH = "Your order has been dispatch. You can call the delivery guy ( {0} ) on this number {1}.";

    public static final String ORDER_DELIVERY = "Thank you. Your order Id : {0} , has been delivered to you as per our system. We hope you are satisfied with our service and quality. If you have any issues, complains or suggestions, please call on this number directly {1} or send us a quick email at {2}. We will soon revert back to you.";

    public static final String ORDER_TRACK = "For tracking Order Id : {0} . Please use this link {1}";

    public static final String PREPAID_PAYMENT = "Thanks for choosing the prepaid mode. We have successfully received amount Rs. {0}.";

    public static final String COD_PAYMENT = "You have chosen COD as payment. Please ready with Rs. {0} , so that it can be easy for us to deliver order without much of a hassle.";

    public static final String ORDER_RECEIVE = "Hello {0}, Thanks for giving us a chance to serve you .We have received your order. Your order ID is: {1}.";
}
