# README - Aplicación Spring Boot con Controladores MVC y REST

## 📝 Descripción
Aplicación Spring Boot que combina:
- Controlador MVC tradicional para renderizar vistas Thymeleaf
- Controlador REST para manejar peticiones API
- Ejemplos de manejo de peticiones GET y POST

## 🔧 Características principales

### ✔️ Arquitectura híbrida
- **Controlador MVC** (`HomeController`) para vistas web
- **Controlador REST** (`ProductController`) para API JSON
- Separación clara de responsabilidades

### 🛠️ Endpoints disponibles
| Método | Ruta       | Tipo      | Descripción                     |
|--------|------------|-----------|---------------------------------|
| GET    | /          | MVC       | Página principal con Thymeleaf  |
| GET    | /api/users | REST/JSON | Devuelve datos de usuario       |
| POST   | /api/create| REST/JSON | Procesa datos enviados          |

## 🚀 Cómo ejecutar

1. **Requisitos**:
   - Java 17+
   - Maven 3.6+

2. **Ejecutar**:
```bash
mvn spring-boot:run
```

3. **Endpoints**:
   - Vista web: `http://localhost:8080`
   - API REST: `http://localhost:8080/api/users`

## ⚙️ Componentes clave

### `HomeController.java`
```java
@Controller
public class HomeController {
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Título");
        model.addAttribute("nombre", "Pepe");
        model.addAttribute("apellido", "Gomez");
        return "home"; 
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

## 📌 Estructura recomendada
```
src/
├── main/
│   ├── java/
│   │   └── com/example/demo/
│   │       ├── DemoApplication.java
│   │       └── Controlador/
│   │           ├── HomeController.java   # Controladores MVC
│   │           └── Api/                  # Controladores REST
│   │               └── ProductController.java
│   └── resources/
│       ├── static/
│       ├── templates/
│       └── application.properties
```

## 👨‍💻 Autor
Kevin Gómez Valderas

## 💡 Mejoras sugeridas
1. **Validación**: Añadir `@Valid` para validar datos de entrada
2. **DTOs**: Usar objetos DTO para las APIs
3. **Manejo de errores**: Implementar `@ControllerAdvice`
4. **Seguridad**: Añadir Spring Security
5. **Pruebas**: Escribir tests unitarios e integración

## ℹ️ Notas importantes
- Los controladores REST deben estar en el paquete `Controlador/Api/` para mejor organización
- Usar `@RestController` para APIs JSON (combina `@Controller` + `@ResponseBody`)
- `@RequestBody` automáticamente convierte JSON a objetos Java
- MVC y REST pueden coexistir en la misma aplicación