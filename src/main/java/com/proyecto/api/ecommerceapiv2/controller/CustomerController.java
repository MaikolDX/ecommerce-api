package com.proyecto.api.ecommerceapiv2.controller;

import com.proyecto.api.ecommerceapiv2.dto.auth.SaveUser;
import com.proyecto.api.ecommerceapiv2.service.auth.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<?> registerOne(@RequestBody @Valid SaveUser newUser){
        return authenticationService.registerOneCustomer(newUser);
    }

}
