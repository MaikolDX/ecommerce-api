package com.proyecto.api.ecommerceapiv2.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.proyecto.api.ecommerceapiv2.persistence.security.User;
import com.proyecto.api.ecommerceapiv2.util.enums.PedidoStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(insertable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime fechaPedido;

    @Enumerated(EnumType.STRING)
    private PedidoStatus pedidoStatus;

    @ManyToOne
    @JoinColumn(name = "cupon_id")
    private Cupon cupon;

    @OneToOne
    @JoinColumn(name = "pago_id")
    private Pago pago;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.PERSIST)
    @JsonIgnore
    private List<DetallePedido> detalles;

    public BigDecimal getTotal(){
        if (detalles == null || detalles.isEmpty()){
            return BigDecimal.ZERO;
        }
        return detalles.stream()
                .map( detalle -> detalle.getSubtotal() )
                .reduce(BigDecimal.ZERO, (current, total) -> total.add(current));
    }
}
