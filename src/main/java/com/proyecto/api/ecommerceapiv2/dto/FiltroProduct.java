package com.proyecto.api.ecommerceapiv2.dto;

import com.proyecto.api.ecommerceapiv2.persistence.entity.Category;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class FiltroProduct implements Serializable {
    String name;
    @DecimalMin(value = "0.01")
    BigDecimal price;
    List<Category> categories;

    public FiltroProduct(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }
}
