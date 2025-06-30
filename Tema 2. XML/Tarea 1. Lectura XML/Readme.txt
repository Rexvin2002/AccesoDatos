# README - XMLReader

## 📝 Descripción
Clase Java para leer y mostrar archivos XML de forma estructurada y legible. El programa analiza documentos XML y los muestra en la consola con un formato indentado que refleja la jerarquía del documento.

## 🔧 Características principales
- **Análisis de documentos XML** con formato indentado
- **Visualización jerárquica** de nodos y atributos
- **Manejo de diferentes tipos de nodos**:
  - Nodos de elementos
  - Atributos
  - Contenido de texto
- **Opciones de configuración**:
  - Ignora comentarios
  - Ignora espacios en blanco redundantes
- **Salida configurable** (consola o archivo)

## 🛠️ Uso
1. **Compilación**:
```bash
javac Unidad01/Practica10/XMLReader.java
```

2. **Ejecución**:
```bash
java Unidad01.Practica10.XMLReader archivo.xml
```

3. **Parámetros**:
   - `archivo.xml`: Ruta del archivo XML a analizar (obligatorio)

## 📋 Ejemplo de salida
```xml
<libro id="123">
  <titulo>
    El nombre del viento
  </titulo>
  <autor>
    Patrick Rothfuss
  </autor>
  <paginas>
    662
  </paginas>
</libro>
```

## ⚙️ Requisitos
- Java JDK 8 o superior
- Archivo XML bien formado
- Permisos de lectura para el archivo XML

## 👨‍💻 Autor
Kevin Gómez Valderas

## 💡 Notas técnicas
- Usa `DocumentBuilderFactory` para el análisis XML
- Implementa recursión para mostrar la estructura jerárquica
- Configuración optimizada para ignorar comentarios y espacios irrelevantes
- Manejo de errores para casos comunes:
  - Archivo no encontrado
  - Errores de análisis XML
  - Problemas de E/S

## 📌 Dependencias
- API estándar de Java para XML (javax.xml.parsers)
- No requiere librerías externas