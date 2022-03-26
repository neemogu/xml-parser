package ru.fit.nsu.np.xml.jaxb;

import org.apache.commons.lang3.mutable.MutableLong;
import ru.fit.nsu.np.jaxb.Node;
import ru.fit.nsu.np.jaxb.Tag;
import ru.fit.nsu.np.openmap.OpenMapDataPrinter;
import ru.fit.nsu.np.openmap.OpenMapXmlStats;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class OpenMapStatsJaxbProcessor extends CompressedXmlJaxbProcessor<OpenMapXmlStats, Node> implements OpenMapXmlStats {

    private Map<String, MutableLong> userToChanges;
    private Map<String, MutableLong> keyNameToTags;

    @Override
    protected Consumer<OpenMapXmlStats> getResultConsumer() {
        return OpenMapDataPrinter::printData;
    }

    @Override
    public Map<String, MutableLong> getKeyNameToTagsStats() {
        return Map.copyOf(keyNameToTags);
    }

    @Override
    public Map<String, MutableLong> getUserToChangesStats() {
        return Map.copyOf(userToChanges);
    }

    @Override
    protected Class<Node> getXmlClass() {
        return Node.class;
    }

    @Override
    protected void initResult() {
        userToChanges = new HashMap<>();
        keyNameToTags = new HashMap<>();
    }

    @Override
    protected OpenMapXmlStats getResult() {
        return this;
    }

    @Override
    protected void processXmlObject(Node node) {
        String user = node.getUser();
        if (user != null) {
            userToChanges.computeIfAbsent(node.getUser(), (u) -> new MutableLong(0)).increment();
        }
        for (Tag tag : node.getTag()) {
            String key = tag.getK();
            if (key != null) {
                keyNameToTags.computeIfAbsent(tag.getK(), (k) -> new MutableLong(0)).increment();
            }
        }
    }

    @Override
    protected String getRootNamespaceURI() {
        return "http://openstreetmap.org/osm/0.6";
    }
}
