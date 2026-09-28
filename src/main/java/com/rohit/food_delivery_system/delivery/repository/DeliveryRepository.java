package com.rohit.food_delivery_system.delivery.repository;

import com.rohit.food_delivery_system.delivery.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeliveryRepository
        extends JpaRepository<Delivery, Long> {

    Optional<Delivery> findByOrderId(Long orderId);
}