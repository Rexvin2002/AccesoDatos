package Main;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
import java.io.*;
import java.nio.charset.Charset;

/**
 *
 * PROCESO DE EJECUCIÓN
 *
 * javac Main/FileEncoding.java java Main.FileEncoding
 * "C:\Users\kgv17\OneDrive\Documents\GitHub\AccesoDatos\Ficheros\Codificación de
 * Ficheros\FileEncoding\src\Resources\ArchivoEjemplo.txt" ASCII
 * "C:\Users\kgv17\OneDrive\Documents\GitHub\AccesoDatos\Ficheros\Codificación de
 * Ficheros\FileEncoding\src\Resources\ArchivoEjemplo_encoded.txt" UTF-8
 *
 * Formato de ejecución: java Main.FileEncoding <inputPath> <inputEncoding>
 * <outputPath> <outputEncoding>
 *
 */
class FileEncoding {

    public static void main(String[] args) {
        // Verificar que se reciban exactamente 4 argumentos
        if (args.length != 4) {
            System.out.println("Usage: java Main.FileEncoding <inputPath> <inputEncoding> <outputPath> <outputEncoding>");
            return; // Salir si la cantidad de argumentos es incorrecta
        }

        // Asignar los argumentos a variables descriptivas
        String inputPath = args[0];         // Ruta del archivo de entrada
        String inputEncoding = args[1];      // Codificación del archivo de entrada
        String outputPath = args[2];         // Ruta del archivo de salida
        String outputEncoding = args[3];     // Codificación del archivo de salida

        // Validar los encodings
        if (!isValidEncoding(inputEncoding) || !isValidEncoding(outputEncoding)) {
            System.out.println("Invalid encoding. Supported encodings are: ASCII, UTF-8, UTF-16, ISO-8859-1");
            return; // Salir si los encodings no son válidos
        }

        // Intentar convertir el archivo y manejar posibles excepciones
        try {
            convertFile(inputPath, inputEncoding, outputPath, outputEncoding);
            System.out.println("File converted successfully."); // Confirmar éxito
        } catch (FileNotFoundException e) {
            System.err.println("Error: File not found - " + e.getMessage()); // Error si no se encuentra el archivo
        } catch (IOException e) {
            System.err.println("Error: IO Exception - " + e.getMessage()); // Error de entrada/salida
        } catch (Exception e) {
            System.err.println("An unexpected error occurred - " + e.getMessage()); // Error inesperado
        }
    }

    /**
     * Convierte un archivo de un encoding a otro.
     *
     * @param inputPath Ruta del archivo de entrada.
     * @param inputEncoding Codificación del archivo de entrada.
     * @param outputPath Ruta del archivo de salida.
     * @param outputEncoding Codificación del archivo de salida.
     * @throws IOException si ocurre un error de entrada/salida.
     */
    private static void convertFile(String inputPath, String inputEncoding, String outputPath, String outputEncoding) throws IOException {
        // Uso de try-with-resources para garantizar el cierre automático de los recursos
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputPath), Charset.forName(inputEncoding))); BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputPath), Charset.forName(outputEncoding)))) {

            String line; // Variable para almacenar cada línea leída
            while ((line = reader.readLine()) != null) { // Leer línea por línea
                writer.write(line); // Escribir la línea en el archivo de salida
                writer.newLine();   // Agregar un salto de línea
            }
        }
    }

    /**
     * Valida si el encoding proporcionado es uno de los soportados.
     *
     * @param encoding Codificación a validar.
     * @return true si el encoding es válido, false en caso contrario.
     */
    private static boolean isValidEncoding(String encoding) {
        // Usar un switch para comprobar los encodings válidos
        return switch (encoding.toUpperCase()) {
            case "ASCII", "UTF-8", "UTF-16", "ISO-8859-1" ->
                true; // Encodings válidos
            default ->
                false; // No válido
        };
    }
}
