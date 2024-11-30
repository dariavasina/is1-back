package is.lab1.user;

import is.lab1.auth.AuthDto;
import is.lab1.auth.SignUpDto;
import is.lab1.jwt.UserAuthProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserAuthProvider userAuthProvider;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserAuthProvider userAuthProvider,
                           UserRepository userRepository) {
        this.userAuthProvider = userAuthProvider;
        this.userRepository = userRepository;
    }

    @Override
    public RegisteredUserDto register(SignUpDto signUpDto) {
        System.out.println("Registering user: " + signUpDto.getLogin());

        final User user = UserMapper.toEntity(signUpDto);
        user.setPassword(encodePassword(signUpDto.getPassword()));
        user.setRole(Role.USER);

        System.out.println("Saving user to the database: " + user.getLogin());
        userRepository.save(user);

        final RegisteredUserDto registeredUserDto = UserMapper.toRegisteredUserDto(user);
        registeredUserDto.setToken(userAuthProvider.createToken(user.getLogin(), user.getRole()));

        System.out.println("User registered and token generated: " + registeredUserDto.getLogin());
        return registeredUserDto;
    }

    @Override
    public RegisteredUserDto authorize(AuthDto authDto) {
        System.out.println("Authorizing user: " + authDto.getLogin());

        final User user = userRepository.findByLogin(authDto.getLogin())
                .orElseThrow(() -> {
                    System.out.println("No such user found: " + authDto.getLogin());
                    return new ResponseStatusException(HttpStatus.BAD_REQUEST, "No such user");
                });

        final String requestPassword = encodePassword(authDto.getPassword());
        System.out.println("Password encoding completed.");

        if (requestPassword.equals(user.getPassword())) {
            System.out.println("Password matches for user: " + user.getLogin());
            final RegisteredUserDto registeredUserDto = UserMapper.toRegisteredUserDto(user);
            registeredUserDto.setToken(userAuthProvider.createToken(user.getLogin(), user.getRole()));
            return registeredUserDto;
        }

        System.out.println("Bad credentials for user: " + authDto.getLogin());
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Bad credentials");
    }

    @Override
    public User getUserByToken(String token) {
        System.out.println("Extracting login from token: " + token);
        final String login = userAuthProvider.getLoginFromJwt(token);
        System.out.println("Login extracted from token: " + login);

        return userRepository.findByLogin(login)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No such user"));
    }

    @Override
    public List<String> getAllUserNames() {
        System.out.println("Fetching all usernames.");
        return userRepository.findAll().stream().map(User::getLogin).toList();
    }


    @Override
    public User getUserByLogin(String login) {
        System.out.println("Fetching user by login: " + login);
        return userRepository.findByLogin(login)
                .orElse(null);
    }

    @Override
    public boolean requestAdminRights(String token) {
        System.out.println("Requesting admin rights for user with token: " + token);
        final User user = getUserByToken(token.split(" ")[1]);
        System.out.println("User retrieved for admin rights request: " + user.getLogin());

        if (userRepository.existsByRole(Role.ADMIN)) {
            System.out.println("Admin role already exists. Changing user role to POTENTIAL_ADMIN.");
            user.setRole(Role.POTENTIAL_ADMIN);
            userRepository.save(user);
            return false;
        }

        System.out.println("Granting admin rights to user: " + user.getLogin());
        user.setRole(Role.ADMIN);
        userRepository.save(user);
        return true;
    }

//    private String encodePassword(String password) {
//        try {
//            System.out.println("Encoding password.");
//            final MessageDigest messageDigest = MessageDigest.getInstance("SHA-384");
//            return new String(messageDigest.digest(password.getBytes()));
//        } catch (NoSuchAlgorithmException e) {
//            System.err.println("Error encoding password: " + e.getMessage());
//            throw new IllegalStateException("Couldn't encode password", e);
//        }
//    }
private String encodePassword(String password) {
    try {
        System.out.println("Encoding password.");
        final MessageDigest messageDigest = MessageDigest.getInstance("SHA-384");
        byte[] hashedBytes = messageDigest.digest(password.getBytes());

        // Преобразуем в строку в формате Base64
        return java.util.Base64.getEncoder().encodeToString(hashedBytes);
    } catch (NoSuchAlgorithmException e) {
        System.err.println("Error encoding password: " + e.getMessage());
        throw new IllegalStateException("Couldn't encode password", e);
    }
}

}
