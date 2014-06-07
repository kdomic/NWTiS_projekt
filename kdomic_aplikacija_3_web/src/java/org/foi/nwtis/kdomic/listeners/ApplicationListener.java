/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.foi.nwtis.kdomic.listeners;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.foi.nwtis.kdomic.beans.MessageQueueBridge;
import org.foi.nwtis.kdomic.web.websocket.PeriodicalAddressChecker;

/**
 * Web application lifecycle listener.
 *
 * @author Krunoslav
 */
public class ApplicationListener implements ServletContextListener {

    MessageQueueBridge messageQueueBridge = lookupMessageQueueBridgeBean();
    PeriodicalAddressChecker checker;
    
    @Override

    public void contextInitialized(ServletContextEvent sce) {
        String adminUsername = sce.getServletContext().getInitParameter("adminUsername");
        String adminPassword = sce.getServletContext().getInitParameter("adminPassword");
        String host = sce.getServletContext().getInitParameter("host");
        Integer port = Integer.parseInt(sce.getServletContext().getInitParameter("port"));
        Integer numberOfAttempts = Integer.parseInt(sce.getServletContext().getInitParameter("numberOfAttempts"));
        Integer pauseTime = Integer.parseInt(sce.getServletContext().getInitParameter("pauseTime"));
        String fileNameAddress = sce.getServletContext().getRealPath("WEB-INF") + java.io.File.separator + sce.getServletContext().getInitParameter("fileNameAddress");
        String fileNameEmail = sce.getServletContext().getRealPath("WEB-INF") + java.io.File.separator + sce.getServletContext().getInitParameter("fileNameEmail");
        messageQueueBridge.init(adminUsername, adminPassword, host, port, numberOfAttempts, pauseTime, fileNameAddress, fileNameEmail);

        checker = new PeriodicalAddressChecker();
        checker.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        checker.interrupt();
        messageQueueBridge.runSerialization();
    }

    private MessageQueueBridge lookupMessageQueueBridgeBean() {
        try {
            Context c = new InitialContext();
            return (MessageQueueBridge) c.lookup("java:global/kdomic_aplikacija_3/kdomic_aplikacija_3_ejb/MessageQueueBridge!org.foi.nwtis.kdomic.beans.MessageQueueBridge");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
