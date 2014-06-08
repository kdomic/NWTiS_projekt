/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.kdomic.beans.admin;

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
import javax.faces.bean.RequestScoped;
import org.foi.nwtis.kdomic.eb.Logs;
import org.foi.nwtis.kdomic.sb.LogsFacade;

/**
 *
 * @author Krunoslav
 */
@ManagedBean
@RequestScoped
public class Dnevnik implements Serializable{

    @EJB
    private LogsFacade logsFacade;

    private List<Logs> logs;
    private Date startDate;
    private Date endDate;

    /**
     * Creates a new instance of Dnevnik
     */
    public Dnevnik() {
    }

    @PostConstruct
    public void init() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            startDate = sdf.parse("2014-06-01 00:00:00");
        } catch (ParseException ex) {
            Logger.getLogger(Dnevnik.class.getName()).log(Level.SEVERE, null, ex);
        }
        endDate = new Date();
        getData();
    }

    public void getData() {
        logs = logsFacade.findByDataRange(startDate, endDate);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("START: " + df.format(startDate));
        System.out.println("END: " + df.format(endDate));
    }

    public List<Logs> getLogs() {
        return logs;
    }

    public void setLogs(List<Logs> logs) {
        this.logs = logs;
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
