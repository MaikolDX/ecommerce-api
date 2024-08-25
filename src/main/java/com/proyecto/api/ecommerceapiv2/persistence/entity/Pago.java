package com.proyecto.api.ecommerceapiv2.persistence.entity;

import com.proyecto.api.ecommerceapiv2.util.enums.PagoStatus;
import com.proyecto.api.ecommerceapiv2.util.enums.TipoPago;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PagoStatus status;

    @Enumerated(EnumType.STRING)
    private TipoPago type;
}
