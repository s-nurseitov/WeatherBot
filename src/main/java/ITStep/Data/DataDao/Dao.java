package ITStep.Data.DataDao;

import java.util.List;

public interface Dao<T> {

    T findOne(long id);

    List<T> findAll();

    boolean create(T entity);

    boolean update(T entity);

    boolean delete(T entity);
}
