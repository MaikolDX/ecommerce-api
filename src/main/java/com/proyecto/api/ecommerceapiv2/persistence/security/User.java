package com.proyecto.api.ecommerceapiv2.persistence.security;

import com.proyecto.api.ecommerceapiv2.util.enums.UserStatus;
import com.proyecto.api.ecommerceapiv2.util.enums.auth.RoleEnum;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "\"user\"")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String apePat;

    private String apeMat;

    @Column(unique = true)
    private String username;

    private String password;

    private String urlPhoto;

    private String email;

    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private UserStatus state;

    private Boolean emailVerify;

    /*@ManyToOne
    @JoinColumn(name = "role_id")*/
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (role == null) return null;
        if (role.getPermissions() == null) return null;

        List<SimpleGrantedAuthority> authorities = role.getPermissions().stream()
                .map(each -> each.name())
                .map(each  -> new SimpleGrantedAuthority(each))
                .collect(Collectors.toList());

        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.role));

        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
