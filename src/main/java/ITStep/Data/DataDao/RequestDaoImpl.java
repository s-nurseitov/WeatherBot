package ITStep.Data.DataDao;

import ITStep.Data.model.Request;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository("requestDao")
@Transactional
public class RequestDaoImpl implements Dao<Request> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Request findOne(long id) {
        return entityManager.find(Request.class, id);
    }

    @Override
    public List<Request> findAll() {
        return entityManager.createQuery("select Request from Request request", Request.class)
                .getResultList();
    }

    @Override
    public boolean create(Request entity) {
        try {
            entityManager.persist(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Request entity) {
        try {
            entityManager.merge(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Request entity) {
        try {
            entityManager.remove(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
