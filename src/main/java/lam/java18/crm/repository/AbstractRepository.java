package lam.java18.crm.repository;

import lam.java18.crm.exception.DatabaseNotFoundException;
import lam.java18.crm.jdbc.MysqlConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AbstractRepository<T> {
    public T executeQuery(JdbcExecute<T> processor) {
        try (Connection connection = MysqlConnection.getConnection()) {
            return processor.processQuery(connection);
        } catch (SQLException e) {
            throw new DatabaseNotFoundException(e.getMessage());
        }
    }

    public T executeQuerySingle(JdbcExecute<T> processor) {
        try (Connection connection = MysqlConnection.getConnection()) {
            return (T) processor.processQuery(connection);
        } catch (SQLException e) {
            throw new DatabaseNotFoundException(e.getMessage());
        }
    }

    public int executeUpdate(JdbcExecute<Integer> processor) {
        try (Connection connection = MysqlConnection.getConnection()) {
            return processor.processQuery(connection);
        } catch (SQLException e) {
            throw new DatabaseNotFoundException(e.getMessage());
        }
    }
}
