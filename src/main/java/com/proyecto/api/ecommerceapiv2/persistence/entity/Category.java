package com.proyecto.api.ecommerceapiv2.persistence.entity;

import com.proyecto.api.ecommerceapiv2.util.enums.CategoryStatus;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private CategoryStatus categoryStatus;

    @ManyToOne
    @JoinColumn(name = "category_padre")
    private Category categoryPadre;

    public Category(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
