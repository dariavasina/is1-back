package is.lab1.jwt;

import is.lab1.user.Role;
import org.springframework.security.core.Authentication;

public interface UserAuthProvider {
    String createToken(String login, Role role);

    String getLoginFromJwt(String token);

    Authentication validateToken(String token);
}
