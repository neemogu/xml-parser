package ru.fit.nsu.np.xml;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.function.Consumer;

@Slf4j
public abstract class CompressedXmlProcessor<T> {
    public void processFile(String fileName) {
        if (fileName == null) {
            return;
        }
        _processFile(fileName, getResultConsumer());
    }

    private void _processFile(String fileName, Consumer<T> resultConsumer) {
        if (!fileName.endsWith(".bz2")) {
            log.warn("File '" + fileName + "' is not .bz2");
            return;
        }
        try (InputStream bZip2InputStream =
                     new BZip2CompressorInputStream(
                             new BufferedInputStream(
                                     new FileInputStream(fileName)))) {
            log.info("Started processing '" + fileName + "' compressed xml file");
            resultConsumer.accept(processStream(bZip2InputStream));
            log.info("Finished processing '" + fileName + "' compressed xml file");
        } catch (Exception e) {
            log.error("Error while processing xml file {}", fileName, e);
        }
    }

    protected abstract Consumer<T> getResultConsumer();

    protected abstract T processStream(InputStream inputStream) throws Exception;
}
