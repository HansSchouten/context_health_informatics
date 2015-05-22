package XML;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import model.Column;
import model.ColumnType;
import model.Group;

public class SAXHandler extends DefaultHandler{

    /**
     * This variables stores whether a name should be read.
     */
    public boolean nameBool;

    /**
     * This variables stores whether a delimiter should be read.
     */
    public boolean delimiterBool;

    /**
     * This variables stores whether a primary key should be read.
     */
    public boolean primaryBool;

    /**
     * This variables stores whether a file should be read.
     */
    public boolean fileBool;

    /**
     * This variables stores all the groups of the files.
     */
    public ArrayList<Group> groups = new ArrayList<Group>();

    /**
     * This variable keeps an list of filepaths, to add to a group.
     */
    public ArrayList<String> files = new ArrayList<String>();

    /**
     * This variable keeps an stack of elements.
     */
    Stack<String> elements;
    
    /**
     * This string contains the name of the group.
     */
    protected String name;

    /**
     * This string contains the delimiter that should be used for the group.
     */
    protected String delimiter;

    /**
     * This variable keeps track of all the columns that should be added to a group.
     */
    public ArrayList<Column> columns = new ArrayList<Column>();

    /**
     * This variable contains the primary column of the group.
     */
    protected String primary;

    /**
     * Construct an SAX handler for reading the required XML file.
     */
    public SAXHandler() {
        reset();
    }

    @Override
    public void startElement(String uri, String localName,
        String qName, Attributes attributes) throws SAXException {
        switch (qName) {
        case "groups" :
            if (elements.isEmpty()) {
                elements.push(qName);
            } else {
                throw new SAXException("Should start with groups.");
            }
            break;
        case "group" :
            if ("groups".equals(elements.peek())) {
                elements.push(qName);
                cleanGroup();
            } else {
                throw new SAXException("The file should contain groups.");
            }
            break;
        case "name" :
            if ("group".equals(elements.peek())) {
                nameBool = true;
            } else {
                throw new SAXException("Name should be in a group.");
            }
            break;
        case "delimiter":
            if ("group".equals(elements.peek())) {
                delimiterBool = true;
            } else {
                throw new SAXException("Delimiter should be in a group.");
            }
            break;
        case "primary":
            if ("group".equals(elements.peek())) {
                primaryBool = true;
            } else {
                throw new SAXException("primary key should be in a group.");
            }
            break;
        case "files":
            if ("group".equals(elements.peek())) {
                elements.push(qName);
            } else {
                throw new SAXException("files should be in a group.");
            }
            break;
        case "file":
            if ("files".equals(elements.peek())) {
                fileBool = true;
            } else {
                throw new SAXException("file should be in a files element.");
            }
            break;
        case "columns":
            if ("group".equals(elements.peek())) {
                elements.push(qName);
            } else {
                throw new SAXException("columns should be in a group.");
            }
            break;
        case "column": 
            if ("columns".equals(elements.peek())) {
                if (attributes.getValue("name") != null && attributes.getValue("type") != null) {
                    Column col = new Column(attributes.getValue("name"));
                    col.setType(ColumnType.getTypeOf(attributes.getValue("type")));
                    columns.add(col);
                }
                else {
                    throw new SAXException("You are missing an attribute in column: name and type");
                }
            } else {
                throw new SAXException("file should be in a files element.");
            }
            break;
        default:
            throw new SAXException("Invalid keyword used in XML sturcture: " + qName);
        }
    }

    @Override
    public void endElement(String uri, String localName,
        String qName) throws SAXException {
        
        switch (qName) {
        case "groups" :
            if ("groups".equals(elements.pop())) {
                break;
            } else {
                throw new SAXException("Should start with groups.");
            }
        case "group" :
            if ("group".equals(elements.pop())) {
                writeGroup();
                break;
            } else {
                throw new SAXException("The file should contain groups.");
            }
        case "name":
            nameBool = false;
            break;
        case "delimiter":
            delimiterBool = false;
            break;
        case "primary":
            primaryBool = false;
            break;
        case "files":
            if ("files".equals(elements.pop())) {
                break;
            } else {
                throw new SAXException("The files should be contained in a group.");
            }
        case "columns":
            if ("columns".equals(elements.pop())) {
                break;
            } else {
                throw new SAXException("The columns should contain be contained in a group.");
            }
        case "file":
            fileBool = false;
            break;
        case "column":
            break;
        default:
            throw new SAXException(qName + " is not used in this XML structure");
        }
    }

    @Override
    public void characters(char ch[], int start, int length)
        throws SAXException {
        if(nameBool) {
            name = (new String(ch)).substring(start, start + length);
        } else if (primaryBool) {
            primary = (new String(ch)).substring(start, start + length);
        } else if (delimiterBool) {
            delimiter = (new String(ch)).substring(start, start + length);
        } else if (fileBool) {
            files.add((new String(ch)).substring(start, start + length));
        }
    }

    /**
     * This method writes a group to the list of groups.
     * @throws IOException 
     */
    protected void writeGroup() throws SAXException {
        if (name != null && primary != null && delimiter != null) {
            
            Column[] cols = new Column[columns.size()];
            for (int i = 0; i < columns.size(); i++) {
                cols[i] = columns.get(i);
            }
            
            Group group = new Group(name, delimiter, cols, primary);
            
            for (String file: files)
                try {
                    group.addFile(file);
                } catch (IOException e) {
                    throw new SAXException("file: "+ file + " in your xml does not exist.");
                }
            
            groups.add(group);
        }
        else {
            throw new SAXException("You have forgotten to specify any part of a group");
        }
    }

    /**
     * This method cleans the group values.
     */
    protected void cleanGroup() {
        name = null;
        delimiter = null;
        primary = null;
        columns = new ArrayList<Column>();        
    }
    
    /**
     * This method resets the handler.
     */
    public void reset() {
        cleanGroup();
        elements = new Stack<String>();
        nameBool = false;
        delimiterBool = false;
        primaryBool = false;
        fileBool = false;
    }
}
