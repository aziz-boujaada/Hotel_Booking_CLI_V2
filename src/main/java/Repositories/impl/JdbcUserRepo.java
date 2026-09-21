package Repositories.impl;

import Config.DatabaseConfig;
import Helpers.SearchByParam;
import Mappers.UserMapper;
import Models.User;
import Repositories.UserRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class JdbcUserRepo implements UserRepository {

    private final DatabaseConfig databaseConfig;

    public JdbcUserRepo(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    private static final HashMap<String, User> users = new HashMap<>();

    @Override
    public User save(User user) {
        String sql = """
                INSERT INTO users( id , full_name , email ,phone , password , is_logged , role)
                VALUES(?, ?, ?, ?, crypt(? , gen_salt('bf')), ?, ? )
                """;
        try (Connection connection = databaseConfig.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getId());
            statement.setString(2, user.getFullName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPhone());
            statement.setString(5, user.getPassword());
            statement.setBoolean(6, user.isLogged());
            statement.setString(7, user.getRole().name());

            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Save User Failed "+ e.getMessage(),e);
        }

        return user;
    }

    @Override
    public Optional<User> findByEmail(String email) {

        String sql = """
            SELECT id, full_name, email, phone, is_logged, password, role
            FROM users
            WHERE email = ?
            """;

        try (
                Connection connection = databaseConfig.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, email);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (!resultSet.next()) {
                    return Optional.empty();
                }

                return Optional.of(UserMapper.map(resultSet));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to find user by email", e);
        }
    }

    @Override
    public Optional<User> login(String email, String password) {
        String sql = """
                UPDATE users
                SET is_logged = true
                WHERE email = ?
                AND password = crypt(?, password)
                RETURNING id, full_name, email, phone, is_logged, password, role
                """;
        try (
                Connection connection = databaseConfig.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, email);
            statement.setString(2, password);

            try(ResultSet resultSet = statement.executeQuery()){
                if(!resultSet.next()){
                    return Optional.empty();
                }
                return Optional.of(UserMapper.map(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Login Failed" + e.getMessage(), e);
        }

    }

    @Override
    public Optional<User> findById(String id) {

        String sql = """
            SELECT id, full_name, email, phone, is_logged, password, role
            FROM users
            WHERE id = ?
            """;

       return  SearchByParam.searchByParameter(
               id ,
               sql ,
               databaseConfig,
               UserMapper::map
       );
    }


    @Override
    public boolean existsByEmail(String email) {

        String sql = """
            SELECT EXISTS(
                SELECT 1
                FROM users
                WHERE email = ?
            )
            """;

        try (
                Connection connection = databaseConfig.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, email);

            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getBoolean(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Check email existence failed", e);
        }
    }

    @Override
    public List<User> findAll() {

        String sql = """
            SELECT id, full_name, email, phone, is_logged, password, role
            FROM users
            """;

        List<User> users = new ArrayList<>();

        try (
                Connection connection = databaseConfig.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {

            while (resultSet.next()) {
                users.add(UserMapper.map(resultSet));
            }

            return users;

        } catch (SQLException e) {
            throw new RuntimeException("Find all users failed", e);
        }
    }

    @Override
    public User update(User user) {

        String sql = """
            UPDATE users
            SET full_name = ?,
                email = ?,
                phone = ?,
                is_logged = ?,
                role = ?
            WHERE id = ?
            """;

        try (
                Connection connection = databaseConfig.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, user.getFullName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPhone());
            statement.setBoolean(4, user.isLogged());
            statement.setString(5, user.getRole().name());
            statement.setString(6, user.getId());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) {
                throw new IllegalArgumentException(
                        "User with id " + user.getId() + " not found"
                );
            }

            return user;

        } catch (SQLException e) {
            throw new RuntimeException("Update user failed", e);
        }
    }

    @Override
    public void updatePassword(User user, String password) {

        String sql = """
            UPDATE users
            SET password = crypt(?, gen_salt('bf'))
            WHERE id = ?
            """;

        try (
                Connection connection = databaseConfig.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, password);
            statement.setString(2, user.getId());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) {
                throw new IllegalArgumentException(
                        "User with id " + user.getId() + " not found"
                );
            }

        } catch (SQLException e) {
            throw new RuntimeException("Update password failed", e);
        }
    }

}
