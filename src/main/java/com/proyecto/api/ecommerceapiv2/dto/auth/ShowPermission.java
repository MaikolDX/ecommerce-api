package com.proyecto.api.ecommerceapiv2.dto.auth;

import lombok.Data;

import java.io.Serializable;

@Data
public class ShowPermission implements Serializable {
    private long id;
    private String operation;
    private String module;
    private String rol;
}
