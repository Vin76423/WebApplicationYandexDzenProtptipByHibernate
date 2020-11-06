package by.tms.dao.user;


import by.tms.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Repository
@Transactional
public class UserDbDao implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void createUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User is null!");
        }
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.save(user);
        currentSession.flush();
        currentSession.clear();
    }

    @Override
    public Optional<User> getUserById(long userId) {
        if (userId < 1) {
            throw new IllegalArgumentException("UserId is not correct!");
        }
        Session currentSession = sessionFactory.getCurrentSession();
        User user = currentSession.get(User.class, userId);
        currentSession.clear();
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> getUserByLogin(String login) {
        if (login == null) {
            throw new IllegalArgumentException("Login is null!");
        }
        Session currentSession = sessionFactory.getCurrentSession();
        String sql = "from User u where u.login = :login";
        Optional<User> optionalUser = currentSession.createQuery(sql, User.class)
                .setParameter("login", login)
                .getResultStream()
                .findAny();
        currentSession.clear();
        return optionalUser;
    }
}
