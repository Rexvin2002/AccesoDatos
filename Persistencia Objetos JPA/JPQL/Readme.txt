# Sistema de Gestión de Comercio Electrónico

## Descripción del Proyecto
Sistema de comercio electrónico desarrollado con Spring Boot que incluye funcionalidades CRUD completas para:
- Gestión de clientes, productos y pedidos
- Clasificación por categorías de productos
- Administración de proveedores
- Carritos de compra y detalles de pedidos

## Estructura del Proyecto

### Entidades Principales
| Entidad           | Relaciones JPA                          |
|-------------------|-----------------------------------------|
| **Clientes**      | OneToOne: CarritoCompras                |
|                   | OneToMany: Pedidos                      |
| **Productos**     | ManyToOne: CategoriasProducto           |
|                   | ManyToMany: Proveedores                 |
| **Pedidos**       | OneToMany: DetallesPedido               |
| **Categorias**    | OneToMany: Productos                    |
| **Proveedores**   | ManyToMany: Productos (gestión bidireccional) |

### Repositorios
- `RepositorioClientes` (JPQL + consultas nativas)
- `RepositorioProductos`
- `RepositorioPedidos`
- `RepositorioCategorias`
- `RepositorioProveedores` (métodos personalizados)
- `RepositorioDetallesPedido`

## Configuración

### Base de Datos (MySQL)
`application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/spring_example_db
spring.datasource.username=root
spring.datasource.password=passwd
spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

### Ejecución
La clase principal `SpringexampleApplication` incluye operaciones de demostración:
1. Creación de cliente de prueba
2. Actualización de dirección
3. Consulta y visualización de datos
```java
@SpringBootApplication
public class SpringexampleApplication implements CommandLineRunner {
    // Implementación con ejemplos CRUD
}
```

## Características Técnicas
- **Spring Boot 3.x** con Spring Data JPA/Hibernate
- **Relaciones JPA**:
  - `@OneToOne`, `@OneToMany`, `@ManyToOne`, `@ManyToMany`
- Validación de campos con `@NotNull`, `@Column`, etc.
- Consultas personalizadas JPQL/SQL nativo
- Gestión automática de transacciones

### Ejemplos Destacados
**Consulta personalizada (Clientes):**
```java
@Query("SELECT c FROM Clientes c WHERE c.nombre = :nombre AND c.apellido = :apellido")
List<Clientes> findByNombreCompleto(String nombre, String apellido);
```

**Relación ManyToMany (Producto-Proveedor):**
```java
// En EntidadProveedor
public void agregarProducto(Producto producto) {
    this.productos.add(producto);
    producto.getProveedores().add(this); // Sincronización bidireccional
}
```

## Requisitos del Sistema
| Componente | Versión |
|------------|---------|
| Java       | 17+     |
| MySQL      | 8+      |
| Maven      | 3.6+    |

## Buenas Prácticas Implementadas
- Uso de `spring.jpa.hibernate.ddl-auto=update` en producción
- Configuración para formato SQL legible
- Sincronización bidireccional en relaciones `@ManyToMany`
- Manejo de fechas con `java.time` (LocalDate)

## Autor
Kevin Gómez Valderas - 2º DAM

## Notas de Implementación
1. Cambiar `ddl-auto` a `update` después del primer despliegue:
properties
spring.jpa.hibernate.ddl-auto=update

2. Los datos de prueba se generan automáticamente al iniciar la aplicación
3. Consultas nativas para operaciones complejas con fechas
```java
@Query(value = "SELECT * FROM clientes WHERE fecha_registro > :fecha", nativeQuery = true)
List<Clientes> findClientesRegistradosDespuesDe(Date fecha);


