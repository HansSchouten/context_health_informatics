package xml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import model.Column;
import model.Group;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * This class is used to read in an XML file with the groups specified.
 * @author Matthijs
 *
 */
public class XMLhandler {

    /**
     * This variable stores the parser that is used to parse a file.
     */
    protected SAXParser parser;

    /**
     * This variable stores the handler of the parser.
     */
    protected SAXHandler handler;

    /**
     * construct a new XML handler.
     * @throws ParserConfigurationException - Thrown when configuration is not right.
     * @throws SAXException                 - Thrown when parsing goes wrong.
     */
    public XMLhandler() throws ParserConfigurationException, SAXException {
        parser = SAXParserFactory.newInstance().newSAXParser();
        handler = new SAXHandler();
    }

    /**
     * This method reads an XML file and converts it to Groups.
     * @param filename          - Filename of the xml file to read.
     * @return                  - ArrayList of all the groups in the files.
     * @throws SAXException     - Thrown when XML parsing goes wrong
     * @throws IOException      - Thrown when reading the file goes wrong.
     */
    public ArrayList<Group> readXMLFile(String filename) throws SAXException, IOException {
        parser.parse(filename, handler);
        ArrayList<Group> groups = handler.groups;
        handler.reset();
        return groups;
    }

    /**
     * This method writes the array of groups to an XML file of chosing.
     * @param filename          - Name and path to write the file to.
     * @param groups            - Groups to write to a file.
     * @throws SAXException thrown when writing goes wrong.
     */
    public void writeXMLFile(String filename, ArrayList<Group> groups) throws SAXException {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            
            Document doc = docBuilder.newDocument();
            
            Element rootElement = doc.createElement("groups");
            doc.appendChild(rootElement);
            
            for (Group group: groups) {
                rootElement.appendChild(createXMLForGroup(group, doc));
            }
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(filename));
            
            transformer.transform(source, result);
        }
        catch (TransformerException | ParserConfigurationException  e) {
            throw new SAXException("Something seriously when wrong during writing the file.");
        }
    }

    /**
     * This method creates an xml element for a group.
     * @param group     - Group to converge to XML.
     * @param doc       - Document to use
     * @return          - XML Element containing the group.
     */
    protected Element createXMLForGroup(Group group, Document doc) {
        Element groupElement = doc.createElement("group");

        Element name = doc.createElement("name");
        name.appendChild(doc.createTextNode(group.getName()));
        groupElement.appendChild(name);

        Element delimiter = doc.createElement("delimiter");
        delimiter.appendChild(doc.createTextNode(group.getDelimiter()));
        groupElement.appendChild(delimiter);

        Element primary = doc.createElement("primary");
        primary.appendChild(doc.createTextNode(group.getPrimary()));
        groupElement.appendChild(primary);

        Element files = doc.createElement("files");
        groupElement.appendChild(files);

        for (String file: group.keySet()) {
            Element fileElement = doc.createElement("file");
            fileElement.appendChild(doc.createTextNode(file));
            files.appendChild(fileElement);
        }

        Element columns = createColumnsElement(group.getColumns(), doc);
        groupElement.appendChild(columns);

        return groupElement;
    }

    /**
     * This method creates the column elements for a group.
     * @param columns       - Columns to be put in XML element.
     * @param doc           - Document to use.
     * @return              - XML Element containing the columns (with name and type).
     */
    protected Element createColumnsElement(Column[] columns, Document doc) {
        Element columnsElement = doc.createElement("columns");

        for (int i = 0; i < columns.length; i++) {
            Element column = doc.createElement("column");  

            Attr nameattr = doc.createAttribute("name");
            nameattr.setValue(columns[i].getName());
            column.setAttributeNode(nameattr);

            Attr typeattr = doc.createAttribute("type");
            typeattr.setValue(columns[i].getType().toString());
            column.setAttributeNode(typeattr);

            columnsElement.appendChild(column);
        }
        return columnsElement;
    }
}
