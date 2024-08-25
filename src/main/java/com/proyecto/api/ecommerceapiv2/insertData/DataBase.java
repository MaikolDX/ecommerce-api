package com.proyecto.api.ecommerceapiv2.insertData;

import com.proyecto.api.ecommerceapiv2.persistence.entity.*;
import com.proyecto.api.ecommerceapiv2.persistence.repository.*;
import com.proyecto.api.ecommerceapiv2.persistence.security.*;
import com.proyecto.api.ecommerceapiv2.persistence.security.Module;
import com.proyecto.api.ecommerceapiv2.util.enums.ProductStatus;
import com.proyecto.api.ecommerceapiv2.util.enums.UserStatus;
import com.proyecto.api.ecommerceapiv2.util.enums.auth.RoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Configuration
public class DataBase {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private GrantedPermissionRepository permissionRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    @Bean
    @Order(1)
    public CommandLineRunner createModules() {
        return args -> {

            //MODULES
            moduleRepository.save(Module.builder()
                    .name("CATEGORY")
                    .basePath("/categories")
                    .build());
            moduleRepository.save(Module.builder()
                    .name("PRODUCTS")
                    .basePath("/products")
                    .build());
            moduleRepository.save(Module.builder()
                    .name("USERS")
                    .basePath("/users")
                    .build());

            System.out.println("MODULES CREATED");
            moduleRepository.findAll().forEach(System.out::println);
        };
    }

    @Bean
    @Order(2)
    public CommandLineRunner createOperations() {
        return args -> {

            List<Module> modules = moduleRepository.findAll();

            //OPERATIONS--------------------------------------------

            //CATEGORIES
            operationRepository.save(
                    Operation.builder()
                            .name("CREATE_ONE_CATEGORY")
                            .path("")
                            .httpMethod("POST")
                            .permitAll(false)
                            .module(modules.get(0))
                            .build()
            );
            operationRepository.save(
                    Operation.builder()
                            .name("DISABLE_ONE_CATEGORY")
                            .path("/[0-9]*/disabled")
                            .httpMethod("PUT")
                            .permitAll(false)
                            .module(modules.get(0))
                            .build()
            );
            operationRepository.save(
                    Operation.builder()
                            .name("READ_ALL_CATEGORIES")
                            .path("")
                            .httpMethod("GET")
                            .permitAll(false)
                            .module(modules.get(0))
                            .build()
            );
            operationRepository.save(
                    Operation.builder()
                            .name("READ_ONE_CATEGORY")
                            .path("")
                            .httpMethod("GET")
                            .permitAll(false)
                            .module(modules.get(0))
                            .build()
            );
            operationRepository.save(
                    Operation.builder()
                            .name("UPDATE_ONE_CATEGORY")
                            .path("[0-9]*")
                            .httpMethod("PUT")
                            .permitAll(false)
                            .module(modules.get(0))
                            .build()
            );

            //PRODUCTS
            operationRepository.save(
                    Operation.builder()
                            .name("CREATE_ONE_PRODUCT")
                            .path("")
                            .httpMethod("POST")
                            .permitAll(false)
                            .module(modules.get(1))
                            .build()
            );
            operationRepository.save(
                    Operation.builder()
                            .name("DISABLE_ONE_PRODUCT")
                            .path("/[0-9]*/disabled")
                            .httpMethod("PUT")
                            .permitAll(false)
                            .module(modules.get(1))
                            .build()
            );
            operationRepository.save(
                    Operation.builder()
                            .name("READ_ALL_PRODUCTS")
                            .path("")
                            .httpMethod("GET")
                            .permitAll(false)
                            .module(modules.get(1))
                            .build()
            );
            operationRepository.save(
                    Operation.builder()
                            .name("READ_ONE_CPRODUCT")
                            .path("")
                            .httpMethod("GET")
                            .permitAll(false)
                            .module(modules.get(1))
                            .build()
            );
            operationRepository.save(
                    Operation.builder()
                            .name("UPDATE_ONE_PRODUCT")
                            .path("[0-9]*")
                            .httpMethod("PUT")
                            .permitAll(false)
                            .module(modules.get(1))
                            .build()
            );

            System.out.println("OPERATIONS CREATED");
            operationRepository.findAll().forEach(System.out::println);
        };
    }

