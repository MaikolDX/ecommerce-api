package com.proyecto.api.ecommerceapiv2.persistence.repository;

import com.proyecto.api.ecommerceapiv2.persistence.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
