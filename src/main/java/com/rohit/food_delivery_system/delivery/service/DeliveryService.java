package com.rohit.food_delivery_system.delivery.service;

import com.rohit.food_delivery_system.auth.model.Role;
import com.rohit.food_delivery_system.auth.model.User;
import com.rohit.food_delivery_system.auth.repository.UserRepository;
import com.rohit.food_delivery_system.common.exception.BadRequestException;
import com.rohit.food_delivery_system.common.exception.ResourceNotFoundException;
import com.rohit.food_delivery_system.common.response.MessageManager;
import com.rohit.food_delivery_system.delivery.dto.DeliveryRequest;
import com.rohit.food_delivery_system.delivery.dto.DeliveryResponse;
import com.rohit.food_delivery_system.delivery.dto.DeliveryStatusRequest;
import com.rohit.food_delivery_system.delivery.model.Delivery;
import com.rohit.food_delivery_system.delivery.model.DeliveryStatus;
import com.rohit.food_delivery_system.delivery.repository.DeliveryRepository;
import com.rohit.food_delivery_system.order.model.Order;
import com.rohit.food_delivery_system.order.model.OrderStatus;
import com.rohit.food_delivery_system.order.repository.OrderRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    public DeliveryService(
            DeliveryRepository deliveryRepository,
            OrderRepository orderRepository,
            UserRepository userRepository) {

        this.deliveryRepository = deliveryRepository;
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    // Assign delivery
    public DeliveryResponse assignDelivery(
            Long orderId,
            DeliveryRequest request) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                MessageManager.ORDER_NOT_FOUND
                        )
                );

        User driver = userRepository.findById(
                request.getDriverId()
        ).orElseThrow(() ->
                new ResourceNotFoundException(
                        MessageManager.DRIVER_NOT_FOUND
                )
        );

        if (driver.getRole() != Role.ROLE_DRIVER) {
            throw new BadRequestException(
                    MessageManager.NOT_A_DRIVER
            );
        }

        if (deliveryRepository
                .findByOrderId(orderId)
                .isPresent()) {

            throw new BadRequestException(
                    MessageManager.DELIVERY_ALREADY_ASSIGNED
            );
        }

        Delivery delivery = new Delivery();

        delivery.setOrder(order);
        delivery.setDriver(driver);
        delivery.setStatus(DeliveryStatus.ASSIGNED);
        delivery.setAssignedAt(LocalDateTime.now());

        Delivery savedDelivery =
                deliveryRepository.save(delivery);

        return convertToResponse(savedDelivery);
    }

    // Get delivery
    public DeliveryResponse getDelivery(
            Long deliveryId) {

        Delivery delivery =
                deliveryRepository.findById(deliveryId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        MessageManager.DELIVERY_NOT_FOUND
                                )
                        );

        return convertToResponse(delivery);
    }

    // Update delivery status
    public DeliveryResponse updateDeliveryStatus(
            Long deliveryId,
            DeliveryStatusRequest request) {

        Delivery delivery =
                deliveryRepository.findById(deliveryId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        MessageManager.DELIVERY_NOT_FOUND
                                )
                        );

        DeliveryStatus currentStatus =
                delivery.getStatus();

        DeliveryStatus newStatus =
                request.getStatus();

        if (newStatus == null) {
            throw new BadRequestException(
                    MessageManager.INVALID_DELIVERY_STATUS
            );
        }

        if (currentStatus == DeliveryStatus.ASSIGNED
                && newStatus != DeliveryStatus.PICKED_UP) {

            throw new BadRequestException(
                    MessageManager.INVALID_DELIVERY_STATUS
            );
        }

        if (currentStatus == DeliveryStatus.PICKED_UP
                && newStatus != DeliveryStatus.OUT_FOR_DELIVERY) {

            throw new BadRequestException(
                    MessageManager.INVALID_DELIVERY_STATUS
            );
        }

        if (currentStatus == DeliveryStatus.OUT_FOR_DELIVERY
                && newStatus != DeliveryStatus.DELIVERED) {

            throw new BadRequestException(
                    MessageManager.INVALID_DELIVERY_STATUS
            );
        }

        delivery.setStatus(newStatus);

        updateOrderStatus(
                delivery.getOrder(),
                newStatus
        );

        Delivery savedDelivery =
                deliveryRepository.save(delivery);

        return convertToResponse(savedDelivery);
    }

    // Update order according to delivery status
    private void updateOrderStatus(
            Order order,
            DeliveryStatus deliveryStatus) {

        if (deliveryStatus == DeliveryStatus.PICKED_UP) {

            order.setStatus(OrderStatus.PREPARING);

        } else if (deliveryStatus ==
                DeliveryStatus.OUT_FOR_DELIVERY) {

            order.setStatus(OrderStatus.OUT_FOR_DELIVERY);

        } else if (deliveryStatus ==
                DeliveryStatus.DELIVERED) {

            order.setStatus(OrderStatus.DELIVERED);
        }

        orderRepository.save(order);
    }

    private DeliveryResponse convertToResponse(
            Delivery delivery) {

        return new DeliveryResponse(
                delivery.getId(),
                delivery.getOrder().getId(),
                delivery.getDriver().getId(),
                delivery.getDriver().getName(),
                delivery.getStatus(),
                delivery.getAssignedAt()
        );
    }
}