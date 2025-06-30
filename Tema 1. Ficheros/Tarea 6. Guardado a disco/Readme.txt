# README - FileProcessor

## Descripción
Programa Java para procesar archivos de texto que realiza dos operaciones principales:
1. Convierte a mayúsculas la primera letra después de cada punto
2. Elimina espacios en blanco consecutivos

## Características principales
- **Procesamiento de texto avanzado**:
  - Normalización de espacios (elimina múltiples espacios)
  - Capitalización automática después de puntos
- **Manejo seguro de archivos**:
  - Usa archivos temporales para el procesamiento
  - Genera archivo de salida con prefijo "processed_"
  - Muestra rutas completas de archivos
- **Robusto manejo de errores**:
  - Captura excepciones de E/S
  - Proporciona mensajes de error descriptivos

## Uso
1. Compilar el programa:
   ```bash
   javac FileProcessor.java
   ```
2. Ejecutar el programa:
   ```bash
   java FileProcessor
   ```
3. Introducir la ruta completa del archivo a procesar cuando se solicite

El programa generará:
- Un archivo temporal durante el procesamiento
- Un archivo final con prefijo "processed_" en el mismo directorio que el original

## Requisitos
- Java JDK 8 o superior
- Permisos de lectura/escritura en el directorio del archivo

## Ejemplo de transformación
**Entrada**:
```
hola.  esto   es   una prueba.  otro ejemplo.
```
**Salida**:
```
hola. Esto es una prueba. Otro ejemplo.
```

## Autor
Kevin Gómez Valderas

## Notas técnicas
- Conserva los saltos de línea originales
- El procesamiento es línea por línea
- El archivo temporal se elimina automáticamente al finalizar
- Compatible con diferentes codificaciones de texto