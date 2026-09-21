package Services;

import Config.DatabaseConfig;
import  Enums.UserRole;
import  Models.User;

import Repositories.impl.InMemoryUserRepo;
import  Utils.InputValidation;

import java.util.Optional;

///
public class AuthService {

    private User loggedUser;

    private final InputValidation validator;
    private final InMemoryUserRepo userRepo;
    private final DatabaseConfig databaseConfig;

    public AuthService(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
        this.validator = new InputValidation();
        this.userRepo = new InMemoryUserRepo(databaseConfig);
    }

    // REGISTER
    public User register(String fullName, String email, String phone, String password, UserRole role) {

        validator.validateNames(fullName);
        validator.validateEmail(email);
        validator.validatePassword(password);

        User user = new User(null, fullName, email, phone, false, password, role);

        return userRepo.save(user);
    }

    // LOGIN
    public User login(String email, String password) {

        validator.validateEmail(email);
        validator.validatePassword(password);

        Optional<User> optionalUser = userRepo.login(email , password);

        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("email or password incorrect");
        }

        User user = optionalUser.get();


        user.setLogged(true);
        loggedUser = user;
        return user;

    }

    // LOGOUT

    public void logout(User user) {
        if (user.isLogged()) {
            user.setLogged(false);
        }
    }

    public void changePassword(User user, String password) {

        validator.validatePassword(password);
        userRepo.updatePassword(user, password);

    }

    // UPDATE PROFILE
    public User updateProfile(User user, String fullName, String email, String phone) {
        if (user == null) {
            throw new IllegalArgumentException("User is required");
        }

        validator.validateNames(fullName);
        validator.validateEmail(email);
        validator.validateEmpty(phone);

        Optional<User> userWithEmail = userRepo.findByEmail(email);
        if (userWithEmail.isPresent() && !userWithEmail.get().getId().equals(user.getId())) {
            throw new IllegalArgumentException("This email is already in use");
        }

        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhone(phone);

        return userRepo.update(user);
    }

    public User getLoggedUser() {
        return loggedUser;
    }
}
