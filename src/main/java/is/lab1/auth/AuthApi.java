package is.lab1.auth;

import is.lab1.user.RegisteredUserDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

public interface AuthApi {
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
    ResponseEntity<RegisteredUserDto> register(@RequestBody SignUpDto signUpDto);

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE + "; charset=utf-8")
    ResponseEntity<RegisteredUserDto> authorize(@RequestBody AuthDto authDto);

    @PostMapping("/request/admin")
    ResponseEntity<Void> requestAdminRole(@RequestHeader(name = "Authorization") String token);
}
