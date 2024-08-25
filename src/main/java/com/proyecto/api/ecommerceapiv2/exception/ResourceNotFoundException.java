package com.proyecto.api.ecommerceapiv2.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{

    private String resourceName; //Nombre del recurso: "Cliente"
    private String fieldName; //Nombre del campo o columna: "id"
    private Object fieldValue; //Valor del ID por ejemplo: 5


    /*
        TODO -  En el constructor, super se utiliza para llamar al constructor de la clase base (RuntimeException en este caso)
     */
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s no fue encontrado con: %s = '%s'", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
    public ResourceNotFoundException(String resourceName) {
        super(String.format("No hay registros de %s en el sistema ", resourceName));
        this.resourceName = resourceName;
    }

}