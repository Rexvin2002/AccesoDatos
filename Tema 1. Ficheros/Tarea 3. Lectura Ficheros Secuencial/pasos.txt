Modificad la práctica 1 (que recorre los ficheros dentro de una carpeta) para que además realice un conteo de las vocales [ a, e, i, o, u ] que hay dentro de cada fichero y mostrarlo en pantalla junto con la información del fichero.

Se puede utilizar la clase FileReader con su método read() o la clase BufferedReader con su metodo readLine().

Para sacar mejor nota realizad ambas implementaciones.

// Creo el objeto para LEER
FileReader fil = new FileReader( miFichero );
BufferedReader brf = new BufferedReader( fil );

// Utilizando FileReader
int letra = fil.read();
while( letra != -1 )
{
       // CÓDIGO AQUÍ ARRIBA
       // LEEMOS LO ÚLTIMO
       letra = fil.read();
}
// Utilizando BufferedReader
String line = brf.readLine();
while( line != null )
{
       // CÓDIGO AQUÍ ARRIBA
       // LEEMOS LO ÚLTIMO
       line = brf.readLine();
}