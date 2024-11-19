package is.lab1.admin;

import is.lab1.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;

    @Autowired
    public AdminServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserDto> getAllPotentialAdmins(Pageable paging) {
        return userRepository.findAllByRole(Role.POTENTIAL_ADMIN, paging)
                .stream()
                .map(UserMapper::toDto)
                .toList();
    }

    @Override
    public void setNewRoleToPotentialAdmin(Long id, Role role) {
        final User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        user.setRole(role);
        userRepository.save(user);
    }
}