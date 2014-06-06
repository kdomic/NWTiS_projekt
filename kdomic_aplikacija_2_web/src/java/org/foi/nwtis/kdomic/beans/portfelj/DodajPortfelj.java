/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.kdomic.beans.portfelj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Message;
import javax.jms.Queue;
import org.foi.nwtis.kdomic.data.CommunicationMessageAddress;
import org.foi.nwtis.kdomic.eb.MeteoPortfolio;
import org.foi.nwtis.kdomic.eb.PortfolioAddresses;
import org.foi.nwtis.kdomic.listeners.ApplicationListener;
import org.foi.nwtis.kdomic.sb.MeteoPortfolioFacade;
import org.foi.nwtis.kdomic.sb.PortfolioAddressesFacade;
import org.foi.nwtis.kdomic.ws.client.GeoMeteoWSClient;
import org.foi.nwtis.kdomic.ws.server.Location;

/**
 *
 * @author Krunoslav
 */
@Singleton
@LocalBean
@ManagedBean
@ViewScoped
public class DodajPortfelj implements Serializable {

    @Resource(mappedName = "jms/NWTiS_kdomic_2_new")
    private Queue nWTiS_kdomic_2_new;
    @Inject
    @JMSConnectionFactory("jms/NWTiS_QF_kdomic_2_new")
    private JMSContext context;

    @EJB
    private PortfolioAddressesFacade portfolioAddressesFacade;
    @EJB
    private MeteoPortfolioFacade meteoPortfolioFacade;

    List<Location> locationsFromDatabase;
    List<String> tempLocationsFromDatabase;

    private String portfolioName;
    String newLocation;
    String selectedAvailableLocation;
    List<String> availableLocations;
    String selectedSelectedLocations;
    List<String> selectedLocations;

    public DodajPortfelj() {
        locationsFromDatabase = GeoMeteoWSClient.getAllMeteoAddress();
        tempLocationsFromDatabase = new ArrayList<>();
        availableLocations = new ArrayList<>();
        selectedLocations = new ArrayList<>();

        for (Location l : locationsFromDatabase) {
            availableLocations.add(l.getAdress());
            tempLocationsFromDatabase.add(l.getAdress());
        }
    }

    public void addAddress() {
        selectedLocations.add(selectedAvailableLocation);
        availableLocations.remove(selectedAvailableLocation);
    }

    public void removeAddress() {
        availableLocations.add(selectedSelectedLocations);
        selectedLocations.remove(selectedSelectedLocations);
    }

    public void addNewAddress() {
        if (newLocation != null && newLocation.length() > 0) {
            String temp = GeoMeteoWSClient.getFullNameFromGoogle(newLocation);
            if (temp != null) {
                if (selectedLocations.contains(temp)) {
                    newLocation = "ADRESA JE VEĆ DODANA";
                } else {
                    selectedLocations.add(temp);
                    newLocation = "";
                }
            } else {
                newLocation = "NEPOZNATA LOKACIJA";
            }
        }
    }

    public String saveForm() {
        Integer min = Integer.parseInt(ApplicationListener.context.getInitParameter("portfolioAddressNumMin"));
        Integer max = Integer.parseInt(ApplicationListener.context.getInitParameter("portfolioAddressNumMax"));
        if (portfolioName == null || portfolioName.length() == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Greška!", "Niste upisali ime portfolia"));
        } else if (selectedLocations.size() < min) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Greška!", "Odabrali ste premalo adresa"));
        } else if (selectedLocations.size() > max) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Greška!", "Odabrali ste prevoše adresa"));
        } else {
            System.out.println("INSERT: " + portfolioName);
            MeteoPortfolio mp = new MeteoPortfolio(portfolioName, Prijava.loggdeUser);
            meteoPortfolioFacade.create(mp);
            for (String s : selectedLocations) {
                if (!tempLocationsFromDatabase.contains(s)) {
                    sendJMSMessageToNWTiS_kdomic_2_new(s);
                }
                portfolioAddressesFacade.create(new PortfolioAddresses(s, mp));
            }
            return "portfolioAdded";
        }
        return null;
    }

    public String getPortfolioName() {
        return portfolioName;
    }

    public void setPortfolioName(String portfolioName) {
        this.portfolioName = portfolioName;
    }

    public String getNewLocation() {
        return newLocation;
    }

    public void setNewLocation(String newLocation) {
        this.newLocation = newLocation;
    }

    public String getSelectedAvailableLocation() {
        return selectedAvailableLocation;
    }

    public void setSelectedAvailableLocation(String selectedAvailableLocation) {
        this.selectedAvailableLocation = selectedAvailableLocation;
    }

    public List<String> getAvailableLocations() {
        return availableLocations;
    }

    public void setAvailableLocations(List<String> availableLocations) {
        this.availableLocations = availableLocations;
    }

    public String getSelectedSelectedLocations() {
        return selectedSelectedLocations;
    }

    public void setSelectedSelectedLocations(String selectedSelectedLocations) {
        this.selectedSelectedLocations = selectedSelectedLocations;
    }

    public List<String> getSelectedLocations() {
        return selectedLocations;
    }

    public void setSelectedLocations(List<String> selectedLocations) {
        this.selectedLocations = selectedLocations;
    }

    private void sendJMSMessageToNWTiS_kdomic_2_new(String messageData) {
        context.createProducer().send(nWTiS_kdomic_2_new, (Message) context.createObjectMessage(new CommunicationMessageAddress(messageData)));
    }

}
