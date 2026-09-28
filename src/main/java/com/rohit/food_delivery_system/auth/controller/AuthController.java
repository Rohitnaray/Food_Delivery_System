package com.rohit.food_delivery_system.auth.controller;

import com.rohit.food_delivery_system.auth.dto.AddressRequest;
import com.rohit.food_delivery_system.auth.dto.AddressResponse;
import com.rohit.food_delivery_system.auth.dto.RegisterRequest;
import com.rohit.food_delivery_system.auth.dto.UserResponse;
import com.rohit.food_delivery_system.auth.service.AuthService;
import com.rohit.food_delivery_system.common.response.ApiResponse;
import com.rohit.food_delivery_system.common.response.MessageManager;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // REGISTER USER

    @PostMapping("/auth/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<UserResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        UserResponse userResponse =
                authService.register(request);

        return new ApiResponse<>(
                HttpStatus.CREATED.value(),
                MessageManager.USER_CREATED,
                userResponse
        );
    }

    // GET USER

    @GetMapping("/users/{userId}")
    public ApiResponse<UserResponse> getUser(
            @PathVariable Long userId) {

        UserResponse userResponse =
                authService.getUser(userId);

        return new ApiResponse<>(
                HttpStatus.OK.value(),
                MessageManager.SUCCESS,
                userResponse
        );
    }

    // ADD ADDRESS

    @PostMapping("/users/{userId}/addresses")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<AddressResponse> addAddress(
            @PathVariable Long userId,
            @RequestBody AddressRequest request) {

        AddressResponse addressResponse =
                authService.addAddress(
                        userId,
                        request
                );

        return new ApiResponse<>(
                HttpStatus.CREATED.value(),
                MessageManager.ADDRESS_CREATED,
                addressResponse
        );
    }

    // GET USER ADDRESSES

    @GetMapping("/users/{userId}/addresses")
    public ApiResponse<List<AddressResponse>> getUserAddresses(
            @PathVariable Long userId) {

        List<AddressResponse> addresses =
                authService.getUserAddresses(userId);

        return new ApiResponse<>(
                HttpStatus.OK.value(),
                MessageManager.SUCCESS,
                addresses
        );
    }
}