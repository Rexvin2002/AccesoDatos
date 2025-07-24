# Sistema de Gestión de Tienda con Spring Boot

## 📝 Descripción
Aplicación Spring Boot completa para gestión de tienda con:
- CRUD completo para Productos, Clientes y Pedidos
- Relaciones JPA entre entidades
- Conversor personalizado de fechas
- Vistas Thymeleaf para interfaz web
- Repositorios Spring Data JPA

## 🔧 Características principales

### ✔️ Módulos principales
- **Gestión de Productos**: CRUD completo con stock y descripción
- **Gestión de Clientes**: Datos completos de clientes
- **Gestión de Pedidos**: Con detalles de productos y cálculo automático de totales
- **Detalles de Pedido**: Relación muchos-a-uno con productos y pedidos

### 🛠️ Endpoints disponibles
| Método | Ruta                  | Descripción                          |
|--------|-----------------------|--------------------------------------|
| GET    | /                     | Página principal                     |
| GET    | /productos            | Listado de productos                 |
| GET    | /productos/add        | Formulario añadir producto           |
| POST   | /productos/add        | Procesar nuevo producto              |
| GET    | /productos/delete/{id}| Eliminar producto                    |
| GET    | /clientes             | Listado de clientes                  |
| GET    | /clientes/add         | Formulario añadir cliente            |
| POST   | /clientes/add         | Procesar nuevo cliente               |
| GET    | /clientes/delete/{id} | Eliminar cliente                     |
| GET    | /pedidos              | Listado de pedidos                   |
| GET    | /pedidos/add          | Formulario complejo de pedido        |
| POST   | /pedidos/add          | Procesar nuevo pedido                |
| GET    | /pedidos/delete/{id}  | Eliminar pedido                      |
| GET    | /detalles             | Listado de detalles de pedido        |

## 🚀 Cómo ejecutar

1. **Requisitos**:
   - Java 17+
   - Maven 3.6+
   - Base de datos H2 (embebida) o configurar MySQL en application.properties

2. **Ejecutar**:
```bash
mvn spring-boot:run
```

3. **Acceder**:
   - Interfaz web: `http://localhost:8080`
   - Consola H2: `http://localhost:8080/h2-console` (JDBC URL: jdbc:h2:mem:testdb)

## ⚙️ Componentes clave

### `StringToDateConverter.java`
```java
@Component
public class StringToDateConverter implements Converter<String, Date> {
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    
    @Override
    public Date convert(String source) {
        // Conversión de String a Date
    }
}
```

### `WebController.java`
Controlador principal con todos los endpoints MVC:
```java
@Controller
public class WebController {
    // Inyección de repositorios
    @Autowired private ProductoRepository productoRepository;
    @Autowired private ClienteRepository clienteRepository;
    @Autowired private PedidoRepository pedidoRepository;
    @Autowired private DetallePedidoRepository detallePedidoRepository;
    
    // Métodos para productos, clientes y pedidos
}
```

### Entidades JPA
```java
@Entity
public class Producto {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Double precio;
    private Integer stock;
    
    @OneToMany(mappedBy = "producto")
    private List<DetallePedido> detalles;
}
```

### Repositorios Spring Data
```java
public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
```

# MODELO ENTIDAD-RELACIÓN (E/R) PARA SISTEMA DE TIENDA

ENTIDADES Y ATRIBUTOS:
---------------------

* CLIENTE
  - id (PK)
  - nombre
  - email
  - telefono
  - direccion

* PRODUCTO
  - id (PK)
  - nombre
  - precio
  - stock
  - descripcion

* PEDIDO
  - id (PK)
  - fecha
  - total
  - cliente_id (FK)

* DETALLE_PEDIDO
  - id (PK)
  - cantidad
  - subtotal
  - producto_id (FK)
  - pedido_id (FK)

RELACIONES:
-----------
1. CLIENTE (1) --- REALIZA --- (N) PEDIDO
2. PEDIDO (1) --- CONTIENE --- (N) DETALLE_PEDIDO
3. PRODUCTO (1) --- APARECE_EN --- (N) DETALLE_PEDIDO

MODELO RELACIONAL:
------------------

CLIENTE(
  id INT PK,
  nombre VARCHAR,
  email VARCHAR,
  telefono VARCHAR,
  direccion VARCHAR
)

PRODUCTO(
  id INT PK,
  nombre VARCHAR,
  precio DECIMAL,
  stock INT,
  descripcion TEXT
)

PEDIDO(
  id INT PK,
  fecha DATE,
  total DECIMAL,
  cliente_id INT FK REFERENCES CLIENTE(id)
)

DETALLE_PEDIDO(
  id INT PK,
  cantidad INT,
  subtotal DECIMAL,
  producto_id INT FK REFERENCES PRODUCTO(id),
  pedido_id INT FK REFERENCES PEDIDO(id)
)

CARDINALIDADES:
---------------
- Un cliente puede tener muchos pedidos (1:N)
- Un pedido puede tener muchos detalles (1:N)
- Un producto puede aparecer en muchos detalles de pedido (1:N)

OBSERVACIONES:
--------------
- Las relaciones están implementadas con JPA mediante:
  * @ManyToOne para las relaciones N:1
  * @OneToMany para las relaciones 1:N
- Cada entidad tiene su correspondiente clave primaria (@Id)
- Las relaciones están correctamente mapeadas con @JoinColumn

## 👨‍💻 Autor
Kevin Gómez Valderas

## 💡 Mejoras sugeridas
1. **Validación**: Añadir anotaciones `@Valid` en los controladores
2. **Seguridad**: Implementar Spring Security con roles
3. **API REST**: Crear versión API JSON para integraciones
4. **Pruebas**: Añadir tests unitarios y de integración
5. **Internacionalización**: Soporte para múltiples idiomas
6. **Documentación**: Swagger para API REST
7. **Paginación**: Añadir paginación en listados

## ℹ️ Notas técnicas
- Uso de Spring Data JPA para persistencia
- Relaciones JPA bien definidas entre entidades
- Conversor personalizado para fechas
- Diseño MVC tradicional con Thymeleaf
- Configuración simplificada con Spring Boot
- Base de datos H2 embebida por defecto (fácil para desarrollo)

## 🛠️ Dependencias principales
- Spring Boot Starter Web
- Spring Boot Starter Data JPA
- Thymeleaf
- H2 Database (o MySQL/PostgreSQL para producción)
- Spring Boot DevTools (desarrollo)