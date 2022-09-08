package kulish.webapp.services;

import kulish.webapp.models.Role;
import kulish.webapp.models.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    void addUser(User user);

    void updateUser(User user);

    void removeUser(Long id);

    User findUserById(Long id);

    List<User> getListUsers();

    List<Role> getRoles();
}
