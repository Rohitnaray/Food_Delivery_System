package com.rohit.food_delivery_system.order.controller;

import com.rohit.food_delivery_system.common.response.ApiResponse;
import com.rohit.food_delivery_system.common.response.MessageManager;
import com.rohit.food_delivery_system.order.dto.OrderRequest;
import com.rohit.food_delivery_system.order.dto.OrderResponse;
import com.rohit.food_delivery_system.order.service.OrderService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Create order
    @PostMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<OrderResponse> createOrder(
            @PathVariable Long userId,
            @RequestBody OrderRequest request) {

        OrderResponse orderResponse =
                orderService.createOrder(
                        userId,
                        request
                );

        return new ApiResponse<>(
                HttpStatus.CREATED.value(),
                MessageManager.ORDER_CREATED,
                orderResponse
        );
    }

    // Get order
    @GetMapping("/{orderId}")
    public ApiResponse<OrderResponse> getOrder(
            @PathVariable Long orderId) {

        OrderResponse orderResponse =
                orderService.getOrder(orderId);

        return new ApiResponse<>(
                HttpStatus.OK.value(),
                MessageManager.SUCCESS,
                orderResponse
        );
    }

    // Get user orders
    @GetMapping("/users/{userId}")
    public ApiResponse<List<OrderResponse>> getUserOrders(
            @PathVariable Long userId) {

        List<OrderResponse> orders =
                orderService.getUserOrders(userId);

        return new ApiResponse<>(
                HttpStatus.OK.value(),
                MessageManager.SUCCESS,
                orders
        );
    }
}