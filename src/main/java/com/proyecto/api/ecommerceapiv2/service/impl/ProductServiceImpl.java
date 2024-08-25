package com.proyecto.api.ecommerceapiv2.service.impl;

import com.proyecto.api.ecommerceapiv2.dto.CategoryDto;
import com.proyecto.api.ecommerceapiv2.dto.ProductDto;
import com.proyecto.api.ecommerceapiv2.exception.ResourceNotFoundException;
import com.proyecto.api.ecommerceapiv2.persistence.entity.Category;
import com.proyecto.api.ecommerceapiv2.persistence.entity.Product;
import com.proyecto.api.ecommerceapiv2.persistence.repository.CategoryRepository;
import com.proyecto.api.ecommerceapiv2.persistence.repository.ProductRepository;
import com.proyecto.api.ecommerceapiv2.persistence.repository.specification.CustomerSpecification;
import com.proyecto.api.ecommerceapiv2.service.ProductService;
import com.proyecto.api.ecommerceapiv2.util.ModelMapperUtils;
import com.proyecto.api.ecommerceapiv2.util.ProjectResponse;
import com.proyecto.api.ecommerceapiv2.util.enums.CategoryStatus;
import com.proyecto.api.ecommerceapiv2.util.enums.ProductStatus;
import jakarta.transaction.Transactional;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public ResponseEntity<?> saveProduct(ProductDto productDto) {
        try {
            if (productRepository.existsByName(productDto.getName())) {
                return new ResponseEntity<>(new ProjectResponse(1, "El producto ya existe", true, new Date(), null), HttpStatus.BAD_REQUEST);
            }else{
                //Probaremos con id y objeto
                Category category = categoryRepository.findById(productDto.getCategoryId())
                        .orElseThrow(() -> new ResourceNotFoundException("category", "id", productDto.getCategoryId()));

                // Mapea el DTO a una entidad y luego se guarda en el repositorio
                Product entity = ModelMapperUtils.mapDTOToEntity(productDto, Product.class);
                entity.setStatus(ProductStatus.DISPONIBLE);
                entity.setCategory(category);
                entity = productRepository.save(entity);

                // Mapea la entidad guardada de nuevo a un DTO
                ProductDto savedDto = ModelMapperUtils.mapEntityToDTO(entity, ProductDto.class);

                return new ResponseEntity<>(new ProjectResponse(0, "Producto creado con éxito", false, new Date(), savedDto), HttpStatus.CREATED);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> updateOneProduct(Long id, ProductDto productDto) {
        try{
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("product", "id", id));

            Category category = categoryRepository.findById(productDto.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("category", "id", productDto.getCategoryId()));

            Product productUpdate = ModelMapperUtils.mapDTOToEntity(productDto, Product.class);

            productUpdate.setId(product.getId());

            productUpdate = productRepository.save(productUpdate);

            ProductDto productDtoUpdate = ModelMapperUtils.mapEntityToDTO(productUpdate, ProductDto.class);

            return new ResponseEntity<>(new ProjectResponse(0, "Producto Actualizado", false, new Date(), productDtoUpdate), HttpStatus.OK);

        }catch (DataAccessException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getAllProducts(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        if (products.hasContent()){
            Page<ProductDto> productsDto = products.map(product -> ModelMapperUtils.mapEntityToDTO(product, ProductDto.class));
            return new ResponseEntity<>(new ProjectResponse(0, "Productos encontrados", false, new Date(), productsDto), HttpStatus.OK);
        }else{
            throw new ResourceNotFoundException("productos");
        }
    }

    @Override
    public ResponseEntity<?> readOneProduct(Long id) {
        try{
            Product product = productRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("product", "id", id));
            return new ResponseEntity<>(new ProjectResponse(
                    0, "Producto encontrado", false, new Date(), ModelMapperUtils.mapEntityToDTO(product, ProductDto.class)), HttpStatus.OK);
        }catch (DataAccessException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> findAllProductsByName(Pageable pageable, String nombre) {
        try{
            Page<ProductDto> products = productRepository.findAllByNameLike(nombre, pageable);

            if (products.isEmpty()){
                throw new ResourceNotFoundException("productos");
            }
            //Page<ProductDto> productsDto = products.map(product -> ModelMapperUtils.mapEntityToDTO(product, ProductDto.class));

            return new ResponseEntity<>(new ProjectResponse(
                    0, "Productos encontrados", false, new Date(), products), HttpStatus.OK);
        }catch (DataAccessException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> findProducts(String name, BigDecimal price, List<Category> categories, Pageable pageable) {
        try{
            //AGREGAR FILTRO POR DISPONIBILIDAD
            Specification<Product> specification = Specification.where(CustomerSpecification.nameContains(name))
                    .and(CustomerSpecification.priceGreaterThan(price))
                    .and(CustomerSpecification.belongsToCategory(categories));

            Page<Product> products = productRepository.findAll(specification, pageable);

            if (products.isEmpty()){
                throw new ResourceNotFoundException("productos");
            }
            Page<ProductDto> productsDto = products.map(product -> ModelMapperUtils.mapEntityToDTO(product, ProductDto.class));

            return new ResponseEntity<>(new ProjectResponse(
                    0, "Productos encontrados", false, new Date(), productsDto), HttpStatus.OK);
        }catch (DataAccessException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> findProducts2(String name, BigDecimal price, List<Category> categories, Pageable pageable) {
        try {
            List<Long> categoryIds = categories.stream().map(Category::getId).collect(Collectors.toList());
            Page<Product> products = productRepository.findProductsWithCategories(name, price, categoryIds, pageable);

            if (products.isEmpty()) {
                throw new ResourceNotFoundException("productos");
            }

            Page<ProductDto> productsDto = products.map(product -> ModelMapperUtils.mapEntityToDTO(product, ProductDto.class));

            return new ResponseEntity<>(new ProjectResponse(
                    0, "Productos encontrados", false, new Date(), productsDto), HttpStatus.OK);
        } catch (DataAccessException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
