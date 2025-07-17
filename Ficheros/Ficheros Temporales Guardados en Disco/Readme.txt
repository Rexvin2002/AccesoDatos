# FileProcessor

## Descripción
Este programa Java procesa archivos de texto de manera eficiente y segura, implementando un sistema mejorado de manejo de archivos temporales y procesamiento de texto.

## Características principales

### Mejoras implementadas:
- **Manejo optimizado de archivos temporales**:
  - Ya no se eliminan automáticamente al salir
  - Se renombran directamente al archivo final para mayor eficiencia
- **Procesamiento más robusto**:
  - Validación exhaustiva de archivos de entrada
  - Manejo de errores mejorado
- **Mayor eficiencia**:
  - Operaciones de I/O optimizadas
  - Eliminación de pasos redundantes

### Funcionalidades:
1. Validación completa de archivos de entrada
2. Creación segura de archivos temporales
3. Procesamiento de texto con:
   - Normalización de espacios
   - Capitalización inteligente
4. Generación de archivos de salida con prefijo "processed_"

## Uso

```bash
# Compilación
javac Main/FileProcessor.java

# Ejecución
java Main.FileProcessor
```

Durante la ejecución:
1. Introduzca la ruta del archivo a procesar
2. El programa mostrará:
   - Ruta original
   - Ruta temporal
   - Ruta final del archivo procesado
3. Opción para procesar otro archivo o salir

## Ejemplo de salida

```
Por favor introduzca la ruta de un archivo (o 'exit' para salir): documento.txt

Ruta del archivo original: /carpeta/documento.txt
Ruta del archivo temporal: /tmp/tempFile_12345.txt
Archivo procesado creado en: /carpeta/processed_documento.txt
Archivo procesado con éxito.
```

## Requisitos
- Java JDK 8 o superior
- Permisos de lectura/escritura en los directorios involucrados

## Notas técnicas
- Los archivos temporales ya no se eliminan automáticamente
- Se utiliza renombrado directo para mayor eficiencia
- Procesamiento de texto optimizado

## Autor
Kevin Gómez Valderas - 2º Desarrollo de Aplicaciones Multiplataforma (DAM)