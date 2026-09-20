package DTOs;

import Models.User;

public class UserDto {
    public final String fullName;
    public final String email;
    public final String phone;

    public UserDto(
            String fullName,
            String email,
            String phone
    ) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
    }

    public static UserDto fromModel(User user){
        return new UserDto(
                user.getFullName(),
                user.getEmail(),
                user.getPhone()
        );
    }
    @Override
    public String toString() {
        return "UserDto{" +
                "fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
