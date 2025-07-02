# README - SequentialFileReading2

## Descripción
Clase Java mejorada para leer y analizar archivos y directorios de forma secuencial. Permite buscar texto específico dentro de archivos y muestra información detallada sobre archivos y directorios, incluyendo metadatos avanzados.

## Características principales
- Búsqueda recursiva de texto en archivos dentro de directorios
- Visualización detallada de metadatos:
  - Información básica (nombre, rutas, tipo)
  - Tamaño en bytes, KB y MB
  - Conteo de archivos y subdirectorios
  - Permisos (lectura, escritura, ejecución)
  - Fechas de creación y última modificación
- Interfaz interactiva para búsquedas múltiples
- Soporte para rutas relativas y absolutas

## Uso
1. Compilar el archivo `SequentialFileReading2.java`
2. Ejecutar el programa:
   ```
   java SequentialFileReading2
   ```
3. Introducir la ruta del directorio a analizar
4. Ingresar el texto a buscar
5. El programa mostrará:
   - Metadatos del directorio
   - Ubicaciones exactas (archivo, línea, columna) donde aparece el texto buscado
6. Opción para realizar nuevas búsquedas

## Requisitos
- Java JDK 8 o superior
- Permisos de lectura en los directorios/archivos a analizar
- Sistema operativo compatible con Java NIO para obtener atributos de creación

## Autor
Kevin Gómez Valderas - 2ºDAM

## Notas
- La búsqueda de texto es sensible a mayúsculas/minúsculas
- Solo busca en archivos regulares (no directorios)
- Muestra información detallada de cada coincidencia encontrada
- Incluye fecha de creación usando Java NIO (mejora sobre versión anterior)