    /*@Bean
    @Order(3)
    public CommandLineRunner createRoles() {
        return args -> {

            //ROLES
            Role role1 = Role.builder()
                    .name(RoleEnum.ADMINISTRATOR)
                    .build();
            Role role2 = Role.builder()
                    .name(RoleEnum.CUSTOMER)
                    .build();
            Role role3 = Role.builder()
                    .name(RoleEnum.ASSISTANT)
                    .build();

            //OBTENEMOS LAS OPERACIONES
            List<Operation> operations = operationRepository.findAll();
            Operation operationCreateCategory = operations.get(0);
            Operation disableCreateCategory = operations.get(1);
            Operation readAllCategories = operations.get(2);


            GrantedPermission grantedPermission1 = GrantedPermission.builder()
                    .role(role1)
                    .operation(operationCreateCategory)
                    .build();
            GrantedPermission grantedPermission2 = GrantedPermission.builder()
                    .role(role1)
                    .operation(disableCreateCategory)
                    .build();
            GrantedPermission grantedPermission3 = GrantedPermission.builder()
                    .role(role1)
                    .operation(readAllCategories)
                    .build();

            GrantedPermission grantedPermission4 = GrantedPermission.builder()
                    .role(role2)
                    .operation(readAllCategories)
                    .build();

            role1.setGrantedPermissions(Arrays.asList(grantedPermission1, grantedPermission2, grantedPermission3));
            role2.setGrantedPermissions(Collections.singletonList(grantedPermission4));

            roleRepository.saveAll(Arrays.asList(role1, role2, role3));

            System.out.println("ROLES CREADOS");
            roleRepository.findAll().forEach(System.out::println);
        };
    }*/
    @Bean
    @Order(4)
    public CommandLineRunner createCategories() {
        return args -> {
            categoryRepository.save(
                    Category.builder()
                            .name("Categoria 1")
                            .build()
            );
            categoryRepository.save(
                    Category.builder()
                            .name("Categoria 2")
                            .build()
            );
            categoryRepository.save(
                    Category.builder()
                            .name("Categoria 3")
                            .build()
            );
            System.out.print("==CATEGORÍAS CREADAS==");

            categoryRepository.findAll().forEach(System.out::println);


            System.out.println("Agregar padre a Categorías");

            Category category = categoryRepository.findById(1L).orElseThrow(()->new RuntimeException("Error con el padre"));

            categoryRepository.save(
                    Category.builder()
                            .name("Categoria 4 con padre 1")
                            .categoryPadre(category)
                            .build()
            );
            categoryRepository.save(
                    Category.builder()
                            .name("Categoria 5 con padre 1")
                            .categoryPadre(category)
                            .build()
            );
            categoryRepository.save(
                    Category.builder()
                            .name("Categoria 6 con padre 1")
                            .categoryPadre(category)
                            .build()
            );
        };
    }

