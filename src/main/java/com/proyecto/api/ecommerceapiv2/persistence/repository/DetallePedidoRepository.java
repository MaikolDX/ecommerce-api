package com.proyecto.api.ecommerceapiv2.persistence.repository;

import com.proyecto.api.ecommerceapiv2.persistence.entity.DetallePedido;
import com.proyecto.api.ecommerceapiv2.persistence.entity.DetallePedidoPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetallePedidoRepository extends JpaRepository<DetallePedido, DetallePedidoPK> {
}
