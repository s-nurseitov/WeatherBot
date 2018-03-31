package ITStep.Weather;

public interface WeatherService {
    void LoadCurrentWeather(String city) throws Exception;
    String getCity();
    String getTemp();
    String getHumidity();
    String getPressure();
    String getCountry();
}
