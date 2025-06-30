Utilizando los métodos de la clase java.io.File, el propósito de la práctica es crear la siguiente estructura de directorios desde un programa Java mediante unos bucles.

Tras crear la estructura hay que recorrer recursivamente buscando un fichero y eliminarlo.

TIP 1 > Preguntar al usuario el nombre del fichero a borrar ( consola o args[] ).

/ Abuelo / Padre / Hijo1.txt

/ Abuelo / Padre / Hija2.txt

/ Abuelo / Madre / Hijo3.txt

/ Abuelo / Madre / Hija4.txt

/ Abuela / Padre / Hijo5.txt

/ Abuela / Padre / Hija6.txt

/ Abuela / Madre / Hijo7.txt

/ Abuela / Madre / Hija8.txt

TIP 2 > El listado de métodos disponibles ( mkdir, delete, createNewFile ) está en la ayuda de Java:

https://docs.oracle.com/javase/8/docs/api/index.html?java/io/File.html