package Config;

import java.sql.Connection;

public class DatabaseTest {

    public static void main(String[] args) {

        try (Connection connection = DatabaseConfig.getInstance().getConnection()) {

            System.out.println("Database connected successfully!");
            System.out.println("Database: " + connection.getCatalog());

        } catch (Exception e) {

            System.out.println("Database connection failed!");
            e.printStackTrace();
        }
    }
}
