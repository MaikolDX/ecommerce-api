package com.proyecto.api.ecommerceapiv2.controller;
import com.proyecto.api.ecommerceapiv2.dto.CategoryDto;
import com.proyecto.api.ecommerceapiv2.dto.SaveCategory;
import com.proyecto.api.ecommerceapiv2.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.cdi.Eager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody @Valid SaveCategory category){
        return categoryService.createCategory(category);
    }

    @GetMapping
    public ResponseEntity<?> readAllCategories(Pageable pageable){
        return categoryService.readAllCategories(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> readOneCategory(@PathVariable(name = "id") Long id){
        return categoryService.readOneCategory(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOneCategory(@PathVariable(name = "id")Long id, @RequestBody @Valid CategoryDto categoryDto){
        return categoryService.updateCategory(id, categoryDto);
    }

    @PutMapping("/{id}/disable")
    public ResponseEntity<?> disableOneCategoty(@PathVariable(name = "id") Long id){
        return categoryService.disableOneCategory(id);
    }

}
