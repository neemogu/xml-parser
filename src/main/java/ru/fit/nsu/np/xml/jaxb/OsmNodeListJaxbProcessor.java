package ru.fit.nsu.np.xml.jaxb;

import ru.fit.nsu.np.jaxb.Node;
import ru.fit.nsu.np.openmap.OpenMapDataSaver;
import ru.fit.nsu.np.openmap.dao.NodeEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class OsmNodeListJaxbProcessor extends CompressedXmlJaxbProcessor<List<NodeEntity>, Node> {

    private List<NodeEntity> nodes;

    @Override
    protected Consumer<List<NodeEntity>> getResultConsumer() {
        return OpenMapDataSaver::saveNodes;
    }

    @Override
    protected Class<Node> getXmlClass() {
        return Node.class;
    }

    @Override
    protected void initResult() {
        nodes = new ArrayList<>();
    }

    @Override
    protected void processXmlObject(Node xmlObject) {
        nodes.add(NodeEntity.fromXmlObject(xmlObject));
    }

    @Override
    protected List<NodeEntity> getResult() {
        return nodes.stream().collect(Collectors.toUnmodifiableList());
    }

    @Override
    protected String getRootNamespaceURI() {
        return "http://openstreetmap.org/osm/0.6";
    }
}
