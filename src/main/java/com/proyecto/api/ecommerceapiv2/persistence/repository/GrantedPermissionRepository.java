package com.proyecto.api.ecommerceapiv2.persistence.repository;

import com.proyecto.api.ecommerceapiv2.persistence.security.GrantedPermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrantedPermissionRepository extends JpaRepository<GrantedPermission, Long> {
}
