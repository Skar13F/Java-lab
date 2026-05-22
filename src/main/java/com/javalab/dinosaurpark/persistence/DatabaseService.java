package com.javalab.dinosaurpark.persistence;

import com.javalab.dinosaurpark.record.EventRecord;
import com.javalab.dinosaurpark.record.ExpenseRecord;
import com.javalab.dinosaurpark.record.RevenueRecord;
import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DatabaseService {

    private final Connection connection;

    public DatabaseService(String dbPath) {

        try {

            this.connection =
                    DriverManager.getConnection(
                            "jdbc:h2:" + dbPath,
                            "sa",
                            ""
                    );

            runLiquibase();

        } catch (Exception e) {

            throw new RuntimeException(
                    "Error al iniciar la base de datos",
                    e
            );
        }
    }

    private void runLiquibase() throws Exception {

        Database database =
                DatabaseFactory.getInstance()
                        .findCorrectDatabaseImplementation(
                                new JdbcConnection(connection)
                        );

        Liquibase liquibase =
                new Liquibase(
                        "db/changelog/db.changelog-master.xml",
                        new ClassLoaderResourceAccessor(),
                        database
                );

        liquibase.update(
                new Contexts(),
                new LabelExpression()
        );
    }

    public void appendRevenue(RevenueRecord record) {

        String sql = """
                INSERT INTO revenues
                (type, amount, tourist_id, zone, timestamp)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (PreparedStatement stmt =
                     connection.prepareStatement(sql)) {

            stmt.setString(1, record.type());
            stmt.setDouble(2, record.amount());
            stmt.setInt(3, record.touristId());
            stmt.setString(4, record.zone());
            stmt.setTimestamp(
                    5,
                    java.sql.Timestamp.valueOf(
                            record.timestamp()
                    )
            );

            stmt.executeUpdate();

        } catch (Exception e) {

            throw new RuntimeException(
                    "Error insertando ingresos",
                    e
            );
        }
    }

    public void appendExpense(ExpenseRecord record) {

        String sql = """
                INSERT INTO expenses
                (type, amount, description, timestamp)
                VALUES (?, ?, ?, ?)
                """;

        try (PreparedStatement stmt =
                     connection.prepareStatement(sql)) {

            stmt.setString(1, record.type());
            stmt.setDouble(2, record.amount());
            stmt.setString(3, record.description());

            stmt.setTimestamp(
                    4,
                    java.sql.Timestamp.valueOf(
                            record.timestamp()
                    )
            );

            stmt.executeUpdate();

        } catch (Exception e) {

            throw new RuntimeException(
                    "Error insertando gastos",
                    e
            );
        }
    }

    public void appendEvent(EventRecord record) {

        String sql = """
                INSERT INTO events
                (step, event_name, description,
                 affected_entities, timestamp)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (PreparedStatement stmt =
                     connection.prepareStatement(sql)) {

            stmt.setInt(1, record.step());
            stmt.setString(2, record.eventName());
            stmt.setString(3, record.description());
            stmt.setString(4, record.affectedEntities());

            stmt.setTimestamp(
                    5,
                    java.sql.Timestamp.valueOf(
                            record.timestamp()
                    )
            );

            stmt.executeUpdate();

        } catch (Exception e) {

            throw new RuntimeException(
                    "Error insertando eventos",
                    e
            );
        }
    }

    public void close() {

        try {

            if (connection != null &&
                    !connection.isClosed()) {

                connection.close();
            }

        } catch (Exception e) {

            throw new RuntimeException(
                    "Error cerrando la conexión a la base de datos",
                    e
            );
        }
    }

    public Connection getConnection() {
        return connection;
    }
}