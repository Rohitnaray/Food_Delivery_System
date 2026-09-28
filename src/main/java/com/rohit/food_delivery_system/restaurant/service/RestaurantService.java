package com.rohit.food_delivery_system.restaurant.service;

import com.rohit.food_delivery_system.common.exception.ResourceNotFoundException;
import com.rohit.food_delivery_system.common.response.MessageManager;
import com.rohit.food_delivery_system.restaurant.dto.MenuItemRequest;
import com.rohit.food_delivery_system.restaurant.dto.MenuItemResponse;
import com.rohit.food_delivery_system.restaurant.dto.RestaurantRequest;
import com.rohit.food_delivery_system.restaurant.dto.RestaurantResponse;
import com.rohit.food_delivery_system.restaurant.model.MenuItem;
import com.rohit.food_delivery_system.restaurant.model.Restaurant;
import com.rohit.food_delivery_system.restaurant.repository.MenuItemRepository;
import com.rohit.food_delivery_system.restaurant.repository.RestaurantRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final MenuItemRepository menuItemRepository;

    public RestaurantService(
            RestaurantRepository restaurantRepository,
            MenuItemRepository menuItemRepository) {

        this.restaurantRepository = restaurantRepository;
        this.menuItemRepository = menuItemRepository;
    }

    // Create restaurant
    public RestaurantResponse createRestaurant(
            RestaurantRequest request) {

        Restaurant restaurant = new Restaurant();

        restaurant.setName(request.getName());
        restaurant.setAddress(request.getAddress());
        restaurant.setPhone(request.getPhone());
        restaurant.setActive(true);

        Restaurant savedRestaurant =
                restaurantRepository.save(restaurant);

        return new RestaurantResponse(
                savedRestaurant.getId(),
                savedRestaurant.getName(),
                savedRestaurant.getAddress(),
                savedRestaurant.getPhone(),
                savedRestaurant.getActive()
        );
    }

    // Get all restaurants
    public List<RestaurantResponse> getAllRestaurants() {

        return restaurantRepository.findAll()
                .stream()
                .map(restaurant -> new RestaurantResponse(
                        restaurant.getId(),
                        restaurant.getName(),
                        restaurant.getAddress(),
                        restaurant.getPhone(),
                        restaurant.getActive()
                ))
                .toList();
    }

    // Add menu item
    public MenuItemResponse addMenuItem(
            Long restaurantId,
            MenuItemRequest request) {

        Restaurant restaurant =
                restaurantRepository.findById(restaurantId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        MessageManager.RESTAURANT_NOT_FOUND
                                )
                        );

        MenuItem menuItem = new MenuItem();

        menuItem.setName(request.getName());
        menuItem.setPrice(request.getPrice());
        menuItem.setDescription(request.getDescription());
        menuItem.setAvailable(true);
        menuItem.setRestaurant(restaurant);

        MenuItem savedMenuItem =
                menuItemRepository.save(menuItem);

        return new MenuItemResponse(
                savedMenuItem.getId(),
                savedMenuItem.getName(),
                savedMenuItem.getPrice(),
                savedMenuItem.getDescription(),
                savedMenuItem.getAvailable(),
                savedMenuItem.getRestaurant().getId()
        );
    }

    // Get menu items
    public List<MenuItemResponse> getMenuItems(
            Long restaurantId) {

        restaurantRepository.findById(restaurantId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                MessageManager.RESTAURANT_NOT_FOUND
                        )
                );

        return menuItemRepository
                .findByRestaurantId(restaurantId)
                .stream()
                .map(menuItem -> new MenuItemResponse(
                        menuItem.getId(),
                        menuItem.getName(),
                        menuItem.getPrice(),
                        menuItem.getDescription(),
                        menuItem.getAvailable(),
                        menuItem.getRestaurant().getId()
                ))
                .toList();
    }
}