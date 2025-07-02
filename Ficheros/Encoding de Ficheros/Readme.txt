# README - FileConverter

## Descripción
Herramienta Java para conversión de codificación de archivos de texto entre diferentes formatos de caracteres. Permite transformar archivos entre codificaciones comunes como ASCII, UTF-8, UTF-16 e ISO-8859-1.

## Características principales
- **Conversión entre codificaciones** soportadas:
  - ASCII
  - UTF-8
  - UTF-16
  - ISO-8859-1
- **Modo de ejecución por línea de comandos**
- **Validación automática** de parámetros y codificaciones
- **Manejo robusto de errores** con mensajes descriptivos
- **Eficiente procesamiento** línea por línea

## Uso
Compilar y ejecutar desde la línea de comandos:

```bash
# Compilación (desde el directorio src)
javac Unidad01/Practica07/FileConverter.java

# Ejecución
java Unidad01.Practica07.FileConverter <inputPath> <inputEncoding> <outputPath> <outputEncoding>
```

### Parámetros requeridos:
1. `<inputPath>` - Ruta completa del archivo de entrada
2. `<inputEncoding>` - Codificación del archivo de entrada (ASCII/UTF-8/UTF-16/ISO-8859-1)
3. `<outputPath>` - Ruta completa del archivo de salida
4. `<outputEncoding>` - Codificación deseada para el archivo de salida

### Ejemplo práctico:
```bash
java Unidad01.Practica07.FileConverter C:\datos\entrada.txt ASCII C:\datos\salida.txt UTF-8
```

## Requisitos
- Java JDK 8 o superior
- Permisos de lectura/escritura en las rutas especificadas

## Manejo de errores
El programa detecta y reporta:
- Número incorrecto de argumentos
- Codificaciones no soportadas
- Archivos de entrada no encontrados
- Problemas de permisos o E/S

## Autor
Kevin Gómez Valderas

## Notas técnicas
- Utiliza `try-with-resources` para manejo automático de recursos
- Implementa buffers para mejor rendimiento con archivos grandes
- Conserva los saltos de línea originales
- Compatible con múltiples plataformas (Windows/Linux/Mac)