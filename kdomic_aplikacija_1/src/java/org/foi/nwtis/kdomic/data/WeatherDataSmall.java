/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.kdomic.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Krunoslav
 */
public class WeatherDataSmall {

    private String adresaId;
    private String adress;
    private String latitude;
    private String longitude;
    private Float dewPoint;
    private Float humidity;
    private Float rainRate;
    private Float temperature;
    private Float windSpeed;
    private Float windDirection;
    private Float feelsLike;

    public WeatherDataSmall(String adresaId, String adress, String latitude, String longitude, Float dewPoint, Float humidity, Float rainRate, Float temperature, Float windSpeed, Float windDirection, Float feelsLike) {
        this.adresaId = adresaId;
        this.adress = adress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dewPoint = dewPoint;
        this.humidity = humidity;
        this.rainRate = rainRate;
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.feelsLike = feelsLike;
    }

    public WeatherDataSmall(ResultSet resultSet) {
        try {
            this.adresaId = resultSet.getString("idAddress");
            this.adress = resultSet.getString("address");
            this.latitude = resultSet.getString("latitude");
            this.longitude = resultSet.getString("longitude");
            this.dewPoint = resultSet.getString("dewPoint")==null ? null : Float.parseFloat(resultSet.getString("dewPoint"));
            this.humidity = resultSet.getString("humidity")==null ? null : Float.parseFloat(resultSet.getString("humidity"));
            this.rainRate = resultSet.getString("rainRate")==null ? null : Float.parseFloat(resultSet.getString("rainRate"));
            this.temperature = resultSet.getString("temperature")==null ? null : Float.parseFloat(resultSet.getString("temperature"));
            this.windSpeed = resultSet.getString("windSpeed")==null ? null : Float.parseFloat(resultSet.getString("windSpeed"));
            this.windDirection = resultSet.getString("windDirection")==null ? null : Float.parseFloat(resultSet.getString("windDirection"));
            this.feelsLike = resultSet.getString("feelsLike")==null ? null : Float.parseFloat(resultSet.getString("feelsLike"));
        } catch (SQLException ex) {
            Logger.getLogger(WeatherDataSmall.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getAdresaId() {
        return adresaId;
    }

    public void setAdresaId(String adresaId) {
        this.adresaId = adresaId;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Float getDewPoint() {
        return dewPoint;
    }

    public void setDewPoint(Float dewPoint) {
        this.dewPoint = dewPoint;
    }

    public Float getHumidity() {
        return humidity;
    }

    public void setHumidity(Float humidity) {
        this.humidity = humidity;
    }

    public Float getRainRate() {
        return rainRate;
    }

    public void setRainRate(Float rainRate) {
        this.rainRate = rainRate;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public Float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Float windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Float getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(Float windDirection) {
        this.windDirection = windDirection;
    }

    public Float getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(Float feelsLike) {
        this.feelsLike = feelsLike;
    }

}
