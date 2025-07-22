# Sistema de Gestión de Comercio Electrónico

## Descripción del Proyecto
Este proyecto es un sistema de gestión de comercio electrónico desarrollado con Spring Boot que incluye funcionalidades CRUD para clientes, productos, pedidos, categorías y proveedores. El sistema utiliza Spring Data JPA para la persistencia de datos y está configurado para trabajar con MySQL.

## Estructura del Proyecto

### Entidades Principales
1. **Clientes**: Gestión de información de clientes con relaciones a pedidos y carritos de compra
2. **Productos**: Catálogo de productos con categorías y proveedores asociados
3. **Pedidos**: Sistema de pedidos con detalles y estado
4. **CategoriasProducto**: Clasificación de productos
5. **Proveedores**: Gestión de proveedores con relación many-to-many a productos

### Repositorios
- RepositorioClientes (con consultas personalizadas JPQL y nativas)
- RepositorioProductos
- RepositorioPedidos
- RepositorioCategorias
- RepositorioProveedores (con métodos personalizados)
- RepositorioDetallesPedido

## Configuración

### Base de Datos
El proyecto está configurado para MySQL con las siguientes propiedades (en `application.properties`):
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/spring_example_db
spring.datasource.username=root
spring.datasource.password=passwd
spring.jpa.hibernate.ddl-auto=create
```

### Ejecución
La clase principal `SpringexampleApplication` implementa `CommandLineRunner` y realiza operaciones de ejemplo al iniciar:
1. Crea un cliente de ejemplo
2. Busca y actualiza la dirección del cliente
3. Muestra los cambios por consola

## Características Técnicas

- Spring Boot 3.x
- Spring Data JPA con Hibernate
- Configuración automática de la base de datos
- Relaciones JPA:
  - OneToOne (Cliente-CarritoCompras)
  - OneToMany (Cliente-Pedidos, Categoria-Productos)
  - ManyToOne (DetallePedido-Pedido/Producto)
  - ManyToMany (Producto-Proveedor)
- Consultas personalizadas con JPQL y SQL nativo
- Validación de campos con anotaciones JPA (@Column, @NotNull, etc.)

## Ejemplos de Uso

### Consultas personalizadas en RepositorioClientes:
```java
@Query("SELECT c FROM Clientes c WHERE c.nombre = :nombre AND c.apellido = :apellido")
List<Clientes> findByNombreCompleto(@Param("nombre") String nombre, @Param("apellido") String apellido);

@Query(value = "SELECT * FROM clientes WHERE fecha_registro > :fecha", nativeQuery = true)
List<Clientes> findClientesRegistradosDespuesDe(@Param("fecha") Date fecha);
```

### Relación ManyToMany gestionada (Proveedor-Producto):
```java
public void agregarProducto(Producto producto) {
    this.productos.add(producto);
    producto.getProveedores().add(this);
}
```

## Requisitos del Sistema

- Java 17+
- MySQL 8+
- Maven

## Autor
Kevin Gómez Valderas - 2º DAM

## Notas Adicionales
El proyecto incluye configuración para:
- Mostrar SQL formateado en consola
- Crear automáticamente la base de datos si no existe
- Inicializar datos al arrancar la aplicación

Para desarrollo se recomienda cambiar `spring.jpa.hibernate.ddl-auto` a `update` después de la primera ejecución.