package ru.fit.nsu.np.xml;

import lombok.Getter;
import org.apache.commons.lang3.mutable.MutableLong;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class OpenMapHandler extends DefaultHandler {
    private static final String NODE = "node";
    private static final String USER = "user";
    private static final String TAG = "tag";
    private static final String KEY = "k";

    @Getter
    private Map<String, MutableLong> userToChanges;
    @Getter
    private Map<String, MutableLong> keyNameToTags;

    private Stack<String> elementStack;

    @Override
    public void startDocument() {
        userToChanges = new HashMap<>();
        keyNameToTags = new HashMap<>();
        elementStack = new Stack<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        String lastElement = elementStack.empty() ? null : elementStack.peek();
        elementStack.push(qName);
        if (qName.equals(NODE)) {
            String user = attributes.getValue(USER);
            if (user != null) {
                userToChanges.computeIfAbsent(user, (u) -> new MutableLong(0)).increment();
            }
        } else if (qName.equals(TAG) && NODE.equals(lastElement)) {
            String key = attributes.getValue(KEY);
            if (key != null) {
                keyNameToTags.computeIfAbsent(key, (k) -> new MutableLong(0)).increment();
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (!elementStack.empty() && elementStack.peek().equals(qName)) {
            elementStack.pop();
        } else {
            throw new SAXException("Unexpected end of " + qName + " element");
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {}
}
