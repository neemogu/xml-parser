package ru.fit.nsu.np.openmap;

import org.apache.commons.lang3.mutable.MutableLong;

import java.util.Map;

public interface OpenMapXmlStats {
    Map<String, MutableLong> getKeyNameToTagsStats();
    Map<String, MutableLong> getUserToChangesStats();
}
