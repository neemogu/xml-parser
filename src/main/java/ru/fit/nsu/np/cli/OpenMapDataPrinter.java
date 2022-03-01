package ru.fit.nsu.np.cli;

import org.apache.commons.lang3.mutable.MutableLong;
import ru.fit.nsu.np.xml.OpenMapHandler;

import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

public class OpenMapDataPrinter {
    public static void printData(OpenMapHandler openMapHandler) {
        System.out.println("[User -> Changes]:");
        System.out.println(openMapHandler.getUserToChanges().entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry<String, MutableLong>::getValue).reversed())
                .map(e -> e.getKey() + " -> " + e.getValue())
                .collect(Collectors.joining("\n"))
        );
        System.out.println("\n[Key name -> Tags]:");
        System.out.println(openMapHandler.getKeyNameToTags().entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry<String, MutableLong>::getValue))
                .map(e -> e.getKey() + " -> " + e.getValue())
                .collect(Collectors.joining("\n"))
        );
    }
}
