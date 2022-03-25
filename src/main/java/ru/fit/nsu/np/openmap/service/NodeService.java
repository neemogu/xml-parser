package ru.fit.nsu.np.openmap.service;

import org.springframework.stereotype.Service;
import ru.fit.nsu.np.openmap.dao.NodeEntity;
import ru.fit.nsu.np.openmap.repository.NodeRepository;

@Service
public class NodeService extends BaseService<NodeEntity, NodeRepository> {
    public NodeService(NodeRepository repository) {
        super(repository);
    }
}
