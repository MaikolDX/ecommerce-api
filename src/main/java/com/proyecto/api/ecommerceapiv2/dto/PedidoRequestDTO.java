package com.proyecto.api.ecommerceapiv2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoRequestDTO {
    private PedidoDTO pedidoDTO;
    private List<ProductosPedidoDTO> productosPedidoDTOS;
}
