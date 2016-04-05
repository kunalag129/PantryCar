package com.pantrycar.system.services;

import com.google.inject.Inject;
import com.pantrycar.system.core.MenuCategory;
import com.pantrycar.system.core.Restaurant;
import com.pantrycar.system.dao.MenuCategoryDAO;
import com.pantrycar.system.representations.menu.MenuResponse.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Provider;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by kunal.agarwal on 13/11/15.
 */
public class MenuCategoryService {
    private static final Logger logger = LoggerFactory.getLogger(MenuCategoryService.class);
    private final Provider<MenuCategoryDAO> daoProvider;
    private final Provider<MenuItemService> menuItemServiceProvider;

    @Inject
    public MenuCategoryService(Provider<MenuCategoryDAO> daoProvider, Provider<MenuItemService> menuItemServiceProvider) {
        this.daoProvider = daoProvider;
        this.menuItemServiceProvider = menuItemServiceProvider;
    }

    public Category addCategory(Restaurant restaurant, Category request) {
        MenuCategory menuCategory = MenuCategory.builder()
                .name(request.getName())
                .menuItems(new ArrayList<>())
                .restaurant(restaurant).build();
        menuCategory = daoProvider.get().persist(menuCategory);
        Category category = buildCategoryResponse(menuCategory);
        return category;
    }

    public MenuCategory getCategoryById(int categoryId) {
        return daoProvider.get().getById(categoryId);
    }


    public Category buildCategoryResponse(MenuCategory menuCategory) {
        List<Category.Item> items = menuCategory.getMenuItems().stream().map(item -> menuItemServiceProvider.get().buildItemResponse(item)).collect(Collectors.toList());
        Category category = Category.builder()
                .id(menuCategory.getId())
                .name(menuCategory.getName())
                .items(items)
                .restaurantId(menuCategory.getRestaurant().getInternalId())
                .build();
        return category;
    }

    public Category editCategory(MenuCategory category, String newName) {
        category.setName(newName);
        category = daoProvider.get().persist(category);
        return buildCategoryResponse(category);
    }

    public Category toggleCategory(MenuCategory category) {
        category.setActive(!category.isActive());
        category = daoProvider.get().persist(category);
        return buildCategoryResponse(category);
    }
}
