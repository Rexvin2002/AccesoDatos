# README - Aplicación Web Spring Boot Básica

## 📝 Descripción
Aplicación web básica desarrollada con Spring Boot que demuestra:
- Configuración mínima de un proyecto Spring Boot
- Controlador MVC básico
- Paso de datos a plantillas Thymeleaf
- Estructura básica de un proyecto web

## 🔧 Características principales

### ✔️ Stack tecnológico
- **Spring Boot 3.x** para configuración automática
- **Spring MVC** para el patrón Modelo-Vista-Controlador
- **Thymeleaf** como motor de plantillas (implícito)

### 🛠️ Estructura del proyecto
```
src/
├── main/
│   ├── java/
│   │   └── com/example/demo/
│   │       ├── DemoApplication.java      # Clase principal
│   │       └── Controlador/             # Paquete de controladores
│   │           └── HomeController.java  # Controlador de ejemplo
│   └── resources/
│       ├── static/                      # Archivos estáticos
│       ├── templates/                   # Plantillas Thymeleaf
│       │   └── home.html                # Plantilla de ejemplo
│       └── application.properties       # Configuración
```

## 🚀 Cómo ejecutar

1. **Requisitos previos**:
   - Java 17+ instalado
   - Maven 3.6+ instalado

2. **Ejecutar la aplicación**:
```bash
mvn spring-boot:run
```

3. **Acceder en el navegador**:
```
http://localhost:8080
```

## ⚙️ Componentes clave

### `DemoApplication.java`
Clase principal que inicia la aplicación Spring Boot:
```java
@SpringBootApplication
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
```

### `HomeController.java`
Controlador MVC básico:
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

## 📌 Plantilla Thymeleaf (home.html)
Ejemplo de plantilla que debería estar en `src/main/resources/templates/home.html`:
```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title th:text="${title}">Título por defecto</title>
</head>
<body>
    <h1>Bienvenido</h1>
    <p th:text="'Nombre: ' + ${nombre}">Nombre por defecto</p>
    <p th:text="'Apellido: ' + ${apellido}">Apellido por defecto</p>
</body>
</html>
```

## 👨‍💻 Autor
Kevin Gómez Valderas

## 💡 Posibles extensiones
1. Añadir más controladores y rutas
2. Integrar con una base de datos
3. Añadir estilos CSS
4. Implementar autenticación de usuarios
5. Crear una API REST adicional

## ℹ️ Notas
- El proyecto asume que tienes Thymeleaf en el classpath (incluido por defecto en Spring Boot Starter Web)
- Para cambiar el puerto, editar `application.properties`:
```properties
server.port=8081
```