package com.rohit.food_delivery_system.restaurant.repository;

import com.rohit.food_delivery_system.restaurant.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository
        extends JpaRepository<Restaurant, Long> {
}