package ru.fit.nsu.np.xml.stax;

import org.apache.commons.lang3.mutable.MutableLong;
import ru.fit.nsu.np.openmap.OpenMapDataPrinter;
import ru.fit.nsu.np.openmap.OpenMapXmlStats;

import javax.xml.namespace.QName;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import static ru.fit.nsu.np.openmap.OpenMapXmlNames.*;

public class OpenMapStatsStaxProcessor extends CompressedXmlStaxProcessor<OpenMapXmlStats> implements OpenMapXmlStats {

    private Map<String, MutableLong> userToChanges;
    private Map<String, MutableLong> keyNameToTags;

    @Override
    protected Consumer<OpenMapXmlStats> getResultConsumer() {
        return OpenMapDataPrinter::printData;
    }

    @Override
    protected void initResult() {
        userToChanges = new HashMap<>();
        keyNameToTags = new HashMap<>();
    }

    @Override
    protected void processStartElement(StartElement startElement, String lastElement) {
        String qName = startElement.getName().getLocalPart();
        if (qName.equals(NODE)) {
            Attribute user = startElement.getAttributeByName(new QName(USER));
            if (user != null) {
                userToChanges.computeIfAbsent(user.getValue(), (u) -> new MutableLong(0)).increment();
            }
        } else if (qName.equals(TAG) && NODE.equals(lastElement)) {
            Attribute key = startElement.getAttributeByName(new QName(KEY));
            if (key != null) {
                keyNameToTags.computeIfAbsent(key.getValue(), (k) -> new MutableLong(0)).increment();
            }
        }
    }

    @Override
    protected void processCharacters(Characters characters, String lastElement) {}

    @Override
    protected OpenMapXmlStats getResult() {
        return this;
    }

    @Override
    public Map<String, MutableLong> getKeyNameToTagsStats() {
        return Map.copyOf(userToChanges);
    }

    @Override
    public Map<String, MutableLong> getUserToChangesStats() {
        return Map.copyOf(keyNameToTags);
    }
}
