/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.kdomic.beans.general;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.foi.nwtis.kdomic.beans.admin.Dnevnik;
import org.foi.nwtis.kdomic.ws.client.GeoMeteoWSClient;
import org.foi.nwtis.kdomic.ws.server.WeatherDataSmall;

/**
 *
 * @author Krunoslav
 */
@ManagedBean
@RequestScoped
public class JavniDio implements Serializable {

    private List<WeatherDataSmall> wd;
    private Date startDate;
    private Date endDate;

    /**
     * Creates a new instance of JavniDio
     */
    public JavniDio() {
    }

    @PostConstruct
    public void init() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            startDate = sdf.parse("2014-05-01 00:00:00");
        } catch (ParseException ex) {
            Logger.getLogger(Dnevnik.class.getName()).log(Level.SEVERE, null, ex);
        }
        endDate = new Date();
        getData();
    }
    
    public void getData() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("START: " + df.format(startDate));
        System.out.println("END: " + df.format(endDate));
        wd = GeoMeteoWSClient.getRecentGeoMeteo(df.format(startDate), df.format(endDate));
    }

    public List<WeatherDataSmall> getWd() {
        return wd;
    }

    public void setWd(List<WeatherDataSmall> wd) {
        this.wd = wd;
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

}
