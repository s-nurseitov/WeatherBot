import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bitpipeline.lib.owm.OwmClient;
import org.bitpipeline.lib.owm.WeatherData;
import org.bitpipeline.lib.owm.WeatherStatusResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class Weather {
    private final static String APPID = "4a8268b475e4cf751ca75d5b7f4572c9";
    private String url = "http://samples.openweathermap.org/data/2.5/weather?id=1526273&appid=4a8268b475e4cf751ca75d5b7f4572c9";

    private String city;
    private String temp;
    private String humidity;

    public void GetCurrentWeather() throws Exception {
        OwmClient owm = new OwmClient ();
        WeatherStatusResponse currentWeather = owm.currentWeatherAtCity ("Tokyo", "JP");
        if (currentWeather.hasWeatherStatus ()) {
            WeatherData weather = currentWeather.getWeatherStatus ().get (0);
            if (weather.getPrecipitation () == Integer.MIN_VALUE) {
                WeatherData.WeatherCondition weatherCondition = weather.getWeatherConditions ().get (0);
                String description = weatherCondition.getDescription ();
                if (description.contains ("rain") || description.contains ("shower"))
                    System.out.println ("No rain measures in Tokyo but reports of " + description);
                else
                    System.out.println ("No rain measures in Tokyo: " + description);
            } else
                System.out.println ("It's raining in Tokyo: " + weather.getPrecipitation () + " mm/h");
        }
//        URL obj = new URL(url);
//        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//
//        con.setRequestMethod("GET");
//
//
//        StringBuffer buffer = new StringBuffer();
//        try {
//            int responseCode = con.getResponseCode();
//            System.out.println("\nSending 'GET' request to URL : " + url);
//            System.out.println("Response Code : " + responseCode);
//
//            BufferedReader in = new BufferedReader(
//                    new InputStreamReader(con.getInputStream()));
//            String inputLine;
//
//            while ((inputLine = in.readLine()) != null) {
//                buffer.append(inputLine);
//            }
//            in.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        JsonObject jsonObject = new JsonParser().parse(buffer.toString()).getAsJsonObject();
//        JsonObject weather = jsonObject.get("weather").getAsJsonObject();
//        String temp = weather.get("name").getAsString(); //John
//        return buffer;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }
}
