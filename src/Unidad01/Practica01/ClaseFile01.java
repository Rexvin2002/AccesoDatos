package Unidad01.Practica01;

import java.io.File;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class ClaseFile01 {

    public static void main(String[] args)
    {
        // Crear un objeto Scanner para leer la entrada del usuario
        System.out.println("Introduzca una ruta: ");
        String ruta = new Scanner(System.in).nextLine();
        // /home/dam/Escritorio/2º DAM 24-25/ACCESO A DATOS/UNIDAD 01
        
        // Verifica si se han pasado argumentos desde la línea de comandos
        if( args.length > 0 ) ruta = args[0];
        
        // Crear un objeto File con la ruta especificada
        File fich = new File(ruta);
        
        // Verifica si el fichero o directorio existe
        if( !fich.exists() )
        {
            System.out.println("No existe el fichero o directorio: " + ruta );
        }
        else
        {
            System.out.println("Existe el fichero o directorio: " + ruta );
            
            if( fich.isFile() )
            {
                // Si es un fichero, muestra información detallada
                System.out.println(ruta + " es un fichero.");
                mostrarDetalles(fich);
            }
            else
            {
                // Si es un directorio, muestra su contenido
                System.out.println(ruta + " es un directorio. Contenidos:");
                File[] ficheros = fich.listFiles();
                for( File f: ficheros )
                {
                    // Muestra detalles de cada archivo/directorio en el directorio
                    mostrarDetalles(f);
                }
            }
        }
    }
    
    // Método para mostrar detalles de ficheros o directorios
    public static void mostrarDetalles(File f) {
        // Verificar si es un fichero o un directorio
        String tipo = f.isDirectory() ? "Directorio" : "Fichero";
        System.out.println("Tipo: " + tipo);
        
        // Tamaño del fichero (si es un fichero)
        if (f.isFile()) {
            long tamañoBytes = f.length();
            double tamañoKB = tamañoBytes / 1024.0;
            double tamañoMB = tamañoKB / 1024.0;
            System.out.printf("Tamaño: %d bytes (%.2f KB, %.2f MB)%n", tamañoBytes, tamañoKB, tamañoMB);
        }
        
        // Verificar permisos
        String permisos = (f.canRead() ? "r" : "-") + 
                          (f.canWrite() ? "w" : "-") + 
                          (f.canExecute() ? "x" : "-");
        System.out.println("Permisos: " + permisos);
        
        // Última modificación
        Date ultimaModif = new Date(f.lastModified());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        System.out.println("Última modificación: " + sdf.format(ultimaModif));
        
        // Nombre del fichero o directorio
        System.out.println("Nombre: " + f.getName());
        System.out.println("---------------------------------------");
    }
}
