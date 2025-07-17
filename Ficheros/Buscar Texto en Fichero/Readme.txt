# SearchTextOnFile

## Descripción  
Esta clase Java permite buscar texto dentro de archivos en un directorio especificado, mostrando información detallada sobre cada coincidencia encontrada. Además, proporciona información completa sobre los archivos y directorios analizados.

## Características  
- Búsqueda recursiva de texto en todos los archivos de un directorio  
- Muestra información detallada de archivos y directorios:  
  - Tipo (archivo/directorio)  
  - Rutas absoluta y relativa  
  - Tamaño en bytes, KB y MB  
  - Permisos (lectura, escritura, ejecución)  
  - Fechas de creación y última modificación  
- Información precisa sobre coincidencias encontradas (archivo, línea y columna)  
- Interfaz interactiva en consola  
- Manejo de errores para operaciones de E/S  

## Uso  
1. Compilar el archivo: `javac Main/SearchTextOnFile.java`  
2. Ejecutar el programa: `java Main.SearchTextOnFile`  
3. Introducir la ruta del directorio a analizar  
4. Introducir el texto a buscar  
5. El programa mostrará:  
   - Información detallada del directorio  
   - Todas las coincidencias encontradas (archivo, línea y columna)  
6. Opción para realizar nuevas búsquedas o salir del programa  

## Ejemplo de salida  
```
Enter the directory path (1 to exit): /ruta/ejemplo
Enter text to search in files: ejemplo

---------------------------------------
Type: Directory
Name: ejemplo
Absolute path: /ruta/ejemplo
Relative path: /ruta/ejemplo
Parent path: /ruta
Size: 2048 bytes (2.00 KB, 0.00 MB)
Number of files: 5
Number of folders: 2
Is hidden: No
Permissions: rwx
Creation date: 15/03/2023 10:30:45
Last modification: 20/04/2023 14:25:30
---------------------------------------

Searching for "ejemplo" in files...
Found in File: archivo1.txt, Line: 3, Column: 12
Found in File: documento.pdf, Line: 7, Column: 5

Would you like to search again? (y/n)
```

## Requisitos  
- Java JDK 8 o superior  
- Permisos de lectura en los directorios a analizar  

## Autor  
Kevin Gómez Valderas - 2º Desarrollo de Aplicaciones Multiplataforma (DAM)