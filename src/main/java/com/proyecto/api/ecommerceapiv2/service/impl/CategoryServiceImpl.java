package com.proyecto.api.ecommerceapiv2.service.impl;

import com.proyecto.api.ecommerceapiv2.dto.CategoryDto;
import com.proyecto.api.ecommerceapiv2.dto.SaveCategory;
import com.proyecto.api.ecommerceapiv2.persistence.entity.Category;
import com.proyecto.api.ecommerceapiv2.persistence.repository.CategoryRepository;
import com.proyecto.api.ecommerceapiv2.service.CategoryService;
import com.proyecto.api.ecommerceapiv2.util.ModelMapperUtils;
import com.proyecto.api.ecommerceapiv2.util.ProjectResponse;
import com.proyecto.api.ecommerceapiv2.util.enums.CategoryStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ResponseEntity<?> createCategory(SaveCategory category) {
        try {
            if (categoryRepository.existsByName(category.getName())) {
                return new ResponseEntity<>(new ProjectResponse(1, "La categoría ya existe", true, new Date(), null), HttpStatus.BAD_REQUEST);
            }else{
                // Mapea el DTO a una entidad y luego se guarda en el repositorio
                Category entity = ModelMapperUtils.mapDTOToEntity(category, Category.class);
                entity.setCategoryStatus(CategoryStatus.ACTIVA);
                entity = categoryRepository.save(entity);

                // Mapea la entidad guardada de nuevo a un DTO
                CategoryDto savedDto = ModelMapperUtils.mapEntityToDTO(entity, CategoryDto.class);

                return new ResponseEntity<>(new ProjectResponse(0, "Categoría creada con éxito", false, new Date(), savedDto), HttpStatus.CREATED);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> readAllCategories(Pageable pageable) {
        try{
            Page<Category> categoryPage = categoryRepository.findAll(pageable);

            if (categoryPage.hasContent()){
                Page<CategoryDto> categoryDtos = categoryPage.map(category -> ModelMapperUtils.mapEntityToDTO(category, CategoryDto.class));
                return new ResponseEntity<>(new ProjectResponse(0, "Lista de Categorías", false, new Date(), categoryDtos), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new ProjectResponse(1, "No existen registros", true, new Date(), null), HttpStatus.NOT_FOUND);
                //puede ser directamente una excepción personalizada
            }
        }catch (DataAccessException exception){
            throw new RuntimeException(exception.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> readOneCategory(Long id){
        try{
            Optional<Category> category = categoryRepository.findById(id);

            if (category.isPresent()){
                return new ResponseEntity<>(new ProjectResponse(0, "Categoría encontrada", false, new Date(),
                        ModelMapperUtils.mapEntityToDTO(category.get(), CategoryDto.class)), HttpStatus.OK);
            }

            return new ResponseEntity<>(new ProjectResponse(1, "Categoría no encontrada", true, new Date(), null), HttpStatus.NOT_FOUND);

        }catch (DataAccessException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> updateCategory(Long id, CategoryDto categoryDto) {
        try {
            Optional<Category> category = categoryRepository.findById(id);

            if (category.isPresent()){
                categoryDto.setId(category.get().getId());

                Category categoryBD = categoryRepository.save(ModelMapperUtils.mapDTOToEntity(categoryDto, Category.class));

                return new ResponseEntity<>(new ProjectResponse(0, "Categoría actualizada", false, new Date(),
                        ModelMapperUtils.mapEntityToDTO(categoryBD, CategoryDto.class)), HttpStatus.OK);

            }
            return new ResponseEntity<>(new ProjectResponse(1, "Categoría no encontrada", true, new Date(), null), HttpStatus.NOT_FOUND);

        }catch (DataAccessException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> disableOneCategory(Long id) {
        try{
            Optional<Category> category = categoryRepository.findById(id);

            if (category.isPresent()){
                category.get().setCategoryStatus(CategoryStatus.DESHABILITADA);

                Category categoryBD = categoryRepository.save(category.get());

                return new ResponseEntity<>(new ProjectResponse(0, "Categoría Deshabilitada", false, new Date(), ModelMapperUtils.mapEntityToDTO(categoryBD, CategoryDto.class) ), HttpStatus.OK);

            }
            return new ResponseEntity<>(new ProjectResponse(1, "Categoría no encontrada", true, new Date(), null), HttpStatus.NOT_FOUND);
        }catch (DataAccessException e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
