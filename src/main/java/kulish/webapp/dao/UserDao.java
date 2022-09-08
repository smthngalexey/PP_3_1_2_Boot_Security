package kulish.webapp.dao;

import kulish.webapp.models.Role;
import kulish.webapp.models.User;

import java.util.List;

public interface UserDao {

    void addUser(User user);

    void updateUser(User user);

    void removeUser(Long id);

    User findUserById(Long id);

    User findUserByUsername(String username);

    List<User> getListUsers();

    List<Role> getRoles();
}
