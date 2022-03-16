package ru.fit.nsu.np.xml.sax;

import ru.fit.nsu.np.xml.CompressedXmlProcessor;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.InputStream;

public abstract class CompressedXmlSaxProcessor<H extends SaxHandler> extends CompressedXmlProcessor<H> {
    @Override
    protected H processStream(InputStream inputStream) throws Exception {
        H handler = createHandler();
        SAXParserFactory parserFactory = SAXParserFactory.newInstance();
        SAXParser parser = parserFactory.newSAXParser();
        parser.parse(inputStream, handler);
        return handler;
    }

    protected abstract H createHandler();
}
