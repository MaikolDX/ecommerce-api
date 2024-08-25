package com.proyecto.api.ecommerceapiv2.persistence.repository;

import com.proyecto.api.ecommerceapiv2.persistence.entity.Pedido;
import com.proyecto.api.ecommerceapiv2.util.enums.PedidoStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Pedido p SET p.pedidoStatus = :estado WHERE p.id = :id")
    int actalizarEstado(@Param("id") Long id, @Param("estado") PedidoStatus estado);

}
