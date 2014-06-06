/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.kdomic.data;

import java.io.Serializable;

/**
 *
 * @author Krunoslav
 */
public class CommunicationMessageAddress implements Serializable {

    private String addressName;

    public CommunicationMessageAddress(String addressName) {
        this.addressName = addressName;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

}
