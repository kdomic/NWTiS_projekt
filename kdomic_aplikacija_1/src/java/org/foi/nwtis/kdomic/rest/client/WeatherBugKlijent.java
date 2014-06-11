
package org.foi.nwtis.kdomic.rest.client;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.foi.nwtis.kdomic.data.WeatherData;
import org.foi.nwtis.kdomic.listeners.ApplicationListener;

/**
 * Klasa za dohvaćanje meteoroških podataka
 * @author Krunoslav
 */
public class WeatherBugKlijent {

    String customerKey;
    String secretKey;
    WBRESTHelper helper;
    Client client;

    public WeatherBugKlijent() {
        this.customerKey = ApplicationListener.context.getInitParameter("wb_ckey");
        this.secretKey = ApplicationListener.context.getInitParameter("wb_skey");
        helper = new WBRESTHelper(customerKey, secretKey);
        client = ClientBuilder.newClient();
        helper.autenticiraj();
    }

    public WeatherBugKlijent(String customerKey, String secretKey) {
        this.customerKey = customerKey;
        this.secretKey = secretKey;
        helper = new WBRESTHelper(customerKey, secretKey);
        client = ClientBuilder.newClient();
        helper.autenticiraj();
    }

    public WeatherData getRealTimeWeather(String latitude, String longitude) {
        WebTarget webResource = client.target(WBRESTHelper.getWB_BASE_URI()).path("data/observations/v1/current");
        webResource = webResource.queryParam("location", latitude + "," + longitude);
        webResource = webResource.queryParam("locationtype", "latitudelongitude");
        webResource = webResource.queryParam("cultureinfo", "en-en");
        webResource = webResource.queryParam("verbose", "true");
        webResource = webResource.queryParam("access_token", helper.getAutentikacija().getAccess_token().getToken());

        //System.out.println(webResource.getUri());

        String odgovor = webResource.request(MediaType.APPLICATION_JSON).get(String.class);
        try {
            JSONObject jo = new JSONObject(odgovor);
            WeatherData wd = new WeatherData(jo);
            return wd;
        } catch (JSONException ex) {
            Logger.getLogger(WeatherBugKlijent.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
