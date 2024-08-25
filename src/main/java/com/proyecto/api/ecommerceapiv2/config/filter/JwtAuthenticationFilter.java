package com.proyecto.api.ecommerceapiv2.config.filter;

import com.proyecto.api.ecommerceapiv2.exception.ResourceNotFoundException;
import com.proyecto.api.ecommerceapiv2.service.UserService;
import com.proyecto.api.ecommerceapiv2.service.auth.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter { //Implementa de GenericFilterBean y este de Filter

    @Autowired
    JwtService jwtService;

    @Autowired
    UserService userService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        System.out.println("ENTRÓ EN EL FILTRO JWT AUTHENTICATION FILTER");

        //1. Obtener encabezado http llamado Authorization
        String authorizationHeader = request.getHeader("Authorization");
        if (!StringUtils.hasText(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return; //Como es un mpetodo de tipo void, solo retorna el control a quién mandó a llamar al método doFilterInternal
        }
        //2. Obtener el jwt desde el encabezado
        String jwt = authorizationHeader.split(" ")[1]; //Aca dividimos el String considerando el espacio, tendremos dos arrays, escogemos el jwt
        //Porque sería así: Bearer jwt

        //3. Obtener el subject/username desde el token
        //esta acción a su vez valida el formato del token, firma y fecha de expiración
        String username = jwtService.extractUsername(jwt);

        //4. Setear objeto Authentication dentro de security context holder
        //Funciona tambien con un User normal, pues implementa de UserDetails
        UserDetails user = userService.findOneByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found. Username: "+username));

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                //Recibirá el principal, las credenciales y los auhtorities, necesitaremos el userDetails para obtener los Authorities
                username, null, user.getAuthorities()
        );//Implementa de Authentication, así que normal

        //Antes de agregar el Authentication podemos agregar algunos detalles, como la ip o el id se sesión
        authToken.setDetails(new WebAuthenticationDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authToken);

        //5. Ejecutar el registro de filtros
        filterChain.doFilter(request, response); // Ya no coloco return porque es la última línea del método
    }
}
