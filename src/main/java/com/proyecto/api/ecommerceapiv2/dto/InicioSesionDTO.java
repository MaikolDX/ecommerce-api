package com.proyecto.api.ecommerceapiv2.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InicioSesionDTO implements Serializable {
    private Integer id;
    @NotBlank(message = "Nombre Requerido")
    private String name;

    @NotBlank(message = "Apellido Paterno Requerido")
    private String apepat;

    @NotBlank(message = "Apellido Materno Requerido")
    private String apemat;

    @Email(message = "No tiene formato de correo")
    private String email;

    private byte[] photo;
    private String state;
}
