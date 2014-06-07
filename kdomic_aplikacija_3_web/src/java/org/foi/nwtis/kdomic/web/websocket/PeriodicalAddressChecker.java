/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.foi.nwtis.kdomic.web.websocket;

import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.foi.nwtis.kdomic.beans.MessageQueueBridge;

/**
 *
 * @author Krunoslav
 */
public class PeriodicalAddressChecker extends Thread{
    MessageQueueBridge messageQueueBridge = lookupMessageQueueBridgeBean();

    private Boolean stop;

    @Override
    public synchronized void start() {
        stop = false;
        super.start(); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void run() {
        while(!stop){
            if(messageQueueBridge.refreshAddress()){
                System.out.println("ZOVEM SOCKET adrese");
                AdresaEndpointAddress.obavijestiPromjenu();
            }
            if(messageQueueBridge.refreshMessage()){
                System.out.println("ZOVEM SOCKET email");
                AdresaEndpointEmail.obavijestiPromjenu();
            }
            try { sleep(1 * 1000); } catch (InterruptedException ex) {}
        }
    }

    
    @Override
    public void interrupt() {
        stop = true;
        super.interrupt(); //To change body of generated methods, choose Tools | Templates.
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
