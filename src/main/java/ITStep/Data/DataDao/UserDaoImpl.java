package ITStep.Data.DataDao;

import ITStep.Data.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;

@Repository("userDao")
@Transactional
public class UserDaoImpl implements Dao<User> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User findOne(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public List<User> findAll() {
        return entityManager
                .createQuery("select User from User user", User.class)
                .getResultList();
    }

    @Override
    public boolean create(User entity) {
        try {
            entityManager.persist(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean update(User entity) {
        try {
            entityManager.merge(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(User entity) {
        try {
            entityManager.remove(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
