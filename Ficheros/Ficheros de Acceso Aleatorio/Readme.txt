# BinaryRecord - Sistema de Gestión de Registros Binarios

## Descripción
Este programa Java implementa un sistema para gestionar registros en archivos binarios con campos de longitud fija. Permite realizar operaciones básicas CRUD (Crear, Leer, Actualizar) sobre registros estructurados.

## Características principales

### Estructura de datos
- **Registros con campos de longitud fija**:
  - ID: 9 bytes
  - NAME: 32 bytes  
  - DIRECTION: 32 bytes
  - ZC: 5 bytes
- **Total por registro**: 78 bytes
- **Codificación UTF-8**: Soporte para caracteres internacionales

### Funcionalidades
- **Inserción**: Añadir nuevos registros en posiciones específicas
- **Lectura**: Recuperar registros completos
- **Modificación**: Actualizar registros existentes
- **Validación**: Comprobación de registros existentes

## Uso

### Compilación y ejecución
```bash
javac Main/BinaryRecord.java
java Main.BinaryRecord
```

### Menú interactivo
El programa ofrece un menú con 4 opciones:
1. Insertar registro
2. Leer registro  
3. Modificar registro
4. Salir

## Ejemplo de uso
```java
// Crear instancia
BinaryRecord binaryRecord = new BinaryRecord(
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

## Ventajas
- **Eficiencia**: Acceso directo a registros con RandomAccessFile
- **Consistencia**: Campos de longitud fija garantizan estructura uniforme
- **Sencillez**: Interfaz fácil de usar con menú interactivo

## Requisitos
- Java JDK 8 o superior
- Permisos de lectura/escritura en el directorio de trabajo

## Autor
Kevin Gómez Valderas - 2º Desarrollo de Aplicaciones Multiplataforma (DAM)