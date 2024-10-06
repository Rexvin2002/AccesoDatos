package Unidad01.Practica08;

/**
 *
 * @author kgv17
 */
import java.io.*;
import java.util.*;

public class Ejercicio_faa {

    private final File f;
    private final List<String> campos;
    private final List<Integer> camposLength;
    private long longReg;
    private long numReg = 0;

    public Ejercicio_faa(String path, List<String> campos, List<Integer> camposLength) throws IOException {
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
    }

    public static void main(String[] args) {  
        Scanner scanner = new Scanner(System.in);
        List<String> campos = Arrays.asList("DNI", "NOMBRE", "DIRECCION", "CP", "FECHA");
        List<Integer> camposLength = Arrays.asList(9, 32, 32, 5, 10); // Fecha en formato dd/MM/yyyy (10 caracteres)

        try {
            Ejercicio_faa faa = new Ejercicio_faa("src/Unidad01/Practica08/archivo_binario_3.dat", campos, camposLength);
            
            Map reg = new HashMap();
            
            // PRIMER REGISTRO
            reg.put("DNI", "11111111A");
            reg.put("NOMBRE", "Nombre y Apellidos 1");            
            reg.put("DIRECCION", "Calle Principal Nº 7, Planta4, Letra J");
            reg.put("CP", "543210");
            faa.insertar(reg,10);
            reg.clear();
            
            // SEGUNDO REGISTRO
            reg.put("DNI", "22222222B");
            reg.put("NOMBRE", "Nombre2");
            reg.put("CP", "123456");
            reg.put("DIRECCION", "Calle Principal Nº 7, Planta4, Letra J");
            faa.insertar(reg,9);
            reg.clear();
            
            // TERCER REGISTRO
            reg.put("DNI", "333333B");
            reg.put("NOMBRE", "Nombre3");
            reg.put("CP", "56789");
            reg.put("DIRECCION", "DIrECCION");
            faa.insertar(reg,0);
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
                        Map<String, String> nuevoRegistro = new HashMap<>();
                        for (String campo : campos) {
                            System.out.print("\nIngrese " + campo + ": ");
                            String valor = scanner.nextLine();
                            nuevoRegistro.put(campo, valor);
                        }
                        System.out.print("\nIngrese la posición para insertar: ");
                        long posInsertar = scanner.nextLong();
                        scanner.nextLine();
                        faa.insertar(nuevoRegistro, posInsertar);
                    }

                    case 2 -> {
                        System.out.print("\nIngrese la posición del registro a leer: ");
                        long posLeer = scanner.nextLong();
                        scanner.nextLine();
                        Map<String, String> registroLeido = faa.leer(posLeer);
                        System.out.println("\nRegistro leído:");
                        registroLeido.forEach((campo, valor) -> System.out.println(campo + ": " + valor));
                    }

                    case 3 -> {
                        System.out.print("\nIngrese la posición del registro a modificar: ");
                        long posModificar = scanner.nextLong();
                        scanner.nextLine();
                        Map<String, String> registroModificado = new HashMap<>();
                        for (String campo : campos) {
                            System.out.print("\nIngrese " + campo + ": ");
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
    }
}
