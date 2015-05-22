package xml;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import model.Group;

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
}
