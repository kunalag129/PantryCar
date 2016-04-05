package com.pantrycar.system.resources;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import com.pantrycar.system.core.MenuCategory;
import com.pantrycar.system.core.MenuItem;
import com.pantrycar.system.core.Restaurant;
import com.pantrycar.system.representations.BasicResponse;
import com.pantrycar.system.representations.menu.MenuResponse.Category;
import com.pantrycar.system.representations.menu.MenuResponse.Category.Item;
import com.pantrycar.system.services.MenuCategoryService;
import com.pantrycar.system.services.MenuItemService;
import com.pantrycar.system.services.MenuService;
import com.pantrycar.system.services.RestaurantService;
import io.dropwizard.hibernate.UnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Provider;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by kunal.agarwal on 13/11/15.
 */
@Path("/menu")
@Produces(MediaType.APPLICATION_JSON)
public class MenuResource {
    private static final Logger logger = LoggerFactory.getLogger(MenuResource.class);
    private final Provider<MenuCategoryService> menuCategoryServiceProvider;
    private final Provider<MenuItemService> menuItemServiceProvider;
    private final Provider<RestaurantService> restaurantServiceProvider;
    private final Provider<MenuService> menuServiceProvider;

    @Inject
    public MenuResource(Provider<MenuCategoryService> menuCategoryServiceProvider, Provider<MenuItemService> menuItemServiceProvider, Provider<RestaurantService> restaurantServiceProvider, Provider<MenuService> menuServiceProvider) {
        this.menuCategoryServiceProvider = menuCategoryServiceProvider;
        this.menuItemServiceProvider = menuItemServiceProvider;
        this.restaurantServiceProvider = restaurantServiceProvider;
        this.menuServiceProvider = menuServiceProvider;
    }

    @GET
    @Path("/{id}")
    @Timed
    @UnitOfWork
    public Response getMenuByRestaurnat(@PathParam("id") String restaurantId) {
        Restaurant restaurant = restaurantServiceProvider.get().getRestaurantByInternalId(restaurantId);
        if (restaurant != null) {
            return Response.ok(menuServiceProvider.get().getMenuByRestaurant(restaurant)).build();
        } else
            return Response.ok(new BasicResponse().error(404, "Please enter valid restaurant id for getting menu")).build();
    }

    @POST
    @Path("/add_category")
    @Timed
    @UnitOfWork
    public Response addCategory(Category request) {
        Restaurant restaurant = restaurantServiceProvider.get().getRestaurantByInternalId(request.getRestaurantId());
        if (restaurant != null) {
            return Response.ok(menuCategoryServiceProvider.get().addCategory(restaurant, request)).build();
        } else
            return Response.ok(new BasicResponse().error(404, "Please enter valid restaurant id for adding category")).build();
    }

    @POST
    @Path("/add_item")
    @Timed
    @UnitOfWork
    public Response addItem(Item request) {
        MenuCategory menuCategory = menuCategoryServiceProvider.get().getCategoryById(request.getCategoryId());
        if (menuCategory != null) {
            return Response.ok(menuItemServiceProvider.get().addItem(menuCategory, request)).build();
        } else
            return Response.ok(new BasicResponse().error(404, "Please enter valid category id for adding item")).build();
    }

    @PUT
    @Path("/edit_category")
    @Timed
    @UnitOfWork
    public Response editCategory(MenuCategory request) {
        MenuCategory category = menuCategoryServiceProvider.get().getCategoryById(request.getId());
        if (category != null) {
            return Response.ok(menuCategoryServiceProvider.get().editCategory(category, request.getName())).build();
        } else
            return Response.ok(new BasicResponse().error(404, "Please enter valid category id for editing")).build();
    }

    @PUT
    @Path("/toggle_category")
    @Timed
    @UnitOfWork
    public Response disableCategory(MenuCategory request) {
        MenuCategory category = menuCategoryServiceProvider.get().getCategoryById(request.getId());
        if (category != null) {
            return Response.ok(menuCategoryServiceProvider.get().toggleCategory(category)).build();
        } else
            return Response.ok(new BasicResponse().error(404, "Please enter valid category id for disabling")).build();
    }

    @PUT
    @Path("/disable_item")
    @Timed
    @UnitOfWork
    public Response disableItem(MenuItem request) {
        MenuItem item = menuItemServiceProvider.get().getItemById(request.getId());
        if (item != null) {
            return Response.ok(menuItemServiceProvider.get().toggleItem(item)).build();
        } else
            return Response.ok(new BasicResponse().error(404, "Please enter valid item id for disabling")).build();
    }
}
