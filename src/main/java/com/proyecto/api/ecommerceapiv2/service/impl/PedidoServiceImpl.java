package com.proyecto.api.ecommerceapiv2.service.impl;

import com.proyecto.api.ecommerceapiv2.dto.DetallePedidoMostrarDTO;
import com.proyecto.api.ecommerceapiv2.dto.PedidoDTO;
import com.proyecto.api.ecommerceapiv2.dto.PedidoMostrarDTO;
import com.proyecto.api.ecommerceapiv2.dto.ProductosPedidoDTO;
import com.proyecto.api.ecommerceapiv2.exception.ResourceNotFoundException;
import com.proyecto.api.ecommerceapiv2.persistence.entity.DetallePedido;
import com.proyecto.api.ecommerceapiv2.persistence.entity.DetallePedidoPK;
import com.proyecto.api.ecommerceapiv2.persistence.entity.Pedido;
import com.proyecto.api.ecommerceapiv2.persistence.entity.Product;
import com.proyecto.api.ecommerceapiv2.persistence.repository.PedidoRepository;
import com.proyecto.api.ecommerceapiv2.persistence.repository.ProductRepository;
import com.proyecto.api.ecommerceapiv2.persistence.repository.UserRepository;
import com.proyecto.api.ecommerceapiv2.persistence.security.User;
import com.proyecto.api.ecommerceapiv2.service.PedidoService;
import com.proyecto.api.ecommerceapiv2.util.ModelMapperUtils;
import com.proyecto.api.ecommerceapiv2.util.ProjectResponse;
import com.proyecto.api.ecommerceapiv2.util.enums.PedidoStatus;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Override
    public ResponseEntity<?> crearPedido(Long usuarioId, List<ProductosPedidoDTO> productosPedidoDTOS, PedidoDTO pedidoDTO) {
        try {
            Optional<User> usuario = userRepository.findById(usuarioId);

            if (usuario.isPresent()) {
                //Ordenar IDS para agregar
                User usuarioValidado = usuario.get();

                Pedido pedido = ModelMapperUtils.mapDTOToEntity(pedidoDTO, Pedido.class);
                pedido.setUser(usuarioValidado);

                List<Long> idsProductos = productosPedidoDTOS.stream()
                        .map(ProductosPedidoDTO::getId)
                        .toList();

                List<Product> productosBD = productRepository.findAllById(idsProductos);

                // Crear un map para acceder rápidamente a ProductosPedidoDTO por id de producto
                Map<Long, ProductosPedidoDTO> productosPedidoMap = productosPedidoDTOS.stream()
                        .collect(Collectors.toMap(ProductosPedidoDTO::getId, producto -> producto));

                //producto -> producto porque no se mapea el objeto, simplemente se asigna el mismo

                // Crear los DetallePedido
                List<DetallePedido> detallePedidos = productosBD.stream()
                        .map(productoBD -> {
                            ProductosPedidoDTO productoPedidoDTO = productosPedidoMap.get(productoBD.getId());
                            if (productoPedidoDTO != null) {
                                DetallePedidoPK detallePedidoPK = new DetallePedidoPK(productoBD.getId());
                                return new DetallePedido(detallePedidoPK, productoPedidoDTO.getCantidad(), productoBD.getPrice(), pedido);
                            }
                            return null;
                        })
                        .filter(Objects::nonNull)
                        .toList();

                // Validar la cantidad de productos basándose en el id
                for (DetallePedido detalle : detallePedidos) {
                    if (detalle.getCantidad() > 100) { // supongamos que 100 es el límite
                        return new ResponseEntity<>(new ProjectResponse(1, "Cantidad excede el límite permitido", true, new Date(), null), HttpStatus.BAD_REQUEST);
                    }
                }
                pedido.setDetalles(detallePedidos);
                Pedido pedidoNuevo = pedidoRepository.save(pedido);

                //Sacar nombre de los pedidos de los productos y agregarlos

                //PedidoDTO pedidoGuardado = ModelMapperUtils.mapEntityToDTO(pedidoNuevo, PedidoDTO.class);
                PedidoMostrarDTO pedidoGuardado = ModelMapperUtils.mapEntityToDTO(pedidoNuevo, PedidoMostrarDTO.class);

                pedidoGuardado.setDetalles(pedidoNuevo.getDetalles().stream()
                        .map(detallePedido -> new DetallePedidoMostrarDTO(
                                detallePedido.getDetallePedidoPK().getProductId(), detallePedido.getCantidad(), null,
                                detallePedido.getPrecioUnitario(), detallePedido.getSubtotal())
                        ).collect(Collectors.toList()));

                pedidoGuardado.getDetalles().forEach(detallePedido -> productosBD.stream()
                        .filter(product -> product.getId().equals(detallePedido.getProductId()))
                        .findFirst()
                        .ifPresent(product -> detallePedido.setName(product.getName())));

                return new ResponseEntity<>(new ProjectResponse(0, "Pedido Agregado", false, new Date(), pedidoGuardado), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ProjectResponse(1, "Usuario no encontrado", true, new Date(), null), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            // Lanzar una excepción o devolver un error genérico al cliente según sea necesario
            return new ResponseEntity<>(new ProjectResponse(1, "Error interno del servidor - Descripción del error", true, new Date(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> editarPedido(Long pedidoId, String estado) {
        try{
            Pedido pedido = pedidoRepository.findById(pedidoId).orElseThrow(()-> new ResourceNotFoundException("pedido", "id", pedidoId));

            if (estado.equalsIgnoreCase("cancelado")){
                pedido.setPedidoStatus(PedidoStatus.CANCELADO);
            }else if(estado.equalsIgnoreCase("en_proceso")){
                pedido.setPedidoStatus(PedidoStatus.EN_PROCESO);
            }else if (estado.equalsIgnoreCase("completado")){
                pedido.setPedidoStatus(PedidoStatus.COMPLETADO);
            }else{
                return new ResponseEntity<>(new ProjectResponse(1, "Error de estado de pedido", false, new Date(), null), HttpStatus.BAD_REQUEST);
            }

            //CREAR QUERY PERSONALIZADO PARA EDITAR EL STATUS
            //FILTROS CON ALGUNA CLAVE, IGUALMENTE CON QUERY PERSONALIZADO
            // pedidoMostrarDTO = ModelMapperUtils.mapEntityToDTO(pedidoRepository.save(pedido), PedidoMostrarDTO.class);
           pedidoRepository.save(pedido);

            return new ResponseEntity<>(new ProjectResponse(0, "Estado de pedido actualizado", false, new Date(), null), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new ProjectResponse(1, "Error interno del servidor - Descripción del error", true, new Date(), null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Override
    public ResponseEntity<?> updateOneProduct2 (Long id, PedidoStatus nuevoEstado){

        int updateRows = pedidoRepository.actalizarEstado(id, nuevoEstado);

        if (updateRows == 0){
            throw new ResourceNotFoundException("pedido", "id", id);
        }
        return new ResponseEntity<>(new ProjectResponse(0, "Estado de pedido actualizado", false, new Date(), null), HttpStatus.OK);
    }
}
