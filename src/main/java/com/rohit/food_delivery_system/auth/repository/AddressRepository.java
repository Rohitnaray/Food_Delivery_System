package com.rohit.food_delivery_system.auth.repository;

import com.rohit.food_delivery_system.auth.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByUserId(Long userId);
}