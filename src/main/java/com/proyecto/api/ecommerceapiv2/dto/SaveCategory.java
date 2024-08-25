package com.proyecto.api.ecommerceapiv2.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SaveCategory implements Serializable {
    @NotBlank
    private String name;
}
