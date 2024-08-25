package com.proyecto.api.ecommerceapiv2.config.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.proyecto.api.ecommerceapiv2.util.ApiError;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ApiError error = new ApiError();
        error.setBackendMessage(authException.getMessage());
        error.setUrl(request.getRequestURL().toString());
        error.setMethod(request.getMethod());
        error.setMessage("No se encontraron credenciales de autenticación." +
                " Por favor, inicia sesión para acceder a esta función");
        error.setTimestamp(LocalDateTime.now());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        //REVISAR ESTO, usaremos la DEPENDENCIA en el pom jackson-datatype - JSR310 para poder trabajar la respuesta en formato Json
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String apiErrorAsJson = objectMapper.writeValueAsString(error);

        response.getWriter().write(apiErrorAsJson);
    }
}
