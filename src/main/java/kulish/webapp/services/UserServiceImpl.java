package kulish.webapp.services;

import kulish.webapp.dao.UserDao;
import kulish.webapp.models.Role;
import kulish.webapp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public void addUser(User user) {
        this.userDao.addUser(user);
    }

    @Override
    @Transactional
    public void updateUser(User user) {
        this.userDao.updateUser(user);
    }

    @Override
    @Transactional
    public void removeUser(Long id) {
        this.userDao.removeUser(id);
    }

    @Override
    public User findUserById(Long id) {
        return this.userDao.findUserById(id);
    }

    @Override
    public List<User> getListUsers() {
        return this.userDao.getListUsers();
    }

    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
        User user = userDao.findUserByUsername(username);
        return user;
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map((r -> new SimpleGrantedAuthority(r.getRolename()))).collect(Collectors.toList());
    }

    @Override
    public List<Role> getRoles() {
        return userDao.getRoles();
    }
}
