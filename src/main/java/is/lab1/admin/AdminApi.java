package is.lab1.admin;

import is.lab1.user.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface AdminApi {
    @GetMapping("/admin")
    ResponseEntity<List<UserDto>> getAllPotentialAdmins(@RequestParam(defaultValue = "0") int page,
                                                        @RequestParam(defaultValue = "5") int limit);

    @PutMapping("/admin/{id}:admin")
    ResponseEntity<Void> addAdmin(@PathVariable("id") Long id);

    @PutMapping("/admin/{id}:user")
    ResponseEntity<Void> addUser(@PathVariable("id") Long id);
}