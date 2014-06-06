/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.kdomic.beans.portfelj;

import java.awt.Event;
import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;
import org.foi.nwtis.kdomic.eb.MeteoPortfolio;
import org.foi.nwtis.kdomic.eb.PortfolioAddresses;
import org.foi.nwtis.kdomic.sb.MeteoPortfolioFacade;
import org.foi.nwtis.kdomic.sb.PortfolioAddressesFacade;
import org.foi.nwtis.kdomic.ws.client.GeoMeteoWSClient;
import org.foi.nwtis.kdomic.ws.server.Location;
import org.foi.nwtis.kdomic.ws.server.WeatherData;

/**
 *
 * @author Krunoslav
 */
@ManagedBean
@ViewScoped
public class Index implements Serializable {

    @EJB
    private PortfolioAddressesFacade portfolioAddressesFacade;

    @EJB
    private MeteoPortfolioFacade meteoPortfolioFacade;

    private List<MeteoPortfolio> portfolioList;
    private MeteoPortfolio selectedPortfolio;

    private List<PortfolioAddresses> addressList;
    private PortfolioAddresses selectedAddress;

    private List<WeatherData> wdList;
    private WeatherData selectedWd;

    private Date startDate;
    private Date endDate;
    
    private String currentTemp;

    /**
     * Creates a new instance of Index
     */
    public Index() {
    }

    @PostConstruct
    public void init() {
        portfolioList = meteoPortfolioFacade.findByUser(Prijava.loggdeUser);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            startDate = sdf.parse("2014-05-01 00:00:00");
        } catch (ParseException ex) {
            Logger.getLogger(Index.class.getName()).log(Level.SEVERE, null, ex);
        }
        endDate = new Date();
    }

    public void generateAddressList() {
        addressList = portfolioAddressesFacade.findByPortfolio(selectedPortfolio);
    }

    public void generateTempList() {
        Location l = GeoMeteoWSClient.getAddresFromName(selectedAddress.getAddress());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("START: " + df.format(startDate));
        System.out.println("END: " + df.format(endDate));

        wdList = GeoMeteoWSClient.getMeteoInDateRange(l.getAdresaId(), df.format(startDate), df.format(endDate));
        currentTemp = GeoMeteoWSClient.getCurrentMeteoForAddreass(l.getAdresaId()).getTemperature().toString();
    }


    public List<MeteoPortfolio> getPortfolioList() {
        return portfolioList;
    }

    public void setPortfolioList(List<MeteoPortfolio> portfolioList) {
        this.portfolioList = portfolioList;
    }

    public MeteoPortfolio getSelectedPortfolio() {
        return selectedPortfolio;
    }

    public void setSelectedPortfolio(MeteoPortfolio selectedPortfolio) {
        this.selectedPortfolio = selectedPortfolio;
    }

    public List<PortfolioAddresses> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<PortfolioAddresses> addressList) {
        this.addressList = addressList;
    }

    public PortfolioAddresses getSelectedAddress() {
        return selectedAddress;
    }

    public void setSelectedAddress(PortfolioAddresses selectedAddress) {
        this.selectedAddress = selectedAddress;
    }

    public List<WeatherData> getWdList() {
        return wdList;
    }

    public void setWdList(List<WeatherData> wdList) {
        this.wdList = wdList;
    }

    public WeatherData getSelectedWd() {
        return selectedWd;
    }

    public void setSelectedWd(WeatherData selectedWd) {
        this.selectedWd = selectedWd;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getCurrentTemp() {
        return currentTemp;
    }

    public void setCurrentTemp(String currentTemp) {
        this.currentTemp = currentTemp;
    }

}
