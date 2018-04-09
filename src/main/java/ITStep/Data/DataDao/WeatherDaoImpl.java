package ITStep.Data.DataDao;

import ITStep.Data.model.Weather;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository("weatherDao")
@Transactional
public class WeatherDaoImpl implements WeatherDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Weather findOne(long id) {
        return entityManager.find(Weather.class, id);
    }

    @Override
    public List<Weather> findAll() {
        return entityManager
                .createQuery("select Weather from Weather weather", Weather.class)
                .getResultList();
    }

    @Override
    public boolean create(Weather entity) {
        try {
            entityManager.persist(entity);
            entityManager.flush();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Weather entity) {
        try {
            entityManager.merge(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Weather entity) {
        try {
            entityManager.remove(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Weather lastWeatherData(String city) {
        System.out.println("lastWeatherData===== " + city);
        List<Weather> list = entityManager
                .createQuery("select w from Weather w " +
                        "where w.city = :city_ " +
                        "order by w.datetime desc")
                .setParameter("city_", city).getResultList();
        if(list.size()>0){
            return list.get(0);
        }
        return null;
    }
}
