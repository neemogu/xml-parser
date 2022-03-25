package ru.fit.nsu.np.jdbc;

import ru.fit.nsu.np.openmap.dao.NodeEntity;

import java.util.List;

public interface NodeQueryExecutor {
    void saveNodes(List<NodeEntity> nodes) throws Exception;
}
