package com.proyecto.api.ecommerceapiv2.service;

import com.proyecto.api.ecommerceapiv2.dto.auth.SaveUser;
import com.proyecto.api.ecommerceapiv2.persistence.security.User;

import java.util.Optional;

public interface UserService {
    User registerOneCustomer(SaveUser newUser);
    Optional<User> findOneByUsername(String username);
}
