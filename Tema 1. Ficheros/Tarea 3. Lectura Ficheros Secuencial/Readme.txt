# README - SequentialFileReading

## Descripción
Clase Java para leer y analizar archivos y directorios de forma secuencial. Proporciona información detallada sobre archivos y directorios, incluyendo metadatos, conteo de vocales en archivos de texto y análisis de permisos.

## Características
- Lectura recursiva de directorios y su contenido
- Mostrar metadatos completos de archivos/directorios:
  - Tipo (archivo/directorio)
  - Rutas (absoluta, relativa, padre)
  - Tamaño (bytes, KB, MB)
  - Permisos (lectura, escritura, ejecución)
  - Fecha de última modificación
- Conteo de vocales en archivos de texto
- Soporte para entrada por argumentos o interacción por consola
- Cálculo automático del tamaño de directorios (incluyendo subdirectorios)

## Uso
1. Compilar el archivo `SequentialFileReading.java`
2. Ejecutar el programa:
   - Sin argumentos: modo interactivo (`java SequentialFileReading`)
   - Con argumento: analiza la ruta especificada (`java SequentialFileReading /ruta/a/analizar`)
3. En modo interactivo:
   - Introducir la ruta a analizar
   - Introducir `1` para salir del programa

## Requisitos
- Java JDK 8 o superior
- Permisos de lectura en los archivos/directorios a analizar

## Autor
Kevin Gómez Valderas