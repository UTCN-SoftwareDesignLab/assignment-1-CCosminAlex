package repository.user;

import model.User;
import model.validation.Notification;

import javax.naming.AuthenticationException;
import java.util.List;

public interface UserRepository {
    List<User> findAll();

    Notification<User> findByUsernameAndPassword(String username, String password) throws AuthenticationException, repository.user.AuthenticationException;

    Notification<User> finDById(int id) throws AuthenticationException, repository.user.AuthenticationException;

    boolean save(User user);

    boolean update(User user);

    boolean remove(int id);

    void removeAll();
}