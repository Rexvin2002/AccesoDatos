# SequentialWriting

## Descripción  
Este programa Java permite crear y modificar archivos de texto mediante operaciones secuenciales, ofreciendo un menú interactivo para gestionar archivos de texto.

## Características  
- **Creación de archivos nuevos** con contenido personalizado  
- **Añadir texto al final** de archivos existentes  
- **Insertar texto al principio** de archivos existentes  
- **Interfaz de menú intuitiva** con validación de entrada  
- **Manejo de errores** para operaciones de E/S  
- **Soporte para archivos .txt**  

## Uso  
1. Compilar el programa: `javac Main/SequentialWriting.java`  
2. Ejecutar el programa: `java Main.SequentialWriting`  
3. Seleccionar una opción del menú:  
   - **1. Crear archivo**:  
     - Ingresar nombre del archivo (ej. `documento.txt`)  
     - Escribir contenido  
   - **2. Añadir al inicio**:  
     - Seleccionar archivo existente  
     - Escribir contenido a insertar al principio  
   - **3. Añadir al final**:  
     - Seleccionar archivo existente  
     - Escribir contenido a añadir al final  
   - **4. Salir**: Finaliza el programa  

## Ejemplo de uso  
```
Menu:
1. Create a new file
2. Add text to the beginning of a file
3. Add text to the end of a file
4. Exit
Choose an option: 1
Enter the file name (including .txt): ejemplo.txt
Enter the content to write to the file:
Este es el contenido inicial
File created successfully.
```

## Requisitos  
- Java JDK 8 o superior  
- Permisos de escritura en el directorio de trabajo  

## Notas técnicas  
- Usa BufferedWriter para operaciones eficientes de escritura  
- Implementa StringBuilder para manipulación de contenido  
- Incluye manejo de excepciones para entradas inválidas  

## Autor  
Kevin Gómez Valderas - 2º Desarrollo de Aplicaciones Multiplataforma (DAM)