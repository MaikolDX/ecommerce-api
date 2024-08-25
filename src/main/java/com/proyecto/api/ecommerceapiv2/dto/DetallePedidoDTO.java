package com.proyecto.api.ecommerceapiv2.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.proyecto.api.ecommerceapiv2.persistence.entity.DetallePedidoPK;
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
public class DetallePedidoDTO implements Serializable {

    private DetallePedidoPK id;

    private int cantidad;
    private BigDecimal precioUnitario;
    //private Pedido pedido;

    //TODO - Observado para luego
    public BigDecimal getSubtotal(){
        if (precioUnitario == null) return BigDecimal.ZERO;

        return precioUnitario.multiply(BigDecimal.valueOf(cantidad));
    }
}
