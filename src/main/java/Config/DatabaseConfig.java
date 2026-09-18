package Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    private static final String URL =
            "jdbc:postgresql://localhost:5432/your_DB";

    private static final String USER = "UserName";

    private static final String PASSWORD = "Password";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
