# README - Procesador XML de Libros

## Descripción
Este proyecto contiene dos programas Java para procesar archivos XML de catálogos de libros:

1. **MostrarTitulos**: Extrae y muestra los títulos de los libros de un archivo XML
2. **TraducirXML**: Traduce un catálogo de libros de inglés a español y genera un nuevo archivo XML

## Programas incluidos

### MostrarTitulos.java
- Lee el archivo BOOKS.xml
- Extrae todos los títulos de libros (etiquetas <title>)
- Muestra la lista de títulos en consola

### TraducirXML.java
- Lee el archivo BOOKS.xml
- Crea un nuevo documento XML con etiquetas traducidas al español
- Guarda el resultado en libros.xml
- Incluye funcionalidad para mostrar los títulos originales

## Requisitos
- Java 8 o superior
- Archivo BOOKS.xml en el directorio del proyecto

## Uso
1. Compilar:
```
javac MostrarTitulos.java
javac TraducirXML.java
```

2. Ejecutar:
```
java MostrarTitulos
java TraducirXML
```

## Resultados
- MostrarTitulos: Muestra lista de títulos en consola
- TraducirXML: Genera archivo libros.xml con la estructura traducida

## Autor
Kevin Gómez Valderas