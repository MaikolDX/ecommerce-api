package com.proyecto.api.ecommerceapiv2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductosPedidoDTO {
    private Long id;
    private int cantidad;
}
