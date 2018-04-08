package ITStep.Weather;

import ITStep.Data.model.Weather;
import ITStep.HttpRequest;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ApixuWeatherServiceImpl implements WeatherService {
    private final static String APPID = "9f9df973e4044c82b8052818182703";
    private String url = "http://api.apixu.com/v1/current.json";

    public Weather getCurrentWeather(String city) throws Exception {
        String resultUrl = url + "?key=" + APPID + "&q="+ city;
        JSONObject responseBody = HttpRequest.doQuery(resultUrl);
        System.out.println("getCurrentWeather===== " + responseBody.toString());
        JSONObject current = responseBody.getJSONObject("current");
        JSONObject location = responseBody.getJSONObject("location");

        Weather weather = new Weather();
        weather.setCity(location.getString("name"));
        weather.setCountry(location.getString("country"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        Date parsedDate = dateFormat.parse(current.getString("last_updated"));
        weather.setDatetime(new Timestamp(parsedDate.getTime()));
        weather.setHumidity(current.getString("humidity"));
        weather.setPressure(current.getString("pressure_mb"));
        weather.setTemp(current.getString("temp_c"));
        weather.setWind(current.getString("wind_kph"));
        weather.setWindDir(current.getString("wind_dir"));
        return weather;
    }
}
