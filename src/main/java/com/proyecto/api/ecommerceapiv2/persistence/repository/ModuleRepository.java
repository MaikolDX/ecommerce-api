package com.proyecto.api.ecommerceapiv2.persistence.repository;

import com.proyecto.api.ecommerceapiv2.persistence.security.Module;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuleRepository extends JpaRepository<Module, Long> {
}
