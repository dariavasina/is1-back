package is.lab1.user;

import is.lab1.auth.SignUpDto;

public class UserMapper {
    public static User toEntity(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .login(userDto.getLogin())
                .build();
    }

    public static User toEntity(SignUpDto signUpDto) {
        return User.builder()
                .login(signUpDto.getLogin())
//                .password(signUpDto.getPassword())
                .role(signUpDto.getRole())
                .build();
    }

    public static UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .login(user.getLogin())
                .build();
    }

    public static RegisteredUserDto toRegisteredUserDto(User user) {
        // token is set in UserServiceImpl
        return RegisteredUserDto.builder()
                .id(user.getId())
                .login(user.getLogin())
                .role(user.getRole())
                .build();
    }
}
