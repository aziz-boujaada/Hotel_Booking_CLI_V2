package Helpers;

import Config.DatabaseConfig;
import Mappers.RoomMapper;
import Models.Room;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ShowAll {

    public static <T> List<T> showAll(
            String parameter,  // Optional: can be null when the SQL query contains no parameter
            String query,
            DatabaseConfig databaseConfig,
            ResultSetMapper<T> mapper
    ) {
        List<T> roomList = new ArrayList<>();

        try (
                Connection connection = databaseConfig.getConnection();
                PreparedStatement statement = connection.prepareStatement(query);

        ) {
            if (parameter != null) {
                statement.setString(1, parameter);
            }

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    roomList.add(mapper.map(resultSet));
                }
            }
            return roomList;

        } catch (Exception e) {
            throw new RuntimeException("No result " , e);
        }
    }
}
