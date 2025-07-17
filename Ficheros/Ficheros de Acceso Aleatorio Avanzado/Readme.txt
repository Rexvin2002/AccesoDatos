# AdvancedBinaryRecord - Sistema de Gestión de Registros Binarios

## Descripción
Este programa Java implementa un sistema completo para gestionar registros en archivos binarios con campos de longitud fija. Permite realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre registros estructurados, manteniendo la integridad de los datos.

## Características principales

### Estructura de datos
- **Registros con campos de longitud fija**: Cada registro contiene múltiples campos con tamaños predefinidos
- **Acceso aleatorio eficiente**: Uso de RandomAccessFile para operaciones rápidas
- **Codificación UTF-8**: Soporte para caracteres internacionales

### Funcionalidades CRUD
- **Inserción**: Añadir nuevos registros en posiciones específicas
- **Lectura**: Recuperar registros completos o campos individuales
- **Modificación**: Actualizar registros existentes
- **Eliminación**: Borrar registros marcándolos como vacíos

### Operaciones avanzadas
- **Búsqueda por columna**: Obtener todos los valores de un campo específico
- **Selección flexible**: Recuperar registros como mapas o listas
- **Validación integrada**: Comprobación de formatos y existencia de datos

## Uso

### Compilación y ejecución
```bash
javac Main/AdvancedBinaryRecord.java
java Main.AdvancedBinaryRecord
```

### Menú interactivo
El programa ofrece un menú con las siguientes opciones:
1. Insertar registro
2. Leer registro
3. Modificar registro
4. Seleccionar campo específico
5. Seleccionar columna completa
6. Seleccionar fila como lista
7. Seleccionar fila como mapa
8. Actualizar registro
9. Eliminar registro
10. Salir

## Estructura del archivo binario
- Campos predefinidos: ID (9 bytes), NAME (32 bytes), DIRECTION (32 bytes), ZC (5 bytes)
- Longitud total del registro: 78 bytes
- Los registros se almacenan secuencialmente

## Requisitos
- Java JDK 8 o superior
- Permisos de lectura/escritura en el directorio de trabajo
- Sistema de archivos que soporte operaciones random-access

## Ejemplo de uso
```java
// Crear instancia
AdvancedBinaryRecord binaryRecord = new AdvancedBinaryRecord(
    "datos.bin", 
    Arrays.asList("ID", "NAME", "DIRECTION", "ZC"),
    Arrays.asList(9, 32, 32, 5)
);

// Insertar registro
Map<String, String> nuevoRegistro = new HashMap<>();
nuevoRegistro.put("ID", "12345678A");
nuevoRegistro.put("NAME", "Juan Pérez");
binaryRecord.insert(nuevoRegistro, 0);

// Leer registro
Map<String, String> registro = binaryRecord.read(0);
```

## Manejo de errores
El sistema detecta y notifica:
- Posiciones inválidas
- Campos inexistentes
- Problemas de permisos
- Errores de formato
- Violaciones de longitud de campo

## Autor
Kevin Gómez Valderas - 2º Desarrollo de Aplicaciones Multiplataforma (DAM)