package com.rohit.food_delivery_system.auth.service;

import com.rohit.food_delivery_system.auth.dto.AddressRequest;
import com.rohit.food_delivery_system.auth.dto.AddressResponse;
import com.rohit.food_delivery_system.auth.dto.RegisterRequest;
import com.rohit.food_delivery_system.auth.dto.UserResponse;
import com.rohit.food_delivery_system.auth.model.Address;
import com.rohit.food_delivery_system.auth.model.User;
import com.rohit.food_delivery_system.auth.repository.AddressRepository;
import com.rohit.food_delivery_system.auth.repository.UserRepository;
import com.rohit.food_delivery_system.common.exception.ResourceNotFoundException;
import com.rohit.food_delivery_system.common.response.MessageManager;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    public AuthService(
            UserRepository userRepository,
            AddressRepository addressRepository) {

        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    // Register user
    public UserResponse register(RegisterRequest request) {

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());

        User savedUser = userRepository.save(user);

        return new UserResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getPhone(),
                savedUser.getRole()
        );
    }

    // Get user
    public UserResponse getUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                MessageManager.USER_NOT_FOUND
                        )
                );

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getRole()
        );
    }

    // Add address
    public AddressResponse addAddress(
            Long userId,
            AddressRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                MessageManager.USER_NOT_FOUND
                        )
                );

        Address address = new Address();

        address.setStreet(request.getStreet());
        address.setCity(request.getCity());
        address.setZipCode(request.getZipCode());
        address.setLatitude(request.getLatitude());
        address.setLongitude(request.getLongitude());

        address.setUser(user);

        Address savedAddress =
                addressRepository.save(address);

        return new AddressResponse(
                savedAddress.getId(),
                savedAddress.getStreet(),
                savedAddress.getCity(),
                savedAddress.getZipCode(),
                savedAddress.getLatitude(),
                savedAddress.getLongitude()
        );
    }

    // Get user addresses
    public List<AddressResponse> getUserAddresses(
            Long userId) {

        userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                MessageManager.USER_NOT_FOUND
                        )
                );

        return addressRepository
                .findByUserId(userId)
                .stream()
                .map(address -> new AddressResponse(
                        address.getId(),
                        address.getStreet(),
                        address.getCity(),
                        address.getZipCode(),
                        address.getLatitude(),
                        address.getLongitude()
                ))
                .toList();
    }
}