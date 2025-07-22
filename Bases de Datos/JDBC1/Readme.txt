# README.txt - Aplicación JDBC para gestión de base de datos MySQL

## Descripción
Esta aplicación Java demuestra el uso de JDBC para interactuar con una base de datos MySQL. Implementa operaciones CRUD básicas (Crear, Leer, Actualizar, Eliminar) en una tabla de agenda, con funciones para manipular registros individuales y columnas específicas.

## Estructura del Proyecto

### Clases principales:
1. **App** - Clase principal básica
   - Muestra conexión básica a MySQL y consulta simple
   - Demuestra el uso de DriverManager y ResultSet

2. **JDBC1** - Clase principal avanzada
   - Gestiona conexiones a bases de datos MySQL
   - Verifica y crea la base de datos y tabla si no existen
   - Implementa operaciones CRUD completas
   - Proporciona múltiples métodos de consulta:
     - selectField: Obtiene un campo específico
     - selectColumn: Obtiene todos los valores de una columna
     - selectRowList/selectRowMap: Obtiene filas como List o Map
     - update: Actualiza registros (individual o múltiples campos)
     - delete: Elimina registros

## Configuración y Uso

### Requisitos:
- Java 8+
- MySQL Server instalado
- Driver JDBC de MySQL (mysql-connector-java)

### Instalación:
1. Configurar MySQL con usuario y contraseña (por defecto usa root/passwd)
2. Importar el proyecto en tu IDE favorito
3. Asegurarse de tener el driver JDBC en el classpath

### Operaciones implementadas:
- **Creación** automática de base de datos y tabla si no existen
- **Lectura** de datos (campos individuales, columnas o filas completas)
- **Actualización** de registros (por campo o múltiples campos)
- **Eliminación** de registros

## Configuración de Base de Datos
La aplicación está configurada para:
- Servidor: localhost:3306
- Usuario: root (modificar en el código)
- Contraseña: passwd (modificar en el código)
- Base de datos: testdb (se crea automáticamente si no existe)
- Tabla: agenda (se crea automáticamente si no existe)

Para personalizar:
1. Modificar los parámetros en el constructor de JDBC1
2. Cambiar la estructura de la tabla en el método verifyAndCreateTable()

## Personalización
- **Estructura de tabla**: Modificar el SQL en verifyAndCreateTable()
- **Consultas**: Añadir nuevos métodos siguiendo el patrón existente
- **Parámetros de conexión**: Editar en el constructor/main

## Autor
Kevin Gómez Valderas - 2º DAM

## Notas
- La aplicación incluye datos de ejemplo al crear la tabla
- Se recomienda cambiar las credenciales por defecto
- El cierre de conexiones se gestiona automáticamente con try-with-resources

## Licencia
Este código se proporciona libremente para fines educativos. Se permite su uso y modificación siempre que se mantenga esta nota de atribución.