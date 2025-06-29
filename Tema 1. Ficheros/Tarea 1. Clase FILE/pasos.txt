Completa este ejemplo utilizando el mayor número de métodos de la clase java.io.File, el propósito de la práctica es mostrar en pantalla la información disponible de un fichero mediante un programa Java.

El listado de métodos disponibles está en la ayuda de Java:

https://docs.oracle.com/javase/8/docs/api/index.html?java/io/File.html

package javaapplication3;

import java.io.File;

public class JavaApplication3 {

    public static void main(String[] args)
    {
        
        String ruta = "/media";
        if( args.length > 0 ) ruta = args[0];
        
        File fich = new File( ruta );
        
        if( !fich.exists() )
        {
            System.out.println("No existe el fichero o direcotio" + ruta );
        }
        else
        {
            if( fich.isFile() )
            {
                System.out.println(ruta+" es un fichero.");
            }
            else
            {
                System.out.println(" " + ruta + " es un directorio. Contenidos:");
                File[] ficheros = fich.listFiles();
                for( File f: ficheros )
                {
                    String texto = f.isDirectory() ? "/" : f.isFile() ? "_":"?";

                    System.out.println(texto + " " + f.getName() );
                }
            }
        }       
    }   
}