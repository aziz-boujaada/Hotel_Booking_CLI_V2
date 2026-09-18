package main.java.Repositories;

import main.java.Models.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user );
    Optional<User> findById(String id);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    List<User> findAll();
    User update(User user);
    void updatePassword(User user , String password);
}
