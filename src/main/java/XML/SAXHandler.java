package XML;

import java.util.ArrayList;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import model.Column;
import model.Group;

public class SAXHandler extends DefaultHandler{
    
    public boolean nameBool;
    
    public boolean delimiterBool;
    
    public boolean primaryBool;
    
    public boolean fileBool;
    
    public ArrayList<Group> groups = new ArrayList<Group>();
    
    public ArrayList<Column> columns = new ArrayList<Column>();
    
    Stack<String> elements;
    
    public SAXHandler() {
        elements = new Stack<String>();
        nameBool = false;
        delimiterBool = false;
        primaryBool = false;
        fileBool = false;
    }

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
                columns.add(new Column(attributes.getValue(0), attributes.getValue(1)));
            } else {
                throw new SAXException("file should be in a files element.");
            }
        }
        System.out.println(qName);
    }

    public void endElement(String uri, String localName,
        String qName) throws SAXException {
        
        switch (qName) {
        case "groups" :
            if ("groups".equals(elements.pop())) {
                elements.push(qName);
            } else {
                throw new SAXException("Should start with groups.");
            }
        case "group" :
            if ("group".equals(elements.pop())) {
                elements.push(qName);
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
            throw new SAXException("A column should not have an endElement");
        default:
            throw new SAXException(qName + " is not used in this XML structure");
        }
    }

    public void characters(char ch[], int start, int length)
        throws SAXException {
    }
}
