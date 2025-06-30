# JDBC Database Manager - README

## Descripción
Este proyecto contiene una clase Java (`JDBC1`) para gestionar bases de datos MySQL con enfoque en usuarios y licencias, además de una clase de demostración básica (`App`).

## JDBC1 - Clase Avanzada

### Características principales
- Gestión de tablas relacionadas (usuarios-licencias)
- Transacciones ACID con commit/rollback manual
- Operaciones CRUD completas
- Inserción de múltiples registros relacionados
- Eliminación en cascada automática
- Consultas avanzadas con diferentes formatos de resultado

### Métodos principales

#### Gestión de tablas
```java
public void createTables() throws SQLException
```
Crea las tablas usuarios y licencias con sus relaciones.

#### Operaciones con usuarios
```java
public boolean insertLicencias(String DNI, String DIRECCION, String CP, String NOMBRE,
                             ArrayList<ArrayList<String>> licencias)
```
Inserta un usuario con sus licencias asociadas en una transacción.

```java
public boolean cleanUserByDNI(String DNI) throws SQLException
```
Elimina un usuario por DNI (incluye licencias por cascada).

#### Operaciones con licencias
```java
public boolean eliminarLicencias(String DNI)
```
Elimina todas las licencias de un usuario.

#### Consultas de datos
```java
public String selectCampo(int numRegistro, String nomColumna)
public List<String> selectColumna(String nomColumna)
public List<String> selectRowList(int numRegistro)
public Map<String, String> selectRowMap(int numRegistro)
```
Diferentes métodos para recuperar datos en varios formatos.

### Ejemplo de uso
```java
try {
    JDBC1 db = new JDBC1("testdb", "root", "password");
    db.createTables();
    
    // Insertar usuario con licencias
    ArrayList<ArrayList<String>> licencias = new ArrayList<>();
    ArrayList<String> licencia = new ArrayList<>();
    licencia.add("B1");
    licencia.add("2023-01-01");
    licencia.add("2025-01-01");
    licencias.add(licencia);
    
    db.insertLicencias("12345678A", "Calle Principal", "28001", "Juan Pérez", licencias);
    
    // Consultar datos
    Map<String, String> usuario = db.selectRowMap(0);
    System.out.println("Usuario: " + usuario.get("NOMBRE"));
    
    db.closeConnection();
} catch (SQLException e) {
    System.err.println("Error de base de datos: " + e.getMessage());
}
```

## App - Ejemplo Básico

### Características
- Conexión básica a MySQL
- Ejemplo de consulta simple
- Manejo de recursos con try-with-resources
- Carga explícita del driver JDBC

### Código de ejemplo
```java
public class App {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/testdb";
        String user = "root";
        String password = "passwd";
        
        try {
            // Cargar driver MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver cargado correctamente");
            
            // Establecer conexión
            try (Connection conn = DriverManager.getConnection(url, user, password);
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM users")) {
                
                System.out.println("Conexión establecida");
                
                // Procesar resultados
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id") + 
                                     ", Nombre: " + rs.getString("name"));
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
```

## Requisitos
- Java 11 o superior
- MySQL Server 8.0+
- Connector/J 8.0+
- Tablas: `usuarios` y `licencias` (se crean automáticamente)

## Configuración
1. Importar el proyecto en tu IDE
2. Añadir mysql-connector-java al classpath
3. Configurar credenciales en los constructores
4. Ejecutar App.java para prueba básica o JDBC1.java para funcionalidad avanzada

## Estructura de la base de datos
Las tablas se crean con este esquema:
```sql
CREATE TABLE usuarios (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    DNI VARCHAR(9) UNIQUE,
    DIRECCION VARCHAR(100),
    CP VARCHAR(5),
    NOMBRE VARCHAR(100)
);

CREATE TABLE licencias (
    ID INT,
    TIPO VARCHAR(2),
    EXPEDICION DATETIME,
    CADUCIDAD DATETIME,
    FOREIGN KEY (ID) REFERENCES usuarios(ID) ON DELETE CASCADE
);
```

## Buenas prácticas implementadas
- Uso de PreparedStatement para evitar inyección SQL
- Transacciones atómicas
- Gestión adecuada de recursos
- Tipado fuerte de resultados
- Manejo completo de excepciones
- Separación clara de responsabilidades