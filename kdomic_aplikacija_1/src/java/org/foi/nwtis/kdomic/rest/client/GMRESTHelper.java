
package org.foi.nwtis.kdomic.rest.client;

/**
 * Klasa za autentifikaciju na Google Maps servis
 * @author Krunoslav
 */
public class GMRESTHelper {
    private static final String GM_BASE_URI = "http://maps.google.com/";    

    public GMRESTHelper() {
    }

    public static String getGM_BASE_URI() {
        return GM_BASE_URI;
    }
        
}
