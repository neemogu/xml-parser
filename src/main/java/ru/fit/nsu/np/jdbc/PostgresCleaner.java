package ru.fit.nsu.np.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import static ru.fit.nsu.np.jdbc.PostgresConnectionConfig.connectionStr;
import static ru.fit.nsu.np.jdbc.PostgresConnectionConfig.user;
import static ru.fit.nsu.np.jdbc.PostgresConnectionConfig.password;

public class PostgresCleaner {

    private final static String cleanQuery = """
    delete from node;
    delete from way;
    delete from relation;""";

    public static void cleanTables() throws Exception {
        try (Connection connection = DriverManager.getConnection(connectionStr, user, password)) {
            try (Statement statement = connection.createStatement()) {
                statement.execute(cleanQuery);
            }
        }
    }

    public static void main(String[] args) {
        try {
            PostgresCleaner.cleanTables();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
