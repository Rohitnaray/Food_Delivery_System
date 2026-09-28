package com.rohit.food_delivery_system.order.repository;

import com.rohit.food_delivery_system.order.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository
        extends JpaRepository<OrderItem, Long> {
}