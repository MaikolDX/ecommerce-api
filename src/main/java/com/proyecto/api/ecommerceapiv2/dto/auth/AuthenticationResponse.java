package com.proyecto.api.ecommerceapiv2.dto.auth;

import lombok.Data;

import java.io.Serializable;
@Data
public class AuthenticationResponse implements Serializable {
    private String jwt;
}
