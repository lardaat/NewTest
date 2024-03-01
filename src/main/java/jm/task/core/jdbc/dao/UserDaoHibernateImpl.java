package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Transaction transaction = null;
        String SQL = "CREATE TABLE IF NOT EXISTS users (id SERIAL PRIMARY KEY, name VARCHAR(255), lastname VARCHAR(255), age SMALLINT)";
        try (Session session = Util.getHibernateConnection().openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(SQL).executeUpdate();
            transaction.commit();
            System.out.println("Таблица создана");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Ошибка при создании таблицы с Hibernate: " + e.getMessage());
        }
    }



    @Override
    public void dropUsersTable() {
        Transaction transaction = null;
        String SQL = "DROP TABLE IF EXISTS users";
        try (Session session = Util.getHibernateConnection().openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(SQL).executeUpdate();
            transaction.commit();
            System.out.println("Таблица удалена");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Ошибка при удалении таблицы c Hibernate: " + e.getMessage());
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        String SQL = "INSERT INTO users (name, lastname, age) VALUES (?,?,?)";
        try (Session session = Util.getHibernateConnection().openSession()) {
            transaction = session.beginTransaction();
            NativeQuery query = session.createNativeQuery(SQL);
            query.setParameter(1, name);
            query.setParameter(2, lastName);
            query.setParameter(3,age);
            query.executeUpdate();
            transaction.commit();
            System.out.println("Пользователь с именем "+ name + " добавлен в базу данных");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Ошибка при добавлении " + name + " c Hibernate: " + e.getMessage());
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction transaction = null;
        String SQL = "DELETE FROM users WHERE id = ?";
        try (Session session = Util.getHibernateConnection().openSession()) {
            transaction = session.beginTransaction();
            NativeQuery query = session.createNativeQuery(SQL);
            query.setParameter(1, id);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Ошибка при удалении c Hibernate: " + e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsers() {
        Transaction transaction = null;
        String SQL = "SELECT * FROM users";
        try (Session session = Util.getHibernateConnection().openSession()) {
            transaction = session.beginTransaction();
            List<Object[]> rows = session.createNativeQuery(SQL).list();
            List<User> userList = new ArrayList<>();
            for (Object[] row : rows) {
                User user = new User();
                user.setId(Long.valueOf((Integer) row[0]));
                user.setName((String) row[1]);
                user.setLastName((String) row[2]);
                user.setAge(((Short) row[3]).byteValue());
                userList.add(user);
            }
            transaction.commit();
            return userList;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Ошибка при возвращении списка users c Hibernate: " + e.getMessage());
        }
    }

    @Override
    public void cleanUsersTable() {
        Transaction transaction = null;
        String SQL = "DELETE FROM users";
        try (Session session = Util.getHibernateConnection().openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(SQL).executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Ошибка при очищении таблицы с Hibernate: " + e.getMessage());
        }
    }
}