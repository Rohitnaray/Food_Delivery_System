package com.rohit.food_delivery_system.order.dto;

import com.rohit.food_delivery_system.order.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private Long id;

    private Long userId;

    private OrderStatus status;

    private Double totalAmount;

    private LocalDateTime createdAt;

    private List<OrderItemResponse> items;
}