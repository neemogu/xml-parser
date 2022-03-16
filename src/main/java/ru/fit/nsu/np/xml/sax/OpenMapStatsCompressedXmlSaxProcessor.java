package ru.fit.nsu.np.xml.sax;

import ru.fit.nsu.np.openmap.OpenMapDataPrinter;

import java.util.function.Consumer;

public class OpenMapStatsCompressedXmlSaxProcessor extends CompressedXmlSaxProcessor<OpenMapHandler> {

    @Override
    protected Consumer<OpenMapHandler> getResultConsumer() {
        return OpenMapDataPrinter::printData;
    }

    @Override
    protected OpenMapHandler createHandler() {
        return new OpenMapHandler();
    }
}
