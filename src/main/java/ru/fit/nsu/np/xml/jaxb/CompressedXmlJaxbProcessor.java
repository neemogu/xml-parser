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
        xsr = new OsmTypeReader(xsr);
        JAXBContext context = JAXBContext.newInstance(getXmlClass());
        Unmarshaller unmarshaller = context.createUnmarshaller();
        log.info("Started to unmarshal xml file");
        while (xsr.hasNext()) {
            if (xsr.isStartElement() && xsr.getLocalName().equals(getXmlClass().getSimpleName().toLowerCase())) {
                Xml xmlObject = (Xml) unmarshaller.unmarshal(xsr);
                processXmlObject(xmlObject);
            } else {
                xsr.next();
            }
        }
        log.info("Finished to unmarshal xml file");
        return getResult();
    }

    protected abstract Class<Xml> getXmlClass();

    protected abstract void initResult();

    protected abstract void processXmlObject(Xml xmlObject);

    protected abstract Res getResult();

    private class OsmTypeReader extends StreamReaderDelegate {

        public OsmTypeReader(XMLStreamReader reader) {
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
