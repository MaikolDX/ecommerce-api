package com.proyecto.api.ecommerceapiv2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoMostrarDTO implements Serializable {

    private Long id;
    private String userName;
    private String userApePat;
    private String userApeMat;
    private LocalDateTime fechaPedido;
    //private Pedido.OrderStatus status;
    private String pedidoStatus;
    //private InicioSesionDTO usuario;
    private String cuponCodigoCupon;

    private List<DetallePedidoMostrarDTO> detalles;

    private BigDecimal total;

    /*public BigDecimal getTotal(){
        if (detalles == null || detalles.isEmpty()){
            return BigDecimal.ZERO;
        }
        return detalles.stream()
                .map( detalle -> detalle.getSubtotal() )
                .reduce(BigDecimal.ZERO, (current, total) -> total.add(current));
    }*/
}
