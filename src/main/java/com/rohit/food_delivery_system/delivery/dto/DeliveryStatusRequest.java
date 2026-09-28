package com.rohit.food_delivery_system.delivery.dto;

import com.rohit.food_delivery_system.delivery.model.DeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryStatusRequest {

    private DeliveryStatus status;
}