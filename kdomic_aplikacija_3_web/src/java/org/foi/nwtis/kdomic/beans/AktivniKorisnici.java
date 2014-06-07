/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.kdomic.beans;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import org.foi.nwtis.kdomic.data.Interoperability;
import org.foi.nwtis.kdomic.data.Users;
import org.foi.nwtis.kdomic.rest.client.RESTAddress;
import org.foi.nwtis.kdomic.rest.client.RESTportfolios;
import org.foi.nwtis.kdomic.rest.client.RESTusers;

/**
 *
 * @author Krunoslav
 */
@ManagedBean
@ViewScoped
public class AktivniKorisnici implements Serializable  {
    @EJB
    private MessageQueueBridge messageQueueBridge;

    private List<Users> users;
    private List<Interoperability> portfolios;
    private List<Interoperability> address;

    private Users selectedUser;
    private Interoperability selectedPortfolio;
    private Interoperability selectedAddress;
    
    private String meteoData;

    public AktivniKorisnici() {
        generateUsers();
    }

    public void generateUsers() {
        RESTusers u = new RESTusers();
        users = u.getUsers();
    }

    public void generatePortfolios() {
        RESTportfolios p = new RESTportfolios();
        address = null;
        meteoData = "";
        portfolios = p.getPortfolios(selectedUser.getId());
    }

    public void generateAddress() {
        RESTAddress a = new RESTAddress();
        meteoData = "";
        address = a.getAddress(selectedPortfolio.getId());
    }
    
    public void genenerateCurrentMeteo(){
        meteoData = messageQueueBridge.getCurrentMeteo(selectedAddress.getNaziv());
    }

    public Users getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(Users selectedUser) {
        this.selectedUser = selectedUser;
    }

    public Interoperability getSelectedPortfolio() {
        return selectedPortfolio;
    }

    public void setSelectedPortfolio(Interoperability selectedPortfolio) {
        this.selectedPortfolio = selectedPortfolio;
    }

    public Interoperability getSelectedAddress() {
        return selectedAddress;
    }

    public void setSelectedAddress(Interoperability selectedAddress) {
        this.selectedAddress = selectedAddress;
    }

    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }

    public List<Interoperability> getPortfolios() {
        return portfolios;
    }

    public void setPortfolios(List<Interoperability> portfolios) {
        this.portfolios = portfolios;
    }

    public List<Interoperability> getAddress() {
        return address;
    }

    public void setAddress(List<Interoperability> address) {
        this.address = address;
    }

    public String getMeteoData() {
        return meteoData;
    }

    public void setMeteoData(String meteoData) {
        this.meteoData = meteoData;
    }

    
    
}
