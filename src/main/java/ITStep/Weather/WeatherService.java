package ITStep.Weather;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;

public class WeatherService implements IWeatherService{
    private final static String APPID = "9f9df973e4044c82b8052818182703";
    private String url = "http://api.apixu.com/v1/current.json?key=9f9df973e4044c82b8052818182703&q=";

    private String city;
    private String temp;
    private String humidity;
    private String country;
    private String pressure;
    private HttpClient httpClient;

    public WeatherService() {
        this.httpClient = new DefaultHttpClient();
    }

    public void GetCurrentWeather(String city) throws Exception {
        url+=city;
        JSONObject responseBody = doQuery();
        System.out.println(responseBody.toString());
        JSONObject current = responseBody.getJSONObject("current");
        JSONObject location = responseBody.getJSONObject("location");
        this.country = location.getString("country");
        this.city = location.getString("name");
        this.temp = current.getString("temp_c");
        this.humidity = current.getString("humidity");
        this.pressure = current.getString("pressure_mb");
        //return new String("\n"+ current.getString("temp_c"));
    }

    private JSONObject doQuery() throws JSONException, IOException {
        String responseBody = null;
        HttpGet httpget = new HttpGet(url);

        HttpResponse response = this.httpClient.execute(httpget);
        InputStream contentStream = null;

        try {
            StatusLine statusLine = response.getStatusLine();
            if (statusLine == null) {
                throw new IOException(String.format("Unable to get a response from Weather service"));
            } else {
                int statusCode = statusLine.getStatusCode();
                if (statusCode < 200 || statusCode >= 300) {
                    throw new IOException(String.format("Weather service responded with status code %d: %s", statusCode, statusLine));
                } else {
                    HttpEntity responseEntity = response.getEntity();
                    contentStream = responseEntity.getContent();
                    Reader isReader = new InputStreamReader(contentStream);
                    int contentSize = (int)responseEntity.getContentLength();
                    if (contentSize < 0) {
                        contentSize = 8192;
                    }

                    StringWriter strWriter = new StringWriter(contentSize);
                    char[] buffer = new char[8192];
                    boolean var13 = false;

                    int n;
                    while((n = isReader.read(buffer)) != -1) {
                        strWriter.write(buffer, 0, n);
                    }

                    responseBody = strWriter.toString();
                    contentStream.close();
                    return new JSONObject(responseBody);
                }
            }
        } catch (IOException var18) {
            throw var18;
        } catch (RuntimeException var19) {
            httpget.abort();
            throw var19;
        } finally {
            if (contentStream != null) {
                contentStream.close();
            }

        }
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
