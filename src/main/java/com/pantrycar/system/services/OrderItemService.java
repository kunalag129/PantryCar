package com.pantrycar.system.services;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.pantrycar.system.core.MenuItem;
import com.pantrycar.system.core.Order;
import com.pantrycar.system.core.OrderItem;
import com.pantrycar.system.dao.OrderItemDAO;
import com.pantrycar.system.representations.order.OrderDetails.OrderItemDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by kunal.agarwal on 15/11/15.
 */
public class OrderItemService {
    private static final Logger logger = LoggerFactory.getLogger(OrderItemService.class);
    private final Provider<OrderItemDAO> daoProvider;
    private final Provider<MenuItemService> menuItemServiceProvider;

    @Inject
    public OrderItemService(Provider<OrderItemDAO> daoProvider, Provider<MenuItemService> menuItemServiceProvider) {
        this.daoProvider = daoProvider;
        this.menuItemServiceProvider = menuItemServiceProvider;
    }

    public String validateOrderItemCreationRequest(List<OrderItemDetails> orderItems, double amountBilled) {
        if (orderItems == null || orderItems.isEmpty())
            return "Order items not present";

        double orderItemsValue = 0;
        for (OrderItemDetails orderItem : orderItems) {
            if (orderItem.getMenuItemId() == 0)
                return "Menu item is cannot be 0";
            else if (menuItemServiceProvider.get().getItemById(orderItem.getMenuItemId()) == null)
                return "Menu item not present in the system";

            if (orderItem.getQuantity() <= 0)
                return "Order item quantity cannot be <=0";
            MenuItem menuItem = menuItemServiceProvider.get().getItemById(orderItem.getMenuItemId());
            orderItemsValue += orderItem.getQuantity() * menuItem.getPrice();
        }

        if (orderItemsValue != amountBilled)
            return "Billed amount(" + amountBilled + ") doesnt match with the order items value(" + orderItemsValue + ")";

        return "Valid";
    }

    public List<OrderItemDetails> getOrderItemDetails(List<OrderItem> orderItems) {
        List<OrderItemDetails> orderItemsList = orderItems.stream().map(orderItem -> OrderItemDetails.builder()
                .menuItemId(orderItem.getMenuItem().getId())
                .name(orderItem.getMenuItem().getName())
                .perItemCost(orderItem.getMenuItem().getPrice())
                .quantity(orderItem.getQuantity())
                .build()).collect(Collectors.toList());
        ;
        return orderItemsList;
    }

    public List<OrderItem> createOrderItems(Order order, List<OrderItemDetails> orderItems) {
        List<OrderItem> orderItemList = new ArrayList<>();
        for (OrderItemDetails orderItem : orderItems) {
            MenuItem menuItem = menuItemServiceProvider.get().getItemById(orderItem.getMenuItemId());
            orderItemList.add(daoProvider.get().persist(OrderItem.builder().order(order).menuItem(menuItem)
                    .name(menuItem.getName()).perItemCost(menuItem.getPrice()).quantity(orderItem.getQuantity()).build()));
        }
        return orderItemList;
    }
}
