
package org.foi.nwtis.kdomic.rest.client;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

/**
 * Klasa za autentifikaciju na Weather Bug servis
 * @author Krunoslav
 */
public class WBRESTHelper {
    private static final String WB_BASE_URI = "https://thepulseapi.earthnetworks.com/";    
    private String cKey;
    private String sKey;
    private Authentication autentikacija;

    public WBRESTHelper(String cKey, String sKey) {
        this.cKey = cKey;
        this.sKey = sKey;
    }

    public static String getWB_BASE_URI() {
        return WB_BASE_URI;
    }
    
    public void autenticiraj() {
        autentikacija = ClientBuilder.newClient()
                .target(WB_BASE_URI + "oauth20/token?grant_type=client_credentials&client_id=" + cKey + "&client_secret=" + sKey)
                .request(MediaType.APPLICATION_XML)
                .get(Authentication.class);
    } 
    
    // todo pripremi autentikaciju na bazi json-a

    public Authentication getAutentikacija() {
        return autentikacija;
    }

    public void setAautentikacija(Authentication autentikacija) {
        this.autentikacija = autentikacija;
    }
    
    
}
