package gz.storage;

import gz.model.User;

import java.sql.Timestamp;
import java.util.List;

public interface Storage {

    void removeAll();

    void removeUser(int id);

    void removeUserByName(String name);

    String addUser(User user);

    void updateUser(User user);

    User getUserById(int id);

    User getUserByName(String name);

    List<User> getAllUsers();

    List<User> getByAge(Timestamp timestampFrom, Timestamp timestampTo);

}
