# README - Sistema de Gestión de Pedidos con Spring Boot

## 📝 Descripción
Aplicación Spring Boot completa para gestión de clientes, productos, categorías y pedidos con relaciones JPA avanzadas. El proyecto demuestra:

- **Modelado de entidades** con relaciones complejas (OneToMany, ManyToMany)
- **Repositorios Spring Data JPA** con consultas personalizadas
- **Operaciones transaccionales** completas
- **Logging** con SLF4J
- **Arquitectura en capas** bien definida

## 🔧 Características principales

### ✔️ Modelo de datos completo
- **Clientes**: Información básica de clientes
- **Productos**: Catálogo de productos con precios y stock
- **Categorías**: Clasificación de productos (relación ManyToMany)
- **Pedidos**: Registro de transacciones con fechas y totales

### 🛡️ Relaciones JPA avanzadas
- `@OneToMany` entre Productos y Pedidos
- `@ManyToOne` entre Pedidos y Clientes/Productos
- `@ManyToMany` entre Productos y Categorías
- FetchType.LAZY para optimización

### 📊 Consultas personalizadas
- Consultas JPQL en repositorios
- Métodos derivados de queries
- Agregaciones (COUNT, SUM)
- Filtros por rangos (fechas, precios)

## 🛠️ Configuración y uso

### 1. Requisitos previos
- Java 17+
- Maven 3.6+
- Base de datos configurada (MySQL recomendado)

### 2. Estructura del proyecto
```
src/
├── main/
│   ├── java/
│   │   └── com/proyectomaven/springexample/
│   │       ├── Entities/       # Entidades JPA
│   │       ├── Repositories/   # Interfaces de repositorio
│   │       └── SpringexampleApplication.java  # Clase principal
│   └── resources/
│       └── application.properties # Configuración BD
```

### 3. Ejecución
```bash
mvn spring-boot:run
```

## ⚙️ Ejemplos de operaciones

### Consultas de clientes
```java
// Buscar por nombre y apellido
List<Clientes> clientes = clienteRepository.findByNombreAndApellidoJPQL("Juan", "Perez");

// Actualizar dirección
clienteRepository.updateDireccion(1L, "Nueva Dirección 123");

// Eliminar por nombre y apellido
clienteRepository.deleteByNombreAndApellido("Ana", "Gomez");
```

### Consultas de productos
```java
// Productos con precio > 500
List<Productos> productos = productosRepository.findByPrecioGreaterThan(BigDecimal.valueOf(500));

// Suma total de stock
Integer totalStock = productosRepository.sumTotalStock();
```

### Consultas de pedidos
```java
// Pedidos entre fechas
List<Pedidos> pedidos = pedidosRepository.findByFechaPedidoBetween(
    LocalDate.of(2024, 1, 1), 
    LocalDate.of(2024, 12, 31));

// Suma de totales por cliente
BigDecimal total = pedidosRepository.sumTotalByClienteId(1L);
```

## 👨‍💻 Autor
Kevin Gómez Valderas

## 💡 Buenas prácticas implementadas
- **Inyección de dependencias** con `@Autowired`
- **Transacciones** con `@Transactional`
- **Logging** con SLF4J
- **Consultas parametrizadas** para seguridad
- **Separación clara** de responsabilidades
- **Documentación** completa de entidades y repositorios

## 📌 Personalización
Para adaptar el proyecto:
1. Configurar conexión a BD en `application.properties`
2. Modificar entidades según necesidades
3. Añadir nuevos repositorios y consultas
4. Extender la lógica de negocio