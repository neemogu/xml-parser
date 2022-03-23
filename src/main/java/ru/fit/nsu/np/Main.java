package ru.fit.nsu.np;

import ru.fit.nsu.np.xml.stax.OpenMapStatsCompressedXmlStaxProcessor;

import static ru.fit.nsu.np.cli.OptionParser.getFileName;

public class Main {
    public static void main(String[] args) {
        String fileName = getFileName(args);
        new OpenMapStatsCompressedXmlStaxProcessor().processFile(fileName);
    }
}
