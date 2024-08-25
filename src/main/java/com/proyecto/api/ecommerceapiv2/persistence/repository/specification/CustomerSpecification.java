package com.proyecto.api.ecommerceapiv2.persistence.repository.specification;

import com.proyecto.api.ecommerceapiv2.persistence.entity.Category;
import com.proyecto.api.ecommerceapiv2.persistence.entity.Product;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class CustomerSpecification {

    //AGREGAR FILTRO POR DISPONIBILIDAD
    public static Specification<Product> belongsToCategory(List<Category> categories) {
        return (root, query, criteriaBuilder) -> {
            if (categories == null || categories.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return root.get("category").in(categories);
        };
    }

    public static Specification<Product> priceGreaterThan(BigDecimal price) {
        return (root, query, criteriaBuilder) -> {
            if (price == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.greaterThanOrEqualTo(root.get("price"), price);
        };
    }

    public static Specification<Product> nameContains(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null || name.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        };
    }

    public static Specification<Product> expired() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThan(root.get("expirationDate"), LocalDate.now());
    }
}
