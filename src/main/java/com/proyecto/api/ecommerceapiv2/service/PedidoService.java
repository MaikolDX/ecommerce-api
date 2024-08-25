package com.proyecto.api.ecommerceapiv2.service;

import com.proyecto.api.ecommerceapiv2.dto.PedidoDTO;
import com.proyecto.api.ecommerceapiv2.dto.ProductosPedidoDTO;
import com.proyecto.api.ecommerceapiv2.util.enums.PedidoStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PedidoService {
    ResponseEntity<?> crearPedido(Long usuarioId, List<ProductosPedidoDTO> productosPedidoDTOS, PedidoDTO pedidoDTO);

    ResponseEntity<?> editarPedido(Long pedidoId, String estado);


    ResponseEntity<?> updateOneProduct2(Long pedidoId, PedidoStatus estado);
}
