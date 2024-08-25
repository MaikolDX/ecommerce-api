package com.proyecto.api.ecommerceapiv2.service.auth;

import com.proyecto.api.ecommerceapiv2.dto.auth.AuthenticationRequest;
import com.proyecto.api.ecommerceapiv2.dto.auth.AuthenticationResponse;
import com.proyecto.api.ecommerceapiv2.dto.auth.RegisteredUser;
import com.proyecto.api.ecommerceapiv2.dto.auth.SaveUser;
import com.proyecto.api.ecommerceapiv2.exception.ResourceNotFoundException;
import com.proyecto.api.ecommerceapiv2.persistence.security.User;
import com.proyecto.api.ecommerceapiv2.service.UserService;
import com.proyecto.api.ecommerceapiv2.util.ModelMapperUtils;
import com.proyecto.api.ecommerceapiv2.util.ProjectResponse;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;


    public ResponseEntity<?> registerOneCustomer(SaveUser newUser) {

        User user = userService.registerOneCustomer(newUser);

        RegisteredUser userDto = new RegisteredUser();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUsername(user.getUsername());
        userDto.setRole(user.getRole().name()); //Para enums
        //userDto.setRole(user.getRole()); //Prueba x
        //userDto.setRole(user.getRol().getName());

        //AHORA A GENERAR EL TOKEN
        String jwt = jwtService.generateToken(user, generateExtraClaims(user)); // Este user lo recibe utilizando el userDetails
        //String jwt = jwtService.generateToken(user); // Este user lo recibe utilizando el userDetails
        userDto.setJwt(jwt);

        return new ResponseEntity<>(new ProjectResponse(0, "Usuario creado con éxito", false, new Date(), userDto), HttpStatus.CREATED);
    }
    private Map<String, Object> generateExtraClaims(User user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("name", user.getName());
        extraClaims.put("role", user.getRole().name()); //Para enums
        //extraClaims.put("role", user.getRol().getName());
        //extraClaims.put("authorities", user.getRole().getPermissions());
        extraClaims.put("authorities", user.getAuthorities());

        return extraClaims;
    }
    //Método para hacer login
    public AuthenticationResponse login(AuthenticationRequest authRequest) {
        //1. Creo el objeto para el login, hago la autenticación
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                authRequest.getUsername(), authRequest.getPassword() //Se parsea el password con el ByCript para compararlo con el de BD
        );
        //2. Hago el proceso del Login
        authenticationManager.authenticate(authentication); //Le pasamos ese Authentication para que intente hacer el login.

        //3. Obtengo los detalles del usuario que se acaba de loguear
        //Luego obtendremos los detalles de ese username, para llegar acá ya debería ser exitosa la autenticación, sino nos saldría una excepción.
        UserDetails user = userService.findOneByUsername(authRequest.getUsername()).get();

        //4. Crear el Token
        String jwt = jwtService.generateToken(user, generateExtraClaims((User)user)); //El extraclaims recibe un user, pero como es un user que implementa userDetails normal, solo
        //lo casteamos

        //5. Devolver el AuthenticationResponse
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setJwt(jwt);

        return authenticationResponse;
    }

    public boolean validateToken(String jwt) {
        //1. El formato del token ddebe ser el correcto, la firma también, el tiempo...
        //Ya se nos proporciona un método para hacer esto
        //Podemos extraer cualquier claim, lo importante es que se intente extraer y validar ese json, su firma y fecha de exp
        //Con solo ectraer un claim, se validarán los otros
        try{
            jwtService.extractUsername(jwt);
            return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public ResponseEntity<?> findLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof UsernamePasswordAuthenticationToken authToken){
            //Lo del método es practicamente parsear, esto sería: authToken = (UsernamePasswordAuthenticationToken)authentication
            String username = (String) authToken.getPrincipal(); //Podemos ver lo que le pasamos en JwtAuthentiationFilter en que configuramos el UsernamePasswordAuthenticationToken

            User user = userService.findOneByUsername(username).orElseThrow( () -> new ResourceNotFoundException("User not found. Username: "+username));

            return new ResponseEntity<>(new ProjectResponse(0, "Perfil", false, new Date(), ModelMapperUtils.mapEntityToDTO(user, RegisteredUser.class)), HttpStatus.OK);
        }

        return null; //Es muy probable que nunca se ejecute, pues se supone que el token enviado es de un usuario que sí existe.
    }

    public ResponseEntity<?> findLoggedByUsername(String username) {
        String usernameJwt = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!username.equals(usernameJwt)){
            throw new AccessDeniedException("Acceso Denegado");
        }
        User user = userService.findOneByUsername(username).orElseThrow( () -> new AccessDeniedException("Acceso denegado"));
        return new ResponseEntity<>(new ProjectResponse(0, "Perfil con username", false, new Date(), ModelMapperUtils.mapEntityToDTO(user, RegisteredUser.class)), HttpStatus.OK);
    }
}
