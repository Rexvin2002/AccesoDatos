
package Unidad02.Practica02;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author kgv17
 */
public class TraducirXML {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try {
            // Crear DocumentBuilder para leer el XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse("src/Unidad02/Practica02/BOOKS.xml");
            doc.getDocumentElement().normalize();

            // Obtiene una instancia de DOMImplementation desde el DocumentBuilder.
            DOMImplementation domImpl = builder.getDOMImplementation();
            // Crea un nuevo documento XML vacio (<Catalogo></Catalogo>)
            Document newDoc = domImpl.createDocument(null, "Catalogo", null);
            // Establecer la versión XM (<?xml version="1.0"?>)
            newDoc.setXmlVersion("1.0");
            // Indica que el documento XML no dependerá de una declaración externa, 
            // como un DTD o una entidad externa.
            newDoc.setXmlStandalone(true);

            // Se traduce las etiquetas y se copia el contenido
            // Devuelve una lista que almacena los nodos del documento, 
            // en este caso, los elementos books
            NodeList bookNodes = doc.getElementsByTagName("book");

            for (int i = 0; i < bookNodes.getLength(); i++) {
                // Accede a un elemento de la NodeList
                Element book = (Element) bookNodes.item(i);

                // Crea un nuevo elemento traducido "Libro" y lo inserta en el nuevo documento
                Element libroElement = newDoc.createElement("Libro");
                newDoc.getDocumentElement().appendChild(libroElement);

                // Se añade los atributos del libro, en este xaso la od
                libroElement.setAttribute("id", book.getAttribute("id"));

                // Traduce los nodos hijos del elemento book
                traducirElemento(newDoc, libroElement, book, "title", "Titulo");
                traducirElemento(newDoc, libroElement, book, "author", "Autor");
                traducirElemento(newDoc, libroElement, book, "genre", "Genero");
                traducirElemento(newDoc, libroElement, book, "price", "Precio");
                traducirElemento(newDoc, libroElement, book, "publish_date", "FechaPublicacion");
                traducirElemento(newDoc, libroElement, book, "description", "Descripcion");
            }

            // Guardar el documento traducido en un archivo XML
            // Se crea la instancia de TransformerFactory
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            // Crea un transformador mediante la instancia
            Transformer transformer = transformerFactory.newTransformer();
            // Se define la fuente del documento XML que queremos guardar como un DOMSource
            DOMSource source = new DOMSource(newDoc);
            
            // Se crea el archivo donde se almacenará el resultado
            File f = new File("src/Unidad02/Practica02/libros.xml");
            f.createNewFile();
            
            // Se crea un StreamResult para especificar dónde se enviará la salida del transformador
            StreamResult result = new StreamResult(f);

            // Transforma y guarda el documento XML
            transformer.transform(source, result);
            
            // Finalmente se muestran todos los titulo de los libros
            mostrarTitulos(doc);
            
            System.out.println("\nArchivo traducido guardado como libros.xml");

        } catch (IOException | ParserConfigurationException | TransformerException | DOMException | SAXException e) {
            System.err.println("\nERROR: "+e.getMessage());
        }
    }
    
    // Traduce cada elemento mediante: 
    // newDoc (documento creado donde se almacena el resultado traducido)
    // parentElement: Es el elemento padre del nuevo documento XML (newDoc) en el que se añaden los elementos traducidos.
    // oldElement: Es el elemento original del documento XML antiguo (doc) que contiene los datos que queremos copiar y traducir.
    // oldTagName y newTagName son el nombre/palabra del elemento a traducir y la palabra traducida
    private static void traducirElemento(Document newDoc, Element parentElement, Element oldElement, String oldTagName, String newTagName) {
        
        // Busca el elemento original por su nombre (ej: title)
        Element oldTagElement = (Element) oldElement.getElementsByTagName(oldTagName).item(0);
        
        // Si el nombre antiguo del elemento existe
        if (oldTagElement != null) {
            
            // Crea un nuevo elemento en el nuevo documento newDoc 
            // con el nombre de la etiqueta especificada por newTagName
            Element newTagElement = newDoc.createElement(newTagName);
            // Añade dentro del nuevo elemento el texto correspondiente del antiguo documento
            newTagElement.appendChild(newDoc.createTextNode(oldTagElement.getTextContent()));
            // Luego se añade el nuevo elemento al padre (Ej: titulo dentro de libro)
            parentElement.appendChild(newTagElement);
            
        }
        
    }
    
    // Muestra todos los títulos de los libros
    private static void mostrarTitulos(Document doc){
        
        doc.getDocumentElement().normalize();

        // Obtiene todos los elementos "title"
        NodeList nodeList = doc.getElementsByTagName("title");
        System.out.println("\nLista de títulos de los libros:");

        // Muestra los títulos
        for (int i = 0; i < nodeList.getLength(); i++) {
            
            // Muestra todo el contenido dentro del "title"
            Element titleElement = (Element) nodeList.item(i);
            System.out.println("- " + titleElement.getTextContent());
            
        }
        
    }
    
}
    
