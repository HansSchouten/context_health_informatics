package XML;

import java.awt.List;
import java.util.ArrayList;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import model.Group;

public class SAXHandler extends DefaultHandler{
    
    public boolean name;
    
    public boolean delimiter;
    
    public boolean primary;
    
    public boolean file;
    
    public boolean columns;
    
    public ArrayList<Group> groups = new ArrayList<Group>();
    
    Stack<String> elements;
    
    public SAXHandler() {
        elements = new Stack<String>();
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
        case "group" :
            if ("groups".equals(elements.peek())) {
                elements.push(qName);
            } else {
                throw new SAXException("The file should contain groups.");
            }
        case "name" :
            if ("group".equals(elements.peek())) {
                
            } else {
                throw new SAXException("Name should be in a group.");
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

        System.out.println(qName);
    }

    public void characters(char ch[], int start, int length)
        throws SAXException {
    }
}
