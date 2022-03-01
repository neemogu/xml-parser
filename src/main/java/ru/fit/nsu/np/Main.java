package ru.fit.nsu.np;

import ru.fit.nsu.np.cli.OpenMapDataPrinter;
import ru.fit.nsu.np.xml.OpenMapXmlProcessor;

import static ru.fit.nsu.np.cli.OptionParser.getFileName;

public class Main {
    public static void main(String[] args) {
        String fileName = getFileName(args);
        OpenMapXmlProcessor xmlProcessor = new OpenMapXmlProcessor();
        xmlProcessor.processFile(fileName, OpenMapDataPrinter::printData);
    }
}
