package com.rohit.food_delivery_system.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponse {

    private Long id;

    private Long menuItemId;

    private String menuItemName;

    private Integer quantity;

    private Double price;

    private Double subtotal;
}