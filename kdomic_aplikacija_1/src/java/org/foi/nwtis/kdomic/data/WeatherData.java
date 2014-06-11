
package org.foi.nwtis.kdomic.data;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jettison.json.JSONObject;
import org.foi.nwtis.kdomic.rest.client.WeatherBugKlijent;

/**
 * Struktura (MODEL) podataka za opis meteoroloških podataka
 * @author Krunoslav
 */
public class WeatherData {

    private String key;
    private String stationId;
    private int providerId;
    private Date observationTimeLocalStr;
    private Date observationTimeUtcStr;
    //private String observationTimeLocalStr;
    //private String observationTimeUtcStr;
    private String iconCode;
    private Float altimeter;
    private Float altimeterRate;
    private Float dewPoint;
    private Float dewPointRate;
    private Float heatIndex;
    private Float humidity;
    private Float humidityRate;
    private Float pressureSeaLevel;
    private Float pressureSeaLevelRate;
    private Float rainDaily;
    private Float rainRate;
    private Float rainMonthly;
    private Float rainYearly;
    private Float snowDaily;
    private Float snowRate;
    private Float snowMonthly;
    private Float snowYearly;
    private Float temperature;
    private Float temperatureRate;
    private Float visibility;
    private Float visibilityRate;
    private Float windChill;
    private Float windSpeed;
    private Float windDirection;
    private Float windSpeedAvg;
    private Float windDirectionAvg;
    private Float windGustHourly;
    //private String windGustTimeLocalHourlyStr;
    //private String windGustTimeUtcHourlyStr;
    private Date windGustTimeLocalHourlyStr;
    private Date windGustTimeUtcHourlyStr;
    private Float windGustDirectionHourly;
    private Float windGustDaily;
    //private String windGustTimeLocalDailyStr;
    //private String windGustTimeUtcDailyStr;
    private Date windGustTimeLocalDailyStr;
    private Date windGustTimeUtcDailyStr;
    private Float windGustDirectionDaily;
    //private String observationTimeAdjustedLocalStr;
    private Date observationTimeAdjustedLocalStr;
    private Float feelsLike;

    public WeatherData() {
    }

