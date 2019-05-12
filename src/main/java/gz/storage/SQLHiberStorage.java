package gz.storage;

import gz.model.User;
import gz.utills.UserHiberDao;

import java.sql.Timestamp;
import java.util.List;

public class SQLHiberStorage implements Storage {

    private UserHiberDao userHiberDao;

    public SQLHiberStorage() {
        userHiberDao = new UserHiberDao();
    }

    public void close() {
        userHiberDao.close();
    }

    @Override
    public void removeAll() {
        int count = userHiberDao.clear();
        System.out.println("Deleted items count: " + count);
    }

    @Override
    public void removeUser(int id) {
        userHiberDao.delete(userHiberDao.getByID(id));
    }

    @Override
    public void removeUserByName(String name) {
        userHiberDao.delete(userHiberDao.getByName(name));
    }

    @Override
    public String addUser(User user) {
        userHiberDao.insert(user);
        return null;
    }

    @Override
    public void updateUser(User user) {
        userHiberDao.update(user);
    }

    @Override
    public User getUserById(int id) {
        return userHiberDao.getByID(id);
    }

    @Override
    public User getUserByName(String name) {
        return userHiberDao.getByName(name);
    }

    @Override
    public List<User> getAllUsers() {
        return userHiberDao.getAll();
    }

    @Override
    public List<User> getByAge(Timestamp timestampFrom, Timestamp timestampTo) {
        return userHiberDao.getByAge(timestampFrom, timestampTo);
    }
}