    @Bean
    @Order(5)
    public CommandLineRunner createProducts() {
        return args -> {

            List<Category> categorias = categoryRepository.findAll();

            productRepository.save(
                    Product.builder()
                            .category(categorias.get(0))
                            .name("Playera para adultoS")
                            .status(ProductStatus.DISPONIBLE)
                            .price(BigDecimal.valueOf(9.99)).build()
            );
            productRepository.save(
                    Product.builder()
                            .category(categorias.get(0))
                            .name("Taza navideña")
                            .status(ProductStatus.NO_DISPONIBLE)
                            .price(BigDecimal.valueOf(4.99)).build()
            );
            productRepository.save(
                    Product.builder()
                            .category(categorias.get(0))
                            .name("Aretes rosas para niña")
                            .status(ProductStatus.DISPONIBLE)
                            .price(BigDecimal.valueOf(1.99)).build()
            );
            productRepository.save(
                    Product.builder()
                            .category(categorias.get(0))
                            .name("Playera para niñps")
                            .status(ProductStatus.DISPONIBLE)
                            .price(BigDecimal.valueOf(7.99)).build()
            );
            productRepository.save(
                    Product.builder()
                            .category(categorias.get(0))
                            .name("Playeras simples")
                            .status(ProductStatus.DISPONIBLE)
                            .price(BigDecimal.valueOf(2.99)).build()
            );
            productRepository.save(
                    Product.builder()
                            .category(categorias.get(0))
                            .name("Chompas para adultos")
                            .status(ProductStatus.DISPONIBLE)
                            .price(BigDecimal.valueOf(12.99)).build()
            );

            System.out.println("== Productos Guardados ==");
            productRepository.findAll().forEach(System.out::println);

        };
    }
    @Bean
    @Order(6)
    public CommandLineRunner createUsers(){
        return args -> {
            //List<Role> roles = roleRepository.findAll();

            User user = User.builder()
                    .name("Maikol")
                    .apePat("Ramírez")
                    .apeMat("Guevara")
                    .state(UserStatus.ENABLE)
                    .emailVerify(false)
                    .username("maikoldx")
                    .password("$2a$12$P5ohCXVNmF86ZxNPntktj.ZCoLMj4xjvU1qCxC.ahL9.vzQ49U9Iy")
                    .phoneNumber("927918404")
                    .urlPhoto("fotosperfil/maikol.jpg")
                    .role(RoleEnum.CUSTOMER)
                    .build();
            User user2 = User.builder()
                    .name("Danny")
                    .apePat("Taica")
                    .apeMat("Guevara")
                    .state(UserStatus.DISABLE)
                    .emailVerify(true)
                    .username("danny123")
                    .password("$2a$12$P5ohCXVNmF86ZxNPntktj.ZCoLMj4xjvU1qCxC.ahL9.vzQ49U9Iy")
                    .phoneNumber("987654321")
                    .urlPhoto("fotosperfil/danny.jpg")
                    .role(RoleEnum.ADMINISTRATOR)
                    .build();

            User user3 = User.builder()
                    .name("Alessandro")
                    .apePat("Taica")
                    .apeMat("Guevara")
                    .state(UserStatus.ENABLE)
                    .emailVerify(true)
                    .username("sandro123")
                    .password("$2a$12$P5ohCXVNmF86ZxNPntktj.ZCoLMj4xjvU1qCxC.ahL9.vzQ49U9Iy")
                    .phoneNumber("987654389")
                    .urlPhoto("fotosperfil/sandroid.jpg")
                    .role(RoleEnum.ASSISTANT_ADMINISTRATOR)
                    .build();

            userRepository.saveAll(Arrays.asList(user, user2, user3));
            System.out.println("USUARIOS GUARDADOS");
            userRepository.findAll().forEach(System.out::println);
        };
    }
    /*@Bean
    @Order(6)
    public CommandLineRunner createUsers(){
        return args -> {
            List<Role> roles = roleRepository.findAll();

            User user = User.builder()
                    .name("Maikol")
                    .apePat("Ramírez")
                    .apeMat("Guevara")
                    .state(UserStatus.ENABLE)
                    .emailVerify(false)
                    .username("maikoldx")
                    .password("$2a$12$P5ohCXVNmF86ZxNPntktj.ZCoLMj4xjvU1qCxC.ahL9.vzQ49U9Iy")
                    .phoneNumber("927918404")
                    .urlPhoto("fotosperfil/maikol.jpg")
                    .role(roles.get(0))
                    .build();
            User user2 = User.builder()
                    .name("Danny")
                    .apePat("Taica")
                    .apeMat("Guevara")
                    .state(UserStatus.DISABLE)
                    .emailVerify(true)
                    .username("danny123")
                    .password("$2a$12$P5ohCXVNmF86ZxNPntktj.ZCoLMj4xjvU1qCxC.ahL9.vzQ49U9Iy")
                    .phoneNumber("987654321")
                    .urlPhoto("fotosperfil/danny.jpg")
                    .role(roles.get(1))
                    .build();

            userRepository.saveAll(Arrays.asList(user, user2));
            System.out.println("USUARIOS GUARDADOS");
            userRepository.findAll().forEach(System.out::println);
        };
    }
    @Bean
    @Order(7)
    public CommandLineRunner createPedidos(){
        return args -> {

            List<User> users = userRepository.findAll();

            List<Product> productos = productRepository.findAll(Sort.by("id"));
            Product producto1 = productos.get(0);
            Product producto2 = productos.get(1);
            Product producto3 = productos.get(2);

            Pedido pedido = Pedido.builder()
                    .pedidoStatus(PedidoStatus.EN_PROCESO)
                    .user(users.get(0))
                    .build();

            DetallePedidoPK detallePedidoPK = DetallePedidoPK.builder()
                    .productId(producto1.getId())
                    .build();
            DetallePedidoPK detallePedidoPK2 = DetallePedidoPK.builder()
                    .productId(producto2.getId())
                    .build();
            DetallePedidoPK detallePedidoPK3 = DetallePedidoPK.builder()
                    .productId(producto3.getId())
                    .build();

            DetallePedido detallePedido = DetallePedido.builder()
                    .detallePedidoPK(detallePedidoPK)
                    .pedido(pedido)
                    .cantidad(3)
                    .precioUnitario(producto1.getPrice())
                    .build();

            DetallePedido detallePedido2 = DetallePedido.builder()
                    .detallePedidoPK(detallePedidoPK2)
                    .pedido(pedido)
                    .cantidad(5)
                    .precioUnitario(producto2.getPrice())
                    .build();

            DetallePedido detallePedido3 = DetallePedido.builder()
                    .detallePedidoPK(detallePedidoPK3)
                    .pedido(pedido)
                    .cantidad(2)
                    .precioUnitario(producto3.getPrice())
                    .build();

            pedido.setDetalles(Arrays.asList(detallePedido, detallePedido2, detallePedido3));

            pedido = pedidoRepository.save(pedido);

            System.out.println("-- Pedido Guardado --");
            System.out.println(pedido);
            System.out.println("El total de la orden es: " + pedido.getTotal());
            System.out.println("Subtotales: ");
            pedido.getDetalles()
                    .forEach(detalle -> System.out.println("\t -> " + detalle.getSubtotal()));
        };
    }*/
    /*@Bean
    @Order(6)
    public CommandLineRunner createUsers(){
        return args -> {
            List<Role> roles = roleRepository.findAll();

            User user = User.builder()
                    .name("Maikol")
                    .apePat("Ramírez")
                    .apeMat("Guevara")
                    .state(UserStatus.ENABLE)
                    .emailVerify(false)
                    .username("maikoldx")
                    .password("$2a$12$P5ohCXVNmF86ZxNPntktj.ZCoLMj4xjvU1qCxC.ahL9.vzQ49U9Iy")
                    .phoneNumber("927918404")
                    .urlPhoto("fotosperfil/maikol.jpg")
                    .role(roles.get(0))
                    .build();
            User user2 = User.builder()
                    .name("Danny")
                    .apePat("Taica")
                    .apeMat("Guevara")
                    .state(UserStatus.DISABLE)
                    .emailVerify(true)
                    .username("danny123")
                    .password("$2a$12$P5ohCXVNmF86ZxNPntktj.ZCoLMj4xjvU1qCxC.ahL9.vzQ49U9Iy")
                    .phoneNumber("987654321")
                    .urlPhoto("fotosperfil/danny.jpg")
                    .role(roles.get(1))
                    .build();

            userRepository.saveAll(Arrays.asList(user, user2));
            System.out.println("USUARIOS GUARDADOS");
            userRepository.findAll().forEach(System.out::println);
        };
    }
    @Bean
    @Order(7)
    public CommandLineRunner createPedidos(){
        return args -> {

            List<User> users = userRepository.findAll();

            List<Product> productos = productRepository.findAll(Sort.by("id"));
            Product producto1 = productos.get(0);
            Product producto2 = productos.get(1);
            Product producto3 = productos.get(2);

            Pedido pedido = Pedido.builder()
                    .pedidoStatus(PedidoStatus.EN_PROCESO)
                    .user(users.get(0))
                    .build();

            DetallePedidoPK detallePedidoPK = DetallePedidoPK.builder()
                    .productId(producto1.getId())
                    .build();
            DetallePedidoPK detallePedidoPK2 = DetallePedidoPK.builder()
                    .productId(producto2.getId())
                    .build();
            DetallePedidoPK detallePedidoPK3 = DetallePedidoPK.builder()
                    .productId(producto3.getId())
                    .build();

            DetallePedido detallePedido = DetallePedido.builder()
                    .detallePedidoPK(detallePedidoPK)
                    .pedido(pedido)
                    .cantidad(3)
                    .precioUnitario(producto1.getPrice())
                    .build();

            DetallePedido detallePedido2 = DetallePedido.builder()
                    .detallePedidoPK(detallePedidoPK2)
                    .pedido(pedido)
                    .cantidad(5)
                    .precioUnitario(producto2.getPrice())
                    .build();

            DetallePedido detallePedido3 = DetallePedido.builder()
                    .detallePedidoPK(detallePedidoPK3)
                    .pedido(pedido)
                    .cantidad(2)
                    .precioUnitario(producto3.getPrice())
                    .build();

            pedido.setDetalles(Arrays.asList(detallePedido, detallePedido2, detallePedido3));

            pedido = pedidoRepository.save(pedido);

            System.out.println("-- Pedido Guardado --");
            System.out.println(pedido);
            System.out.println("El total de la orden es: " + pedido.getTotal());
            System.out.println("Subtotales: ");
            pedido.getDetalles()
                    .forEach(detalle -> System.out.println("\t -> " + detalle.getSubtotal()));
        };
    }*/
}
