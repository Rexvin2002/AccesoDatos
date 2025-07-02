# README - AdvancedBinaryRecord

## 📝 Descripción
Clase Java avanzada para gestión de registros en archivos binarios con campos de longitud fija. Ofrece operaciones CRUD completas (Crear, Leer, Actualizar, Eliminar) y funciones avanzadas de consulta y manipulación de datos.

## 🔧 Características principales
- **Almacenamiento estructurado** en archivos binarios
- **Soporte para campos de longitud fija** con codificación UTF-8
- **Operaciones avanzadas**:
  - Inserción, lectura, modificación y eliminación de registros
  - Selección de campos específicos
  - Consulta por columnas completas
  - Visualización de registros como listas o mapas
  - Actualizaciones parciales o completas
- **Acceso aleatorio eficiente** con `RandomAccessFile`
- **Interfaz de menú interactivo** con validación de entrada

## 🛠️ Uso
1. **Compilación**:
```bash
javac Unidad01/Practica09/AdvancedBinaryRecord.java
```

2. **Ejecución**:
```bash
java Unidad01.Practica09.AdvancedBinaryRecord
```

3. **Estructura predefinida**:
   - ID (9 caracteres)
   - NAME (32 caracteres)
   - DIRECTION (32 caracteres)
   - ZC (5 caracteres)

## 📋 Funcionalidades del menú
1. **Insertar registro**: Añade nuevos registros en posiciones específicas
2. **Leer registro**: Muestra el contenido completo de un registro
3. **Modificar registro**: Edita todos los campos de un registro existente
4. **Seleccionar campo**: Consulta un campo específico de un registro
5. **Seleccionar columna**: Muestra todos los valores de una columna
6. **Seleccionar fila como lista**: Visualiza un registro como lista de valores
7. **Seleccionar fila como mapa**: Visualiza un registro como mapa clave-valor
8. **Actualizar registro**: Permite actualizaciones parciales o completas
9. **Eliminar registro**: Borra un registro específico
10. **Salir**: Finaliza la ejecución del programa

## ⚙️ Requisitos
- Java JDK 8 o superior
- Permisos de lectura/escritura en el directorio de trabajo
- Sistema operativo compatible con Java NIO

## 📊 Estructura técnica
- Registros de longitud fija (78 bytes)
- Campos alineados y rellenados con espacios
- Codificación UTF-8 para caracteres especiales
- Manejo eficiente de memoria con buffers

## 👨‍💻 Autor
Kevin Gómez Valderas

## 💡 Notas adicionales
- Incluye 3 registros de ejemplo al iniciar
- Validación robusta de entrada de usuario
- Compatible con caracteres especiales y acentuados
- Mensajes de error descriptivos
- Opción de cancelación en cualquier operación