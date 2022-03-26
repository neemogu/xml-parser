package ru.fit.nsu.np.xml.jaxb;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fit.nsu.np.jaxb.Node;
import ru.fit.nsu.np.openmap.dao.NodeEntity;
import ru.fit.nsu.np.openmap.service.BaseService;
import ru.fit.nsu.np.openmap.service.NodeService;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class NodeListJaxbProcessor extends OsmListJaxbProcessor<NodeEntity, Node> {

    private final NodeService nodeService;

    @Override
    protected Class<Node> getXmlClass() {
        return Node.class;
    }

    @Override
    protected BaseService<NodeEntity, ?, ?> getService() {
        return nodeService;
    }

    @Override
    protected Function<Node, NodeEntity> getMappingFunc() {
        return NodeEntity::fromXmlObject;
    }
}
