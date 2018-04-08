package ITStep.Data.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "WEATHER", schema = "WEATHERBOT", catalog = "TEST")
public class Weather {
    private long id;
    private Timestamp datetime;
    private String city;
    private String temp;
    private String humidity;
    private String country;
    private String pressure;
    private String wind;
    private String windDir;
    private List<Request> requests = new LinkedList<>();

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "DATETIME_")
    public Timestamp getDatetime() {
        return datetime;
    }

    public void setDatetime(Timestamp datetime) {
        this.datetime = datetime;
    }

    @Basic
    @Column(name = "CITY")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "TEMP")
    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    @Basic
    @Column(name = "HUMIDITY")
    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    @Basic
    @Column(name = "COUNTRY")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Basic
    @Column(name = "PRESSURE")
    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    @Basic
    @Column(name = "WIND")
    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    @Basic
    @Column(name = "WIND_DIR")
    public String getWindDir() {
        return windDir;
    }

    public void setWindDir(String windDir) {
        this.windDir = windDir;
    }

    @OneToMany(mappedBy = "weather", fetch = FetchType.LAZY)
    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Weather that = (Weather) o;
        return id == that.id &&
                Objects.equals(datetime, that.datetime) &&
                Objects.equals(city, that.city) &&
                Objects.equals(temp, that.temp) &&
                Objects.equals(humidity, that.humidity) &&
                Objects.equals(country, that.country) &&
                Objects.equals(pressure, that.pressure) &&
                Objects.equals(wind, that.wind) &&
                Objects.equals(windDir, that.windDir);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, datetime, city, temp, humidity, country, pressure, wind, windDir);
    }
}
