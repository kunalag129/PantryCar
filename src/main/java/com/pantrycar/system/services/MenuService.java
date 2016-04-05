package com.pantrycar.system.services;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.pantrycar.system.core.MenuCategory;
import com.pantrycar.system.core.Restaurant;
import com.pantrycar.system.representations.menu.MenuResponse;
import com.pantrycar.system.representations.menu.MenuResponse.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kunal.agarwal on 13/11/15.
 */
public class MenuService {
    private static final Logger logger = LoggerFactory.getLogger(MenuService.class);
    private final Provider<MenuCategoryService> menuCategoryServiceProvider;

    @Inject
    public MenuService(Provider<MenuCategoryService> menuCategoryServiceProvider) {
        this.menuCategoryServiceProvider = menuCategoryServiceProvider;
    }

    public MenuResponse getMenuByRestaurant(Restaurant restaurant) {
        List<MenuCategory> menuCategories = restaurant.getMenuCategories();
        MenuResponse menuResponse = new MenuResponse(new ArrayList<>());
        for (MenuCategory menuCategory : menuCategories) {
            Category category = menuCategoryServiceProvider.get().buildCategoryResponse(menuCategory);
            menuResponse.getCategories().add(category);
        }
        return menuResponse;
    }


}
