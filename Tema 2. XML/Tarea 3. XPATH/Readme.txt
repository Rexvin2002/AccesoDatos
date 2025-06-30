# README - ConsultasXPath

## 📝 Descripción
Aplicación Java para realizar consultas XPath sobre archivos XML de catálogos de libros. El programa demuestra diferentes tipos de consultas XPath para extraer información específica de un archivo XML estructurado.

## 🔧 Características principales
- **5 consultas XPath** de ejemplo:
  1. Todos los títulos de libros
  2. Autor de un libro específico (por ID)
  3. Libros con precio superior a 30
  4. Fechas de publicación
  5. Primer libro del catálogo
- **Manejo de resultados** como nodos o valores simples
- **Visualización clara** de resultados en consola
- **Validación de estructura XML**

## 🛠️ Uso
1. **Compilación**:
```bash
javac Unidad02/Practica03/ConsultasXPath.java
```

2. **Ejecución** (requiere archivo BOOKS.xml en el mismo directorio):
```bash
java Unidad02.Practica03.ConsultasXPath
```

3. **Salida esperada**:
```
Consulta 1 - Títulos de todos los libros:
XML Developer's Guide
Midnight Rain
...

Consulta 2 - Autor del libro con id 'bk102': 
Ralls, Kim

Consulta 3 - Libros con precio superior a 30:
Maeve Ascendant
...
```

## ⚙️ Requisitos
- Java JDK 8 o superior
- Archivo BOOKS.xml válido en el directorio de ejecución
- Permisos de lectura del archivo XML

## 👨‍💻 Autor
Kevin Gómez Valderas

## 💡 Consultas implementadas
1. `//book/title` - Todos los títulos
2. `//book[@id='bk102']/author` - Autor por ID
3. `//book[price>30]/title` - Libros caros
4. `//book/publish_date` - Fechas publicación
5. `//book[1]/title` - Primer libro

## 📌 Dependencias
- API estándar de Java para XML (javax.xml.parsers)
- API XPath de Java (javax.xml.xpath)
- No requiere librerías externas

## 🛡️ Manejo de errores
- Archivo no encontrado
- XML mal formado
- Expresiones XPath inválidas
- Problemas de E/S