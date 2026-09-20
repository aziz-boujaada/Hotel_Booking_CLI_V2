package Config;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DatabaseConfig {
    private static volatile DatabaseConfig instance;
    private Connection connection;

    private DatabaseConfig() {
        try {
// Chargement de la configuration via db.properties
            Properties props = new Properties();
            try (InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties")) {
                if (input == null) {
                    throw new RuntimeException(
                            "db.properties introuvable dans src/main/resources"
                    );
                }
                props.load(input);
            }
            this.connection = DriverManager.getConnection(
                    props.getProperty("db.url"),
                    props.getProperty("db.user"),
                    props.getProperty("db.password")
            );
        } catch (Exception e) {
            throw new RuntimeException("Erreur critique de connexion JDBC : " + e.getMessage(), e);
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

    public Connection getConnection() {
        return connection;
    }
}
