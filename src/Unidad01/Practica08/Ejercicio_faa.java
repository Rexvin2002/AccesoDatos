
package Unidad01.Practica08;

/**
 *
 * @author kgv17
 */
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Ejercicio_faa {

    private static final String FILE_NAME = "C:/Users/kgv17/OneDrive/Escritorio/archivo.txt";
    private static final int RECORD_SIZE = 64; // 40 bytes para String + 4 bytes para int + 8 bytes para long + 12 bytes para padding

    public static void main(String[] args) {
        try {
            File file = new File(FILE_NAME);
            if (!file.exists()) {
                file.createNewFile();
            }

            Scanner scanner = new Scanner(System.in);
            int option;
            do {
                System.out.println("=== MENU ===");
                System.out.println("1. Añadir Registro");
                System.out.println("2. Leer Registro");
                System.out.println("3. Modificar Registro");
                System.out.println("4. Salir");
                System.out.print("Seleccione una opción: ");
                option = scanner.nextInt();

                switch (option) {
                    case 1:
                        añadirRegistro();
                        break;
                    case 2:
                        leerRegistro();
                        break;
                    case 3:
                        modificarRegistro();
                        break;
                    case 4:
                        System.out.println("Saliendo del programa...");
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }
            } while (option != 4);

            scanner.close();
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void añadirRegistro() {
        try (RandomAccessFile file = new RandomAccessFile(FILE_NAME, "rw")) {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Ingrese el nombre (max 40 caracteres): ");
            String nombre = scanner.nextLine();
            System.out.print("Ingrese el número (int): ");
            int numero = scanner.nextInt();
            System.out.print("Ingrese la fecha (yyyy-MM-dd): ");
            scanner.nextLine();  // Clear the buffer
            String fechaStr = scanner.nextLine();

            // Parsear fecha
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date fecha = sdf.parse(fechaStr);
            long fechaMillis = fecha.getTime();

            // Posicionar al final del archivo
            long pos = file.length();
            file.seek(pos);

            // Escribir los datos
            file.writeUTF(String.format("%-40s", nombre)); // Escribir nombre con padding
            file.writeInt(numero); // Escribir número
            file.writeLong(fechaMillis); // Escribir fecha como long

            System.out.println("Registro añadido correctamente.");
        } catch (Exception e) {
            System.err.println("Error al añadir registro: " + e.getMessage());
        }
    }

    private static void leerRegistro() {
        try (RandomAccessFile file = new RandomAccessFile(FILE_NAME, "r")) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Ingrese el número de registro a leer: ");
            int registroNum = scanner.nextInt();

            long pos = registroNum * RECORD_SIZE;
            if (pos >= file.length()) {
                System.out.println("Registro no encontrado.");
                return;
            }

            file.seek(pos);
            String nombre = file.readUTF();
            int numero = file.readInt();
            long fechaMillis = file.readLong();
            Date fecha = new Date(fechaMillis);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            System.out.println("Nombre: " + nombre.trim());
            System.out.println("Número: " + numero);
            System.out.println("Fecha: " + sdf.format(fecha));
        } catch (IOException e) {
            System.err.println("Error al leer registro: " + e.getMessage());
        }
    }

    private static void modificarRegistro() {
        try (RandomAccessFile file = new RandomAccessFile(FILE_NAME, "rw")) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Ingrese el número de registro a modificar: ");
            int registroNum = scanner.nextInt();

            long pos = registroNum * RECORD_SIZE;
            if (pos >= file.length()) {
                System.out.println("Registro no encontrado.");
                return;
            }

            file.seek(pos);

            scanner.nextLine();  // Clear the buffer
            System.out.print("Ingrese el nuevo nombre (max 40 caracteres): ");
            String nombre = scanner.nextLine();
            System.out.print("Ingrese el nuevo número (int): ");
            int numero = scanner.nextInt();
            System.out.print("Ingrese la nueva fecha (yyyy-MM-dd): ");
            scanner.nextLine();  // Clear the buffer
            String fechaStr = scanner.nextLine();

            // Parsear fecha
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date fecha = sdf.parse(fechaStr);
            long fechaMillis = fecha.getTime();

            // Escribir los datos actualizados
            file.writeUTF(String.format("%-40s", nombre));
            file.writeInt(numero);
            file.writeLong(fechaMillis);

            System.out.println("Registro modificado correctamente.");
        } catch (Exception e) {
            System.err.println("Error al modificar registro: " + e.getMessage());
        }
    }
}

