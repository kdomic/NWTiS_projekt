
package org.foi.nwtis.kdomic.data;

/**
 * Klasa za opis lokacijskih podataka
 * @author Krunoslav
 */
public class Location {

    private String latitude;
    private String longitude;
    private String adress;
    private String adresaId;

    public Location() {
    }

    public Location(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
    
    public Location(String latitude, String longitude, String adress, String adresaId) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.adress = adress;
        this.adresaId = adresaId;
    }
    
    public Location(String latitude, String longitude, String adress) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.adress = adress;
    }
    
    public void postavi(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
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

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getAdresaId() {
        return adresaId;
    }

    public void setAdresaId(String adresaId) {
        this.adresaId = adresaId;
    }


    
}
