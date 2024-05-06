package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionProvider {

    private final static DBConnectionProvider INSTANCE = new DBConnectionProvider();
    private Connection connection;
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/company_employee";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "postgres";

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    private DBConnectionProvider() {
    }

    public static DBConnectionProvider getInstance() {
        return INSTANCE;
    }
}
