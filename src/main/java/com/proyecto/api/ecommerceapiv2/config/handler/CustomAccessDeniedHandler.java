package com.proyecto.api.ecommerceapiv2.config.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.proyecto.api.ecommerceapiv2.util.ApiError;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        ApiError error = new ApiError();
        error.setBackendMessage(accessDeniedException.getMessage());
        error.setUrl(request.getRequestURL().toString());
        error.setMethod(request.getMethod());
        error.setMessage("Acceso Denegado: No tiene los permisos necesarios para acceder a esta función. POr favor, contacta la administradpr " +
                "si crees que es un error");
        error.setTimestamp(LocalDateTime.now());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.FORBIDDEN.value());

        //TENER EN CUENTA, podríamos crear una clase de utilería o librería que reciba ese response y escriba todos lo necesario

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String apiErrorAsJson = objectMapper.writeValueAsString(error);

        response.getWriter().write(apiErrorAsJson);
    }
}
