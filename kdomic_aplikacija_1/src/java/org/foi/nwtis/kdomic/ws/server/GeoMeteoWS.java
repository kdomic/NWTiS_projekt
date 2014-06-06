/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.kdomic.ws.server;

import java.util.ArrayList;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import org.foi.nwtis.kdomic.data.Location;
import org.foi.nwtis.kdomic.data.WeatherData;
import org.foi.nwtis.kdomic.data.WeatherDataSmall;
import org.foi.nwtis.kdomic.database.Database;
import org.foi.nwtis.kdomic.rest.client.GoogleMapsKlijent;
import org.foi.nwtis.kdomic.rest.client.WeatherBugKlijent;

/**
 *
 * @author Krunoslav
 */
@WebService(serviceName = "GeoMeteoWS")
public class GeoMeteoWS {

    /**
     * Popis svih adresa za koje se prikupljaju meteorološki podaci
     *
     * @return
     */
    @WebMethod(operationName = "getAllMeteoAddress")
    public List<Location> getAllMeteoAddress() {
        return Database.getAllAddress();
    }

    /**
     * Trenutni meteorološki podaci za izabranu adresu
     */
    @WebMethod(operationName = "getCurrentMeteoForAddreass")
    public WeatherData getCurrentMeteoForAddreass(@WebParam(name = "adressId") final String adressId) {
        if (adressId != null && adressId.length() > 0) {
            WeatherBugKlijent wbk = new WeatherBugKlijent();
            Location l = Database.getAddressById(adressId);
            WeatherData wd = wbk.getRealTimeWeather(l.getLatitude(), l.getLongitude());
            return wd;
        }
        return null;
    }

    /**
     * Rang lista (prvih n) adresa za koje je prikupljeno najviše meteoroloških
     * podataka
     */
    @WebMethod(operationName = "getAddressListWithMostData")
    public List<Location> getAddressListWithMostData(@WebParam(name = "limitNum") final Integer limitNum) {
        if (limitNum != null) {
            return Database.getAddressListWithMostData(limitNum);
        }
        return null;
    }

    /**
     * Posljednjih n meteoroloških podataka za izabranu adresu
     */
    @WebMethod(operationName = "getLastsMeteoForAdress")
    public List<WeatherData> getLastsMeteoForAdress(@WebParam(name = "addressId") final String addressId, @WebParam(name = "limitNum") final Integer limitNum) {
        return Database.getMeteoAll(addressId, limitNum);
    }

    /**
     * Meteorološki podaci za adresu u nekom vremenskom intervalu (od Date,do
     * Date)
     */
    @WebMethod(operationName = "getMeteoInDateRange")
    public List<WeatherData> getMeteoInDateRange(@WebParam(name = "addressId") final String addressId, @WebParam(name = "startDate") final String startDate, @WebParam(name = "endDate") final String endDate) {
        return Database.getMeteoAll(addressId, startDate, endDate);
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getRecentGeoMeteo")
    public List<WeatherDataSmall> getRecentGeoMeteo(@WebParam(name = "startDate") final String startDate, @WebParam(name = "endDate") final String endDate) {
        return Database.getRecentGeoMeteo(startDate, endDate);
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "getFullNameFromGoogle")
    public String getFullNameFromGoogle(@WebParam(name = "name") final String name) {
        GoogleMapsKlijent gmk = new GoogleMapsKlijent();
        Location l = gmk.getGeoLocation(name);
        return l==null ? null : l.getAdress();
    }
    
    /**
     * Web service operation
     */
    @WebMethod(operationName = "getAddresFromName")
    public Location getAddresFromName(@WebParam(name = "name") final String name) {
        return Database.getAddressByName(name);
    }
    
}
