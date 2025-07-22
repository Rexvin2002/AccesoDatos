# README.txt - Aplicación JDBC para Gestión de Base de Datos MySQL

## Descripción
Esta aplicación Java demuestra el uso avanzado de JDBC para interactuar con bases de datos MySQL. Incluye dos implementaciones con diferentes niveles de complejidad:

1. **App**: Versión básica que muestra una conexión simple y consulta básica
2. **JDBC1**: Versión avanzada con gestión de transacciones, operaciones CRUD completas y manejo seguro de recursos

## Características Principales

### Clase App (Básica)
- Conexión simple a MySQL
- Consulta básica a tabla "users"
- Uso de DriverManager y ResultSet
- Manejo básico de excepciones

### Clase JDBC1 (Avanzada)
✔ **Gestión de transacciones** (commit/rollback explícitos)  
✔ **Operaciones CRUD completas** usando PreparedStatement  
✔ **Cuatro tipos de consultas** diferentes:  
  - Por campo específico (`selectCampo`)  
  - Por columna completa (`selectColumna`)  
  - Por fila como List (`selectRowList`)  
  - Por fila como Map (`selectRowMap`)  
✔ **Actualizaciones flexibles**:  
  - De un solo campo (`update`)  
  - De múltiples campos (`update` con Map)  
✔ **Manejo seguro de recursos** con try-with-resources  
✔ **Control de concurrencia** con TYPE_SCROLL_INSENSITIVE  

## Estructura del Código

### Métodos principales en JDBC1:
1. **Constructores**:
   - Establece conexión y configura autoCommit=false

2. **Métodos de consulta**:
   ```java
   selectCampo(int numRegistro, String nomColumna)
   selectColumna(String nomColumna)
   selectRowList(int numRegistro)
   selectRowMap(int numRegistro)
   ```

3. **Métodos de modificación**:
   ```java
   update(int row, Map<String, String> values)
   update(int row, String campo, String valor)
   delete(int row)
   ```

4. **Gestión de conexión**:
   ```java
   closeConnection()
   ```

## Configuración y Uso

### Requisitos:
- Java 8+
- MySQL Server
- Driver JDBC de MySQL (mysql-connector-java)

### Configuración inicial:
1. Modificar credenciales en los constructores:
   ```java
   // En App.java
   String url = "jdbc:mysql://localhost:3306/testdb";
   String user = "root";
   String password = "passwd";

   // En JDBC1.java
   new JDBC1("testdb", "root", "passwd");
   ```

2. Asegurar que existan las tablas:
   - `users` para la clase App
   - `agenda` para la clase JDBC1

### Ejecución:
- **App.java**: Ejecutar directamente para prueba básica
- **JDBC1.java**: Usar el método main() de demostración

## Buenas Prácticas Implementadas

1. **Seguridad**:
   - Uso de PreparedStatement para prevenir SQL injection
   - Transacciones atómicas con commit/rollback

2. **Manejo de recursos**:
   - Try-with-resources para cierre automático
   - Conexiones, statements y resultsets adecuadamente cerrados

3. **Control de concurrencia**:
   ```java
   ResultSet.TYPE_SCROLL_INSENSITIVE
   ResultSet.CONCUR_READ_ONLY
   ```

4. **Manejo de errores**:
   - Excepciones específicas (SQLException)
   - Rollback automático en fallos
   - Mensajes de error descriptivos

## Ejemplo de Uso Avanzado

```java
try {
    JDBC1 jdbc = new JDBC1("testdb", "root", "passwd");
    
    // Consultar datos
    Map<String, String> registro = jdbc.selectRowMap(0);
    System.out.println(registro);
    
    // Actualizar múltiples campos
    Map<String, String> nuevosValores = new HashMap<>();
    nuevosValores.put("NOMBRE", "Juan Pérez");
    nuevosValores.put("DIRECCION", "Calle Nueva 123");
    jdbc.update(1, nuevosValores);
    
    // Eliminar registro
    jdbc.delete(2);
    
    jdbc.closeConnection();
} catch (SQLException e) {
    System.err.println("Error de base de datos: " + e.getMessage());
}
```

## Autor
Kevin Gómez Valderas - 2º DAM

## Licencia
Este código se proporciona con fines educativos. Uso libre bajo atribución.