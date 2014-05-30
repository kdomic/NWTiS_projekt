
package org.foi.nwtis.kdomic.rest.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.foi.nwtis.kdomic.data.Location;

/**
 * Klasa za dohvaćanje geolokacijskih podataka
 * @author Krunoslav
 */
public class GoogleMapsKlijent {

    Client client;

    public GoogleMapsKlijent() {
        client = ClientBuilder.newClient();
    }

    public Location getGeoLocation(String adresa) {
        WebTarget webResource = client.target(GMRESTHelper.getGM_BASE_URI()).path("maps/api/geocode/json");
        webResource = webResource.queryParam("address", adresa);
        webResource = webResource.queryParam("sensor", "false");
        
        //System.out.println("URL:::::::::::::::" + webResource.getUri());

        String odgovor = webResource.request(MediaType.APPLICATION_JSON).get(String.class);
        try {
            JSONObject jo = new JSONObject(odgovor);
            JSONObject addr = jo.getJSONArray("results").getJSONObject(0);            
            JSONObject coords = jo.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
            Location loc = new Location(coords.getString("lat"), coords.getString("lng"),addr.getString("formatted_address"));
            return loc;
        } catch (JSONException ex) {
            return null;
            //Logger.getLogger(GoogleMapsKlijent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
