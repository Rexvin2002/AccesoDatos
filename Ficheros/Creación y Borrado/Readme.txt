# CreationAndDeletion  

## Descripción  
Esta clase Java permite crear una estructura de directorios y archivos que representan una familia ficticia, y proporciona funcionalidad para eliminar archivos específicos solicitados por el usuario.  

## Características  
- Crea automáticamente una estructura jerárquica de directorios que simula una familia (Abuelo, Abuela, Padre, Madre, hijos)  
- Genera archivos de texto que representan a los miembros de la familia (Hijo1.txt, Hija2.txt, etc.)  
- Permite al usuario eliminar archivos específicos mediante la consola  
- Implementa búsqueda recursiva para localizar y eliminar archivos en cualquier nivel de la estructura  
- Incluye manejo de errores para operaciones de E/S  
- Interfaz interactiva en consola  

## Uso  
1. Compilar el archivo: `javac Main/CreationAndDeletion.java`  
2. Ejecutar el programa: `java Main.CreationAndDeletion`  
3. El programa creará automáticamente la estructura de directorios  
4. Introducir el nombre del archivo a borrar cuando se solicite (ej. "Hijo1.txt")  
5. Para salir del programa, introducir "1"  

## Estructura de archivos creada  
```
Estructura/  
├── Abuelo/  
│   ├── Padre/  
│   │   ├── Hijo1.txt  
│   │   └── Hija2.txt  
│   └── Madre/  
│       ├── Hijo3.txt  
│       └── Hija4.txt  
└── Abuela/  
    ├── Padre/  
    │   ├── Hijo5.txt  
    │   └── Hija6.txt  
    └── Madre/  
        ├── Hijo7.txt  
        └── Hija8.txt  
```

## Requisitos  
- Java JDK 8 o superior  
- Permisos de escritura en el directorio de ejecución  

## Autor  
Kevin Gómez Valderas - 2º Desarrollo de Aplicaciones Multiplataforma (DAM)