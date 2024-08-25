package com.proyecto.api.ecommerceapiv2.controller;

import com.proyecto.api.ecommerceapiv2.service.auth.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    public AuthenticationService authenticationService;

    @GetMapping("/{username}")
    public ResponseEntity<?> profile(@PathVariable(name = "username") String username){
        System.out.println(username);
        return authenticationService.findLoggedByUsername(username);
    }
}