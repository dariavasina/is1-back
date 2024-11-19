package is.lab1.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisteredUserDto {
    private Long id;
    private String login;
    private String token;
    private Role role;
}
