package ru.fit.nsu.np.xml.jaxb;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fit.nsu.np.jaxb.Node;
import ru.fit.nsu.np.openmap.OpenMapDataSaver;
import ru.fit.nsu.np.openmap.dao.NodeEntity;
import ru.fit.nsu.np.openmap.service.NodeService;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OsmNodeListJaxbProcessor extends CompressedXmlJaxbProcessor<List<NodeEntity>, Node> {

    private final NodeService nodeService;

    private List<NodeEntity> nodes;

    @Override
    protected Consumer<List<NodeEntity>> getResultConsumer() {
        return (list) -> {
            list = list.subList(0, Math.min(list.size(), 10_000));
            nodeService.save(list);
        };
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
