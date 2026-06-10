package filehandling.service;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
import filehandling.util.FileUtils;
import filehandling.util.LoggerConfig;

public class XMLFileService {

    private static final Logger logger = LoggerConfig.getLogger(XMLFileService.class);

    public void writeXML(String fileName, String id, String name, String marks) {
        try {
            FileUtils.validateFileName(fileName);

            if (!FileUtils.hasExtension(fileName, ".xml")) {
                fileName += ".xml";
            }

            logger.info("Writing XML file: " + fileName + " [id=" + id + ", name=" + name + ", marks=" + marks + "]");

            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.newDocument();

            Element root = document.createElement("students");
            document.appendChild(root);

            Element student = document.createElement("student");
            root.appendChild(student);

            appendElement(document, student, "id", id);
            appendElement(document, student, "name", name);
            appendElement(document, student, "marks", marks);

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.transform(new DOMSource(document), new StreamResult(new File(fileName)));

            System.out.println("XML File Created Successfully");
            logger.info("XML file created successfully: " + fileName);

        } 
        catch (Exception e) {
            System.out.println("Error writing XML: " + e.getMessage());
            logger.log(Level.SEVERE, "Error writing XML: " + fileName, e);
        }
    }

    private void appendElement(Document doc, Element parent, String tag, String value) {
        Element element = doc.createElement(tag);
        element.setTextContent(value);
        parent.appendChild(element);
    }

    public void readXML(String fileName) {
        try {
            FileUtils.validateFileName(fileName);

            if (!FileUtils.hasExtension(fileName, ".xml")) {
                System.out.println("File must have .xml extension");
                logger.warning("Read XML rejected - invalid extension: " + fileName);
                return;
            }

            if (!FileUtils.fileExists(fileName)) {
                System.out.println("File does not exist");
                logger.warning("Read XML failed - file does not exist: " + fileName);
                return;
            }

            logger.info("Reading XML file: " + fileName);

            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(new File(fileName));
            document.getDocumentElement().normalize();

            NodeList studentList = document.getElementsByTagName("student");

            logger.info("XML file parsed. Student records found: " + studentList.getLength());

            for (int i = 0; i < studentList.getLength(); i++) {
                Element student = (Element) studentList.item(i);

                System.out.println("Id : " + getTagValue(student, "id"));
                System.out.println("Name : " + getTagValue(student, "name"));
                System.out.println("Marks : " + getTagValue(student, "marks"));
                System.out.println("--------------");
            }

            logger.info("XML read complete: " + fileName);

        } 
        catch (Exception e) {
            System.out.println("Error reading XML: " + e.getMessage());
            logger.log(Level.SEVERE, "Error reading XML: " + fileName, e);
        }
    }

    private String getTagValue(Element element, String tag) {
        Node node = element.getElementsByTagName(tag).item(0);
        return node != null ? node.getTextContent() : "N/A";
    }
}