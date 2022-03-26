package ru.fit.nsu.np.xml.jaxb;

import ru.fit.nsu.np.openmap.dao.PersistentEntity;
import ru.fit.nsu.np.openmap.service.BaseService;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class OsmListJaxbProcessor<T extends PersistentEntity, Xml> extends CompressedXmlJaxbProcessor<List<T>, Xml> {

    private List<T> list;

    @Override
    protected Consumer<List<T>> getResultConsumer() {
        return (list) -> {
            list = list.subList(0, Math.min(list.size(), 10_000));
            list.forEach(e -> e.setId(null));
            getService().save(list);
        };
    }

    protected abstract BaseService<T, ?, ?> getService();

    protected abstract Function<Xml, T> getMappingFunc();

    @Override
    protected void initResult() {
        list = new ArrayList<>();
    }

    @Override
    protected void processXmlObject(Xml xmlObject) {
        list.add(getMappingFunc().apply(xmlObject));
    }

    @Override
    protected List<T> getResult() {
        return list.stream().collect(Collectors.toUnmodifiableList());
    }

    @Override
    protected String getRootNamespaceURI() {
        return "http://openstreetmap.org/osm/0.6";
    }
}
