Aquí tienes la versión corregida y mejorada del README.md:

```markdown
# JDBC Utilities - README

[![Java Version](https://img.shields.io/badge/Java-11%2B-blue)](https://openjdk.java.net/)
[![MySQL Version](https://img.shields.io/badge/MySQL-8.0%2B-orange)](https://www.mysql.com/)
[![License](https://img.shields.io/badge/License-MIT-green)](https://opensource.org/licenses/MIT)

## Descripción
Este proyecto contiene utilidades Java para trabajar con bases de datos MySQL:
1. **JDBC1**: Clase utilitaria avanzada para operaciones CRUD con transacciones
2. **App**: Ejemplo básico de conexión y consulta JDBC

## Requisitos
- Java 11 o superior
- MySQL Server 8.0 o superior
- Connector/J 8.0+ (mysql-connector-java)
- Tablas requeridas:
  - `agenda` (para JDBC1)
  - `users` (para App)

## Configuración

### Dependencias Maven
```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.28</version>
</dependency>
```

### Estructura de tablas
```sql
CREATE TABLE agenda (
    id INT AUTO_INCREMENT PRIMARY KEY,
    NOMBRE VARCHAR(100) NOT NULL,
    DIRECCION VARCHAR(200),
    TELEFONO VARCHAR(20),
    EMAIL VARCHAR(100),
    INDEX (NOMBRE)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

## JDBC1 - Clase Avanzada

### Características principales
- Conexión automatizada con transacciones
- Operaciones CRUD completas
- PreparedStatement para prevenir SQL injection
- Rollback automático en errores
- ResultSet SCROLL_INSENSITIVE
- Cierre automático de recursos
- Múltiples formatos de resultados (List, Map)

### Métodos disponibles

| Método            | Descripción                               | Parámetros                          | Retorno            | Excepciones       |
|-------------------|------------------------------------------|-------------------------------------|--------------------|-------------------|
| `selectCampo()`   | Obtiene un campo específico              | `(posición, nombreColumna)`        | String             | SQLException      |
| `selectColumna()` | Obtiene todos los valores de una columna | `(nombreColumna)`                  | List<String>       | SQLException      |
| `selectRowList()` | Obtiene una fila como List               | `(posición)`                       | List<String>       | SQLException      |
| `selectRowMap()`  | Obtiene una fila como Map                | `(posición)`                       | Map<String,String> | SQLException      |
| `insert()`        | Inserta un nuevo registro                | `(Map<columna,valor>)`             | void               | SQLException      |
| `update()`        | Actualiza campos                         | `(id, Map<columna,valor>)`         | void               | SQLException      |
| `update()`        | Actualiza un campo                       | `(id, columna, valor)`             | void               | SQLException      |
| `delete()`        | Elimina un registro                      | `(id)`                             | void               | SQLException      |

### Manejo de errores
Todas las operaciones lanzan `SQLException` que debe ser manejada por el llamador. Las transacciones se revierten automáticamente en caso de error.

### Ejemplo de uso
```java
try {
    // 1. Inicialización
    JDBC1 jdbc = new JDBC1("agenda_db", "usuario", "contraseña");
    
    // 2. Insertar datos
    Map<String, String> nuevoContacto = new HashMap<>();
    nuevoContacto.put("NOMBRE", "María García");
    nuevoContacto.put("EMAIL", "maria@example.com");
    jdbc.insert(nuevoContacto);
    
    // 3. Consultar datos
    System.out.println("Primer nombre: " + jdbc.selectCampo(0, "NOMBRE"));
    System.out.println("Todos los emails: " + jdbc.selectColumna("EMAIL"));
    
    // 4. Actualizar datos
    Map<String, String> cambios = new HashMap<>();
    cambios.put("NOMBRE", "María G. López");
    cambios.put("TELEFONO", "555-9876");
    jdbc.update(1, cambios); // ID 1
    
    // 5. Cerrar conexión
    jdbc.closeConnection();
} catch (SQLException e) {
    System.err.println("Error en operación: " + e.getMessage());
    e.printStackTrace();
}
```

## App - Ejemplo Básico

### Ejemplo de uso
```java
public class App {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/testdb?useSSL=false&serverTimezone=UTC";
        String user = "root";
        String password = "passwd";

        try {
            // 1. Cargar driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // 2. Establecer conexión
            try (Connection conn = DriverManager.getConnection(url, user, password);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT id, name FROM users")) {
                
                // 3. Procesar resultados
                while(rs.next()) {
                    System.out.printf("ID: %d, Nombre: %s%n", 
                                     rs.getInt("id"), 
                                     rs.getString("name"));
                }
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
```

## Buenas prácticas implementadas
✔ Uso de PreparedStatement para seguridad  
✔ Transacciones con commit/rollback explícitos  
✔ Try-with-resources para manejo de recursos  
✔ ResultSet configurable (SCROLL_INSENSITIVE)  
✔ Separación clara entre lógica y acceso a datos  
✔ Validación básica de parámetros  

## Mejoras futuras
- [ ] Implementar patrón DAO
- [ ] Añadir conexión pooling (HikariCP)
- [ ] Soporte para otros motores de bases de datos
- [ ] Añadir logging con SLF4J
- [ ] Implementar batch updates

## Contribuciones
Las contribuciones son bienvenidas. Por favor:
1. Haz un fork del proyecto
2. Crea una rama para tu feature (`git checkout -b feature/awesome-feature`)
3. Haz commit de tus cambios (`git commit -am 'Add awesome feature'`)
4. Haz push a la rama (`git push origin feature/awesome-feature`)
5. Abre un Pull Request

## Licencia
Este proyecto está licenciado bajo la licencia MIT - ver el archivo [LICENSE](LICENSE) para más detalles.

## Autor
Kevin Gómez Valderas  
Contacto: [email@example.com](mailto:email@example.com)
```