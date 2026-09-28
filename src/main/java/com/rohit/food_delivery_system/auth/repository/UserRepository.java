package com.rohit.food_delivery_system.auth.repository;

import com.rohit.food_delivery_system.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

}