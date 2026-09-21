package Repositories.impl;

import Config.DatabaseConfig;
import DTOs.UserDto;
import Enums.UserRole;
import Models.User;
import Repositories.UserRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class InMemoryUserRepo implements UserRepository {

    private final DatabaseConfig databaseConfig;

    public InMemoryUserRepo(DatabaseConfig databaseConfig) {
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
            throw new RuntimeException("Save User Failed ", e);
        }

        return user;
    }

    @Override
    public Optional<User> login(String email, String password) {

        String sql = """
                SELECT * FROM users WHERE email = ? AND  password = crypt(? , password)
                """;

        try (
                Connection connection = databaseConfig.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setString(1, email);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    return Optional.empty();
                }

                User user = new User(
                        resultSet.getString("id"),
                        resultSet.getString("full_name"),
                        resultSet.getString("email"),
                        resultSet.getString("phone"),
                        resultSet.getBoolean("is_logged"),
                        resultSet.getString("password"),
                        UserRole.valueOf(resultSet.getString("role"))
                );
            return  Optional.of(user);
            }
        } catch (SQLException e) {

            throw new RuntimeException("Login Failed" + e.getMessage(), e);
        }

    }

    @Override
    public Optional<User> findById(String id) {
        for (User user : users.values()) {
            if (user.getId().equalsIgnoreCase(id)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByEmail(String email){
        for(User user : users.values()){
            if(user.getEmail().equalsIgnoreCase(email)){
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean existsByEmail(String email){
        return findByEmail(email).isPresent();
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User update(User user) {
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public void updatePassword(User user, String password) {
        user.setPassword(password);
    }

}
