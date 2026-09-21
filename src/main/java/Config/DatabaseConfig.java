package Config;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DatabaseConfig {

    private static volatile DatabaseConfig instance;

    private final String url;
    private final String username;
    private final String password;

    private DatabaseConfig() {
        try {
            Properties props = new Properties();

            try (InputStream input =
                         getClass().getClassLoader().getResourceAsStream("db.properties")) {

                if (input == null) {
                    throw new RuntimeException(
                            "db.properties introuvable dans src/main/resources"
                    );
                }

                props.load(input);
            }

            this.url = props.getProperty("db.url");
            this.username = props.getProperty("db.user");
            this.password = props.getProperty("db.password");

        } catch (Exception e) {
            throw new RuntimeException(
                    "Erreur critique de configuration JDBC : " + e.getMessage(),
                    e
            );
        }
    }

    public static DatabaseConfig getInstance() {

        if (instance == null) {
            synchronized (DatabaseConfig.class) {
                if (instance == null) {
                    instance = new DatabaseConfig();
                }
            }
        }

        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                url,
                username,
                password
        );
    }
}