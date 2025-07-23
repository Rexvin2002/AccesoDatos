# Traductor XML de Libros

## 📝 Descripción
Aplicación Java para traducir archivos XML de catálogos de libros de inglés a español. El programa toma un archivo XML con estructura en inglés y genera un nuevo archivo XML con las etiquetas traducidas al español, conservando todos los datos.

## 🔧 Características principales
- **Traducción de etiquetas XML** de inglés a español
- **Conservación de estructura y datos** originales
- **Generación de nuevo archivo XML** con las traducciones
- **Visualización de títulos** de libros
- **Manejo de errores** para archivos mal formados

## 🛠️ Uso
1. **Compilación**:
```bash
javac Unidad02/Practica02/TraducirXML.java
```

2. **Ejecución** (requiere archivo BOOKS.xml en el mismo directorio):
```bash
java Unidad02.Practica02.TraducirXML
```

3. **Resultados**:
- Genera archivo `libros.xml` con las etiquetas traducidas
- Muestra lista de títulos de libros en consola

## 📋 Ejemplo de traducción
**Entrada (BOOKS.xml)**:
```xml
<book id="bk101">
  <title>XML Developer's Guide</title>
  <author>Gambardella, Matthew</author>
  <genre>Computer</genre>
</book>
```

**Salida (libros.xml)**:
```xml
<Libro id="bk101">
  <Titulo>XML Developer's Guide</Titulo>
  <Autor>Gambardella, Matthew</Autor>
  <Genero>Computer</Genero>
</Libro>
```

## ⚙️ Requisitos
- Java JDK 8 o superior
- Archivo BOOKS.xml en el directorio de ejecución
- Permisos de lectura/escritura en el directorio

## 👨‍💻 Autor
Kevin Gómez Valderas - 2ºDAM

## 💡 Notas técnicas
- Usa DOM (Document Object Model) para manipulación XML
- Traduce las siguientes etiquetas:
  - book → Libro
  - title → Titulo
  - author → Autor
  - genre → Genero
  - price → Precio
  - publish_date → FechaPublicacion
  - description → Descripcion
- Incluye módulo adicional `MostrarTitulos` para visualización simple

## 📌 Dependencias
- API estándar de Java para XML (javax.xml.parsers)
- No requiere librerías externas