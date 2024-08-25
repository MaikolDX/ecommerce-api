package com.proyecto.api.ecommerceapiv2.controller;

import com.proyecto.api.ecommerceapiv2.dto.PedidoDTO;
import com.proyecto.api.ecommerceapiv2.dto.PedidoRequestDTO;
import com.proyecto.api.ecommerceapiv2.dto.ProductosPedidoDTO;
import com.proyecto.api.ecommerceapiv2.service.PedidoService;
import com.proyecto.api.ecommerceapiv2.util.enums.PedidoStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping("/{usuarioId}/crear-pedido")
    private ResponseEntity<?> crearPedido(@PathVariable Long usuarioId,
                                          @RequestBody PedidoRequestDTO pedidoRequest) {
        PedidoDTO pedidoDTO = pedidoRequest.getPedidoDTO();
        List<ProductosPedidoDTO> productosPedidoDTOS = pedidoRequest.getProductosPedidoDTOS();
        return pedidoService.crearPedido(usuarioId, productosPedidoDTOS, pedidoDTO);
    }

    @PutMapping("/{pedidoId}/editar-pedido/{estado}")
    private ResponseEntity<?> editarEstadoPedido(@PathVariable(name = "pedidoId") Long pedidoId, @PathVariable(name = "estado") String estado){
        return pedidoService.editarPedido(pedidoId, estado);
    }

    @PutMapping("/{pedidoId}/editar-pedido2/{estado}")
    private ResponseEntity<?> editarEstadoPedido2(@PathVariable(name = "pedidoId") Long pedidoId, @PathVariable(name = "estado") PedidoStatus estado){
        return pedidoService.updateOneProduct2(pedidoId, estado);
    }
}
