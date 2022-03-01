package ru.fit.nsu.np.cli;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.*;

@Slf4j
public class OptionParser {
    private static final String zipFileNameOp = "z";
    private static final String zipFileNameFullOp = "zipFile";
    private static final CommandLineParser parser = new DefaultParser();

    public static String getFileName(String[] args) {
        Options options = new Options();
        options.addOption(zipFileNameOp, zipFileNameFullOp, true, "Compressed XML file name");
        try {
            CommandLine cmd = parser.parse(options, args);
            return cmd.getOptionValue(zipFileNameOp);
        } catch (ParseException e) {
            log.error("Can't parse file name", e);
            return null;
        }
    }
}
