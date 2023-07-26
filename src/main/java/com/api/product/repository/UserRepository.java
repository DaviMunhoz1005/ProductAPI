package com.api.product.repository;

import com.api.product.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User, Long> {

    User findByUsername(String username);
}
