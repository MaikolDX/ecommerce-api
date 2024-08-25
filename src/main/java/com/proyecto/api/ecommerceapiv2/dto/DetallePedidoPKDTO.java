package com.proyecto.api.ecommerceapiv2.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class DetallePedidoPKDTO implements Serializable {
    private Long pedidoId;
    private Long productId;
}
