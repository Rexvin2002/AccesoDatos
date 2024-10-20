package Unidad02.Practica03;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

public class ConsultasXPath {

    public static void main(String[] args) {
        
        try {
            
            // Cargar el archivo XML
            File inputFile = new File("src/Unidad02/Practica03/BOOKS.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(inputFile);
            // Garantiza que el documento esté en un estado óptimo para cualquier operación posterior.
            doc.getDocumentElement().normalize();
            
            // Crea mediante la instancia de XPathFactory un objeto XPath
            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xpath = xPathFactory.newXPath();
            
            // Almacena las expresiones compiladas en una lista XPathExpression
            XPathExpression[] expressions = new XPathExpression[5];
            expressions[0] = xpath.compile("/catalog/book/title"); // Todos los títulos
            expressions[1] = xpath.compile("/catalog/book[@id='bk102']/author"); // Autor del libro con ID 'bk102'
            expressions[2] = xpath.compile("/catalog/book[price>30]/title"); // Libros con precio superior a 30
            expressions[3] = xpath.compile("/catalog/book/publish_date"); // Fechas de publicación
            expressions[4] = xpath.compile("/catalog/book[1]/title"); // Título del primer libro

            // Almacena los titulos de cada consulta en una lista
            String[] consultas = {
                "Títulos de todos los libros:",
                "Autor del libro con id 'bk102':",
                "Libros con precio superior a 30:",
                "Fechas de publicación de los libros:",
                "Título del primer libro:"
            };
            
            // Para cada expresion 
            for (int i = 0; i < expressions.length; i++) {
                // Muestra el número y título de consulta
                System.out.println("\nConsulta " + (i + 1) + " - " + consultas[i]);

                if (i == 1 || i == 4) { // Para consultas que devuelven un solo string
                    
                    // Se evalúa la expresión XPath y se almacena en una variable
                    String result = (String) expressions[i].evaluate(doc, XPathConstants.STRING);
                    // Si result está vacío, se muestra un mensaje sino, se imprime el resultado.
                    System.out.println(result.isEmpty() ? "No se encontró resultado." : result);
                    
                } else { // Para consultas que devuelven un nodo
                    // Se evalúa la expresión XPath y se almacena en una variable
                    NodeList results = (NodeList) expressions[i].evaluate(doc, XPathConstants.NODESET);
                    
                    if (results.getLength() == 0) { // Si no hay resultados muestra un mensaje
                        
                        System.out.println("No se encontraron resultados.");
                        
                    } else { // Si hay resultados se muestra el contenido de cada nodo
                        
                        for (int j = 0; j < results.getLength(); j++) {                            
                            System.out.println(results.item(j).getTextContent());
                        }
                        
                    }
                    
                }
                
            }
            

        } catch (IOException | ParserConfigurationException | XPathExpressionException | DOMException | SAXException e) {
            
            System.err.println("\nERROR: " + e.getMessage());
            
        }
        
    }
    
}
