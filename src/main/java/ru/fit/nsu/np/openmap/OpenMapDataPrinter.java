package ru.fit.nsu.np.openmap;

import org.apache.commons.lang3.mutable.MutableLong;

import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

public class OpenMapDataPrinter {
    public static void printData(OpenMapXmlStats openMapXmlStats) {
        System.out.println("[User -> Changes]:");
        System.out.println(openMapXmlStats.getUserToChangesStats().entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry<String, MutableLong>::getValue).reversed())
                .map(e -> e.getKey() + " -> " + e.getValue())
                .collect(Collectors.joining("\n"))
        );
        System.out.println("\n[Key name -> Tags]:");
        System.out.println(openMapXmlStats.getKeyNameToTagsStats().entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry<String, MutableLong>::getValue))
                .map(e -> e.getKey() + " -> " + e.getValue())
                .collect(Collectors.joining("\n"))
        );
    }
}
