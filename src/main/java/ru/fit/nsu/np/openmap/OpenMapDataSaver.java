package ru.fit.nsu.np.openmap;

import lombok.extern.slf4j.Slf4j;
import ru.fit.nsu.np.jdbc.*;
import ru.fit.nsu.np.openmap.dao.NodeEntity;

import java.util.List;

@Slf4j
public class OpenMapDataSaver {

    private final static List<NodeQueryExecutor> queryExecutors = List.of(
            new NodeStatementQueryExecutor(), new NodePreparedStatementQueryExecutor(), new NodeBatchQueryExecutor()
    );

    public static void saveNodes(List<NodeEntity> nodes) {
        for (NodeQueryExecutor queryExecutor : queryExecutors) {
            try {
                long start = System.currentTimeMillis();
                queryExecutor.saveNodes(nodes);
                long end = System.currentTimeMillis();
                log.info("Took {} ms to save all nodes using {}", end - start, queryExecutor.getClass().getSimpleName());
            } catch (Exception e) {
                log.error("Failed to save nodes using {}", queryExecutor.getClass().getSimpleName(), e);
            } finally {
                try {
                    PostgresCleaner.cleanTables();
                } catch (Exception e) {
                    log.error("Failed to clean osm tables", e);
                }
            }
        }
    }
}