    public WeatherData(JSONObject jo) {
        for (Field field : this.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                String type = field.getType().toString();
                String key1 = field.getName().toString();
                String val = jo.getString(key1);
                if (val.compareTo("null") == 0) {
                    continue;
                } else if (type.equals("class java.lang.String")) {
                    field.set(this, (String) jo.getString(key1));
                } else if (type.equals("int")) {
                    field.setInt(this, (int) jo.getInt(key1));
                } else if (type.equals("class java.lang.Float")) {
                    Float floVal = ((Double) jo.getDouble(key1)).floatValue();
                    field.set(this, (Float) floVal);
                } else if (type.equals("class java.util.Date")) {
                    Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(val);
                    field.set(this, (Date) date);
                }
            } catch (Exception ex) {
                Logger.getLogger(WeatherBugKlijent.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public WeatherData(ResultSet resultSet) throws SQLException {        
        for (Field field : this.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                String type = field.getType().toString();
                String key = field.getName().toString();
                String val = resultSet.getString(key);
                if (val==null || val.compareTo("null") == 0) {
                    continue;
                } else if (type.equals("class java.lang.String")) {
                    field.set(this, (String) resultSet.getString(key));
                } else if (type.equals("int")) {
                    field.setInt(this, (int) resultSet.getInt(key));
                } else if (type.equals("class java.lang.Float")) {
                    Float floVal = ((Double) resultSet.getDouble(key)).floatValue();
                    field.set(this, (Float) floVal);
                } else if (type.equals("class java.util.Date")) {
                    Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").parse(val);
                    field.set(this, (Date) date);
                }
            } catch (Exception ex) {
                Logger.getLogger(WeatherBugKlijent.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    
    public WeatherData(String key, String stationId, int providerId, String iconCode, Float altimeter, Float altimeterRate, Float dewPoint, Float dewPointRate, Float heatIndex, Float humidity, Float humidityRate, Float pressureSeaLevel, Float pressureSeaLevelRate, Float rainDaily, Float rainRate, Float rainMonthly, Float rainYearly, Float snowDaily, Float snowRate, Float snowMonthly, Float snowYearly, Float temperature, Float temperatureRate, Float visibility, Float visibilityRate, Float windChill, Float windSpeed, Float windDirection, Float windSpeedAvg, Float windDirectionAvg, Float windGustHourly, Float windGustDirectionHourly, Float windGustDaily, Float windGustDirectionDaily, Float feelsLike, Date observationTimeLocalStr, Date observationTimeUtcStr, Date windGustTimeLocalHourlyStr, Date windGustTimeUtcHourlyStr, Date windGustTimeLocalDailyStr, Date windGustTimeUtcDailyStr, Date observationTimeAdjustedLocalStr) {
        this.key = key;
        this.stationId = stationId;
        this.providerId = providerId;
        this.iconCode = iconCode;
        this.altimeter = altimeter;
        this.altimeterRate = altimeterRate;
        this.dewPoint = dewPoint;
        this.dewPointRate = dewPointRate;
        this.heatIndex = heatIndex;
        this.humidity = humidity;
        this.humidityRate = humidityRate;
        this.pressureSeaLevel = pressureSeaLevel;
        this.pressureSeaLevelRate = pressureSeaLevelRate;
        this.rainDaily = rainDaily;
        this.rainRate = rainRate;
        this.rainMonthly = rainMonthly;
        this.rainYearly = rainYearly;
        this.snowDaily = snowDaily;
        this.snowRate = snowRate;
        this.snowMonthly = snowMonthly;
        this.snowYearly = snowYearly;
        this.temperature = temperature;
        this.temperatureRate = temperatureRate;
        this.visibility = visibility;
        this.visibilityRate = visibilityRate;
        this.windChill = windChill;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.windSpeedAvg = windSpeedAvg;
        this.windDirectionAvg = windDirectionAvg;
        this.windGustHourly = windGustHourly;
        this.windGustDirectionHourly = windGustDirectionHourly;
        this.windGustDaily = windGustDaily;
        this.windGustDirectionDaily = windGustDirectionDaily;
        this.feelsLike = feelsLike;
        this.observationTimeLocalStr = observationTimeLocalStr;
        this.observationTimeUtcStr = observationTimeUtcStr;
        this.windGustTimeLocalHourlyStr = windGustTimeLocalHourlyStr;
        this.windGustTimeUtcHourlyStr = windGustTimeUtcHourlyStr;
        this.windGustTimeLocalDailyStr = windGustTimeLocalDailyStr;
        this.windGustTimeUtcDailyStr = windGustTimeUtcDailyStr;
        this.observationTimeAdjustedLocalStr = observationTimeAdjustedLocalStr;
    }

    public String getSql(String id) {
        Date date = new Date();
        String insertSQL = "INSERT INTO tblMeteodata (`adresa`, `vrijemePreuzimanja`";
        String subSQL = "VALUES (" + id + ", \'" + new Timestamp(date.getTime()) + "\'";
        for (Field field : this.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                String type = field.getType().toString();
                String key = field.getName().toString();
                insertSQL += ", `" + field.getName() + "`";
                if (type.equals("class java.lang.String")) {
                    String val = (String) field.get(this);
                    if (val == null) {
                        subSQL += ", null";
                    } else {
                        subSQL += ", \'" + field.get(this) + "\'";
                    }
                } else if (type.equals("int")) {
                    Integer val = (Integer) field.get(this);
                    if (val == null) {
                        subSQL += ", null";
                    } else {
                        subSQL += ", " + field.get(this) + "";
                    }
                } else if (type.equals("class java.lang.Float")) {
                    Float val = (Float) field.get(this);
                    if (val == null) {
                        subSQL += ", null";
                    } else {
                        subSQL += ", " + field.get(this) + "";
                    }
                } else if (type.equals("class java.util.Date")) {

                    Date val = (Date) field.get(this);
                    if (val == null) {
                        subSQL += ", null";
                    } else {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        subSQL += ", \'" + dateFormat.format(field.get(this)) + "\'";
                        field.set(this, (Date) date);
                    }
                } else {
                    subSQL += ", \'" + field.get(this) + "\'";
                }
            } catch (Exception ex) {
                Logger.getLogger(WeatherData.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        insertSQL += ") " + subSQL + ")";
        return insertSQL;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    public String getIconCode() {
        return iconCode;
    }

    public void setIconCode(String iconCode) {
        this.iconCode = iconCode;
    }

    public Float getAltimeter() {
        return altimeter;
    }

    public void setAltimeter(Float altimeter) {
        this.altimeter = altimeter;
    }

    public Float getAltimeterRate() {
        return altimeterRate;
    }

    public void setAltimeterRate(Float altimeterRate) {
        this.altimeterRate = altimeterRate;
    }

    public Float getDewPoint() {
        return dewPoint;
    }

    public void setDewPoint(Float dewPoint) {
        this.dewPoint = dewPoint;
    }

    public Float getDewPointRate() {
        return dewPointRate;
    }

    public void setDewPointRate(Float dewPointRate) {
        this.dewPointRate = dewPointRate;
    }

    public Float getHeatIndex() {
        return heatIndex;
    }

    public void setHeatIndex(Float heatIndex) {
        this.heatIndex = heatIndex;
    }

    public Float getHumidity() {
        return humidity;
    }

    public void setHumidity(Float humidity) {
        this.humidity = humidity;
    }

    public Float getHumidityRate() {
        return humidityRate;
    }

    public void setHumidityRate(Float humidityRate) {
        this.humidityRate = humidityRate;
    }

    public Float getPressureSeaLevel() {
        return pressureSeaLevel;
    }

    public void setPressureSeaLevel(Float pressureSeaLevel) {
        this.pressureSeaLevel = pressureSeaLevel;
    }

    public Float getPressureSeaLevelRate() {
        return pressureSeaLevelRate;
    }

    public void setPressureSeaLevelRate(Float pressureSeaLevelRate) {
        this.pressureSeaLevelRate = pressureSeaLevelRate;
    }

    public Float getRainDaily() {
        return rainDaily;
    }

    public void setRainDaily(Float rainDaily) {
        this.rainDaily = rainDaily;
    }

    public Float getRainRate() {
        return rainRate;
    }

    public void setRainRate(Float rainRate) {
        this.rainRate = rainRate;
    }

    public Float getRainMonthly() {
        return rainMonthly;
    }

    public void setRainMonthly(Float rainMonthly) {
        this.rainMonthly = rainMonthly;
    }

    public Float getRainYearly() {
        return rainYearly;
    }

    public void setRainYearly(Float rainYearly) {
        this.rainYearly = rainYearly;
    }

    public Float getSnowDaily() {
        return snowDaily;
    }

    public void setSnowDaily(Float snowDaily) {
        this.snowDaily = snowDaily;
    }

    public Float getSnowRate() {
        return snowRate;
    }

    public void setSnowRate(Float snowRate) {
        this.snowRate = snowRate;
    }

    public Float getSnowMonthly() {
        return snowMonthly;
    }

    public void setSnowMonthly(Float snowMonthly) {
        this.snowMonthly = snowMonthly;
    }

    public Float getSnowYearly() {
        return snowYearly;
    }

    public void setSnowYearly(Float snowYearly) {
        this.snowYearly = snowYearly;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public Float getTemperatureRate() {
        return temperatureRate;
    }

    public void setTemperatureRate(Float temperatureRate) {
        this.temperatureRate = temperatureRate;
    }

    public Float getVisibility() {
        return visibility;
    }

    public void setVisibility(Float visibility) {
        this.visibility = visibility;
    }

    public Float getVisibilityRate() {
        return visibilityRate;
    }

    public void setVisibilityRate(Float visibilityRate) {
        this.visibilityRate = visibilityRate;
    }

    public Float getWindChill() {
        return windChill;
    }

    public void setWindChill(Float windChill) {
        this.windChill = windChill;
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

    public Float getWindSpeedAvg() {
        return windSpeedAvg;
    }

    public void setWindSpeedAvg(Float windSpeedAvg) {
        this.windSpeedAvg = windSpeedAvg;
    }

    public Float getWindDirectionAvg() {
        return windDirectionAvg;
    }

    public void setWindDirectionAvg(Float windDirectionAvg) {
        this.windDirectionAvg = windDirectionAvg;
    }

    public Float getWindGustHourly() {
        return windGustHourly;
    }

    public void setWindGustHourly(Float windGustHourly) {
        this.windGustHourly = windGustHourly;
    }

    public Float getWindGustDirectionHourly() {
        return windGustDirectionHourly;
    }

    public void setWindGustDirectionHourly(Float windGustDirectionHourly) {
        this.windGustDirectionHourly = windGustDirectionHourly;
    }

    public Float getWindGustDaily() {
        return windGustDaily;
    }

    public void setWindGustDaily(Float windGustDaily) {
        this.windGustDaily = windGustDaily;
    }

    public Float getWindGustDirectionDaily() {
        return windGustDirectionDaily;
    }

    public void setWindGustDirectionDaily(Float windGustDirectionDaily) {
        this.windGustDirectionDaily = windGustDirectionDaily;
    }

    public Float getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(Float feelsLike) {
        this.feelsLike = feelsLike;
    }

    public Date getObservationTimeLocalStr() {
        return observationTimeLocalStr;
    }

    public void setObservationTimeLocalStr(Date observationTimeLocalStr) {
        this.observationTimeLocalStr = observationTimeLocalStr;
    }

    public Date getObservationTimeUtcStr() {
        return observationTimeUtcStr;
    }

    public void setObservationTimeUtcStr(Date observationTimeUtcStr) {
        this.observationTimeUtcStr = observationTimeUtcStr;
    }

    public Date getWindGustTimeLocalHourlyStr() {
        return windGustTimeLocalHourlyStr;
    }

    public void setWindGustTimeLocalHourlyStr(Date windGustTimeLocalHourlyStr) {
        this.windGustTimeLocalHourlyStr = windGustTimeLocalHourlyStr;
    }

    public Date getWindGustTimeUtcHourlyStr() {
        return windGustTimeUtcHourlyStr;
    }

    public void setWindGustTimeUtcHourlyStr(Date windGustTimeUtcHourlyStr) {
        this.windGustTimeUtcHourlyStr = windGustTimeUtcHourlyStr;
    }

    public Date getWindGustTimeLocalDailyStr() {
        return windGustTimeLocalDailyStr;
    }

    public void setWindGustTimeLocalDailyStr(Date windGustTimeLocalDailyStr) {
        this.windGustTimeLocalDailyStr = windGustTimeLocalDailyStr;
    }

    public Date getWindGustTimeUtcDailyStr() {
        return windGustTimeUtcDailyStr;
    }

    public void setWindGustTimeUtcDailyStr(Date windGustTimeUtcDailyStr) {
        this.windGustTimeUtcDailyStr = windGustTimeUtcDailyStr;
    }

    public Date getObservationTimeAdjustedLocalStr() {
        return observationTimeAdjustedLocalStr;
    }

    public void setObservationTimeAdjustedLocalStr(Date observationTimeAdjustedLocalStr) {
        this.observationTimeAdjustedLocalStr = observationTimeAdjustedLocalStr;
    }

}
