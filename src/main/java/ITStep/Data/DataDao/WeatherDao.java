package ITStep.Data.DataDao;

import ITStep.Data.model.Weather;

public interface WeatherDao extends Dao<Weather> {
    Weather lastWeatherData(String city);
}
