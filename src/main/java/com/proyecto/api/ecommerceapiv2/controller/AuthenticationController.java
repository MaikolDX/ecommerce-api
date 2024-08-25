package com.proyecto.api.ecommerceapiv2.controller;

import com.proyecto.api.ecommerceapiv2.dto.auth.AuthenticationRequest;
import com.proyecto.api.ecommerceapiv2.dto.auth.AuthenticationResponse;
import com.proyecto.api.ecommerceapiv2.persistence.security.User;
import com.proyecto.api.ecommerceapiv2.service.auth.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    AuthenticationService authenticationService;

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validate(@RequestParam String jwt){ //Método de utilería, debe ser simple
        boolean isTokenValid = authenticationService.validateToken(jwt);
        return ResponseEntity.ok(isTokenValid);
    }

    //@CrossOrigin
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> autheticate(@RequestBody @Valid AuthenticationRequest authenticationRequest){
        AuthenticationResponse rsp = authenticationService.login(authenticationRequest);
        return ResponseEntity.ok(rsp);
    }

    @GetMapping("/profile")
    public ResponseEntity<?> findMyProfile(){ //MALA PRÁCTICA, DEVOLVER UN DTO
       return authenticationService.findLoggedInUser();
    }
}