package Mappers;

import Enums.UserRole;
import Models.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper {
    public static User map(ResultSet resultSet)throws SQLException {
         return new User(
                 resultSet.getString("id"),
                 resultSet.getString("full_name"),
                 resultSet.getString("email"),
                 resultSet.getString("phone"),
                 resultSet.getBoolean("is_logged"),
                 resultSet.getString("password"),
                 UserRole.valueOf(resultSet.getString("role"))
         );
    }
    private UserMapper() {
    }
}
