package escrituraxml;

/**
 * Kevin Gómez Valderas 2ºDAM
 */
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

public class TraducirXML {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            // Crear DocumentBuilder para leer el XML
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse("src\\xml\\BOOKS.xml");
            doc.getDocumentElement().normalize();

            // Crear un nuevo documento vacío
            DOMImplementation domImpl = builder.getDOMImplementation();
            Document newDoc = domImpl.createDocument(null, "Catalogo", null);
            newDoc.setXmlVersion("1.0");
            newDoc.setXmlStandalone(true);

            // Traducir las etiquetas y copiar el contenido
            NodeList bookNodes = doc.getElementsByTagName("book");

            for (int i = 0; i < bookNodes.getLength(); i++) {
                Element book = (Element) bookNodes.item(i);

                // Crear nuevo elemento "Libro"
                Element libroElement = newDoc.createElement("Libro");
                newDoc.getDocumentElement().appendChild(libroElement);

                // Añadir atributos del libro (id)
                libroElement.setAttribute("id", book.getAttribute("id"));

                // Traducir y copiar los elementos del libro
                traducirElemento(newDoc, libroElement, book, "title", "Titulo");
                traducirElemento(newDoc, libroElement, book, "author", "Autor");
                traducirElemento(newDoc, libroElement, book, "genre", "Genero");
                traducirElemento(newDoc, libroElement, book, "price", "Precio");
                traducirElemento(newDoc, libroElement, book, "publish_date", "FechaPublicacion");
                traducirElemento(newDoc, libroElement, book, "description", "Descripcion");
            }

            // Guardar el documento traducido en un archivo XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(newDoc);

            File f = new File("src\\xml\\libros.xml");
            f.createNewFile();
            StreamResult result = new StreamResult(f);

            transformer.transform(source, result);

            mostrarTitulos(doc);

            System.out.println("\nArchivo traducido guardado como libros.xml");

        } catch (IOException | ParserConfigurationException | TransformerException | DOMException | SAXException e) {
            System.err.println("\nERROR: " + e.getMessage());
        }
    }

    private static void traducirElemento(Document newDoc, Element parentElement, Element oldElement, String oldTagName, String newTagName) {
        Element oldTagElement = (Element) oldElement.getElementsByTagName(oldTagName).item(0);
        if (oldTagElement != null) {
            Element newTagElement = newDoc.createElement(newTagName);
            newTagElement.appendChild(newDoc.createTextNode(oldTagElement.getTextContent()));
            parentElement.appendChild(newTagElement);
        }
    }

    private static void mostrarTitulos(Document doc) {
        doc.getDocumentElement().normalize();

        // Obtener todos los elementos "title"
        NodeList nodeList = doc.getElementsByTagName("title");
        System.out.println("\nLista de títulos de los libros:");

        // Mostrar los títulos
        for (int i = 0; i < nodeList.getLength(); i++) {
            Element titleElement = (Element) nodeList.item(i);
            System.out.println("- " + titleElement.getTextContent());
        }
    }
}
