package ru.fit.nsu.np;

import ru.fit.nsu.np.jdbc.PostgresInitializer;
import ru.fit.nsu.np.xml.jaxb.OsmNodeListJaxbProcessor;

import static ru.fit.nsu.np.cli.OptionParser.getFileName;

public class Main {
    public static void main(String[] args) throws Exception {
        String fileName = getFileName(args);
        PostgresInitializer.initPostgresDdl();
        new OsmNodeListJaxbProcessor().processFile(fileName);
    }
}
