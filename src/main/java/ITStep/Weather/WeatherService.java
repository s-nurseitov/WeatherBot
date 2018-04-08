package ITStep.Weather;

import ITStep.Data.model.Weather;

public interface WeatherService {
    Weather getCurrentWeather(String city) throws Exception;
}
