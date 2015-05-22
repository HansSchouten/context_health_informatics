package XML;

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
    SAXParser parser;

    /**
     * This variable stores the handler of the parser.
     */
    SAXHandler handler;

    public XMLhandler() throws ParserConfigurationException, SAXException {
        parser = SAXParserFactory.newInstance().newSAXParser();
        handler = new SAXHandler();
    }
    
    public ArrayList<Group> readXMLFile(String filename) throws SAXException, IOException {
        parser.parse(filename, handler);
        ArrayList<Group> groups = handler.groups; 
        handler.reset();
        return groups;
    }
}
