package is.lab1.admin;

import is.lab1.user.Role;
import is.lab1.user.UserDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdminService {
    List<UserDto> getAllPotentialAdmins(Pageable paging);

    void setNewRoleToPotentialAdmin(Long id, Role role);
}