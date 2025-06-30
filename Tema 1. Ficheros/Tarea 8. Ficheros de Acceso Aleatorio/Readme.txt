# README - BinaryRecord

## 📝 Descripción
Clase Java para manejo de registros en archivos binarios con campos de longitud fija. Permite operaciones CRUD (Crear, Leer, Actualizar) sobre registros estructurados.

## 🔧 Características principales
- **Almacenamiento eficiente** en archivos binarios
- **Estructura de registros** con campos de longitud fija
- **Operaciones básicas**:
  - Inserción de registros
  - Lectura de registros
  - Modificación de registros
- **Acceso aleatorio** mediante `RandomAccessFile`
- **Interfaz de menú** interactivo

## 🛠️ Uso
1. **Compilación**:
```bash
javac Unidad01/Practica08/BinaryRecord.java
```

2. **Ejecución**:
```bash
java Unidad01.Practica08.BinaryRecord
```

3. **Estructura de campos predefinida**:
   - ID (9 caracteres)
   - NAME (32 caracteres)
   - DIRECTION (32 caracteres)
   - ZC (5 caracteres)

## 📋 Funcionalidades del menú
1. **Insertar registro**: Permite añadir nuevos registros en posiciones específicas
2. **Leer registro**: Muestra el contenido de un registro en una posición dada
3. **Modificar registro**: Actualiza los campos de un registro existente
4. **Salir**: Finaliza la ejecución del programa

## ⚙️ Requisitos
- Java JDK 8 o superior
- Permisos de lectura/escritura en el directorio de trabajo
- Sistema operativo compatible con Java NIO

## 📊 Estructura del archivo binario
- Registros de longitud fija (78 bytes cada uno)
- Campos alineados y rellenados con espacios
- Codificación UTF-8 para caracteres especiales

## 👨‍💻 Autor
Kevin Gómez Valderas

## 💡 Notas adicionales
- Los registros existentes se sobrescriben al insertar en la misma posición
- El programa incluye 3 registros de ejemplo al iniciar
- Compatible con caracteres especiales y acentuados