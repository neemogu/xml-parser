package ru.fit.nsu.np.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class PostgresInitializer {
    private final static String initQuery = """
        CREATE TABLE IF NOT EXISTS node (
             "id" bigint PRIMARY KEY,
             "user" varchar(100),
             "uid" bigint,
             "visible" boolean,
             "version" bigint,
             "changeset" bigint,
             "timestamp" timestamp,
             "lat" double precision,
             "lon" double precision,
             "tags" text
         );
                     
         CREATE TABLE IF NOT EXISTS way (
             "id" bigint PRIMARY KEY,
             "user" varchar(100),
             "uid" bigint,
             "visible" boolean,
             "version" bigint,
             "changeset" bigint,
             "timestamp" timestamp,
             "tags" text,
             "nds" text
         );
                     
         CREATE TABLE IF NOT EXISTS relation (
             "id" bigint PRIMARY KEY,
             "user" varchar(100),
             "uid" bigint,
             "visible" boolean,
             "version" bigint,
             "changeset" bigint,
             "timestamp" timestamp,
             "tags" text,
             "members" text
        );""";

    public static void initPostgresDdl() throws Exception {
        try (Connection connection = DriverManager.getConnection(
                PostgresConnectionConfig.connectionStr, PostgresConnectionConfig.user, PostgresConnectionConfig.password
        )) {
            try (Statement statement = connection.createStatement()) {
                statement.execute(initQuery);
            }
        }
    }

    public static void main(String[] args) {
        try {
            PostgresInitializer.initPostgresDdl();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
