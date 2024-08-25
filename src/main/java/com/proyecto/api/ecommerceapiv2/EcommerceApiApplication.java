package com.proyecto.api.ecommerceapiv2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EcommerceApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApiApplication.class, args);
	}
	//Para crear contraseñas, inyectamos el PasswordEncoder en el método, pero también lo podría hacer con @Autowired
	//El CommandLineRunner nos sitve para ejecutar código justo después de que el contexto de la aplicación haya sido cargado y ante de que
	//la aplicación empiece a funcionar
//	@Bean
//	public CommandLineRunner createPasswordsCommand(PasswordEncoder passwordEncoder){
//		return args -> {
//			System.out.println(passwordEncoder.encode("clave123"));
//			System.out.println(passwordEncoder.encode("clave456"));
//			System.out.println(passwordEncoder.encode("clave789"));
//		};
//	}

}
