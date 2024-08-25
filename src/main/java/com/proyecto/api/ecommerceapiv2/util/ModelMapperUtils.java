package com.proyecto.api.ecommerceapiv2.util;

import org.modelmapper.ModelMapper;

public class ModelMapperUtils {

    private static final ModelMapper modelMapper = new ModelMapper();

    public static <D, T> D mapEntityToDTO(T entity, Class<D> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }

    public static <T, D> T mapDTOToEntity(D dto, Class<T> entityClass) {
        return modelMapper.map(dto, entityClass);
    }

}
