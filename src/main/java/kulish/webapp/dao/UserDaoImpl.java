package kulish.webapp.dao;

import kulish.webapp.models.Role;
import kulish.webapp.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    public UserDaoImpl(EntityManagerFactory entityManager) {
        this.entityManager = entityManager.createEntityManager();
    }

    @Override
    public void addUser(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        Role role = findRoleByRoleName("ROLE_USER");
        user.addRole(role);
        entityManager.persist(user);
    }

    @Override
    public void updateUser(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        entityManager.merge(user);
    }

    @Override
    public void removeUser(Long id) {
        if (findUserById(id) != null) {
            entityManager.remove(findUserById(id));
        }
    }

    @Override
    public User findUserById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User findUserByUsername(String username) throws UsernameNotFoundException {
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :name", User.class);
        query.setParameter("name", username);
        if (username == null) {
            throw new UsernameNotFoundException("User not found");
        }
        User user = query.getSingleResult();
        user.getRoles().size();
        return user;
    }

    private Role findRoleByRoleName(String userRole) {
        TypedQuery<Role> query = entityManager.createQuery("SELECT r FROM Role r WHERE r.rolename = :rolename", Role.class);
        query.setParameter("rolename", userRole);
        return query.getSingleResult();
    }

    @Override
    public List<User> getListUsers() {
        TypedQuery<User> query = entityManager
                .createQuery("select u from User u", User.class);
        return query.getResultList();
    }

    public List<Role> getRoles() {
        return roleRepository.findAll();
    }
}
