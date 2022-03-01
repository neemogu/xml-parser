package ru.fit.nsu.np.xml;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.util.function.Consumer;

@Slf4j
public abstract class CompressedXmlProcessor <T extends DefaultHandler> {
    public void processFile(String fileName, Consumer<T> handlerConsumer) {
        if (fileName == null) {
            return;
        }
        _processFile(fileName, handlerConsumer);
    }

    private void _processFile(String fileName, Consumer<T> handlerConsumer) {
        if (!fileName.endsWith(".bz2")) {
            log.warn("File '" + fileName + "' is not .bz2");
            return;
        }
        try (InputStream bZip2InputStream =
                    new BZip2CompressorInputStream(
                            new BufferedInputStream(
                                    new FileInputStream(fileName)))) {
            log.info("Started processing '" + fileName + "' compressed xml file");
            processStream(bZip2InputStream, handlerConsumer);
            log.info("Finished processing '" + fileName + "' compressed xml file");
        } catch (Exception e) {
            log.error("Error while processing xml file {}", fileName, e);
        }
    }

    private void processStream(InputStream inputStream, Consumer<T> handlerConsumer) throws Exception {
        T handler = createHandler();
        SAXParserFactory parserFactory = SAXParserFactory.newInstance();
        SAXParser parser = parserFactory.newSAXParser();
        parser.parse(inputStream, handler);
        handlerConsumer.accept(handler);
    }

    protected abstract T createHandler();
}
