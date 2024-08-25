package com.proyecto.api.ecommerceapiv2.dto;

import com.proyecto.api.ecommerceapiv2.persistence.entity.DetallePedidoPK;
import jakarta.persistence.EmbeddedId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetallePedidoMostrarDTO implements Serializable {

    //private Long pedidoId;
    private Long productId;

    private int cantidad;
    private String name;
    private BigDecimal precioUnitario;

    private BigDecimal subTotal;

    //private Pedido pedido;

    //TODO - Observado para luego
    /*public BigDecimal getSubtotal(){
        if (precioUnitario == null) return BigDecimal.ZERO;

        return precioUnitario.multiply(BigDecimal.valueOf(cantidad));
    }*/
}
