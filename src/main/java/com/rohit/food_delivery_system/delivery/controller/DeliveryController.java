package com.rohit.food_delivery_system.delivery.controller;

import com.rohit.food_delivery_system.common.response.ApiResponse;
import com.rohit.food_delivery_system.common.response.MessageManager;
import com.rohit.food_delivery_system.delivery.dto.DeliveryRequest;
import com.rohit.food_delivery_system.delivery.dto.DeliveryResponse;
import com.rohit.food_delivery_system.delivery.dto.DeliveryStatusRequest;
import com.rohit.food_delivery_system.delivery.service.DeliveryService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/deliveries")
public class DeliveryController {

    private final DeliveryService deliveryService;

    public DeliveryController(
            DeliveryService deliveryService) {

        this.deliveryService = deliveryService;
    }

    // Assign delivery
    @PostMapping("/orders/{orderId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<DeliveryResponse> assignDelivery(
            @PathVariable Long orderId,
            @RequestBody DeliveryRequest request) {

        DeliveryResponse deliveryResponse =
                deliveryService.assignDelivery(
                        orderId,
                        request
                );

        return new ApiResponse<>(
                HttpStatus.CREATED.value(),
                MessageManager.DELIVERY_ASSIGNED,
                deliveryResponse
        );
    }

    // Get delivery
    @GetMapping("/{deliveryId}")
    public ApiResponse<DeliveryResponse> getDelivery(
            @PathVariable Long deliveryId) {

        DeliveryResponse deliveryResponse =
                deliveryService.getDelivery(
                        deliveryId
                );

        return new ApiResponse<>(
                HttpStatus.OK.value(),
                MessageManager.SUCCESS,
                deliveryResponse
        );
    }

    // Update delivery status
    @PutMapping("/{deliveryId}/status")
    public ApiResponse<DeliveryResponse> updateDeliveryStatus(
            @PathVariable Long deliveryId,
            @RequestBody DeliveryStatusRequest request) {

        DeliveryResponse deliveryResponse =
                deliveryService.updateDeliveryStatus(
                        deliveryId,
                        request
                );

        return new ApiResponse<>(
                HttpStatus.OK.value(),
                MessageManager.DELIVERY_STATUS_UPDATED,
                deliveryResponse
        );
    }
}