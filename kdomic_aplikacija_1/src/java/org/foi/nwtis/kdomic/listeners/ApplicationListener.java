/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.kdomic.listeners;

import org.foi.nwtis.kdomic.controls.WeatherBugThread;
import java.util.Calendar;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.foi.nwtis.kdomic.konfiguracije.NemaKonfiguracije;
import org.foi.nwtis.kdomic.konfiguracije.bp.BP_Konfiguracija;
import org.foi.nwtis.kdomic.server.SocketServer;

/**
 * Web application lifecycle listener.
 *
 * @author Krunoslav
 */
public class ApplicationListener implements ServletContextListener {

    public static ServletContext context;
    public static BP_Konfiguracija bp;
    public static Boolean paused;
    public static Boolean stoped;
    public static Timer timer;

    public static long lastThreadTime;
    public static int reciecedCommandNum;
    public static int errorCommandNum;
    public static int execCommandNum;

    private SocketServer serverSocket;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("LISTENER STARTUP");
        this.paused = false;
        this.stoped = false;
        this.lastThreadTime = 0;
        this.reciecedCommandNum = 0;
        this.errorCommandNum = 0;
        this.execCommandNum = 0;
        this.context = sce.getServletContext();
        try {
            this.bp = new BP_Konfiguracija(context.getRealPath("WEB-INF") + java.io.File.separator + context.getInitParameter("DatabaseConfigFile"));
            if (this.bp == null) {
                System.out.println("------------------------ERROR: DATABASE NOT FOUND");
            }
        } catch (NemaKonfiguracije ex) {
            Logger.getLogger(ApplicationListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        Integer threadTime = Integer.parseInt(context.getInitParameter("WeatherBugThreadTime"));
        if (threadTime > 0) {
            timer = new Timer();
            Calendar date = Calendar.getInstance();
            timer.schedule(new WeatherBugThread(), date.getTime(), 1000 * threadTime);
        }
        //serverSocket = new SocketServer();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("LISTENER SHUT DOWN");
        if (timer != null) {
            timer.cancel();
        }
        //serverSocket.close();
    }

}
