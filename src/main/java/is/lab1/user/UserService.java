package is.lab1.user;

import is.lab1.auth.AuthDto;
import is.lab1.auth.SignUpDto;

import java.util.List;

public interface UserService {
    RegisteredUserDto register(SignUpDto signUpDto);

    RegisteredUserDto authorize(AuthDto authDto);

    boolean requestAdminRights(String token);

    User getUserByToken(String token);

    User getUserByLogin(String login);

    List<String> getAllUserNames();
}
