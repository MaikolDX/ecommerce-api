package com.proyecto.api.ecommerceapiv2.service;

import com.proyecto.api.ecommerceapiv2.dto.CategoryDto;
import com.proyecto.api.ecommerceapiv2.dto.SaveCategory;
import org.springframework.http.ResponseEntity;


public interface CategoryService {

    ResponseEntity<?> createCategory(SaveCategory category);

    ResponseEntity<?> readAllCategories(org.springframework.data.domain.Pageable pageable);

    ResponseEntity<?> readOneCategory(Long id);

    ResponseEntity<?> updateCategory(Long id, CategoryDto categoryDto);

    ResponseEntity<?> disableOneCategory(Long id);
}
