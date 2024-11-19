package is.lab1.auth;

import is.lab1.user.RegisteredUserDto;
import is.lab1.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthApiImpl implements AuthApi {
    private final UserService userService;

    @Autowired
    public AuthApiImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<RegisteredUserDto> register(SignUpDto signUpDto) {
        System.out.println("Received registration request: " + signUpDto);
        RegisteredUserDto registeredUserDto = userService.register(signUpDto);
        System.out.println("User registered successfully: " + registeredUserDto.getLogin());
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUserDto);
    }

    @Override
    public ResponseEntity<RegisteredUserDto> authorize(AuthDto authDto) {
        System.out.println("Received authorization request: " + authDto);
        RegisteredUserDto registeredUserDto = userService.authorize(authDto);
        System.out.println("User authorized successfully: " + registeredUserDto.getLogin());
        return ResponseEntity.ok(registeredUserDto);
    }

    @Override
    public ResponseEntity<Void> requestAdminRole(String token) {
        System.out.println("Received request for admin role with token: " + token);
        final boolean success = userService.requestAdminRights(token);
        final HttpStatus httpStatus = success ? HttpStatus.OK : HttpStatus.ACCEPTED;
        System.out.println("Admin role request result: " + (success ? "success" : "failure"));
        return ResponseEntity.status(httpStatus).build();
    }
}
