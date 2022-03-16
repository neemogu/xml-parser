package ru.fit.nsu.np.xml.sax;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.Stack;

public abstract class SaxHandler extends DefaultHandler {

    private Stack<String> elementStack = new Stack<>();

    @Override
    public void startDocument() {
        init();
        elementStack = new Stack<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        String lastElement = elementStack.empty() ? null : elementStack.peek();
        elementStack.push(qName);
        processStartElement(lastElement, uri, localName, qName, attributes);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (!elementStack.empty() && elementStack.peek().equals(qName)) {
            elementStack.pop();
        } else {
            throw new SAXException("Unexpected end of " + qName + " element");
        }
    }

    protected abstract void processStartElement(String lastElement, String uri, String localName, String qName, Attributes attributes);
    protected abstract void init();
}
