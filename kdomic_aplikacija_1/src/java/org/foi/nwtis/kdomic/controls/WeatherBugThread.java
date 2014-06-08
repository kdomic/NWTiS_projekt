/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.kdomic.controls;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.foi.nwtis.kdomic.data.WeatherData;
import org.foi.nwtis.kdomic.database.Database;
import org.foi.nwtis.kdomic.konfiguracije.bp.BP_Konfiguracija;
import org.foi.nwtis.kdomic.listeners.ApplicationListener;
import org.foi.nwtis.kdomic.rest.client.WeatherBugKlijent;

/**
 *
 * @author Krunoslav
 */
public class WeatherBugThread extends TimerTask {

    @Override
    public void run() {
        System.out.println("THREAD STARTED AT: : " + new Date());

        if (ApplicationListener.stoped) {
            System.out.println("GASIM TIMER");
            ApplicationListener.timer.cancel();
            ApplicationListener.timer.purge();
            return;
        }
        if (ApplicationListener.paused) {
            System.out.println("STANJE PAUZE");
            return;
        }

        try {
            BP_Konfiguracija bp = ApplicationListener.bp;
            Class.forName(bp.getDriver_database());
            Connection connect = DriverManager.getConnection(bp.getServer_database() + bp.getUser_database(), bp.getUser_username(), bp.getUser_password());
            Statement statement = connect.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM tblAddress");
            while (resultSet.next()) {                
                try {
                    WeatherBugKlijent wbk = new WeatherBugKlijent();
                    WeatherData wd = wbk.getRealTimeWeather(resultSet.getString("latitude"), resultSet.getString("longitude"));
                    Database.insert(wd.getSql(resultSet.getString("idAddress")));
                    System.out.println(resultSet.getString("address"));
                    System.out.println("Temp: " + wd.getTemperature());
                } catch (Exception ex) {
                    Logger.getLogger(WeatherBugThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (SQLException ex) {
            System.err.println("SQLException: " + ex.getMessage());
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(WeatherBugThread.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
