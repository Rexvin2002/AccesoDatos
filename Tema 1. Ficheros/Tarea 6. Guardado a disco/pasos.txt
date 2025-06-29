Acceder a un fichero de texto, volcadlo a otro fichero temporal realizando lo siguiente:
1 - Forzamos Mayúscula después de .
2 - Eliminamos dobles/triples/etc.. consecutivos espacios en blanco.
 
Utilizando las clases: File, BufferedReader, BufferedWriter, FileReader, FileWriter.
 
File.createTempFile nos da un nombre de fichero temporal no utilizado antes.
File(objeto).renameTo nos permite renombrar los ficheros al final del proceso.