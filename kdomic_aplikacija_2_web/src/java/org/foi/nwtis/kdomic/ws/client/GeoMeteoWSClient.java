/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.foi.nwtis.kdomic.ws.client;

import org.foi.nwtis.kdomic.ws.server.Location;
import org.foi.nwtis.kdomic.ws.server.WeatherData;


/**
 *
 * @author Krunoslav
 */
public class GeoMeteoWSClient {

    public static java.util.List<org.foi.nwtis.kdomic.ws.server.Location> getAllMeteoAddress() {
        org.foi.nwtis.kdomic.ws.server.GeoMeteoWS_Service service = new org.foi.nwtis.kdomic.ws.server.GeoMeteoWS_Service();
        org.foi.nwtis.kdomic.ws.server.GeoMeteoWS port = service.getGeoMeteoWSPort();
        return port.getAllMeteoAddress();
    }

    public static String getFullNameFromGoogle(java.lang.String name) {
        org.foi.nwtis.kdomic.ws.server.GeoMeteoWS_Service service = new org.foi.nwtis.kdomic.ws.server.GeoMeteoWS_Service();
        org.foi.nwtis.kdomic.ws.server.GeoMeteoWS port = service.getGeoMeteoWSPort();
        return port.getFullNameFromGoogle(name);
    }   

    public static Location getAddresFromName(java.lang.String name) {
        org.foi.nwtis.kdomic.ws.server.GeoMeteoWS_Service service = new org.foi.nwtis.kdomic.ws.server.GeoMeteoWS_Service();
        org.foi.nwtis.kdomic.ws.server.GeoMeteoWS port = service.getGeoMeteoWSPort();
        return port.getAddresFromName(name);
    }

    public static java.util.List<org.foi.nwtis.kdomic.ws.server.WeatherData> getMeteoInDateRange(java.lang.String addressId, java.lang.String startDate, java.lang.String endDate) {
        org.foi.nwtis.kdomic.ws.server.GeoMeteoWS_Service service = new org.foi.nwtis.kdomic.ws.server.GeoMeteoWS_Service();
        org.foi.nwtis.kdomic.ws.server.GeoMeteoWS port = service.getGeoMeteoWSPort();
        return port.getMeteoInDateRange(addressId, startDate, endDate);
    }

    public static WeatherData getCurrentMeteoForAddreass(java.lang.String adressId) {
        org.foi.nwtis.kdomic.ws.server.GeoMeteoWS_Service service = new org.foi.nwtis.kdomic.ws.server.GeoMeteoWS_Service();
        org.foi.nwtis.kdomic.ws.server.GeoMeteoWS port = service.getGeoMeteoWSPort();
        return port.getCurrentMeteoForAddreass(adressId);
    }

    public static java.util.List<org.foi.nwtis.kdomic.ws.server.WeatherDataSmall> getRecentGeoMeteo(java.lang.String startDate, java.lang.String endDate) {
        org.foi.nwtis.kdomic.ws.server.GeoMeteoWS_Service service = new org.foi.nwtis.kdomic.ws.server.GeoMeteoWS_Service();
        org.foi.nwtis.kdomic.ws.server.GeoMeteoWS port = service.getGeoMeteoWSPort();
        return port.getRecentGeoMeteo(startDate, endDate);
    }

    
    
}
