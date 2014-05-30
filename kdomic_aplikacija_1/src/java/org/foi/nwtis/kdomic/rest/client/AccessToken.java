
package org.foi.nwtis.kdomic.rest.client;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Klasa za manipulaciju s pristupnim tokenom
 * @author Krunoslav
 */
@XmlRootElement(name = "access_token")
public class AccessToken {

    private String token;
    private String refresh_token;
    private String token_type;
    private long expires_in;

    public String getToken() {
        return token;
    }

    @XmlElement
    public void setToken(String token) {
        this.token = token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    @XmlElement
    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getToken_type() {
        return token_type;
    }

    @XmlElement
    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public long getExpires_in() {
        return expires_in;
    }

    @XmlElement
    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    }

}
