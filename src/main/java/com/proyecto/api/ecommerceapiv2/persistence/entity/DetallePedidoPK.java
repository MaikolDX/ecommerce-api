package com.proyecto.api.ecommerceapiv2.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@Embeddable
public class DetallePedidoPK implements Serializable {

    @Column(name = "pedido_id")
    private Long pedidoId;

    @Column(name = "product_id")
    private Long productId;

    public DetallePedidoPK(Long productId) {
        this.productId = productId;
    }

    public DetallePedidoPK() {
    }

    public DetallePedidoPK(Long pedidoId, Long productId) {
        this.pedidoId = pedidoId;
        this.productId = productId;
    }
}
