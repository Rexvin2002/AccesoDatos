# ShowSequentialFileAndFolderDetails

## Descripción  
Este programa Java permite analizar archivos y directorios mostrando información detallada, incluyendo un innovador contador de vocales para archivos de texto. Proporciona una visión completa de las propiedades del sistema de archivos con funcionalidades avanzadas.

## Características principales  
- **Análisis detallado** de archivos y directorios:
  - Información básica (nombre, rutas, tipo)
  - Tamaño en bytes/KB/MB (con cálculo recursivo para directorios)
  - Permisos (lectura/escritura/ejecución)
  - Fechas de modificación
- **Contador de vocales** integrado para archivos de texto:
  - Muestra recuento individual para A, E, I, O, U
  - Implementación eficiente con BufferedReader
- **Exploración recursiva** de directorios
- **Doble modo de uso**:
  - Interactivo (pide ruta al usuario)
  - Por línea de comandos (acepta ruta como argumento)

## Uso  
1. Compilación:  
   `javac Main/ShowSequentialFileAndFolderDetails.java`

2. Ejecución:  
   Modo interactivo:  
   `java Main.ShowSequentialFileAndFolderDetails`  
   Modo con parámetro:  
   `java Main.ShowSequentialFileAndFolderDocuments /ruta/analizar`

3. Durante la ejecución:
   - Introducir ruta (o "1" para salir)
   - El programa mostrará:
     - Información de existencia
     - Detalles completos
     - Conteo de vocales (para archivos de texto)
     - Contenido completo (para directorios)

## Ejemplo de salida
```
Enter a path (1 to exit): ejemplo.txt

---------------------------------------
The file or directory ejemplo.txt exists: true
---------------------------------------

---------------------------------------
Type: File
Name: ejemplo.txt
Absolute path: /ruta/ejemplo.txt
Size: 1024 bytes (1.00 KB, 0.00 MB)
Vowel counts: A=15, E=20, I=8, O=12, U=5
Last modification: 15/03/2023 10:30:45
---------------------------------------
```

## Requisitos técnicos
- Java JDK 8+
- Permisos de lectura en los archivos/directorios analizados
- Sistema de archivos compatible con Java IO

## Ventajas
- **Eficiencia**: Cálculos optimizados para directorios grandes
- **Precisión**: Conteo exacto de vocales incluyendo mayúsculas/minúsculas
- **Flexibilidad**: Funciona tanto con rutas absolutas como relativas

## Autor
Kevin Gómez Valderas - 2º Desarrollo de Aplicaciones Multiplataforma (DAM)