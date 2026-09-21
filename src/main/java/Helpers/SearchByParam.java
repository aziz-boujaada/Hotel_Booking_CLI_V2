package Helpers;

import Config.DatabaseConfig;
import Mappers.UserMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;



public class SearchByParam {

    public  static <T>  Optional<T> searchByParameter(
            String parameter ,
            String query ,
            DatabaseConfig databaseConfig,
            ResultSetMapper<T> mapper
            ){

        try (
                Connection connection = databaseConfig.getConnection();
                PreparedStatement statement = connection.prepareStatement(query)
        ) {
            statement.setString(1, parameter);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (!resultSet.next()) {
                    return Optional.empty();
                }

                return Optional.of(mapper.map(resultSet));
            }

        } catch (SQLException e) {
            throw new RuntimeException("nothing found by this parameter ", e);
        }
    }
    private SearchByParam() {}
}
