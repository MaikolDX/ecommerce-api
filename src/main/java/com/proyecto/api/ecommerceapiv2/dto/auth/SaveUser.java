package com.proyecto.api.ecommerceapiv2.dto.auth;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SaveUser implements Serializable {
    @Size(min = 4)
    private String name;
    private String username;
    @Size(min = 8)
    private String password;
    @Size(min = 8)
    private String repeatedPassword;
}
