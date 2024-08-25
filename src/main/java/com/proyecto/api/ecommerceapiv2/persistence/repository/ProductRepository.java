package com.proyecto.api.ecommerceapiv2.persistence.repository;

import com.proyecto.api.ecommerceapiv2.dto.ProductDto;
import com.proyecto.api.ecommerceapiv2.persistence.entity.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    boolean existsByName(String name);

    //Cuando quieres traer campos específicos podemos usar este enfoque con DTOs,
    @Query("SELECT new com.proyecto.api.ecommerceapiv2.dto.ProductDto(p.name, p.stock, p.price, p.category.id, p.status) FROM Product p WHERE p.name LIKE %:name%")
    Page<ProductDto> findAllByNameLike(@Param("name") String name, Pageable pageable);

    //Otra opción, pero trae todos los campos y luego la podemos convertir a DTO para devolverla
    @Transactional
    Page<Product> findAllByNameLikeAndPriceGreaterThan(String name, BigDecimal bigDecimal, Pageable pageable);

    //Otra OPCION para traer solo los datos que queremos y que no se realice otra consulta por categoría padre
    @Query("SELECT p FROM Product p JOIN FETCH p.category WHERE lower(p.name) LIKE lower(concat('%', :name, '%')) AND p.price >= :price AND p.category.id IN :categoryIds")
    Page<Product> findProductsWithCategories(@Param("name") String name, @Param("price") BigDecimal price, @Param("categoryIds") List<Long> categoryIds, Pageable pageable);



}
