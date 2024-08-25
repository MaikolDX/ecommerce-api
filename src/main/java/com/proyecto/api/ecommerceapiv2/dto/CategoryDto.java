package com.proyecto.api.ecommerceapiv2.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
@Getter
@Setter
public class CategoryDto implements Serializable {
    private Long id;

    @NotBlank(message = "Es obligatorio el nombre de la categoría")
    private String name;

    private String categoryStatus;

    //@JsonIgnoreProperties({"id", "categoryStatus", "categoryPadre" ,"hibernateLazyInitializer"})
    //private CategoryDto categoryPadre;
    private Long categoryPadreId;
    //private Long categoryPadre;
}
