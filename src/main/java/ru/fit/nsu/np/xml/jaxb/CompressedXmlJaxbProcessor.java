package ru.fit.nsu.np.xml.jaxb;

import lombok.extern.slf4j.Slf4j;
import ru.fit.nsu.np.xml.CompressedXmlProcessor;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.util.StreamReaderDelegate;
import java.io.InputStream;

@Slf4j
public abstract class CompressedXmlJaxbProcessor<Res, Xml> extends CompressedXmlProcessor<Res> {

    @Override
    protected Res processStream(InputStream inputStream) throws Exception {
        initResult();
        XMLInputFactory xif = XMLInputFactory.newFactory();
        XMLStreamReader xsr = xif.createXMLStreamReader(inputStream);
        xsr = new XsiTypeReader(xsr);
        JAXBContext context = JAXBContext.newInstance(getXmlClass());
        Unmarshaller unmarshaller = context.createUnmarshaller();
        log.info("Started to unmarshal xml file");
        Xml xmlObject = (Xml) unmarshaller.unmarshal(xsr);
        log.info("Finished to unmarshal xml file, started processing an object...");
        processXmlObject(xmlObject);
        log.info("Finished processing xml object");
        return getResult();
    }

    protected abstract Class<Xml> getXmlClass();

    protected abstract void initResult();

    protected abstract void processXmlObject(Xml xmlObject);

    protected abstract Res getResult();

    private class XsiTypeReader extends StreamReaderDelegate {

        public XsiTypeReader(XMLStreamReader reader) {
            super(reader);
        }

        @Override
        public String getNamespaceURI() {
            return getRootNamespaceURI() == null ? super.getNamespaceURI() : getRootNamespaceURI();
        }
    }

    protected String getRootNamespaceURI() {
        return null;
    }
}
