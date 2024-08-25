package com.proyecto.api.ecommerceapiv2.controller;

import com.proyecto.api.ecommerceapiv2.dto.FiltroProduct;
import com.proyecto.api.ecommerceapiv2.dto.ProductDto;
import com.proyecto.api.ecommerceapiv2.persistence.entity.Product;
import com.proyecto.api.ecommerceapiv2.persistence.repository.ProductRepository;
import com.proyecto.api.ecommerceapiv2.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody @Valid ProductDto productDto){
        return productService.saveProduct(productDto);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<?> updateOneProduct(@PathVariable(name = "id")Long id, @RequestBody @Valid ProductDto productDto){
        return productService.updateOneProduct(id, productDto);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> readOneProduct(@PathVariable(name = "id") Long id){
        return productService.readOneProduct(id);
    }

    @GetMapping
    public ResponseEntity<?> getAllProducts(Pageable pageable){
        return productService.getAllProducts(pageable);
    }

    @GetMapping("/filtrar-por-nombre/{nombre}")
    public ResponseEntity<?> findAllProductsByNameLike(@PathVariable(name = "nombre") String nombre, Pageable pageable){
        return productService.findAllProductsByName(pageable, nombre);
    }

    @GetMapping("find")
    public ResponseEntity<?>findProducts(@RequestBody FiltroProduct filtroProduct, Pageable pageable){
        return productService.findProducts(filtroProduct.getName(), filtroProduct.getPrice(), filtroProduct.getCategories(), pageable);
    }


    @GetMapping("find2")
    public ResponseEntity<?>findProducts2(@RequestBody FiltroProduct filtroProduct, Pageable pageable){
        return productService.findProducts2(filtroProduct.getName(), filtroProduct.getPrice(), filtroProduct.getCategories(), pageable);
    }

    @GetMapping("find3")
    public ResponseEntity<?>findProducts3(@Valid @RequestBody FiltroProduct filtroProduct, Pageable pageable){
        Page<Product> products = productRepository.findAllByNameLikeAndPriceGreaterThan(filtroProduct.getName(), filtroProduct.getPrice(), pageable);
        return ResponseEntity.ok(products);
    }

}
