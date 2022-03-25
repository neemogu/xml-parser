package ru.fit.nsu.np.jdbc;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import ru.fit.nsu.np.openmap.dao.NodeEntity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static ru.fit.nsu.np.jdbc.PostgresConnectionConfig.*;

@Slf4j
public class NodeStatementQueryExecutor implements NodeQueryExecutor {

    @Override
    public void saveNodes(List<NodeEntity> nodes) throws Exception {
        try (Connection connection = DriverManager.getConnection(connectionStr, user, password)) {
            try (Statement statement = connection.createStatement()) {
                log.info("Started saving nodes using NodeStatementQueryExecutor");
                for (NodeEntity nodeEntity : nodes) {
                    String insertQuery = "INSERT INTO node " +
                            "(id, \"user\", uid, visible, version, changeset, timestamp, lat, lon, tags) " +
                            "VALUES (" + nodeToCsv(nodeEntity) + ")";
                    log.info("Executing update: _{}_", insertQuery);
                    statement.execute(insertQuery);
                }
            }
        }
    }

    private String nodeToCsv(NodeEntity nodeEntity) {
        ObjectMapper mapper = new ObjectMapper();
        String tags = "NULL";
        try {
            tags = mapper.writeValueAsString(nodeEntity.getTags());
        } catch (Exception e) {
            log.error("Failed to stringify tags", e);
        }
        return  (nodeEntity.getId() == null ? "NULL" : nodeEntity.getId()) + "," +
                (nodeEntity.getUser() == null ? "NULL" : "'" + nodeEntity.getUser() + "'") + "," +
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
