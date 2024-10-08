package Unidad01.Practica09;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdvancedBinaryRecord2 {

    public Record records;

    public static void main(String[] args) throws IOException {
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
            
            try (Scanner scanner = new Scanner(System.in)) {
                
                List<String> campos = Arrays.asList("ID", "NAME", "DIRECTION", "ZC");
                List<Integer> camposLength = Arrays.asList(9, 32, 32, 5);
                
                ManejadorRegistros manejador = new ManejadorRegistros("src/Unidad01/Practica09/archivo_binario_2.dat", campos, camposLength);
                
                boolean running = true;
                
                while (running) {
                    System.out.println("Menu de opciones:");
                    System.out.println("1. Insertar registro");
                    System.out.println("2. Leer registro");
                    System.out.println("3. Actualizar registro");
                    System.out.println("4. Eliminar registro");
                    System.out.println("5. Salir");
                    System.out.print("Selecciona una opción: ");
                    
                    int opcion = scanner.nextInt();
                    scanner.nextLine(); // Limpiar el buffer
                    
                    switch (opcion) {
                        case 1 -> {
                            // Insertar registro
                            Map<String, String> nuevoRegistro = new HashMap<>();
                            for (String campo : campos) {
                                System.out.print("Introduce " + campo + ": ");
                                String valor = scanner.nextLine();
                                nuevoRegistro.put(campo, valor);
                            }
                            manejador.insertarDatos(nuevoRegistro, ManejadorRegistros.getRegistros().size()); // Inserta al final
                            System.out.println("Registro insertado correctamente.");
                        }
                            
                        case 2 -> {
                            // Leer registro
                            System.out.print("Introduce el número de registro a leer: ");
                            int numRegistroLeer = scanner.nextInt();
                            scanner.nextLine(); // Limpiar el buffer
                            String datosRegistro = manejador.leerDatosString(numRegistroLeer);
                            System.out.println("Datos del registro: " + datosRegistro);
                        }
                            
                        case 3 -> {
                            // Actualizar registro
                            System.out.print("Introduce el número de registro a actualizar: ");
                            int numRegistroActualizar = scanner.nextInt();
                            scanner.nextLine(); // Limpiar el buffer
                            
                            Map<String, String> actualizaciones = new HashMap<>();
                            for (String campo : campos) {
                                System.out.print("Introduce nuevo valor para " + campo + " (dejar vacío para no cambiar): ");
                                String valor = scanner.nextLine();
                                if (!valor.isEmpty()) {
                                    actualizaciones.put(campo, valor);
                                }
                            }
                            manejador.actualizar(numRegistroActualizar, actualizaciones);
                            System.out.println("Registro actualizado correctamente.");
                        }
                            
                        case 4 -> {
                            // Eliminar registro
                            System.out.print("Introduce el número de registro a eliminar: ");
                            int numRegistroEliminar = scanner.nextInt();
                            scanner.nextLine(); // Limpiar el buffer
                            manejador.delete(numRegistroEliminar);
                            System.out.println("Registro eliminado correctamente.");
                        }
                            
                        case 5 -> {
                            // Salir
                            running = false;
                            System.out.println("Saliendo del programa...");
                        }
                            
                        default -> System.out.println("Opción no válida. Intenta de nuevo.");
                    }
                }
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(AdvancedBinaryRecord2.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
} 
