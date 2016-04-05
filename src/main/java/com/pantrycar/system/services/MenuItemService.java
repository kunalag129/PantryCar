package com.pantrycar.system.services;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.pantrycar.system.core.MenuCategory;
import com.pantrycar.system.core.MenuItem;
import com.pantrycar.system.dao.MenuItemDAO;
import com.pantrycar.system.representations.menu.MenuResponse.Category.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by kunal.agarwal on 13/11/15.
 */
public class MenuItemService {
    private static final Logger logger = LoggerFactory.getLogger(MenuItemService.class);
    private final Provider<MenuItemDAO> daoProvider;

    @Inject
    public MenuItemService(Provider<MenuItemDAO> daoProvider) {
        this.daoProvider = daoProvider;
    }

    public Item addItem(MenuCategory menuCategory, Item request) {
        MenuItem menuItem = MenuItem.builder()
                .category(menuCategory)
                .description(request.getDescription())
                .name(request.getName())
                .price(request.getPrice())
                .build();
        menuItem = daoProvider.get().persist(menuItem);
        Item item = buildItemResponse(menuItem);
        return item;
    }

    public Item buildItemResponse(MenuItem item) {
        return Item.builder().id(item.getId())
                .active(item.isActive())
                .name(item.getName())
                .price(item.getPrice())
                .categoryId(item.getCategory().getId())
                .build();
    }

    public MenuItem getItemById(int itemId) {
        return daoProvider.get().getById(itemId);
    }

    public Item toggleItem(MenuItem item) {
        item.setActive(!item.isActive());
        item = daoProvider.get().persist(item);
        return buildItemResponse(item);
    }
}
