# README - Proyecto Spring Boot con JPA

## 📝 Descripción
Aplicación Spring Boot que demuestra el uso de Spring Data JPA para operaciones CRUD con una entidad Clientes. El proyecto incluye:
- Entidad JPA con mapeo a tabla `clientes`
- Repositorio Spring Data JPA
- Clase principal con ejemplos de operaciones CRUD

## 🔧 Características principales

### ✔️ Stack tecnológico
- **Spring Boot 3.x**
- **Spring Data JPA** para acceso a datos
- **Hibernate** como implementación de JPA
- **Base de datos embebida H2** (configurable para MySQL)

### 🛡️ Arquitectura limpia
- Capa de **Entidades** (`Entities`)
- Capa de **Repositorios** (`Repositories`)
- **Separación clara** de responsabilidades

### 📊 Operaciones implementadas
- **Create**: Guardar clientes individuales y en lote
- **Read**: Buscar por ID y listar todos
- **Update**: Modificar entidades existentes
- **Delete**: Eliminar registros

## 🛠️ Configuración y uso

### 1. Requisitos previos
- Java 17+
- Maven 3.6+
- Base de datos configurada (H2 por defecto)

### 2. Configuración de la base de datos
Editar `application.properties`:
```properties
# Para H2 (memoria)
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# Para MySQL
# spring.datasource.url=jdbc:mysql://localhost:3306/tudb
# spring.datasource.username=tuuser
# spring.datasource.password=tupassword
# spring.jpa.hibernate.ddl-auto=update
```

### 3. Ejecución
```bash
mvn spring-boot:run
```

## ⚙️ Estructura del proyecto

### Entidad `Clientes`
```java
@Entity
@Table(name = "clientes")
public class Clientes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmpleado;
    
    @Column(name = "nombre", unique = true, length = 40)
    private String nombre;
    
    // ... otros campos, constructores, getters y setters
}
```

### Repositorio `RepositorioClientes`
```java
@Repository
public interface RepositorioClientes extends JpaRepository<Clientes, Long> {
}
```

### Clase principal con ejemplos CRUD
```java
@SpringBootApplication
public class SpringexampleApplication implements CommandLineRunner {
    
    @Autowired
    private RepositorioClientes clientRepository;
    
    // Ejemplos de operaciones CRUD
    public void funcionPruebas1() {
        // Crear
        clientRepository.save(new Clientes("Pepe", "Pérez", "Calle Mayor 1"));
        
        // Leer
        Optional<Clientes> cliente = clientRepository.findById(1L);
        
        // Actualizar
        cliente.ifPresent(c -> {
            c.setDireccion("Nueva Dirección");
            clientRepository.save(c);
        });
        
        // Eliminar
        clientRepository.deleteById(2L);
    }
}
```

## 👨‍💻 Autor
Kevin Gómez Valderas

## 💡 Buenas prácticas implementadas
- **Inyección de dependencias** con `@Autowired`
- **Transacciones** con `@Transactional`
- **Optional** para manejo seguro de valores nulos
- **Nomenclatura clara** en entidades y repositorios
- **Separación** en paquetes por responsabilidad

## 📌 Personalización
Para adaptar el proyecto:
1. Modificar la entidad `Clientes` según necesidades
2. Añadir métodos personalizados en el repositorio
3. Configurar la base de datos en `application.properties`
4. Extender la lógica de negocio según requerimientos