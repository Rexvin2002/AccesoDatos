package Unidad01.Practica08;


import java.io.*;
import java.util.*;

public class RegistroBinario {

    private final File f;
    private final List<String> campos;
    private final List<Integer> camposLength;
    private long longReg;
    private long numReg = 0;

    public RegistroBinario(String path, List<String> campos, List<Integer> camposLength) throws IOException {
        this.campos = campos;
        this.camposLength = camposLength;

        this.f = new File(path);
        if (!f.exists()) {
            f.createNewFile();
        }
        this.longReg = 0;

        System.out.println();
        for (int i = 0; i < campos.size(); i++) {
            String campo = campos.get(i);
            Integer length = camposLength.get(i);
            System.out.println(campo + ": " + length);
            this.longReg += length;
        }

        if (f.exists()) {
            this.numReg = f.length() / this.longReg;
        }
    }

    public long getNumReg() {
        return numReg;
    }

    public void insertar(Map<String, String> reg, long pos) {
        try (RandomAccessFile rndFile = new RandomAccessFile(this.f, "rws")) {
            rndFile.seek(pos * this.longReg);
            int total = campos.size();
            System.out.println();
            for (int i = 0; i < total; i++) {
                String nomCampo = campos.get(i);
                Integer longCampo = camposLength.get(i);
                String valorCampo = reg.get(nomCampo);

                if (valorCampo == null) {
                    valorCampo = "";
                }

                String valorCampoForm = String.format("%1$-" + longCampo + "s", valorCampo);
                System.out.println(nomCampo + ": " + valorCampoForm);
                rndFile.write(valorCampoForm.getBytes("UTF-8"), 0, longCampo);
            }

        } catch (Exception ex) {
            System.err.println("\nError: " + ex.getMessage());
        }
    }

    public Map<String, String> leer(long pos) {
        Map<String, String> reg = new HashMap<>();
        try (RandomAccessFile rndFile = new RandomAccessFile(this.f, "r")) {
            rndFile.seek(pos * this.longReg);
            byte[] buffer;

            for (int i = 0; i < campos.size(); i++) {
                Integer longCampo = camposLength.get(i);
                buffer = new byte[longCampo];
                rndFile.read(buffer, 0, longCampo);
                String valorCampo = new String(buffer, "UTF-8").trim();
                reg.put(campos.get(i), valorCampo);
            }

        } catch (Exception ex) {
            System.err.println("\nError: " + ex.getMessage());
        }
        return reg;
    }

    public void modificar(Map<String, String> reg, long pos) {
        insertar(reg, pos);
        System.out.println("\nPosición modificada exitosamente.");
    }

    public static void main(String[] args) {  
        try {  
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
            Scanner scanner = new Scanner(System.in);
            List<String> campos = Arrays.asList("DNI", "NOMBRE", "DIRECCION", "CP");
            List<Integer> camposLength = Arrays.asList(9, 32, 32, 5);
            
            try {
                RegistroBinario faa = new RegistroBinario("src/Unidad01/Practica08/archivo_binario_3.dat", campos, camposLength);
                
                Map<String, String> reg = new HashMap<>();
                
                // PRIMER REGISTRO
                reg.put("DNI", "11111111A");
                reg.put("NOMBRE", "Nombre y Apellidos 1");
                reg.put("DIRECCION", "Calle Principal Nº 7, Planta4, Letra J");
                reg.put("CP", "54321");
                faa.insertar(reg, 0);
                reg.clear();
                
                // SEGUNDO REGISTRO
                reg.put("DNI", "22222222B");
                reg.put("NOMBRE", "Nombre2");
                reg.put("DIRECCION", "Calle Principal Nº 7, Planta4, Letra J");
                reg.put("CP", "12345");
                faa.insertar(reg, 1);
                reg.clear();
                
                // TERCER REGISTRO
                reg.put("DNI", "33333333C");
                reg.put("NOMBRE", "Nombre3");
                reg.put("DIRECCION", "Direccion 3");
                reg.put("CP", "56789");
                faa.insertar(reg, 2);
                reg.clear();
                
                while (true) {
                    System.out.println("\nMenu:");
                    System.out.println("1. Insertar registro");
                    System.out.println("2. Leer registro");
                    System.out.println("3. Modificar registro");
                    System.out.println("4. Salir");
                    System.out.print("\nElija una opción: ");
                    
                    int opcion = scanner.nextInt();
                    scanner.nextLine(); // Consumir nueva línea
                    
                    switch (opcion) {
                        case 1 -> {
                            System.out.print("\nIngrese la posición para insertar: ");
                            long posInsertar = scanner.nextLong();
                            scanner.nextLine(); // Consumir nueva línea
                            
                            // Verifica si ya existe un registro en la posición especificada
                            Map<String, String> registroExistente = faa.leer(posInsertar);
                            
                            boolean isEmpty = registroExistente != null && registroExistente.values().stream().allMatch(String::isEmpty);
                            
                            if (!isEmpty) {
                                // Informar al usuario que ya hay un registro en esa posición
                                System.out.println("\nYa existe un registro en la posición " + posInsertar + ":");
                                registroExistente.forEach((campo, valor) -> System.out.println(campo + ": " + valor));
                            } else {
                                System.out.println("\nNo hay ningún registro en la posición " + posInsertar + ", se procederá a crear uno nuevo.");
                            }
                            
                            // Solicitar datos para el nuevo registro
                            Map<String, String> nuevoRegistro = new HashMap<>();
                            for (String campo : campos) {
                                System.out.print("Ingrese " + campo + ": ");
                                String valor = scanner.nextLine();
                                nuevoRegistro.put(campo, valor);
                            }
                            
                            // Insertar el registro en la posición especificada (sobreescribiendo si existe)
                            faa.insertar(nuevoRegistro, posInsertar);
                            System.out.println("\nRegistro insertado en la posición " + posInsertar + ".");
                        }
                        
                        case 2 -> {
                            System.out.print("\nIngrese la posición del registro a leer: ");
                            long posLeer = scanner.nextLong();
                            scanner.nextLine();
                            Map<String, String> registroLeido = faa.leer(posLeer);
                            if (registroLeido != null) {
                                System.out.println("\nRegistro leído:");
                                registroLeido.forEach((campo, valor) -> System.out.println(campo + ": " + valor));
                            } else {
                                System.out.println("\nNo se encontró un registro en la posición especificada.");
                            }
                        }
                        
                        case 3 -> {
                            System.out.print("\nIngrese la posición del registro a modificar: ");
                            long posModificar = scanner.nextLong();
                            scanner.nextLine();
                            Map<String, String> registroModificado = new HashMap<>();
                            System.out.println();
                            for (String campo : campos) {
                                System.out.print("Ingrese " + campo + ": ");
                                String valor = scanner.nextLine();
                                registroModificado.put(campo, valor);
                            }
                            faa.modificar(registroModificado, posModificar);
                        }
                        
                        case 4 -> {
                            System.out.println("\nSaliendo...");
                            return;
                        }

                        default -> System.out.println("\nOpción no válida.");
                    }
                }
                
            } catch (IOException e) {
                System.err.println("\nError: " + e.getMessage());
            }
        } catch (UnsupportedEncodingException e) {
            System.err.println("\nError: " + e.getMessage());
        }
    }
}