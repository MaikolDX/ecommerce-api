package com.proyecto.api.ecommerceapiv2.persistence.entity;

import com.proyecto.api.ecommerceapiv2.util.enums.CuponStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Cupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigoCupon;

    //Que pueda insertar una fecha, pero que no pueda cambiarla luego
    @Column(insertable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime fechaCreacion;

    private Date fechaExpiracion;

    @Enumerated(EnumType.STRING)
    private CuponStatus status;
}
