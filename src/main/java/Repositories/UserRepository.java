package Repositories;

import DTOs.UserDto;
import  Models.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user );
    Optional<User> findById(String id);
    Optional<User> login(String email , String password);
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    List<User> findAll();
    User update(User user);
    void updatePassword(User user , String password);
}
