
package org.foi.nwtis.kdomic.rest.client;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Autentifikacija uz pomoću OAuth
 * @author Krunoslav
 */
@XmlRootElement(name="OAuth20")
public class Authentication {
    
    private AccessToken access_token;

    public AccessToken getAccess_token() {
        return access_token;
    }

    @XmlElement
    public void setAccess_token(AccessToken access_token) {
        this.access_token = access_token;
    }    
}
