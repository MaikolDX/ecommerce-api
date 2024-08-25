package com.proyecto.api.ecommerceapiv2.service;

import com.proyecto.api.ecommerceapiv2.dto.ProductDto;
import com.proyecto.api.ecommerceapiv2.persistence.entity.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    ResponseEntity<?> saveProduct(ProductDto productDto);

    ResponseEntity<?> updateOneProduct(Long id, ProductDto productDto);

    ResponseEntity<?> getAllProducts(Pageable pageable);

    ResponseEntity<?> readOneProduct(Long id);

    ResponseEntity<?> findAllProductsByName(Pageable pageable, String nombre);

    ResponseEntity<?> findProducts(String name, BigDecimal price, List<Category> categories, Pageable pageable);

    ResponseEntity<?> findProducts2(String name, BigDecimal price, List<Category> categories, Pageable pageable);
}
