package ITStep.Weather;

public interface IWeatherService {
    void GetCurrentWeather(String city) throws Exception;
    String getCity();
    String getTemp();
    String getHumidity();
    String getPressure();
    String getCountry();
}
