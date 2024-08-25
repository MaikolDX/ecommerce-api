package com.proyecto.api.ecommerceapiv2.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.proyecto.api.ecommerceapiv2.util.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private int stock;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    private BigDecimal price;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", stock=" + stock +
                ", status=" + status +
                ", price=" + price +
                '}';
    }
}
