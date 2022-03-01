package ru.fit.nsu.np.xml;

public class OpenMapXmlProcessor extends CompressedXmlProcessor<OpenMapHandler> {
    @Override
    protected OpenMapHandler createHandler() {
        return new OpenMapHandler();
    }
}
