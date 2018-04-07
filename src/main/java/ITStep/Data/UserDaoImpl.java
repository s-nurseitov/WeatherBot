package ITStep.Data;

import ITStep.Data.model.UsersEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;

@Repository("userDao")
@Transactional
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public UsersEntity findOne(long id) {
        UsersEntity UsersEntity = entityManager.find(UsersEntity.class, id);
        return UsersEntity;
    }

    @Override
    public List<UsersEntity> findAll() {
        return entityManager
                .createQuery("select UsersEntity from UsersEntity UsersEntity", UsersEntity.class)
                .getResultList();
    }

    @Override
    public boolean createUser(UsersEntity UsersEntity) {
        try {
            if (UsersEntity.getId() != 0) {
                entityManager.merge(UsersEntity);
            } else {
                entityManager.persist(UsersEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean updateUser(UsersEntity UsersEntity) {
        entityManager.merge(UsersEntity);
        return true;
    }

    @Override
    public boolean deleteUser(UsersEntity UsersEntity) {
        entityManager.remove(UsersEntity);
        return true;
    }
}
