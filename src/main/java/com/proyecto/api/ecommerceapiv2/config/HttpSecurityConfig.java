package com.proyecto.api.ecommerceapiv2.config;

import com.proyecto.api.ecommerceapiv2.config.filter.JwtAuthenticationFilter;
import com.proyecto.api.ecommerceapiv2.config.handler.CustomAccessDeniedHandler;
import com.proyecto.api.ecommerceapiv2.config.handler.CustomAuthenticationEntryPoint;
import com.proyecto.api.ecommerceapiv2.util.enums.auth.RoleEnum;
import com.proyecto.api.ecommerceapiv2.util.enums.auth.RolePermissionEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class HttpSecurityConfig {

    @Autowired
    private AuthenticationProvider daoAuthenticationProvider;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        /*
        Aquí vamos a devolver un SecurityFilterChain, que construiremos gracias a un Builder, que es el HttpSecurity, este
        permite personalizar como se gestionará las solicitudes http, además de autorizar rutas, permisos, indicar que tipo de
        aplicación será, configurar filtros, el orden de esos mismos, los componentes de AccesEntryPoint o AccsesDenidHandler.
         */
        SecurityFilterChain securityFilterChain = http
                .cors(Customizer.withDefaults()) //Habilitar los cors
                .csrf(csrfConfig -> csrfConfig.disable())
                //Esto nos servirá para indicar el tipo de aplicación que usaremos, el sessionCreationPolicy recibe una enumeracióm
                //En este caso STATELESS, es decir sin estado.
                .sessionManagement(sessMagConfig -> sessMagConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(daoAuthenticationProvider) //Esto recibe una instancia de un AuthenticationProvidee, nosotros configuramos el DaoAuthe..
                //REGISTRAR UN FILTRO
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                //Lo que estamos poniendo es que se ejecute nuestro filtro personalizado antes del filtro UsernamePasswordAuthenticationFilter
                //Ahora configuraremos las rutas públicas o privadas
                //.authorizeHttpRequests(HttpSecurityConfig::builRequestMatchers)
                /*.authorizeHttpRequests(authReqConfig -> {
                    authReqConfig.anyRequest().access(authorizationManager); //Es un authorizationManager que yo mismo creo
                })*/
                .authorizeHttpRequests(authReqConfig -> {
                    builRequestMatchers2(authReqConfig);
                })
                /*.authorizeHttpRequests( authReqConfig ->{
                    builRequestMatchers(authReqConfig);
                })*/
                .exceptionHandling(exceptionConfig -> {
                    exceptionConfig.authenticationEntryPoint(customAuthenticationEntryPoint);
                    exceptionConfig.accessDeniedHandler(customAccessDeniedHandler);
                })
                .build(); //El método Build ya crea o devuelve un DefaultSecurityFilterChain que implementa de SecutityFilterChain
        return securityFilterChain;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("https://www.google.com", "http://127.0.0.7:5500"));//Orígenes
        configuration.setAllowedMethods(Arrays.asList("*"));//"GET","POST"
        configuration.setAllowedHeaders(Arrays.asList("*")); //Para que acepte el autorization bearer token
        configuration.setAllowCredentials(true); // para que acepte el Bearer Token

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); //Para todos los controladores, puedo cambiar por "/products/**"
        return source;
    }

    //v1. AUTORIZACIÓN EN BASE A PERMISOS / peticiones http
    private static void builRequestMatchers(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authReqConfig) {
    /* Sección 08
    Autorización de endpoint de products
     */
        authReqConfig.requestMatchers(HttpMethod.GET, "/products")
                .hasAnyAuthority(RolePermissionEnum.READ_ALL_PRODUCTS.name());

        authReqConfig.requestMatchers(HttpMethod.GET, "/products/{productId}")
                .hasAnyAuthority(RolePermissionEnum.READ_ONE_PRODUCT.name());

        authReqConfig.requestMatchers(HttpMethod.POST, "/products")
                .hasAnyAuthority(RolePermissionEnum.CREATE_ONE_PRODUCT.name());

        authReqConfig.requestMatchers(HttpMethod.PUT, "/products/{productId}")
                .hasAnyAuthority(RolePermissionEnum.UPDATE_ONE_PRODUCT.name());

        authReqConfig.requestMatchers(HttpMethod.PUT, "/products/{productId}/disabled")
                .hasAnyAuthority(RolePermissionEnum.DISABLE_ONE_PRODUCT.name());

                    /*
                    Autorización de endpoints de categories
                     */
        authReqConfig.requestMatchers(HttpMethod.GET, "/categories")
                .hasAnyAuthority(RolePermissionEnum.READ_ALL_CATEGORIES.name());

        authReqConfig.requestMatchers(HttpMethod.GET, "/categories/{categoryId}")
                .hasAnyAuthority(RolePermissionEnum.READ_ONE_CATEGORY.name());

        authReqConfig.requestMatchers(HttpMethod.POST, "/categories")
                .hasAnyAuthority(RolePermissionEnum.CREATE_ONE_CATEGORY.name());

        authReqConfig.requestMatchers(HttpMethod.PUT, "/categories/{categoryId}")
                .hasAnyAuthority(RolePermissionEnum.UPDATE_ONE_CATEGORY.name());

        authReqConfig.requestMatchers(HttpMethod.PUT, "/categories/{categoryId}/disable")
                .hasAnyAuthority(RolePermissionEnum.DISABLE_ONE_CATEGORY.name());

                    /*
                    Autorización de endpoints Auth
                     */
        authReqConfig.requestMatchers(HttpMethod.GET, "/auth/profile")
                .hasAnyAuthority(RolePermissionEnum.READ_MY_PROFILE.name());

                    /*
                    Autorización de endpoints publicos
                     */
        authReqConfig.requestMatchers(HttpMethod.POST, "/customers").permitAll();
        authReqConfig.requestMatchers(HttpMethod.POST, "/auth/authenticate").permitAll();
        authReqConfig.requestMatchers(HttpMethod.GET, "/auth/validate").permitAll();

        authReqConfig.anyRequest().authenticated(); //El resto estarán asegurados, deben estar autenticados
    }
    //v2. AUTORIZACIÓN EN BASE A ROLES / peticiones http, acá ya se ve ele error del ROLE_, ya está cambiado :3
    private static void builRequestMatchers2(AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry authReqConfig) {
    /* Sección 08
    Autorización de endpoint de products
     */
        authReqConfig.requestMatchers(HttpMethod.GET, "/products")
                .hasAnyRole(RoleEnum.ADMINISTRATOR.name(), RoleEnum.CUSTOMER.name());

        authReqConfig.requestMatchers(HttpMethod.GET, "/products/{productId}")
                .hasAnyRole(RoleEnum.ADMINISTRATOR.name(), RoleEnum.CUSTOMER.name());

        authReqConfig.requestMatchers(HttpMethod.POST, "/products")
                .hasRole(RoleEnum.ADMINISTRATOR.name());

        authReqConfig.requestMatchers(HttpMethod.PUT, "/products/{productId}")
                .hasAnyRole(RoleEnum.ADMINISTRATOR.name(), RoleEnum.CUSTOMER.name());

        authReqConfig.requestMatchers(HttpMethod.PUT, "/products/{productId}/disabled")
                .hasRole(RoleEnum.ADMINISTRATOR.name());

                    /*
                    Autorización de endpoints de categories
                     */
        authReqConfig.requestMatchers(HttpMethod.GET, "/categories")
                .hasAnyRole(RoleEnum.ADMINISTRATOR.name(), RoleEnum.CUSTOMER.name());

        authReqConfig.requestMatchers(HttpMethod.GET, "/categories/{categoryId}")
                .hasAnyRole(RoleEnum.ADMINISTRATOR.name(), RoleEnum.CUSTOMER.name());

        authReqConfig.requestMatchers(HttpMethod.POST, "/categories")
                .hasRole(RoleEnum.ADMINISTRATOR.name());

        authReqConfig.requestMatchers(HttpMethod.PUT, "/categories/{categoryId}")
                .hasAnyRole(RoleEnum.ADMINISTRATOR.name(), RoleEnum.CUSTOMER.name());

        authReqConfig.requestMatchers(HttpMethod.PUT, "/categories/{categoryId}/disable")
                .hasAnyRole(RoleEnum.ADMINISTRATOR.name(), RoleEnum.CUSTOMER.name(), RoleEnum.ASSISTANT_ADMINISTRATOR.name());

                    /*
                    Autorización de endpoints Auth
                     */
        authReqConfig.requestMatchers(HttpMethod.GET, "/auth/profile")
                .hasAnyRole(RoleEnum.ADMINISTRATOR.name(), RoleEnum.CUSTOMER.name(), RoleEnum.ASSISTANT_ADMINISTRATOR.name());

                    /*
                    Autorización de endpoints publicos
                     */
        authReqConfig.requestMatchers(HttpMethod.POST, "/customers").permitAll();
        authReqConfig.requestMatchers(HttpMethod.POST, "/auth/authenticate").permitAll();
        authReqConfig.requestMatchers(HttpMethod.GET, "/auth/validate").permitAll();

        authReqConfig.anyRequest().authenticated(); //El resto estarán asegurados, deben estar autenticados
    }

}
