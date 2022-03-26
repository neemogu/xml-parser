package ru.fit.nsu.np.jdbc;

import lombok.extern.slf4j.Slf4j;
import org.postgresql.util.HStoreConverter;
import ru.fit.nsu.np.converters.TagHStoreConverter;
import ru.fit.nsu.np.openmap.dao.NodeEntity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static ru.fit.nsu.np.jdbc.PostgresConnectionConfig.*;

@Slf4j
public class NodeStatementQueryExecutor implements NodeQueryExecutor {

    private final TagHStoreConverter tagHStoreConverter = new TagHStoreConverter();

    @Override
    public void saveNodes(List<NodeEntity> nodes) throws Exception {
        try (Connection connection = DriverManager.getConnection(connectionStr, user, password)) {
            connection.setAutoCommit(false);
            try (Statement statement = connection.createStatement()) {
                log.info("Started saving nodes using NodeStatementQueryExecutor");
                for (NodeEntity nodeEntity : nodes) {
                    String insertQuery = "INSERT INTO node " +
                            "(id, \"user\", uid, visible, version, changeset, timestamp, lat, lon, tags) " +
                            "VALUES (" + nodeToCsv(nodeEntity) + ")";
                    statement.execute(insertQuery);
                }
                connection.commit();
            } catch (Exception e) {
                connection.rollback();
                throw e;
            }
        }
    }

    private String nodeToCsv(NodeEntity nodeEntity) {
        String tags = "NULL";
        try {
            tags = tagHStoreConverter.convertToDatabaseColumn(nodeEntity.getTags());
            if (tags == null) {
                tags = "NULL";
            }
        } catch (Exception e) {
            log.error("Failed to stringify tags", e);
        }
        return  (nodeEntity.getId() == null ? "NULL" : nodeEntity.getId()) + "," +
                (nodeEntity.getUser() == null ? "NULL" : "'" + nodeEntity.getUser().replaceAll("'", "''") + "'") + "," +
                (nodeEntity.getUid() == null ? "NULL" : nodeEntity.getUid()) + "," +
                (nodeEntity.getVisible() == null ? "NULL" : nodeEntity.getVisible()) + "," +
                (nodeEntity.getVersion() == null ? "NULL" : nodeEntity.getVersion()) + "," +
                (nodeEntity.getChangeset() == null ? "NULL" : nodeEntity.getChangeset()) + "," +
                (nodeEntity.getTimestamp() == null ? "NULL" :
                        "'" + nodeEntity.getTimestamp().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "'") + "," +
                (nodeEntity.getLat() == null ? "NULL" : nodeEntity.getLat()) + "," +
                (nodeEntity.getLon() == null ? "NULL" : nodeEntity.getLon()) + "," +
                (nodeEntity.getTags() == null ? "NULL" : tags.equals("NULL") ? tags : "'" + tags.replaceAll("'", "''") + "'");
    }
}
