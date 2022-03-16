package ru.fit.nsu.np.xml.stax;

import ru.fit.nsu.np.xml.CompressedXmlProcessor;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.InputStream;
import java.util.Stack;

public abstract class CompressedXmlStaxProcessor<T> extends CompressedXmlProcessor<T> {
    private Stack<String> elementStack = new Stack<>();

    @Override
    protected T processStream(InputStream inputStream) throws Exception {
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        XMLEventReader reader = xmlInputFactory.createXMLEventReader(inputStream);
        while (reader.hasNext()) {
            XMLEvent nextEvent = reader.nextEvent();
            if (nextEvent.isStartDocument()) {
                elementStack = new Stack<>();
                initResult();
            }
            if (nextEvent.isStartElement()) {
                String lastElement = elementStack.empty() ? null : elementStack.peek();
                StartElement startElement = nextEvent.asStartElement();
                elementStack.push(startElement.getName().getLocalPart());
                processStartElement(startElement, lastElement);
            }
            if (nextEvent.isCharacters()) {
                Characters characters = nextEvent.asCharacters();
                if (elementStack.empty()) {
                    throw new RuntimeException("Characters" + characters.getData() + "are not inside any element");
                }
                String lastElement = elementStack.peek();
                processCharacters(characters, lastElement);
            }
            if (nextEvent.isEndElement()) {
                EndElement endElement = nextEvent.asEndElement();
                if (!elementStack.empty() && elementStack.peek().equals(endElement.getName().getLocalPart())) {
                    elementStack.pop();
                } else {
                    throw new RuntimeException("Unexpected end of " + endElement.getName().getLocalPart() + " element");
                }
            }
        }
        return getResult();
    }

    protected abstract void initResult();

    protected abstract void processStartElement(StartElement startElement, String lastElement);

    protected abstract void processCharacters(Characters characters, String lastElement);

    protected abstract T getResult();
}
