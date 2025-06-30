# README - FileManager

## Descripción
Programa Java que permite gestionar archivos de texto mediante un menú interactivo. Ofrece funcionalidades básicas de creación y modificación de archivos.

## Características principales
- **Menú interactivo** con 4 opciones:
  1. Crear nuevos archivos
  2. Añadir texto al principio de archivos existentes
  3. Añadir texto al final de archivos existentes
  4. Salir del programa
- **Validación de entrada** para opciones no numéricas
- **Manejo de errores** para operaciones de archivo
- **Soporte para rutas relativas/absolutas** de archivos

## Uso
1. Compilar el archivo `FileManager.java`:
   ```
   javac FileManager.java
   ```
2. Ejecutar el programa:
   ```
   java FileManager
   ```
3. Seleccionar una opción del menú:
   - **Opción 1**: Crear nuevo archivo (solicita nombre y contenido)
   - **Opción 2**: Añadir texto al principio de archivo existente
   - **Opción 3**: Añadir texto al final de archivo existente
   - **Opción 4**: Salir del programa

## Requisitos
- Java JDK 8 o superior
- Permisos de lectura/escritura en el directorio de trabajo

## Autor
Kevin Gómez Valderas

## Notas técnicas
- Los archivos se crean con extensión `.txt` (se debe incluir en el nombre)
- El programa conserva el formato de saltos de línea existente
- Implementa BufferedWriter/BufferedReader para mejor rendimiento
- Maneja excepciones de E/S y entrada inválida