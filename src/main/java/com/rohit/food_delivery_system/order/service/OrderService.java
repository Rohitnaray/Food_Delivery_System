package com.rohit.food_delivery_system.order.service;

import com.rohit.food_delivery_system.auth.model.User;
import com.rohit.food_delivery_system.auth.repository.UserRepository;
import com.rohit.food_delivery_system.common.exception.BadRequestException;
import com.rohit.food_delivery_system.common.exception.ResourceNotFoundException;
import com.rohit.food_delivery_system.common.response.MessageManager;
import com.rohit.food_delivery_system.order.dto.OrderItemRequest;
import com.rohit.food_delivery_system.order.dto.OrderItemResponse;
import com.rohit.food_delivery_system.order.dto.OrderRequest;
import com.rohit.food_delivery_system.order.dto.OrderResponse;
import com.rohit.food_delivery_system.order.model.Order;
import com.rohit.food_delivery_system.order.model.OrderItem;
import com.rohit.food_delivery_system.order.model.OrderStatus;
import com.rohit.food_delivery_system.order.repository.OrderRepository;
import com.rohit.food_delivery_system.restaurant.model.MenuItem;
import com.rohit.food_delivery_system.restaurant.repository.MenuItemRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final MenuItemRepository menuItemRepository;

    public OrderService(
            OrderRepository orderRepository,
            UserRepository userRepository,
            MenuItemRepository menuItemRepository) {

        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.menuItemRepository = menuItemRepository;
    }

    // Create order
    @Transactional
    public OrderResponse createOrder(
            Long userId,
            OrderRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                MessageManager.USER_NOT_FOUND
                        )
                );

        Order order = new Order();

        order.setUser(user);
        order.setStatus(OrderStatus.PLACED);
        order.setCreatedAt(LocalDateTime.now());
        order.setTotalAmount(0.0);

        double totalAmount = 0.0;

        for (OrderItemRequest itemRequest :
                request.getItems()) {

            MenuItem menuItem =
                    menuItemRepository.findById(
                                    itemRequest.getMenuItemId()
                            )
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            MessageManager.MENU_ITEM_NOT_FOUND
                                    )
                            );

            if (!menuItem.getAvailable()) {
                throw new BadRequestException(
                        MessageManager.MENU_ITEM_NOT_AVAILABLE
                );
            }

            OrderItem orderItem = new OrderItem();

            orderItem.setOrder(order);
            orderItem.setMenuItem(menuItem);

            orderItem.setQuantity(
                    itemRequest.getQuantity()
            );

            double price = menuItem.getPrice();

            orderItem.setPrice(price);

            double subtotal =
                    price * itemRequest.getQuantity();

            orderItem.setSubtotal(subtotal);

            order.getOrderItems().add(orderItem);

            totalAmount += subtotal;
        }

        order.setTotalAmount(totalAmount);

        Order savedOrder =
                orderRepository.save(order);

        return convertToOrderResponse(savedOrder);
    }

    // Get order
    public OrderResponse getOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                MessageManager.ORDER_NOT_FOUND
                        )
                );

        return convertToOrderResponse(order);
    }

    // Get user orders
    public List<OrderResponse> getUserOrders(
            Long userId) {

        userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                MessageManager.USER_NOT_FOUND
                        )
                );

        return orderRepository
                .findByUserId(userId)
                .stream()
                .map(this::convertToOrderResponse)
                .toList();
    }

    private OrderResponse convertToOrderResponse(
            Order order) {

        List<OrderItemResponse> items =
                order.getOrderItems()
                        .stream()
                        .map(item ->
                                new OrderItemResponse(
                                        item.getId(),
                                        item.getMenuItem().getId(),
                                        item.getMenuItem().getName(),
                                        item.getQuantity(),
                                        item.getPrice(),
                                        item.getSubtotal()
                                )
                        )
                        .toList();

        return new OrderResponse(
                order.getId(),
                order.getUser().getId(),
                order.getStatus(),
                order.getTotalAmount(),
                order.getCreatedAt(),
                items
        );
    }
}