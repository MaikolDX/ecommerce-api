package com.proyecto.api.ecommerceapiv2.dto.auth;

import com.proyecto.api.ecommerceapiv2.util.enums.auth.RoleEnum;
import com.proyecto.api.ecommerceapiv2.util.enums.auth.RolePermissionEnum;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class RegisteredUser implements Serializable {
    private long id;
    private String username;
    private String name;
    private String role;
    private String jwt;
    private List<String> authorities;
}
