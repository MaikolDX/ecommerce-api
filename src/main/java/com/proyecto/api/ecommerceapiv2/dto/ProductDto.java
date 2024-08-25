package com.proyecto.api.ecommerceapiv2.dto;

import com.proyecto.api.ecommerceapiv2.util.enums.ProductStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto implements Serializable {

    @NotBlank
    private String name;

    private int stock;

    @DecimalMin(value = "0.01")
    private BigDecimal price;

    @Min(value = 1)
    private Long categoryId;

    private ProductStatus status;

    public ProductDto(String name, BigDecimal price) {
        this.name = name;
        this.price = price;
    }

    public ProductDto(String name, int stock, BigDecimal price) {
        this.name = name;
        this.stock = stock;
        this.price = price;
    }

    public ProductDto(String name, int stock, BigDecimal price, ProductStatus status) {
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.status = status;
    }


}
