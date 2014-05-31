/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.kdomic.listeners;

import java.util.Calendar;
import java.util.Timer;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.foi.nwtis.kdomic.controls.EmailReaderThread;

/**
 * Web application lifecycle listener.
 *
 * @author Krunoslav
 */
@WebListener
public class ApplicationListener implements ServletContextListener {

    public static ServletContext context;
    public static Timer timer;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("[APP 2] LISTENER STARTUP");
        context = sce.getServletContext();
        Integer threadTime = Integer.parseInt(context.getInitParameter("EmailReaderThreadTime"));
        if (threadTime > 0) {
            timer = new Timer();
            Calendar date = Calendar.getInstance();
            timer.schedule(new EmailReaderThread(), date.getTime(), 1000*threadTime);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("[APP 2] LISTENER SHUTDOWN");
        if (timer != null) {
            timer.cancel();
        }
    }
}
