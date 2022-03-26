package ru.fit.nsu.np.jdbc;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import ru.fit.nsu.np.converters.TagHStoreConverter;
import ru.fit.nsu.np.openmap.dao.NodeEntity;

import java.sql.*;
import java.util.List;

import static ru.fit.nsu.np.jdbc.PostgresConnectionConfig.*;

@Slf4j
public class NodePreparedStatementQueryExecutor implements NodeQueryExecutor {

    @Override
    public void saveNodes(List<NodeEntity> nodes) throws Exception {
        try (Connection connection = DriverManager.getConnection(connectionStr, user, password)) {
            connection.setAutoCommit(false);
            String preparedSql = "INSERT INTO node " +
                    "(id, \"user\", uid, visible, version, changeset, timestamp, lat, lon, tags) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(preparedSql)) {
                TagHStoreConverter converter = new TagHStoreConverter();
                for (NodeEntity nodeEntity : nodes) {
                    String tags = null;
                    try {
                        tags = converter.convertToDatabaseColumn(nodeEntity.getTags());
                    } catch (Exception e) {
                        log.error("Failed to stringify tags", e);
                    }
                    statement.setObject(1, nodeEntity.getId(), Types.BIGINT);
                    statement.setString(2, nodeEntity.getUser());
                    statement.setObject(3, nodeEntity.getUid(), Types.BIGINT);
                    statement.setObject(4, nodeEntity.getVisible(), Types.BOOLEAN);
                    statement.setObject(5, nodeEntity.getVersion(), Types.BIGINT);
                    statement.setObject(6, nodeEntity.getChangeset(), Types.BIGINT);
                    statement.setTimestamp(7, nodeEntity.getTimestamp() == null ? null :
                            Timestamp.valueOf(nodeEntity.getTimestamp()));
                    statement.setObject(8, nodeEntity.getLat(), Types.DOUBLE);
                    statement.setObject(9, nodeEntity.getLon(), Types.DOUBLE);
                    statement.setString(10, tags);
                    statement.executeUpdate();
                }
                connection.commit();
            } catch (Exception e) {
                connection.rollback();
                throw e;
            }
        }
    }
}
