package com.rohit.food_delivery_system.delivery.dto;

import com.rohit.food_delivery_system.delivery.model.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryResponse {

    private Long id;

    private Long orderId;

    private Long driverId;

    private String driverName;

    private DeliveryStatus status;

    private LocalDateTime assignedAt;
}