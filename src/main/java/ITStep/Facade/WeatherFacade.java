package ITStep.Facade;

import ITStep.Data.DataDao.WeatherDao;
import ITStep.Data.model.Weather;
import ITStep.Weather.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class WeatherFacade {
    @Autowired
    private WeatherDao weatherDao;
    @Autowired
    private WeatherService weatherService;
    private String wrongCity;

    public Weather getWeather(String city) {
        if (wrongCity != null && wrongCity.equals(city)) {
            return null;
        }
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        currentTime.setHours(currentTime.getHours() - 1);
        Weather weather = weatherDao.lastWeatherData(city);
        System.out.println("----------" + weather);
        if (weather == null || weather.getDatetime().before(currentTime)) {
            try {
                weather = weatherService.getCurrentWeather(city);
                weatherDao.create(weather);
            } catch (Exception e) {
                weather = null;
                this.wrongCity = city;
                e.printStackTrace();
            }
        }
        return weather;
    }
}