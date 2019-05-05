package gz.utills;

import gz.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class UserDao {

    private SessionFactory sessionFactory;


    public UserDao() {
        java.util.Properties properties = new java.util.Properties();
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream input = classLoader.getResourceAsStream("local.properties");
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sessionFactory = new Configuration().configure().addProperties(properties).buildSessionFactory();
    }

    public void close() {
        sessionFactory.close();
    }


    public int clear() {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            String hql = String.format("DELETE FROM %s", User.class.getSimpleName());
            Query query = session.createQuery(hql);
            int count = query.executeUpdate();
            transaction.commit();
            return count;
        }
    }

    public void insert(User user) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
        }
    }


    public List<User> getAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM User", User.class).list();
        }
    }


    public void delete(User user) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(user);
            transaction.commit();
        }
    }


    public User getByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            return session
                    .createQuery("FROM User WHERE name = :name ", User.class)
                    .setParameter("name", name)
                    .getSingleResult();
        }
    }

    public User getByID(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session
                    .createQuery("FROM User WHERE _id = :id ", User.class)
                    .setParameter("id", id)
                    .getSingleResult();
        }
    }

    public void update(User user) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
        }
    }


}
