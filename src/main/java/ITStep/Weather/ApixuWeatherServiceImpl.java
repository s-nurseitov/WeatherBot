package ITStep.Weather;

import ITStep.HttpRequest;
import org.json.JSONObject;

public class ApixuWeatherServiceImpl implements WeatherService {
    private final static String APPID = "9f9df973e4044c82b8052818182703";
    private String url = "http://api.apixu.com/v1/current.json";

    private String city;
    private String temp;
    private String humidity;
    private String country;
    private String pressure;

    public void LoadCurrentWeather(String city) throws Exception {
        url+="?key="+APPID+"&q="+city;
        JSONObject responseBody = HttpRequest.doQuery(url);
        System.out.println(responseBody.toString());
        JSONObject current = responseBody.getJSONObject("current");
        JSONObject location = responseBody.getJSONObject("location");
        this.country = location.getString("country");
        this.city = location.getString("name");
        this.temp = current.getString("temp_c");
        this.humidity = current.getString("humidity");
        this.pressure = current.getString("pressure_mb");
    }

    public String getCity() {
        return city;
    }

    public String getTemp() {
        return temp;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getCountry() {
        return country;
    }

    public String getPressure() {
        return pressure;
    }
}
