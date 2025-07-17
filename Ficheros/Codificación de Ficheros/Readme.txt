# FileEncoding - Conversor de Codificación de Archivos

## Descripción
Este programa Java permite convertir archivos de texto entre diferentes codificaciones de caracteres, incluyendo ASCII, UTF-8, UTF-16 e ISO-8859-1. Es especialmente útil para manejar problemas de compatibilidad entre sistemas con diferentes configuraciones regionales.

## Características principales

### Funcionalidades clave:
- **Conversión entre codificaciones**: Soporta ASCII, UTF-8, UTF-16 e ISO-8859-1
- **Validación robusta**: Verifica formatos de encoding y existencia de archivos
- **Manejo de errores**: Proporciona mensajes claros para diferentes tipos de errores
- **Eficiencia**: Utiliza buffers para operaciones de lectura/escritura óptimas

### Mejoras implementadas:
- Uso de try-with-resources para manejo automático de recursos
- Validación exhaustiva de parámetros de entrada
- Mensajes de error descriptivos
- Código limpio y bien documentado

## Uso

### Sintaxis básica:
```bash
java Main.FileEncoding <inputPath> <inputEncoding> <outputPath> <outputEncoding>
```

### Ejemplo práctico:
```bash
javac Main/FileEncoding.java
java Main.FileEncoding \
    "ruta/archivo_entrada.txt" \
    UTF-8 \
    "ruta/archivo_salida.txt" \
    ISO-8859-1
```

### Parámetros:
1. `<inputPath>`: Ruta completa del archivo de entrada
2. `<inputEncoding>`: Codificación del archivo de entrada (ASCII/UTF-8/UTF-16/ISO-8859-1)
3. `<outputPath>`: Ruta completa del archivo de salida
4. `<outputEncoding>`: Codificación deseada para el archivo de salida

## Requisitos del sistema
- Java JDK 8 o superior
- Permisos de lectura en el archivo de entrada
- Permisos de escritura en la ubicación de salida

## Manejo de errores
El programa detecta y notifica claramente:
- Número incorrecto de argumentos
- Codificaciones no soportadas
- Archivos de entrada no encontrados
- Problemas de permisos de acceso
- Errores inesperados durante el procesamiento

## Ejemplo de salida
```
File converted successfully.
```
o en caso de error:
```
Error: File not found - ruta/archivo_inexistente.txt
```

## Autor
Kevin Gómez Valderas - 2º Desarrollo de Aplicaciones Multiplataforma (DAM)