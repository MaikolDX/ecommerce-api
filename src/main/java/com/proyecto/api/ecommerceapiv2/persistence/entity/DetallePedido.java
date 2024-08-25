package com.proyecto.api.ecommerceapiv2.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class DetallePedido {

    @EmbeddedId
    private DetallePedidoPK detallePedidoPK;

    private int cantidad;

    private BigDecimal precioUnitario;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    @MapsId("pedidoId")
    Pedido pedido;

    //En la BD no se crea una relación entre producto y detalle pedido, probemos agregando la relación
    //con producto

    public BigDecimal getSubtotal(){
        if (precioUnitario == null) return BigDecimal.ZERO;
        return precioUnitario.multiply(BigDecimal.valueOf(cantidad));
    }
}
