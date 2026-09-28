package com.rohit.food_delivery_system.restaurant.controller;

import com.rohit.food_delivery_system.common.response.ApiResponse;
import com.rohit.food_delivery_system.common.response.MessageManager;
import com.rohit.food_delivery_system.restaurant.dto.MenuItemRequest;
import com.rohit.food_delivery_system.restaurant.dto.MenuItemResponse;
import com.rohit.food_delivery_system.restaurant.dto.RestaurantRequest;
import com.rohit.food_delivery_system.restaurant.dto.RestaurantResponse;
import com.rohit.food_delivery_system.restaurant.service.RestaurantService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    public RestaurantController(
            RestaurantService restaurantService) {

        this.restaurantService = restaurantService;
    }

    // Create restaurant
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<RestaurantResponse> createRestaurant(
            @RequestBody RestaurantRequest request) {

        RestaurantResponse restaurantResponse =
                restaurantService.createRestaurant(request);

        return new ApiResponse<>(
                HttpStatus.CREATED.value(),
                MessageManager.RESTAURANT_CREATED,
                restaurantResponse
        );
    }

    // Get all restaurants
    @GetMapping
    public ApiResponse<List<RestaurantResponse>> getAllRestaurants() {

        List<RestaurantResponse> restaurants =
                restaurantService.getAllRestaurants();

        return new ApiResponse<>(
                HttpStatus.OK.value(),
                MessageManager.SUCCESS,
                restaurants
        );
    }

    // Add menu item
    @PostMapping("/{restaurantId}/menu-items")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<MenuItemResponse> addMenuItem(
            @PathVariable Long restaurantId,
            @RequestBody MenuItemRequest request) {

        MenuItemResponse menuItemResponse =
                restaurantService.addMenuItem(
                        restaurantId,
                        request
                );

        return new ApiResponse<>(
                HttpStatus.CREATED.value(),
                MessageManager.MENU_ITEM_CREATED,
                menuItemResponse
        );
    }

    // Get menu items
    @GetMapping("/{restaurantId}/menu-items")
    public ApiResponse<List<MenuItemResponse>> getMenuItems(
            @PathVariable Long restaurantId) {

        List<MenuItemResponse> menuItems =
                restaurantService.getMenuItems(
                        restaurantId
                );

        return new ApiResponse<>(
                HttpStatus.OK.value(),
                MessageManager.SUCCESS,
                menuItems
        );
    }
}