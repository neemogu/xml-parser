package ru.fit.nsu.np.xml.sax;

import lombok.Getter;
import org.apache.commons.lang3.mutable.MutableLong;
import org.xml.sax.Attributes;
import ru.fit.nsu.np.openmap.OpenMapXmlStats;

import java.util.HashMap;
import java.util.Map;

import static ru.fit.nsu.np.openmap.OpenMapXmlNames.*;

public class OpenMapHandler extends SaxHandler implements OpenMapXmlStats {
    private Map<String, MutableLong> userToChanges;
    private Map<String, MutableLong> keyNameToTags;

    @Override
    protected void processStartElement(String lastElement, String uri, String localName, String qName, Attributes attributes) {
        if (qName.equals(NODE)) {
            String user = attributes.getValue(USER);
            if (user != null) {
                userToChanges.computeIfAbsent(user, (u) -> new MutableLong(0)).increment();
            }
        } else if (qName.equals(TAG) && NODE.equals(lastElement)) {
            String key = attributes.getValue(KEY);
            if (key != null) {
                keyNameToTags.computeIfAbsent(key, (k) -> new MutableLong(0)).increment();
            }
        }
    }

    @Override
    protected void init() {
        userToChanges = new HashMap<>();
        keyNameToTags = new HashMap<>();
    }

    @Override
    public Map<String, MutableLong> getKeyNameToTagsStats() {
        return keyNameToTags;
    }

    @Override
    public Map<String, MutableLong> getUserToChangesStats() {
        return userToChanges;
    }
}
