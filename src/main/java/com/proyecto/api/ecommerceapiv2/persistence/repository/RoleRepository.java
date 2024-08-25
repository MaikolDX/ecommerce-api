package com.proyecto.api.ecommerceapiv2.persistence.repository;

import com.proyecto.api.ecommerceapiv2.persistence.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
