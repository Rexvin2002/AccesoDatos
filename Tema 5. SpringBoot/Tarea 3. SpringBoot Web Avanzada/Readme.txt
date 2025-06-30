# README - Aplicación Spring Boot con Controladores MVC y REST

## 📝 Descripción
Aplicación Spring Boot que combina:
- Controlador MVC tradicional para renderizar vistas Thymeleaf
- Controlador REST para manejar peticiones API
- Ejemplos de manejo de peticiones GET y POST
- Gestión de productos con entidades y repositorios

## 🔧 Características principales

### ✔️ Arquitectura híbrida
- **Controlador MVC** (`WebController`) para vistas web
- **Controlador REST** (`ProductController`) para API JSON
- **Entidades y Repositorios** para gestión de productos
- Separación clara de responsabilidades

### 🛠️ Endpoints disponibles
| Método | Ruta         | Tipo      | Descripción                          |
|--------|--------------|-----------|--------------------------------------|
| GET    | /            | MVC       | Página principal con Thymeleaf       |
| GET    | /contacto    | MVC       | Página de contacto                   |
| GET    | /productos   | MVC       | Listado de productos                 |
| GET    | /api/users   | REST/JSON | Devuelve datos de usuario            |
| POST   | /api/create  | REST/JSON | Procesa datos enviados               |

## 🚀 Cómo ejecutar

1. **Requisitos**:
   - Java 17+
   - Maven 3.6+

2. **Ejecutar**:
```bash
mvn spring-boot:run
```

3. **Endpoints**:
   - Vista web principal: `http://localhost:8080`
   - Contacto: `http://localhost:8080/contacto`
   - Productos: `http://localhost:8080/productos`
   - API REST: `http://localhost:8080/api/users`

## ⚙️ Componentes clave

### `WebController.java`
```java
@Controller
public class WebController {
    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Bienvenido a nuestra tienda");
        model.addAttribute("welcomeMessage", "Descubre nuestros productos exclusivos");
        return "home";
    }

    @GetMapping("/contacto")
    public String contacto(Model model) {
        model.addAttribute("mensaje", "Contáctenos para más información");
        model.addAttribute("email", "info@ejemplo.com");
        model.addAttribute("telefono", "+34 666 777 888");
        return "contacto";
    }

    @GetMapping("/productos")
    public String productos(Model model) {
        model.addAttribute("productos", productoRepository.findAll());
        model.addAttribute("productoDestacado", new Producto(4, "Portátil Gaming", 1499.99));
        return "productos";
    }
}
```

### `ProductController.java`
```java
@RestController
@RequestMapping("/api")
public class ProductController {
    
    @GetMapping("users")
    public Map<String, Object> pruebas() {
        Map<String, Object> body = new HashMap<>();
        body.put("title", "titulo hola mundo");
        body.put("nombre", "nombre");
        body.put("apellidos", "apellidos");
        return body;
    }
    
    @PostMapping("create")
    public String postMethodName(@RequestBody String entity) {
        return entity.replace("Gomez", "Gómez");
    }
}
```

### `Producto.java` (Entidad)
```java
public class Producto {
    private Integer id;
    private String concepto;
    private Double importe;

    // Constructor, getters y setters
}
```

### `ProductoRepository.java`
```java
@Repository
public class ProductoRepository {
    private List<Producto> productos;

    public ProductoRepository() {
        productos = new ArrayList<>();
        productos.add(new Producto(1, "Ordenador", 999.99));
        productos.add(new Producto(2, "Teléfono", 599.99));
        productos.add(new Producto(3, "Tablet", 399.99));
    }

    public List<Producto> findAll() {
        return productos;
    }
}
```

## 📌 Estructura del proyecto
```
src/
├── main/
│   ├── java/
│   │   └── com/example/demo/
│   │       ├── DemoApplication.java
│   │       ├── Controlador/
│   │       │   ├── WebController.java       # Controlador MVC
│   │       │   └── ProductController.java   # Controlador REST
│   │       ├── Entities/
│   │       │   └── Producto.java           # Entidad Producto
│   │       └── Repositories/
│   │           └── ProductoRepository.java # Repositorio de productos
│   └── resources/
│       ├── static/
│       ├── templates/
│       │   ├── home.html
│       │   ├── contacto.html
│       │   └── productos.html
│       └── application.properties
```

## 👨‍💻 Autor
Kevin Gómez Valderas

## 💡 Mejoras sugeridas
1. **Base de datos**: Integrar con JPA y Hibernate
2. **Validación**: Añadir validación a los formularios
3. **Seguridad**: Implementar Spring Security
4. **Pruebas**: Añadir tests unitarios e integración
5. **Internacionalización**: Soporte para múltiples idiomas
6. **Documentación API**: Integrar Swagger/OpenAPI

## ℹ️ Notas importantes
- La entidad `Producto` gestiona los datos de los productos
- El repositorio `ProductoRepository` actúa como fuente de datos temporal
- Los templates Thymeleaf deben estar en `resources/templates/`
- La aplicación sigue el patrón MVC para la web y REST para APIs
- Se utiliza inyección de dependencias para el repositorio