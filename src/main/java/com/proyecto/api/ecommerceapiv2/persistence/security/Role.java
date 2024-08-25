package com.proyecto.api.ecommerceapiv2.persistence.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.proyecto.api.ecommerceapiv2.util.enums.auth.RoleEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoleEnum name;

    @JsonIgnore
    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    List<GrantedPermission> grantedPermissions;

}
