package com.proyecto.api.ecommerceapiv2.service.impl;

import com.proyecto.api.ecommerceapiv2.dto.auth.SaveUser;
import com.proyecto.api.ecommerceapiv2.exception.InvalidPasswordException;
import com.proyecto.api.ecommerceapiv2.persistence.repository.UserRepository;
import com.proyecto.api.ecommerceapiv2.persistence.security.User;
import com.proyecto.api.ecommerceapiv2.service.UserService;
import com.proyecto.api.ecommerceapiv2.util.enums.auth.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    //ACÁ HUBIESEMOS PODIDO IMPLEMENTAR LA CLASE USERDETAILS SERVICE PARA TRABAJAR el método loadByUser... Pero
    //ya lo hicimos en el SecurityBeansInjector porque querenmos usar nuestra propia nomenclatura.


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User registerOneCustomer(SaveUser newUser) {

        validatePassword(newUser);

        //podríamos mapear con DTO directamente
        User user = new User();
        user.setName(newUser.getName());
        user.setUsername(newUser.getUsername());
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        user.setRole(RoleEnum.CUSTOMER); //ROL POR DEFECTO para enums

        //Para trabajar con roles desde la base de datos - asignar un role por default
        /*Role defaultRole = roleService.findDefaultRol()
                .orElseThrow( () -> new ObjectNotFoundException("Role not found. Default Role"));
        user.setRol(defaultRole);*/

        return userRepository.save(user);
    }

    @Override
    public Optional<User> findOneByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    private void validatePassword(SaveUser newUser) {
        if (!StringUtils.hasText(newUser.getPassword()) || !StringUtils.hasText(newUser.getRepeatedPassword())){
            throw new InvalidPasswordException("Passwords don't match");
        }

        if (!newUser.getPassword().equals(newUser.getRepeatedPassword())){
            throw new InvalidPasswordException("Passwords don't match");
        }
    }
}
