# JDBC Utilities - README

## Descripción
Este proyecto contiene dos clases Java para trabajar con bases de datos MySQL:
1. **JDBC1**: Clase utilitaria avanzada para operaciones CRUD con transacciones en una tabla de agenda
2. **App**: Ejemplo básico de conexión y consulta a una tabla de usuarios

## JDBC1 - Clase Avanzada

### Características principales
- Conexión automatizada a MySQL con control de transacciones
- Operaciones CRUD completas con PreparedStatement
- Manejo robusto de errores con rollback automático
- Tipos de ResultSet configurados (SCROLL_INSENSITIVE)
- Cierre automático de recursos
- Métodos para diferentes formatos de resultados (List, Map)

### Métodos disponibles

| Método | Descripción | Parámetros | Retorno |
|--------|-------------|------------|---------|
| `selectCampo()` | Obtiene un campo específico | `(posición, nombreColumna)` | String |
| `selectColumna()` | Obtiene todos los valores de una columna | `(nombreColumna)` | List<String> |
| `selectRowList()` | Obtiene una fila como List | `(posición)` | List<String> |
| `selectRowMap()` | Obtiene una fila como Map | `(posición)` | Map<String, String> |
| `update()` | Actualiza uno o varios campos | `(id, Map<columna,valor>)` o `(id, columna, valor)` | void |
| `delete()` | Elimina un registro | `(id)` | void |

### Ejemplo de uso
```java
try {
    JDBC1 jdbc = new JDBC1("agenda_db", "usuario", "contraseña");
    
    // Consultar datos
    System.out.println("Primer nombre: " + jdbc.selectCampo(0, "NOMBRE"));
    System.out.println("Todas direcciones: " + jdbc.selectColumna("DIRECCION"));
    
    // Actualizar datos
    Map<String, String> cambios = new HashMap<>();
    cambios.put("NOMBRE", "Juan Pérez");
    cambios.put("TELEFONO", "555-1234");
    jdbc.update(1, cambios);
    
    jdbc.closeConnection();
} catch (SQLException e) {
    System.err.println("Error en operación: " + e.getMessage());
}
```

## App - Ejemplo Básico

### Características
- Demostración simple de conexión JDBC
- Consulta básica a una tabla de usuarios
- Manejo de excepciones combinado
- Carga explícita del driver JDBC
- Cierre adecuado de recursos con try-with-resources

### Ejemplo de uso
```java
String url = "jdbc:mysql://localhost:3306/testdb";
String user = "root";
String password = "passwd";

try {
    Class.forName("com.mysql.cj.jdbc.Driver");
    try (Connection conn = DriverManager.getConnection(url, user, password);
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery("SELECT * FROM users")) {
         
        while(rs.next()) {
            System.out.println("ID: " + rs.getInt("id") + 
                             ", Nombre: " + rs.getString("name"));
        }
    }
} catch (Exception e) {
    System.err.println("Error: " + e.getMessage());
}
```

## Requisitos
- Java 11+
- MySQL Server 8.0+
- Connector/J (mysql-connector-java 8.0+)
- Tablas requeridas:
  - `agenda` (para JDBC1)
  - `users` (para App)

## Configuración
1. Importar el proyecto en tu IDE favorito
2. Añadir el conector MySQL al classpath
3. Configurar credenciales en los constructores
4. Ejecutar App.java o JDBC1.java

## Estructura de tabla agenda
```sql
CREATE TABLE agenda (
    id INT AUTO_INCREMENT PRIMARY KEY,
    NOMBRE VARCHAR(100),
    DIRECCION VARCHAR(200),
    TELEFONO VARCHAR(20),
    EMAIL VARCHAR(100)
);
```

## Buenas prácticas implementadas
- Uso de PreparedStatement para evitar SQL injection
- Transacciones con commit/rollback explícitos
- Try-with-resources para manejo automático de recursos
- ResultSet configurado como SCROLL_INSENSITIVE
- Separación clara entre lógica de negocio y acceso a datos

## Mejoras futuras
- Implementar patrón DAO
- Añadir soporte para conexión pooling
- Implementar logging profesional
- Añadir validación de datos antes de operaciones
- Soporte para otros motores de bases de datos

## Autor
Kevin Gómez Valderas