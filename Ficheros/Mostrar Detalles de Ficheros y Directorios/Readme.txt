# ShowFileAndFolderDetails

## Descripción  
Este programa Java permite visualizar información detallada sobre archivos y directorios, incluyendo sus propiedades, contenido y tamaño total. Es útil para analizar la estructura de directorios y obtener métricas sobre el uso de espacio.

## Características principales
- **Información detallada** de archivos y directorios:
  - Tipo (archivo/directorio)
  - Nombre y rutas (absoluta, relativa, padre)
  - Tamaño en bytes, KB y MB
  - Permisos (lectura, escritura, ejecución)
  - Fecha de última modificación
- **Listado recursivo** de contenidos de directorios
- **Cálculo de tamaño total** de directorios (incluyendo subdirectorios)
- **Interfaz interactiva** con opción para salir
- **Modo de línea de comandos** (acepta ruta como argumento)

## Uso
1. Compilar el programa:  
   `javac Main/ShowFileAndFolderDetails.java`

2. Ejecutar el programa:  
   `java Main.ShowFileAndFolderDetails`  
   o con parámetro de ruta:  
   `java Main.ShowFileAndFolderDetails /ruta/a/analizar`

3. Durante la ejecución:
   - Introducir ruta a analizar (o "1" para salir)
   - El programa mostrará:
     - Información básica de existencia
     - Detalles completos del archivo/directorio
     - Contenido completo (para directorios)

## Ejemplo de salida
```
Introduce una ruta (1 salir): /ejemplo

---------------------------------------
El archivo o directorio ejemplo existe: true
---------------------------------------

---------------------------------------
Tipo: Directorio
Nombre: ejemplo
Ruta absoluta: /ejemplo
Ruta relativa: /ejemplo
Ruta del padre: /
Tamaño: 2048 bytes (2.00 KB, 0.00 MB)
¿Está oculto?: No
Permisos: rwx
Última modificación: 15/03/2023 10:30:45
---------------------------------------

---------------------------------------
Tipo: Archivo
Nombre: archivo1.txt
Ruta absoluta: /ejemplo/archivo1.txt
...
```

## Requisitos
- Java JDK 8 o superior
- Permisos de lectura en los directorios a analizar

## Autor
Kevin Gómez Valderas - 2º Desarrollo de Aplicaciones Multiplataforma (DAM)